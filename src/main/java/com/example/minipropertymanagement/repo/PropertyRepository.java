package com.example.minipropertymanagement.repo;

import com.example.minipropertymanagement.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long>{
}
