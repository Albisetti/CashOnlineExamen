package com.albisetti.cashAPI.repository;

import com.albisetti.cashAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
}