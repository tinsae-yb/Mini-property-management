package com.example.minipropertymanagement.repo;

import com.example.minipropertymanagement.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByPropertyIdAndUserId(Long propertyId, long id);
}
