package com.oc.medilabo.back.patient.service;

import com.oc.medilabo.back.patient.domain.Genre;
import com.oc.medilabo.back.patient.domain.Patient;
import com.oc.medilabo.back.patient.exceptions.PatientNotFoundException;
import com.oc.medilabo.back.patient.repository.PatientRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        Patient patient = new Patient();
        patient.setId(patientId);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        Optional<Patient> result = patientService.getPatientById(patientId);

        assertTrue(result.isPresent());
        assertEquals(patientId, result.get().getId());
    }

    @Nested
    class UpdatePatientAdresse {
        @Test
        void updatePatientAdresse() throws PatientNotFoundException {
            Long patientId = 1L;
            String newAdresse = "2 rue du fort";
            Patient patient = new Patient();
            patient.setId(patientId);

            when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
            when(patientRepository.save(any())).thenReturn(patient);

            Patient result = patientService.updatePatientAdresse(patientId, newAdresse);

            assertNotNull(result);
            assertEquals(newAdresse, result.getAdressePostale());
        }

        @Test
        void updatePatientAdesseNotFound() {
            Long patientId = 1L;
            String newAdresse = "2 rue du fort";

            when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

            assertThrows(PatientNotFoundException.class, () -> patientService.updatePatientAdresse(patientId, newAdresse));
        }
    }

     @Nested
     class UpdatePatientNumero {
         @Test
         void testUpdatePatientNumero() throws PatientNotFoundException {
             Patient testPatient = new Patient();
             testPatient.setId(1L);
             testPatient.setNumeroTelephone("123456789");

             when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
             when(patientRepository.save(any())).thenReturn(testPatient);

             Patient updatedPatient = patientService.updatePatientNumero(1L, "987654321");

             verify(patientRepository, times(1)).findById(1L);
             verify(patientRepository, times(1)).save(testPatient);

             assertEquals("987654321", updatedPatient.getNumeroTelephone());
         }


         @Test
         void testUpdatePatientNumeroPatientNotFound() {
             Long id = 100L;

             when(patientRepository.findById(id)).thenReturn(Optional.empty());

             assertThrows(PatientNotFoundException.class, () -> {
                 patientService.updatePatientNumero(id, "1122334455");
             });

             verify(patientRepository, times(1)).findById(id);

             verify(patientRepository, never()).save(any());
         }
     }


    @Test
    void testAddNewPatient() {
        String prenom = "John";
        String nom = "Doe";
        Date dateNaissance = Date.valueOf("1990-01-01");
        Genre genre = Genre.M;
        String adressePostale = "2 Main Street";
        String numeroTelephone = "1122334455";

        Patient expectedPatient = new Patient();
        expectedPatient.setPrenom(prenom);
        expectedPatient.setNom(nom);
        expectedPatient.setDateNaissance(dateNaissance);
        expectedPatient.setGenre(genre);
        expectedPatient.setAdressePostale(adressePostale);
        expectedPatient.setNumeroTelephone(numeroTelephone);

        when(patientRepository.save(any())).thenReturn(expectedPatient);

        Patient newPatient = patientService.addNewPatient(prenom, nom, dateNaissance, genre, adressePostale, numeroTelephone);

        verify(patientRepository, times(1)).save(any());

        assertNotNull(newPatient);
        assertEquals(expectedPatient.getPrenom(), newPatient.getPrenom());
        assertEquals(expectedPatient.getNom(), newPatient.getNom());
        assertEquals(expectedPatient.getDateNaissance(), newPatient.getDateNaissance());
        assertEquals(expectedPatient.getGenre(), newPatient.getGenre());
        assertEquals(expectedPatient.getAdressePostale(), newPatient.getAdressePostale());
        assertEquals(expectedPatient.getNumeroTelephone(), newPatient.getNumeroTelephone());
    }
}