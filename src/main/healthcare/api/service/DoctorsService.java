package main.healthcare.api.service;

import main.healthcare.api.dto.AppointmentUpdateDTO;
import main.healthcare.api.dto.DoctorCreateDTO;
import main.healthcare.api.dto.DoctorUpdateDTO;
import main.healthcare.api.exception.UnavailableDoctors;
import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.*;
import main.healthcare.api.repository.AppointmentRepository;
import main.healthcare.api.repository.DoctorsRepository;
import main.healthcare.api.repository.PatientsRepository;
import main.healthcare.api.repository.UserRepository;
import main.healthcare.api.security.JwtFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class DoctorsService {
    static Logger logger = LoggerFactory.getLogger(DoctorsService.class);

    @Autowired
    private DoctorsRepository doctorsRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private PatientsRepository patientsRepository;


    public List<Doctors> getAllDoctors() {
        return doctorsRepository.findAll();
    }

    public List<Patients> getAllMyPatients() {
        Doctors doctor = doctorsRepository.findByDoctorId(JwtFilter.id);
        return doctor.getPatientsList();
    }


    public Doctors registerDoctor(DoctorCreateDTO doctorCreateDTO) throws ValidateDataException {
        Doctors doctor = doctorsRepository.findByEmail(doctorCreateDTO.getEmail());
        if (doctor != null) {
            logger.info("Doctor with this email address already exists.");
            throw new ValidateDataException("Already account exists with this email address!");

        }
        Doctors newDoctor = new Doctors();
        if (checkCreateValidation(doctorCreateDTO)) {
            newDoctor.setName(doctorCreateDTO.getName());
            newDoctor.setLastname(doctorCreateDTO.getLastname());
            newDoctor.setEmail(doctorCreateDTO.getEmail());
            newDoctor.setPhoneNumber(doctorCreateDTO.getPhoneNumber());
            newDoctor.setBirthday(doctorCreateDTO.getBirthday());
            newDoctor.setSpecialization(doctorCreateDTO.getSpecialization());
            newDoctor.setUsername(doctorCreateDTO.getUsername());
            newDoctor.setNrPatients(0);
            newDoctor.setRole("DOCTOR");
            logger.info("New doctor is created successfully!");
            usersService.registerUser(doctorCreateDTO.getUsername(), doctorCreateDTO.getPassword(), "DOCTOR");
            return doctorsRepository.save(newDoctor);
        } else {
            logger.info("ERROR! Please check if introduced data are correct!");
            throw new ValidateDataException("Account is not created. Check if data is correct!");
        }
    }

    public Doctors updateDoctorData(DoctorUpdateDTO doctorUpdateDTO) throws ValidateDataException {
        Doctors doctor = doctorsRepository.findByDoctorId(JwtFilter.id);
        String oldUsername = doctor.getUsername();

        if (checkValidation(doctorUpdateDTO)) {
            doctor.setName(doctorUpdateDTO.getName());
            doctor.setLastname(doctorUpdateDTO.getLastname());
            doctor.setEmail(doctorUpdateDTO.getEmail());
            doctor.setSpecialization(doctorUpdateDTO.getSpecialization());
            doctor.setPhoneNumber(doctorUpdateDTO.getPhoneNumber());
            doctor.setUsername(doctorUpdateDTO.getUsername());
            if (doctorUpdateDTO.getStartVacDate() != null && doctorUpdateDTO.getEndVacDate() != null) {
                doctor.setStartVacDate(doctorUpdateDTO.getStartVacDate());
                doctor.setEndVacDate(doctorUpdateDTO.getEndVacDate());
            }
            logger.info("Doctor data are updated successfully!");
            usersService.updateUser(oldUsername, doctorUpdateDTO.getUsername(), doctorUpdateDTO.getPassword(), "DOCTOR");
            return doctorsRepository.save(doctor);
        } else {
            logger.info("ERROR! Please check if introduced data are correct!");
            throw new ValidateDataException("Doctor data are not updated!");
        }
    }

    public String updateVacationDates(DoctorUpdateDTO doctorUpdateDTO) throws ValidateDataException {
        Doctors doctor = doctorsRepository.findByDoctorId(JwtFilter.id);
        if (doctorUpdateDTO.getStartVacDate() != null && doctorUpdateDTO.getEndVacDate() != null) {
            doctor.setStartVacDate(doctorUpdateDTO.getStartVacDate());
            doctor.setEndVacDate(doctorUpdateDTO.getEndVacDate());
            doctorsRepository.save(doctor);
            return "Vacation dates are updated successfully";
        } else {
            logger.info("Please check if introduced data are correct!");
            throw new ValidateDataException("Dates can't be null! Please check dates again!");
        }
    }

    private boolean checkValidation(DoctorUpdateDTO doctorUpdateDTO) {
        if (Objects.equals(doctorUpdateDTO.getName(), "")) {
            return false;
        } else if (Objects.equals(doctorUpdateDTO.getLastname(), "")) {
            return false;
        } else if (Objects.equals(doctorUpdateDTO.getEmail(), "")) {
            return false;
        } else if (Objects.equals(doctorUpdateDTO.getPhoneNumber(), "")) {
            return false;
        } else if (Objects.equals(doctorUpdateDTO.getUsername(), "")) {
            return false;
        } else if (Objects.equals(doctorUpdateDTO.getPassword(), "")) {
            return false;
        } else return !Objects.equals(doctorUpdateDTO.getSpecialization(), "");
    }

    private boolean checkCreateValidation(DoctorCreateDTO doctorCreateDTO) {
        if (Objects.equals(doctorCreateDTO.getName(), "")) {
            return false;
        } else if (Objects.equals(doctorCreateDTO.getLastname(), "")) {
            return false;
        }  else if (Objects.equals(doctorCreateDTO.getBirthday(), "")) {
            return false;
        } else if (Objects.equals(doctorCreateDTO.getUsername(), "")) {
            return false;
        } else if (Objects.equals(doctorCreateDTO.getPassword(), "")) {
            return false;
        } else return !Objects.equals(doctorCreateDTO.getEmail(), "");
    }

    public List<Doctors> findAllDoctorsBySpecialization(String specialization) throws UnavailableDoctors {
        List<Doctors> doctors = doctorsRepository.findBySpecialization(specialization);
        List<Doctors> availableDoctors = new ArrayList<>();
        Date todayDate = new Date();

        for (Doctors doctor: doctors) {
            if (todayDate.compareTo(doctor.getStartVacDate()) > 0 && todayDate.compareTo(doctor.getEndVacDate()) < 0) {
                continue;
            }
            availableDoctors.add(doctor);
        }
        if (availableDoctors.size() > 0) {
            return availableDoctors;
        } else {
            logger.info("At this moment are not available doctors!");
            throw new UnavailableDoctors("There are not available doctors at this moment. Please try later!");
        }
    }

    public void deleteMyAccount() {
        Doctors doctor = doctorsRepository.findByDoctorId(JwtFilter.id);
        Users user = userRepository.findByUsername(doctor.getUsername());
        List<Patients> patients = doctor.getPatientsList();
        for (Patients p : patients) {
            p.setDoctorId(null);
            patientsRepository.save(p);
        }
        userRepository.delete(user);
        doctorsRepository.delete(doctor);
    }
}
