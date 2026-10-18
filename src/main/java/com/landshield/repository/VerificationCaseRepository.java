package com.landshield.repository;

import com.landshield.model.VerificationCase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VerificationCaseRepository extends JpaRepository<VerificationCase, Long> {
    List<VerificationCase> findAllByOrderByIdDesc();
}
