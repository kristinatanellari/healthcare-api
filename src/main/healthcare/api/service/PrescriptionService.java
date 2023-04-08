package main.healthcare.api.service;

import main.healthcare.api.dto.PrescriptionCreateDTO;
import main.healthcare.api.dto.ProcessPrescriptionDTO;
import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.Doctors;
import main.healthcare.api.model.Patients;
import main.healthcare.api.model.Prescription;
import main.healthcare.api.repository.DoctorsRepository;
import main.healthcare.api.repository.PatientsRepository;
import main.healthcare.api.repository.PrescriptionRepository;
import main.healthcare.api.security.JwtFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PrescriptionService {
    static Logger logger = LoggerFactory.getLogger(PatientsService.class);

    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private PatientsRepository patientsRepository;

    @Autowired
    private DoctorsRepository doctorsRepository;

    public Prescription createPrescription(PrescriptionCreateDTO prescriptionCreateDTO) {
        Prescription prescription = new Prescription();
        prescription.setPatientId(prescriptionCreateDTO.getPatientId());
        prescription.setDoctorId(prescriptionCreateDTO.getDoctorId());
        prescription.setDate(prescriptionCreateDTO.getDate());
        prescription.setContent(prescriptionCreateDTO.getContent());

        return prescriptionRepository.save(prescription);
    }

    public String getPrescription(Long patientId, Date date) {
        List<Prescription> prescription = prescriptionRepository.findByPatientIdAndDate(patientId, date);
        StringBuilder response = new StringBuilder();
        if (prescription.size() >= 1) {
            for (Prescription p : prescription) {
                response.append("Prescription: ").append(p.getPrescriptionId()).append("\nPatient: ").append(p.getPatient().getName()).append(" ").append(p.getPatient().getLastname()).append("\nDoctor: ").append(p.getDoctor().getName() + " " + p.getDoctor().getLastname()).append("\nContent: ").append(p.getContent());

                if (p.getProcessed() == null && p.getPharmacistName() == null) {
                    response.append("\nProcessed: ").append("NO");
                    response.append("\nPharmacist: ").append("-");
                } else {
                    response.append("\nProcessed: ").append("YES");
                    response.append("\nPharmacist: ").append(p.getPharmacistName());
                }

            }
        } else {
            return "No prescription found for patient with id " + patientId + "!";
        }
       return response.toString();
    }

    public String executePrescription(ProcessPrescriptionDTO processPrescriptionDTO, Long prescriptionId) throws ValidateDataException {
        Prescription prescription = prescriptionRepository.findByPrescriptionId(prescriptionId);
        if (prescription == null) {
            throw new ValidateDataException("Prescription with id " + prescriptionId + " is not found. Please try again with right id!");
        }
        prescription.setProcessed(processPrescriptionDTO.getProcessed());
        prescription.setPharmacistName(processPrescriptionDTO.getPharmacistName());
        prescriptionRepository.save(prescription);
        return "Prescription is processed successfully!";
    }

    public List<Prescription> getMyPrescriptionsP() throws ValidateDataException {
        Patients patient = patientsRepository.findByPatientId(JwtFilter.id);
        if (patient == null) {
            throw new ValidateDataException("Patient with id " + JwtFilter.id + " doesn't exists or there are not prescriptions!");
        }
        return patient.getPrescriptions();
    }

    public List<Prescription> getMyPrescriptionsD() throws ValidateDataException {
        Doctors doctor = doctorsRepository.findByDoctorId(JwtFilter.id);
        if (doctor == null) {
            throw new ValidateDataException("Doctor with id " + JwtFilter.id + " doesn't exists or there are not prescriptions!");
        }
        return doctor.getPrescriptions();
    }

}
