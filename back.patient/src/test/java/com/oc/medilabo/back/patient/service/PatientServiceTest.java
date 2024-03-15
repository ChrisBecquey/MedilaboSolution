package com.oc.medilabo.back.patient.service;

import com.oc.medilabo.back.patient.domain.Patient;
import com.oc.medilabo.back.patient.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;
    @InjectMocks
    private PatientService patientService;
    @Test
    void getAllPatients() {
        List<Patient> patients = Arrays.asList(
                new Patient(), new Patient(), new Patient(), new Patient(), new Patient()
        );
        when(patientRepository.findAll()).thenReturn(patients);
        List<Patient> result = patientService.getAllPatients();
        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    void getPatientById() {
        Long patientId = 1L;
        Patient patient
    }

    @Test
    void updatePatientAdresse() {
    }
}