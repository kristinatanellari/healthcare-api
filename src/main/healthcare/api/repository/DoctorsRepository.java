package main.healthcare.api.repository;

import main.healthcare.api.model.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorsRepository extends JpaRepository<Doctors, Long> {

    public List<Doctors> findAll();
    public Doctors findByEmail(String email);
    public Doctors findByUsername(String username);

    public Doctors findByDoctorId(Long doctorId);
    public List<Doctors> findBySpecialization(String specialization);

}
