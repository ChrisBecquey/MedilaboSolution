package com.oc.medilabo.back.patient.repository;

import com.oc.medilabo.back.patient.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
