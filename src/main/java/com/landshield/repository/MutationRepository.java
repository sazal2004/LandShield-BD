package com.landshield.repository;

import com.landshield.model.MutationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MutationRepository extends JpaRepository<MutationRecord, Long> {
    Optional<MutationRecord> findByPropertyId(Long propertyId);
}
