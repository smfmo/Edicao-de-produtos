package com.samuel.demo.repository;

import com.samuel.demo.model.UserAdm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAdmRepository extends JpaRepository<UserAdm, Long> {
    Optional<UserAdm> findByUsername(String username);
}
