package main.healthcare.api.controller;

import main.healthcare.api.dto.DoctorCreateDTO;
import main.healthcare.api.dto.DoctorUpdateDTO;
import main.healthcare.api.exception.UnavailableDoctors;
import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.Doctors;
import main.healthcare.api.model.Patients;
import main.healthcare.api.service.DoctorsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DoctorsController {

    static Logger logger = LoggerFactory.getLogger(DoctorsController.class);
    @Autowired
    private DoctorsService doctorsService;


    @PostMapping("/doctor/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Doctors> createNewPatient(@RequestBody DoctorCreateDTO doctorCreateDTO) throws ValidateDataException {
        logger.info("Adding new doctor in database...");
        return new ResponseEntity<>(doctorsService.registerDoctor(doctorCreateDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR', 'PATIENT')")
    @GetMapping("/get/allDoctors")
    public List<Doctors> getAllDoctors() {
        return doctorsService.getAllDoctors();
    }


    @PreAuthorize("hasAuthority('DOCTOR')")
    @PutMapping("/doctor/updateData")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Doctors> updateDoctorById(@RequestBody DoctorUpdateDTO doctorUpdateDTO) throws ValidateDataException {
        logger.info("Updating doctor in database...");
        return new ResponseEntity<>(doctorsService.updateDoctorData(doctorUpdateDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping("/doctor/setVacationDates")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateVacationDates(@RequestBody DoctorUpdateDTO doctorUpdateDTO) throws ValidateDataException {
        logger.info("Updating vacation dates in database...");
        return doctorsService.updateVacationDates(doctorUpdateDTO);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @GetMapping("/get/doctorsBySpecialization/specialization={specialization}")
    public List<Doctors> getDoctorsBySpecialization(@PathVariable String specialization) throws UnavailableDoctors {
        return doctorsService.findAllDoctorsBySpecialization(specialization);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @DeleteMapping("/doctor/deleteMyAccount")
    public ResponseEntity<Void> deleteMyAccount() {
        logger.info("Deleting doctor data in database...");
        doctorsService.deleteMyAccount();
        return ResponseEntity.noContent().build();
    }
}
