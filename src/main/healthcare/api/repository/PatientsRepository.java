package main.healthcare.api.repository;

import main.healthcare.api.model.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientsRepository extends JpaRepository<Patients, Long> {

	public List<Patients> findAll();
	public Patients findByEmail(String email);
	public Patients findByUsername(String username);
	public Patients findByPatientId(Long patientId);
	public List<Patients> findByDoctorId(Long doctorId);

}
