package com.albisetti.cashAPI.controller;

import com.albisetti.cashAPI.model.User;
import com.albisetti.cashAPI.repository.UserRepository;
import com.albisetti.cashAPI.service.UserService;
import com.albisetti.cashAPI.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public List<User> list() {
        return userService.listAllUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id) {
        try {
            User user = userService.getUser(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User userRequest, @PathVariable Integer id) {
        return userRepository.findById(id).map(user -> {
            user.setEmail(userRequest.getEmail());
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            userRepository.save(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException("userId " + id + " not found"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Integer id) {
        return userRepository.findById(id).map(user -> {
            userService.deleteUser(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException("userId " + id + " not found"));
    }
}