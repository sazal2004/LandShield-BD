package com.landshield.repository;

import com.landshield.model.BaseOwnership;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BaseOwnershipRepository extends JpaRepository<BaseOwnership, Long> {
    Optional<BaseOwnership> findByPropertyIdAndOwnerNameIgnoreCase(Long propertyId, String ownerName);
}
