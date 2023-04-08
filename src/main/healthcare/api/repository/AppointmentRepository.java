package main.healthcare.api.repository;

import main.healthcare.api.model.Appointments;
import main.healthcare.api.model.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointments, Long> {
    Appointments findByAppointmentId(Long appointmentID);
}
