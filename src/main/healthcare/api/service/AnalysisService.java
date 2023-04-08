package main.healthcare.api.service;

import main.healthcare.api.dto.AnalysisDTO;
import main.healthcare.api.dto.AnalysisInterpretedDTO;
import main.healthcare.api.dto.AnalysisResultsDTO;
import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.Analysis;
import main.healthcare.api.model.Patients;
import main.healthcare.api.repository.AnalysisRepository;
import main.healthcare.api.repository.PatientsRepository;
import main.healthcare.api.security.JwtFilter;
import main.healthcare.api.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisService {
    static Logger logger = LoggerFactory.getLogger(AnalysisService.class);

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private PatientsRepository patientsRepository;


    public List<Analysis> getMyAnalysis() throws ValidateDataException {
        Patients patient = patientsRepository.findByPatientId(JwtFilter.id);
        if (patient == null) {
            throw new ValidateDataException("User doesn't exist");
        }
        return patient.getAnalyses();

    }
    public Analysis newAnalysis(AnalysisDTO analysisDTO) {
        Analysis analysis = new Analysis();
        analysis.setPatientId(analysisDTO.getPatientId());
        analysis.setDoctorName(analysisDTO.getDoctorName());
        analysis.setAge(analysisDTO.getAge());
        analysis.setGender(analysisDTO.getGender());
        analysis.setDate(analysisDTO.getDate());
        return analysisRepository.save(analysis);
    }

    public Analysis publishAnalysisResults(AnalysisResultsDTO analysisResultsDTO) throws ValidateDataException {
        Analysis analysis = analysisRepository.findByAnalysisId(analysisResultsDTO.getAnalysisId());
        if (analysis == null) {
            throw new ValidateDataException("Analysis with id = " + analysisResultsDTO.getAnalysisId() + " doesn't exist!");
        }
        analysis.setPerformedBy(analysisResultsDTO.getPerformedBy());
        analysis.setGlucose(analysisResultsDTO.getGlucose());
        analysis.setSodium(analysisResultsDTO.getSodium());
        analysis.setPotassium(analysisResultsDTO.getPotassium());
        analysis.setChloride(analysisResultsDTO.getChloride());
        analysis.setCreatinine(analysisResultsDTO.getCreatinine());
        analysis.setUricAcid(analysisResultsDTO.getUricAcid());
        analysis.setCalcium(analysisResultsDTO.getCalcium());
        analysis.setMagnesium(analysisResultsDTO.getMagnesium());
        analysis.setCholesterol(analysisResultsDTO.getCholesterol());
        analysis.setTriglycerides(analysisResultsDTO.getTriglycerides());
        analysis.setAlbumin(analysisResultsDTO.getAlbumin());
        analysis.setBilirubin(analysisResultsDTO.getBilirubin());
        analysis.setIron(analysisResultsDTO.getIron());

        String result = calculateResults(analysisResultsDTO);
        analysis.setResults(result);

        return analysisRepository.save(analysis);
    }

    public Analysis interpretAnalysis(AnalysisInterpretedDTO analysisInterpretedDTO) {
        Analysis analysis = analysisRepository.findByAnalysisId(analysisInterpretedDTO.getAnalysisId());
        analysis.setInterpretedBy(analysisInterpretedDTO.getInterpretedBy());
        analysis.setInterpreted(analysisInterpretedDTO.getInterpreted());
        analysis.setNotes(analysisInterpretedDTO.getNotes());

        return analysisRepository.save(analysis);
    }

    private String calculateResults(AnalysisResultsDTO analysisResultsDTO) {
        String results = "";
        if (analysisResultsDTO.getGlucose() < Constant.GLUCOSE_MIN || analysisResultsDTO.getGlucose() > Constant.GLUCOSE_MAX) {
            results += "Glucose value is " + analysisResultsDTO.getGlucose() + ". Normal value is " + Constant.GLUCOSE_MIN + "-" + Constant.GLUCOSE_MAX + "\n";
        }

        if (analysisResultsDTO.getSodium() < Constant.SODIUM_MIN || analysisResultsDTO.getSodium() > Constant.SODIUM_MAX) {
            results += "Sodium value is " + analysisResultsDTO.getSodium() + ". Normal value is " + Constant.SODIUM_MIN + "-" + Constant.SODIUM_MAX + "\n";
        }

        if (analysisResultsDTO.getPotassium() < Constant.POTASSIUM_MIN || analysisResultsDTO.getPotassium() > Constant.POTASSIUM_MAX) {
            results += "Potassium value is " + analysisResultsDTO.getPotassium() + ". Normal value is " + Constant.POTASSIUM_MIN + "-" + Constant.POTASSIUM_MAX + "\n";
        }

        if (analysisResultsDTO.getChloride() < Constant.CHLORIDE_MIN || analysisResultsDTO.getChloride() > Constant.CHLORIDE_MAX) {
            results += "Chloride value is " + analysisResultsDTO.getChloride() + ". Normal value is " + Constant.CHLORIDE_MIN + "-" + Constant.CHLORIDE_MAX + "\n";
        }

        if (analysisResultsDTO.getCreatinine() < Constant.CREATININE_MIN || analysisResultsDTO.getCreatinine() > Constant.CREATININE_MAX) {
            results += "Creatinine value is " + analysisResultsDTO.getCreatinine() + ". Normal value is " + Constant.CREATININE_MIN + "-" + Constant.CREATININE_MAX + "\n";
        }

        if (analysisResultsDTO.getUricAcid() < Constant.URIC_ACID_MIN || analysisResultsDTO.getUricAcid() > Constant.URIC_ACID_MAX) {
            results += "Uric acid value is " + analysisResultsDTO.getUricAcid() + ". Normal value is " + Constant.URIC_ACID_MIN + "-" + Constant.URIC_ACID_MAX + "\n";
        }

        if (analysisResultsDTO.getCalcium() < Constant.CALCIUM_MIN || analysisResultsDTO.getCalcium() > Constant.CALCIUM_MIN) {
            results += "Calcium value is " + analysisResultsDTO.getCalcium() + ". Normal value is " + Constant.CALCIUM_MIN + "-" + Constant.CALCIUM_MAX + "\n";
        }

        if (analysisResultsDTO.getMagnesium() < Constant.MAGNESIUM_MIN || analysisResultsDTO.getMagnesium() > Constant.MAGNESIUM_MAX) {
            results += "Magnesium value is " + analysisResultsDTO.getMagnesium() + ". Normal value is " + Constant.MAGNESIUM_MIN + "-" + Constant.MAGNESIUM_MAX + "\n";
        }

        if (analysisResultsDTO.getCholesterol() < Constant.CHOLESTEROL_MIN || analysisResultsDTO.getCholesterol() > Constant.CHOLESTEROL_MAX) {
            results += "Cholesterol value is " + analysisResultsDTO.getCholesterol() + ". Normal value is " + Constant.CHOLESTEROL_MIN + "-" + Constant.CHOLESTEROL_MAX + "\n";
        }

        if (analysisResultsDTO.getTriglycerides() < Constant.TRIGLYCERIDES_MIN || analysisResultsDTO.getTriglycerides() > Constant.TRIGLYCERIDES_MAX) {
            results += "Triglycerides value is " + analysisResultsDTO.getTriglycerides() + ". Normal value is " + Constant.TRIGLYCERIDES_MIN + "-" + Constant.TRIGLYCERIDES_MAX + "\n";
        }

        if (analysisResultsDTO.getAlbumin() < Constant.ALBUMIN_MIN || analysisResultsDTO.getAlbumin() > Constant.ALBUMIN_MAX) {
            results += "Albumin value is " + analysisResultsDTO.getAlbumin() + ". Normal value is " + Constant.ALBUMIN_MIN + "-" + Constant.ALBUMIN_MAX + "\n";
        }

        if (analysisResultsDTO.getBilirubin() < Constant.BILIRUBIN_MIN || analysisResultsDTO.getBilirubin() > Constant.BILIRUBIN_MAX) {
            results += "Bilirubin value is " + analysisResultsDTO.getBilirubin() + ". Normal value is " + Constant.BILIRUBIN_MIN + "-" + Constant.BILIRUBIN_MAX + "\n";
        }

        if (analysisResultsDTO.getIron() < Constant.IRON_MIN || analysisResultsDTO.getIron() > Constant.IRON_MAX) {
            results += "Iron value is " + analysisResultsDTO.getIron() + ". Normal value is " + Constant.IRON_MIN + "-" + Constant.IRON_MAX + "\n";
        }
        return results;
    }
}

