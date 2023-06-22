package com.example.minipropertymanagement.repo;

import com.example.minipropertymanagement.enums.OfferStatus;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OfferCustomerRepository {

    private final EntityManager entityManager;

    public     void updateOffersForAPropertyOtherThanGivenOffer(Long offerId, long propertyId, OfferStatus offerStatus){
        entityManager.createQuery("UPDATE Offer SET offerStatus = :offerStatus WHERE id <> :offerId AND property.id = :propertyId")
                .setParameter("offerId", offerId)
                .setParameter("propertyId", propertyId)
                .setParameter("offerStatus", offerStatus)
                .executeUpdate();

    }

}
