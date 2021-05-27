package com.albisetti.cashAPI.loan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Integer>{
    Page<Loan> findAll(Pageable pageable);
    Page<Loan> findByUserId(Integer userId, Pageable pageable);
}