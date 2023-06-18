package com.example.minipropertymanagement.repo;

import com.example.minipropertymanagement.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findByPropertyIdAndCustomerId(Long propertyId, Long customerId);

    List<Offer> findByPropertyId(Long propertyId);

}
