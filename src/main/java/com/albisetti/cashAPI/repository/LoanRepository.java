package com.albisetti.cashAPI.repository;

import com.albisetti.cashAPI.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LoanRepository extends JpaRepository<Loan, Integer>{
    Page<Loan> findAll(Pageable pageable);
    Page<Loan> findByUserId(Integer userId, Pageable pageable);
}