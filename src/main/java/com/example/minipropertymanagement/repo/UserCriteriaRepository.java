package com.example.minipropertymanagement.repo;

import com.example.minipropertymanagement.domain.Property;
import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.enums.AccountStatus;
import com.example.minipropertymanagement.enums.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserCriteriaRepository {


    private final EntityManager entityManager;


    public Page<User> findAllByRoleAccountStatusPageable(Pageable pageable, AccountStatus status, Role role) {


        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        List<Predicate> predicates = buildPredicates(cb, root, status, role);
        for (Predicate predicate : predicates) {
            query.where(predicate);
        }


        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);

        List<Predicate> countPredicates = buildPredicates(countBuilder, countRoot, status, role);
        for (Predicate predicate : countPredicates) {
            countQuery.where(predicate);
        }
        countQuery.select(countBuilder.count(countRoot));
        Long totalResults = entityManager.createQuery(countQuery).getSingleResult();

//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<User> cq = cb.createQuery(User.class);
//        Root<User> root = cq.from(User.class);
//        List<Predicate> predicates = new ArrayList<>();
//        if (status != null) predicates.add(cb.equal(root.get("accountStatus"), status));
//        if (role != null) predicates.add(cb.equal(root.get("role"), role));
//
//        Predicate[] finalPredicates = new Predicate[predicates.size()];
//        predicates.toArray(finalPredicates);
//        cq.where(finalPredicates);
//        cq.orderBy(cb.asc(root.get("id")));
//
//
//        Query countQuery = entityManager.createQuery("select count(u) from User u where u.role = :role and u.accountStatus = :status");
//        countQuery.setParameter("role", role);
//        countQuery.setParameter("status", status);
//
//        Long totalRecords = (Long) countQuery.getSingleResult();
//
//
//        TypedQuery<User> query = entityManager.createQuery(cq);
//        query.setFirstResult((int) pageable.getOffset());
//        query.setMaxResults(pageable.getPageSize());
//        List<User> users = query.getResultList();


        query.orderBy(cb.desc(root.get("id")));

        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<User> users = typedQuery.getResultList();
        return new PageImpl<>(users, pageable, totalResults);

    }

    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<User> root, AccountStatus status, Role role) {
        List<Predicate> predicates = new ArrayList<>();
        if (status != null) predicates.add(cb.equal(root.get("accountStatus"), status));
        if (role != null) predicates.add(cb.equal(root.get("role"), role));
        return predicates;
    }
}
