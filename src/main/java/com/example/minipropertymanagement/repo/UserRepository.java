package com.example.minipropertymanagement.repo;

import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.domain.enums.AccountStatus;
import com.example.minipropertymanagement.domain.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Page<User> findAllByRole (Role role, Pageable pageable);
    Page<User> findAllByRoleAndAccountStatus (Role role, AccountStatus status, Pageable pageable);
}
