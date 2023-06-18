package com.example.minipropertymanagement.repo;

import com.example.minipropertymanagement.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
