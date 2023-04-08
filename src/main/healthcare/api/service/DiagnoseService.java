package main.healthcare.api.service;

import main.healthcare.api.dto.DiagnoseDTO;
import main.healthcare.api.exception.NotFoundData;
import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.Diagnose;
import main.healthcare.api.model.Doctors;
import main.healthcare.api.model.Patients;
import main.healthcare.api.repository.DiagnoseRepository;
import main.healthcare.api.repository.DoctorsRepository;
import main.healthcare.api.repository.PatientsRepository;
import main.healthcare.api.security.JwtFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagnoseService {

    static Logger logger = LoggerFactory.getLogger(PatientsService.class);

    @Autowired
    private DiagnoseRepository diagnoseRepository;
    @Autowired
    private PatientsRepository patientsRepository;
    @Autowired
    private DoctorsRepository doctorsRepository;

    public Diagnose setDiagnose(DiagnoseDTO diagnoseDTO) {
        Doctors doctor = doctorsRepository.findByDoctorId(JwtFilter.id);
        Diagnose diagnose = new Diagnose();
        diagnose.setPatientId(diagnoseDTO.getPatientId());
        diagnose.setDoctorId(doctor.getDoctorId());
        diagnose.setDiagnose(diagnoseDTO.getDiagnose());
        diagnose.setDate(diagnoseDTO.getDate());
        return diagnoseRepository.save(diagnose);
    }

    public List<Diagnose> getMyDiagnosis() throws ValidateDataException {
        Patients patient = patientsRepository.findByPatientId(JwtFilter.id);
        if (patient == null) {
            throw new ValidateDataException("Id of patient is not correct!");
        } else {
            return patient.getDiagnoses();
        }
    }

    public List<Diagnose> getMyPatientDiagnosis(Long patientId) throws NotFoundData {
        List<Diagnose> diagnosis = diagnoseRepository.findByPatientIdAndDoctorId(patientId, JwtFilter.id);
        if (diagnosis.size() == 0) {
            throw new NotFoundData("Cannot find a diagnose with this patient and this doctor");
        }
        return diagnosis;
    }
}
