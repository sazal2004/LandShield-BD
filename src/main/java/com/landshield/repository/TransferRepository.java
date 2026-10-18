package com.landshield.repository;

import com.landshield.model.TransferRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TransferRepository extends JpaRepository<TransferRecord, Long> {
    List<TransferRecord> findByPropertyId(Long propertyId);
    List<TransferRecord> findByDemoGeneratedTrue();

    /** Bulk delete of every runtime (manually completed) demo transfer; seed rows have demo_generated = FALSE. */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from TransferRecord t where t.demoGenerated = true")
    int deleteAllDemoGenerated();
}
