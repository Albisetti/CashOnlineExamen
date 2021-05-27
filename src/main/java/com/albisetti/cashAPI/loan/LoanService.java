package com.albisetti.cashAPI.loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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