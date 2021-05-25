package com.albisetti.cashAPI.controller;

import com.albisetti.cashAPI.exception.ResourceNotFoundException;
import com.albisetti.cashAPI.model.Loan;
import com.albisetti.cashAPI.repository.UserRepository;
import com.albisetti.cashAPI.repository.LoanRepository;
import com.albisetti.cashAPI.service.LoanService;
import com.albisetti.cashAPI.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("")
public class LoanController {
    @Autowired
    LoanService loanService;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/loans")
    public Page<Loan> getAllLoans(Pageable pageable) {
        return loanRepository.findAll(pageable);
    }

    @GetMapping("/loans&user_id={userId}")
    public Page<Loan> getAllLoansByUserId(@PathVariable (value = "userId") Integer userId,
                                                Pageable pageable) {
        return loanRepository.findByUserId(userId, pageable);
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<Loan> get(@PathVariable Integer id) {
        try {
            Loan loan = loanService.getLoan(id);
            return new ResponseEntity<Loan>(loan, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Loan>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/loans&user_id={userId}")
    public ResponseEntity<Loan> createLoan(@PathVariable (value = "userId") int userId, @RequestBody Loan loan) {
        return userRepository.findById(userId).map(user -> {
            loan.setUser(user);
            loanRepository.save(loan);
            return new ResponseEntity<Loan>(loan, HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
    }
    @PutMapping("/loans/{id}")
    public ResponseEntity<Loan> update(@RequestBody Loan loanRequest, @PathVariable Integer id) {
        return loanRepository.findById(id).map(loan -> {
            loan.setTotal(loanRequest.getTotal());
            loanRepository.save(loan);
            return new ResponseEntity<Loan>(loan, HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException("loanID " + id + " not found"));

    }
    @DeleteMapping("/loans/{id}")
    public ResponseEntity<Loan> delete(@PathVariable Integer id) {
        return loanRepository.findById(id).map(loan -> {
            loanService.deleteLoan(id);
            return new ResponseEntity<Loan>(loan, HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException("loanID " + id + " not found"));
    }
}