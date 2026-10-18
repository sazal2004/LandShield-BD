package com.landshield.repository;

import com.landshield.model.PropertyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<PropertyRecord, Long> {
    Optional<PropertyRecord> findByKhatianNoIgnoreCaseAndPlotNoIgnoreCase(String khatianNo, String plotNo);
}
