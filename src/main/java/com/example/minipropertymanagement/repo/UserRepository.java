package com.example.minipropertymanagement.repo;

import com.example.minipropertymanagement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
