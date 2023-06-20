package com.example.minipropertymanagement.repo;

import com.example.minipropertymanagement.domain.Property;
import com.example.minipropertymanagement.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface PropertyRepository extends JpaRepository<Property, Long> {


    @Query("select p from Property p where p.price >= :minPrice and p.price <= :maxPrice and p.numberOfBedrooms = :bedRooms and p.numberOfBathrooms = :bathRooms and p.address.zip = :zipCode and p.address.city like %:city% and p.address.state = :state and p.owner.id = :ownerId")
    Page<Property> searchProperties(Pageable pageable, BigDecimal minPrice, BigDecimal maxPrice, Integer bedRooms, Integer bathRooms, String zipCode, String city, String state, Long ownerId);
}
