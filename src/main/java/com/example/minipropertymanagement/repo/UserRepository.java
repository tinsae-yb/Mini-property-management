package com.example.minipropertymanagement.repo;

import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.enums.AccountStatus;
import com.example.minipropertymanagement.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);


    @Query("select u from User u where u.role = :role and u.accountStatus = :accountStatus")
    Page<User> findAllByRoleAccountStatusPageable(Pageable pageable, AccountStatus accountStatus, Role role);

}
