package com.landshield.repository;

import com.landshield.model.DeedRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DeedRepository extends JpaRepository<DeedRecord, Long> {
    Optional<DeedRecord> findByPropertyId(Long propertyId);
}
