package com.example.minipropertymanagement.repo;

import com.example.minipropertymanagement.domain.Property;
import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.domain.enums.AccountStatus;
import com.example.minipropertymanagement.domain.enums.PropertyType;
import com.example.minipropertymanagement.domain.enums.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PropertyCriteriaRepository {


    private final EntityManager entityManager;


    public Page<Property> searchProperties(BigDecimal minPrice, BigDecimal maxPrice, Integer bedRooms, Integer bathRooms, String zipCode, String city, String state, Pageable pageable) {


        System.out.println("minPrice = " + minPrice);

        String whereClause = " where ";
        if (minPrice != null) whereClause += " p.price >  " + minPrice;
        if (maxPrice != null) whereClause += " p.price <  " + maxPrice;
        if (bedRooms != null) whereClause += " p.numberOfBedrooms = " + bedRooms;
        if (bathRooms != null) whereClause += " p.numberOfBathrooms =  " + bathRooms;
        if (zipCode != null) whereClause += " p.address.zip =  " + zipCode;
        if (city != null) whereClause += " p.address.city like " + "%" + city + "%";
        if (state != null) whereClause += " p.address.state =  " + state;


        if (whereClause.equals(" where ")) whereClause = "";

        Query countQuery = entityManager.createQuery("select count(p) from Property p " + whereClause);
        Query selectQuery = entityManager.createQuery("select p from Property p " + whereClause);


        Long totalRecords = (Long) countQuery.getSingleResult();

        selectQuery.setFirstResult((int) pageable.getOffset());
        selectQuery.setMaxResults(pageable.getPageSize());
        List<Property> properties = selectQuery.getResultList();

        return new PageImpl<>(properties, pageable, totalRecords);

    }
}
