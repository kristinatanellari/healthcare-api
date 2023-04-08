package main.healthcare.api.controller;

import io.swagger.annotations.ApiOperation;
import main.healthcare.api.dto.AppointmentCreateDTO;
import main.healthcare.api.dto.PatientCreateDTO;
import main.healthcare.api.dto.PatientUpdateDTO;
import main.healthcare.api.exception.UnavailableDoctors;
import main.healthcare.api.model.*;
import main.healthcare.api.service.AppointmentService;
import main.healthcare.api.service.PatientsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import main.healthcare.api.exception.ValidateDataException;

import java.util.List;

@RestController
public class PatientsController {

	static Logger logger = LoggerFactory.getLogger(PatientsController.class);
	@Autowired
	private PatientsService patientsService;
	@Autowired
	private AppointmentService appointmentService;

	@ApiOperation(value = "Register a new patient")
	@PostMapping("/patient/register")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Patients> createNewPatient(@RequestBody PatientCreateDTO patientCreateDTO) throws ValidateDataException {
		logger.info("Adding new patient in database...");
		return new ResponseEntity<>(patientsService.registerPatient(patientCreateDTO), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('DOCTOR')")
	@GetMapping("/get/allPatients")
	public List<Patients> getAllPatients() {
		return patientsService.getAllPatients();
	}

	@PreAuthorize("hasAuthority('PATIENT')")
	@GetMapping("/get/doctorInfo")
	public String getDoctorInfo() {
		return patientsService.getDoctorInfo();
	}

	@PreAuthorize("hasAuthority('PATIENT')")
	@GetMapping("/patient/findAFamilyDoctor")
	public String findAFamilyDoctor() {
		return patientsService.findFamilyDoctor();
	}


	@PreAuthorize("hasAuthority('PATIENT')")
	@PutMapping("/patient/updateData")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Patients> updatePatientById(@RequestBody PatientUpdateDTO patientUpdateDTO) throws ValidateDataException {
		logger.info("Updating patient data in database...");
		return new ResponseEntity<>(patientsService.updatePatientData(patientUpdateDTO), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('PATIENT')")
	@DeleteMapping("/patient/deleteMyAccount")
	public ResponseEntity<Void> deleteMyAccount() {
		logger.info("Deleting patient data in database...");
		patientsService.deleteMyAccount();
		return ResponseEntity.noContent().build();
	}

}
