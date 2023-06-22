package com.example.minipropertymanagement.repo;

import com.example.minipropertymanagement.domain.Offer;
import com.example.minipropertymanagement.enums.OfferStatus;
import io.micrometer.common.KeyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findByPropertyIdAndCustomerId(Long propertyId, Long customerId);

    List<Offer> findByPropertyId(Long propertyId);

    List<Offer> findAllByCustomerIdOrderByCreatedDateDesc(long id);




    @Query("select o from Offer o join o.property p where p.owner.id = :id order by o.createdDate desc")
    List<Offer>  findAllByPropertyOwnerId(long id);

    List<Offer> findAllByPropertyIdAndOfferStatus(long id, OfferStatus offerStatus);



}
