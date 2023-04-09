package main.healthcare.api.controller;

import main.healthcare.api.dto.PrescriptionCreateDTO;
import main.healthcare.api.dto.ProcessPrescriptionDTO;
import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.Prescription;
import main.healthcare.api.service.PrescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class PrescriptionController {
    static Logger logger = LoggerFactory.getLogger(PrescriptionController.class);

    @Autowired
    private PrescriptionService prescriptionService;

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping(value = "/patient/getMyPrescriptions")
    public List<Prescription> getMyPrescription() throws ValidateDataException {
        logger.info("Getting prescriptions from database...");
        return prescriptionService.getMyPrescriptionsP();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping(value = "/doctor/getMyPrescriptions")
    public List<Prescription> getMyPrescriptions() throws ValidateDataException {
        logger.info("Getting prescriptions from database...");
        return prescriptionService.getMyPrescriptionsD();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/prescription")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Prescription> createPrescription(@RequestBody PrescriptionCreateDTO prescriptionCreateDTO) throws ValidateDataException {
        logger.info("Adding new prescription in database...");
        return new ResponseEntity<>(prescriptionService.createPrescription(prescriptionCreateDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('PHARMACIST')")
    @GetMapping(value = "/getPrescription/patientId={patientId}/date={date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPrescription(@PathVariable Long patientId, @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws ValidateDataException {
        logger.info("Getting prescription from database...");
        return prescriptionService.getPrescription(patientId, date);
    }

    @PreAuthorize("hasRole('PHARMACIST')")
    @PutMapping("/executePrescription/prescriptionId={prescriptionId}")
    public String executePrescription(@RequestBody ProcessPrescriptionDTO processPrescriptionDTO, @PathVariable Long prescriptionId) throws ValidateDataException {
        logger.info("Executing prescription in database...");
        return prescriptionService.executePrescription(processPrescriptionDTO, prescriptionId);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @DeleteMapping("/doctor/deletePrescription/prescriptionId={prescriptionId}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long prescriptionId) throws ValidateDataException {
        logger.info("Deleting prescription data in database...");
        prescriptionService.deletePrescription(prescriptionId);
        return ResponseEntity.noContent().build();
    }
}
