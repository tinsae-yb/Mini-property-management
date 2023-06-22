package com.example.minipropertymanagement.repo;

import com.example.minipropertymanagement.domain.Property;
import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.enums.PropertyType;
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


    public Page<Property> searchProperties(BigDecimal minPrice, BigDecimal maxPrice, Integer bedRooms, Integer bathRooms, String zipCode, String city, String state, PropertyType propertyType, Pageable pageable, Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> query = cb.createQuery(Property.class);
        Root<Property> root = query.from(Property.class);

        // Create predicates for the main query
        List<Predicate> predicates = buildPredicates(cb, root, minPrice, maxPrice, bedRooms, bathRooms, zipCode, city, state,propertyType, userId);

        query.where(predicates.toArray(new Predicate[0]));

        // Count query for total results
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<Property> countRoot = countQuery.from(Property.class);

        // Create predicates for the count query
        List<Predicate> countPredicates = buildPredicates(countBuilder, countRoot, minPrice, maxPrice, bedRooms, bathRooms, zipCode, city, state,propertyType, userId);

        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));
        Long totalResults = entityManager.createQuery(countQuery).getSingleResult();

        // Pagination
        query.orderBy(cb.desc(root.get("createdDate"))); // Order by property ID (adjust as needed)

        TypedQuery<Property> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Property> properties = typedQuery.getResultList();

        return new PageImpl<>(properties, pageable, totalResults);
    }


    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<Property> root, BigDecimal minPrice, BigDecimal maxPrice, Integer bedRooms, Integer bathRooms, String zipCode, String city, String state, PropertyType propertyType, Long userId) {
        List<Predicate> predicates = new ArrayList<>();

        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }

        // Maximum price
        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        // Bedrooms
        if (bedRooms != null) {
            predicates.add(cb.equal(root.get("numberOfBedrooms"), bedRooms));
        }

        // Bathrooms
        if (bathRooms != null) {
            predicates.add(cb.equal(root.get("numberOfBathrooms"), bathRooms));
        }

        // Zip code
        if (zipCode != null && !zipCode.isEmpty()) {
            predicates.add(cb.equal(root.get("address").get("zipCode"), zipCode));
        }

        // City
        if (city != null && !city.isEmpty()) {
            predicates.add(cb.equal(root.get("address").get("city"), city));
        }


        // State
        if (state != null && !state.isEmpty()) {
            predicates.add(cb.equal(root.get("address").get("state"), state));
        }

        // Property Type
        if (propertyType != null) {
            predicates.add(cb.equal(root.get("propertyType"), propertyType));
        }

        // User Id
        if (userId != null) {
            predicates.add(cb.equal(root.get("owner").get("id"), userId));
        }

        return predicates;

        // State
    }
}