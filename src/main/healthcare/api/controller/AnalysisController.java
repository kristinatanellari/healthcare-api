package main.healthcare.api.controller;

import main.healthcare.api.dto.AnalysisDTO;
import main.healthcare.api.dto.AnalysisInterpretedDTO;
import main.healthcare.api.dto.AnalysisResultsDTO;
import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.Analysis;
import main.healthcare.api.service.AnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnalysisController {

    static Logger logger = LoggerFactory.getLogger(AnalysisController.class);

    @Autowired
    private AnalysisService analysisService;

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/getMyAnalysis")
    public List<Analysis> getMyAnalysis() throws ValidateDataException {
        logger.info("Getting analysis from database...");
        return analysisService.getMyAnalysis();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/newAnalysis")
    public ResponseEntity<Analysis> newAnalysis(@RequestBody AnalysisDTO analysisDTO) {
        logger.info("Creating new analysis in database...");
        return new ResponseEntity<>(analysisService.newAnalysis(analysisDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/publishAnalysisResults")
    public ResponseEntity<Analysis> publishAnalysisResults(@RequestBody AnalysisResultsDTO analysisResultsDTO) throws ValidateDataException {
        logger.info("Updating analysis in database...");
        return new ResponseEntity<>(analysisService.publishAnalysisResults(analysisResultsDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/interpretAnalysis")
    public ResponseEntity<Analysis> interpretAnalysis(@RequestBody AnalysisInterpretedDTO analysisInterpretedDTO) {
        logger.info("Updating analysis in database...");
        return new ResponseEntity<>(analysisService.interpretAnalysis(analysisInterpretedDTO), HttpStatus.CREATED);
    }

}
