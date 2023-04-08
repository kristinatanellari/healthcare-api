package main.healthcare.api.repository;

import main.healthcare.api.model.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

    public Analysis findByAnalysisId(Long analysisId);
    public List<Analysis> findByPatientId(Long patientId);
}
