package main.healthcare.api.controller;

import main.healthcare.api.dto.DiagnoseDTO;
import main.healthcare.api.exception.NotFoundData;
import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.Diagnose;
import main.healthcare.api.service.DiagnoseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiagnoseController {

    static Logger logger = LoggerFactory.getLogger(PatientsController.class);

    @Autowired
    private DiagnoseService diagnoseService;

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/setDiagnose")
    public ResponseEntity<Diagnose> setDiagnose(@RequestBody DiagnoseDTO diagnoseDTO) throws ValidateDataException {
        logger.info("Inserting new diagnose in database...");
        return new ResponseEntity<>(diagnoseService.setDiagnose(diagnoseDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/getMyDiagnosis")
    public List<Diagnose> getMyDiagnosis() throws ValidateDataException {
        logger.info("Getting diagnosis from database...");
        return diagnoseService.getMyDiagnosis();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/getMyPatientDiagnosis/patientId={patientId}")
    public List<Diagnose> getMyPatientDiagnosis(@PathVariable Long patientId) throws NotFoundData {
        logger.info("Getting diagnosis from database...");
        return diagnoseService.getMyPatientDiagnosis(patientId);
    }
}
