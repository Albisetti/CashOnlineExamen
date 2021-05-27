package com.albisetti.cashAPI.loan;

import com.albisetti.cashAPI.exception.ResourceNotFoundException;
import com.albisetti.cashAPI.user.UserRepository;
import com.albisetti.cashAPI.user.UserService;

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
    public Page<Loan> getAllLoansByUserId(@RequestParam (value = "user_id", required = false) Integer user_id,
                                                Pageable pageable) {
        if(user_id != null){
            return loanRepository.findByUserId(user_id, pageable);
        }
        return loanRepository.findAll(pageable);
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
    @PostMapping("/loans")
    public ResponseEntity<Loan> createLoan(@RequestParam (value = "user_id", required = true) int user_id, @RequestBody Loan loan) {
        return userRepository.findById(user_id).map(user -> {
            loan.setUser(user);
            loanRepository.save(loan);
            return new ResponseEntity<Loan>(loan, HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + user_id + " not found"));
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