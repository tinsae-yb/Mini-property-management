package com.example.minipropertymanagement.repo;

import com.example.minipropertymanagement.domain.Offer;
import io.micrometer.common.KeyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findByPropertyIdAndCustomerId(Long propertyId, Long customerId);

    List<Offer> findByPropertyId(Long propertyId);

    List<Offer> findAllByCustomerId(long id);


    @Query("select o from Offer o join o.property p where p.owner.id = :id")
    List<Offer>  findAllByPropertyOwnerId(long id);
}
