package com.oc.medilabo.back.patient.service;

import com.oc.medilabo.back.patient.domain.Genre;
import com.oc.medilabo.back.patient.domain.Patient;
import com.oc.medilabo.back.patient.exceptions.PatientNotFoundException;
import com.oc.medilabo.back.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient updatePatientAdresse(Long id, String nouvelleAdresse) throws PatientNotFoundException {
        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if(optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            patient.setAdressePostale(nouvelleAdresse);
            return patientRepository.save(patient);
        } else {
            throw new PatientNotFoundException("Patient non trouvé avec l'ID : " + id);
        }
    }

    public Patient updatePatientNumero(Long id, String nouveauNumero) throws PatientNotFoundException {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            patient.setNumeroTelephone(nouveauNumero);
            return patientRepository.save(patient);
        } else {
            throw new PatientNotFoundException("Patient non trouvé avec l'ID : " + id);
        }
    }

    public Patient addNewPatient(String prenom, String nom, java.sql.Date dateNaissance, Genre genre,
                                 String adressePostale, String numeroTelephone) {
        Patient newPatient = new Patient();
        newPatient.setPrenom(prenom);
        newPatient.setNom(nom);
        newPatient.setDateNaissance(dateNaissance);
        newPatient.setGenre(genre);
        newPatient.setAdressePostale(adressePostale);
        newPatient.setNumeroTelephone(numeroTelephone);

        return patientRepository.save(newPatient);
    }

}
