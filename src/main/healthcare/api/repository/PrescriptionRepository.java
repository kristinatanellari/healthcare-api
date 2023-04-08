package main.healthcare.api.repository;

import main.healthcare.api.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    public List<Prescription> findByPatientIdAndDate(Long id, Date date);
    public List<Prescription> findByPatientId(Long patientId);
    public Prescription findByPrescriptionId(Long id);

}
