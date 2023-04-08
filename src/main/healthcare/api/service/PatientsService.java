package main.healthcare.api.service;

import main.healthcare.api.dto.PatientCreateDTO;
import main.healthcare.api.dto.PatientUpdateDTO;
import main.healthcare.api.exception.UnavailableDoctors;
import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.*;
import main.healthcare.api.repository.*;
import main.healthcare.api.security.JwtFilter;
import main.healthcare.api.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientsService {

	static Logger logger = LoggerFactory.getLogger(PatientsService.class);

	@Autowired
	private PatientsRepository patientsRepository;

	@Autowired
	private DoctorsRepository doctorsRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UsersService usersService;

	@Autowired
	private AnalysisRepository analysisRepository;


	public List<Patients> getAllPatients() {
		return patientsRepository.findAll();
	}

	public Patients registerPatient(PatientCreateDTO patientCreateDTO) throws ValidateDataException {
		Patients patient = patientsRepository.findByEmail(patientCreateDTO.getEmail());
		if (patient != null) {
			logger.info("Patient with this email address already exists.");
			throw new ValidateDataException("Account already exists with this email address!");
		}

		patient = patientsRepository.findByUsername(patientCreateDTO.getUsername());
		if (patient != null) {
			logger.info("Patient with this username already exists.");
			throw new ValidateDataException("Username is already taken! Please choose another username!");
		}

		Patients newPatient = new Patients();
		if (checkCreateValidation(patientCreateDTO)) {
			newPatient.setName(patientCreateDTO.getName());
			newPatient.setLastname(patientCreateDTO.getLastname());
			newPatient.setEmail(patientCreateDTO.getEmail());
			newPatient.setAddress(patientCreateDTO.getAddress());
			newPatient.setCity(patientCreateDTO.getCity());
			newPatient.setCountry(patientCreateDTO.getCountry());
			newPatient.setPhoneNumber(patientCreateDTO.getPhoneNumber());
			newPatient.setBirthday(patientCreateDTO.getBirthday());
			newPatient.setUsername(patientCreateDTO.getUsername());
			newPatient.setRole("PATIENT");

			Doctors doctor = findDoctorForNewPatient();
			if (doctor != null) {
				newPatient.setDoctorId(doctor.getDoctorId());
				newPatient.setFamilyDoctor(doctor);
			}
			// a message in case that all doctors has max number of patients
			logger.info("New patient is created successfully!");
			usersService.registerUser(patientCreateDTO.getUsername(), patientCreateDTO.getPassword(), "PATIENT");
			return patientsRepository.save(newPatient);
		} else {
			logger.info("ERROR! Please check if patients data are correct!");
			throw new ValidateDataException("Account is not created. Check if data is correct!");
		}
	}

	public String getDoctorInfo() {
		Patients patient = patientsRepository.findByPatientId(JwtFilter.id);
		Doctors doctor = patient.getFamilyDoctor();
		Date todayDate = new Date();
		if (doctor != null) {
			String doctorInfo = "Doctor: " + doctor.getName() + " " + doctor.getLastname() + "\n";
			doctorInfo += "Phone number: " + doctor.getPhoneNumber() + "\n";
			doctorInfo += "Email: " + doctor.getEmail() + "\n";

			boolean available = false;
			if (doctor.getStartVacDate() != null && doctor.getEndVacDate() != null) {
				if (todayDate.compareTo(doctor.getStartVacDate()) < 0 && todayDate.compareTo(doctor.getEndVacDate()) > 0) {
					available = true;
					doctorInfo += "Available: YES" + "\n";
				} else {
					doctorInfo += "Available: NO" + "     " + "Returns: " + doctor.getEndVacDate();
				}
			} else {
				doctorInfo += "Available: YES" + "\n";
			}
			return doctorInfo;
		}

		return "You don't have a family doctor. Please make a request to find an available doctor!";
	}

	public String findFamilyDoctor() {
		Doctors doctor = findDoctorForNewPatient();
		Patients patient = patientsRepository.findByPatientId(JwtFilter.id);
		if (doctor != null) {
			patient.setFamilyDoctor(doctor);
			patient.setDoctorId(doctor.getDoctorId());
			patientsRepository.save(patient);
			return "Your new family doctor is " + doctor.getName() + " " + doctor.getLastname() + ".";
		} else {
			return "Now we can't find an available doctor. Please try later again!";
		}
	}
	public Patients updatePatientData(PatientUpdateDTO patientUpdateDTO) throws ValidateDataException {
		Patients patient = patientsRepository.findByPatientId(JwtFilter.id);
		String oldUsername = patient.getUsername();

		if (checkValidation(patientUpdateDTO)) {
			patient.setName(patientUpdateDTO.getName());
			patient.setLastname(patientUpdateDTO.getLastname());
			patient.setEmail(patientUpdateDTO.getEmail());
			patient.setAddress(patientUpdateDTO.getAddress());
			patient.setCity(patientUpdateDTO.getCity());
			patient.setCountry(patientUpdateDTO.getCountry());
			patient.setPhoneNumber(patientUpdateDTO.getPhoneNumber());
			patient.setUsername(patientUpdateDTO.getUsername());
			logger.info("Patient data are updated successfully!");
			usersService.updateUser(oldUsername, patientUpdateDTO.getUsername(), patientUpdateDTO.getPassword(), "PATIENT");
			return patientsRepository.save(patient);
		} else {
			logger.info("ERROR! Please check if introduced data are correct!");
			throw new ValidateDataException("Patient data are not updated!");
		}
	}


	private Doctors findDoctorForNewPatient() {
		List<Doctors> allDoctors = doctorsRepository.findAll();
		for (Doctors doctor : allDoctors) {
			if (doctor.getNrPatients() < Constant.NR_MAX_PATIENTS) {
				doctor.setNrPatients(doctor.getNrPatients() + 1);
				doctorsRepository.save(doctor);
				return doctor;
			}
		}
		return null;
	}
	private boolean checkValidation(PatientUpdateDTO patientUpdateDTO) {
		if (Objects.equals(patientUpdateDTO.getName(), "")) {
			return false;
		} else if (Objects.equals(patientUpdateDTO.getLastname(), "")) {
			return false;
		} else if (Objects.equals(patientUpdateDTO.getAddress(), "")) {
			return false;
		} else if (Objects.equals(patientUpdateDTO.getCity(), "")) {
			return false;
		} else if (Objects.equals(patientUpdateDTO.getCountry(), "")) {
			return false;
		} else if (Objects.equals(patientUpdateDTO.getPhoneNumber(), "")) {
			return false;
		} else if (Objects.equals(patientUpdateDTO.getUsername(), "")) {
			return false;
		} else if (Objects.equals(patientUpdateDTO.getPassword(), "")) {
			return false;
		} else return !Objects.equals(patientUpdateDTO.getEmail(), "");
	}

	private boolean checkCreateValidation(PatientCreateDTO patientCreateDTO) {
		if (Objects.equals(patientCreateDTO.getName(), "")) {
			return false;
		} else if (Objects.equals(patientCreateDTO.getLastname(), "")) {
			return false;
		} else if (Objects.equals(patientCreateDTO.getAddress(), "")) {
			return false;
		} else if (Objects.equals(patientCreateDTO.getBirthday(), "")) {
			return false;
		} else if (Objects.equals(patientCreateDTO.getCity(), "")) {
			return false;
		} else if (Objects.equals(patientCreateDTO.getCountry(), "")) {
			return false;
		} else if (Objects.equals(patientCreateDTO.getPhoneNumber(), "")) {
			return false;
		} else if (Objects.equals(patientCreateDTO.getUsername(), "")) {
			return false;
		} else if (Objects.equals(patientCreateDTO.getPassword(), "")) {
			return false;
		} else return !Objects.equals(patientCreateDTO.getEmail(), "");
	}

	public void deleteMyAccount() {
		Patients patient = patientsRepository.findByPatientId(JwtFilter.id);
		Users user = userRepository.findByUsername(patient.getUsername());
		userRepository.delete(user);
		Doctors doctor = patient.getFamilyDoctor();
		if (doctor != null) {
			doctor.setNrPatients(doctor.getNrPatients() - 1);
		}
		patientsRepository.delete(patient);
	}
}
