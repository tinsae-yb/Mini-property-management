package com.example.minipropertymanagement.repo;

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

        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        List<Predicate> predicates = new ArrayList<>();
        if (status != null) predicates.add(cb.equal(root.get("accountStatus"), status));
        if (role != null) predicates.add(cb.equal(root.get("role"), role));

        Predicate[] finalPredicates = new Predicate[predicates.size()];
        predicates.toArray(finalPredicates);
        cq.where(finalPredicates);
        cq.orderBy(cb.asc(root.get("id")));


        Query countQuery = entityManager.createQuery("select count(u) from User u where u.role = :role and u.accountStatus = :status");
        countQuery.setParameter("role", role);
        countQuery.setParameter("status", status);

        Long totalRecords = (Long) countQuery.getSingleResult();


        TypedQuery<User> query = entityManager.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<User> users = query.getResultList();

        return new PageImpl<>(users, pageable, totalRecords);

    }
}
