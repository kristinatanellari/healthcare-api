package main.healthcare.api.controller;

import main.healthcare.api.dto.PharmacistDTO;
import main.healthcare.api.exception.UserNotFoundException;
import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.Pharmacist;
import main.healthcare.api.service.PharmacistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PharmacistController {
    static Logger logger = LoggerFactory.getLogger(PatientsController.class);

    @Autowired
    private PharmacistService pharmacistService;

    @PostMapping("/pharmacist/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Pharmacist> createNewPharmacist(@RequestBody PharmacistDTO pharmacistDTO) throws ValidateDataException {
        logger.info("Adding new pharmacist in database...");
        return new ResponseEntity<>(pharmacistService.registerPharmacist(pharmacistDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('PHARMACIST')")
    @PutMapping("/pharmacist/update")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Pharmacist> updateData(@RequestBody PharmacistDTO pharmacistDTO) throws ValidateDataException, UserNotFoundException {
        logger.info("Updating data in database...");
        return new ResponseEntity<>(pharmacistService.updatePharmacistData(pharmacistDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('PHARMACIST')")
    @DeleteMapping("/pharmacist/deleteMyAccount")
    public ResponseEntity<Void> deleteMyAccount() {
        logger.info("Deleting pharmacist data in database...");
        pharmacistService.deleteMyAccount();
        return ResponseEntity.noContent().build();
    }
}
