package main.healthcare.api.repository;

import main.healthcare.api.model.Diagnose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnoseRepository extends JpaRepository<Diagnose, Long> {
    public List<Diagnose> findByPatientId(Long patientId);
    public List<Diagnose> findByPatientIdAndDoctorId(Long patientId, Long doctorId);
}
