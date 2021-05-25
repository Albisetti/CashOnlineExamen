package com.albisetti.cashAPI.service;

import com.albisetti.cashAPI.model.Loan;
import com.albisetti.cashAPI.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }

    public Loan getLoan(Integer id) {
        return loanRepository.findById(id).get();
    }

    public void deleteLoan(Integer id) {
        loanRepository.deleteById(id);
    }
}