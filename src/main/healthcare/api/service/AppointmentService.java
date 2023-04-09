package main.healthcare.api.service;

import main.healthcare.api.dto.AppointmentCreateDTO;
import main.healthcare.api.dto.AppointmentUpdateDTO;
import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.Appointments;
import main.healthcare.api.model.Doctors;
import main.healthcare.api.model.Patients;
import main.healthcare.api.repository.AppointmentRepository;
import main.healthcare.api.repository.DoctorsRepository;
import main.healthcare.api.repository.PatientsRepository;
import main.healthcare.api.security.JwtFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    static Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientsRepository patientsRepository;
    @Autowired
    private DoctorsRepository doctorsRepository;


    public List<Appointments> getMyAppointmentsD() {
        Doctors doctor = doctorsRepository.findByDoctorId(JwtFilter.id);
        return doctor.getAppointmentsList();
    }

    public List<Appointments> getMyAppointmentsP() {
        Patients patient = patientsRepository.findByPatientId(JwtFilter.id);
        return patient.getMyAppointments();
    }

    public Appointments scheduleAppointment(AppointmentCreateDTO appointmentCreateDTO) throws ValidateDataException {
        Patients patients = patientsRepository.findByPatientId(JwtFilter.id);
        Doctors doctors = doctorsRepository.findByDoctorId(appointmentCreateDTO.getDoctorId());
        Appointments appointments = new Appointments();

        if (checkDataValidation(appointmentCreateDTO)) {
            appointments.setPatientId(patients.getPatientId());
            appointments.setPatientName(patients.getName() + " " + patients.getLastname());
            appointments.setDoctorId(appointmentCreateDTO.getDoctorId());
            appointments.setDoctorName(doctors.getName() + " " + doctors.getLastname());
            appointments.setAppointmentDate(appointmentCreateDTO.getAppointmentDate());
            return appointmentRepository.save(appointments);
        } else {
            logger.info("Introduced data is not correct");
            throw new ValidateDataException("Check introduced data! ID and date can't be empty!");
        }

    }

    private boolean checkDataValidation(AppointmentCreateDTO appointmentCreateDTO) {
        if (appointmentCreateDTO.getDoctorId() == null) {
            return false;
        } else return appointmentCreateDTO.getAppointmentDate() != null;
    }

    public String approveAppointment(Long appointmentId, AppointmentUpdateDTO appointmentUpdateDTO) throws ValidateDataException {
        Appointments appointment = appointmentRepository.findByAppointmentId(appointmentId);
        if (appointment == null) {
            throw new ValidateDataException("Appointment with id " + appointmentId + " doesn't exist! Please enter a valid id!");
        }
        if (appointmentUpdateDTO.isApproved()) {
            appointment.setApproved(true);
            appointmentRepository.save(appointment);
            return "Appointment is approved.";
        } else {
            appointment.setApproved(false);
            appointmentRepository.save(appointment);
            return "Appointment is not approved.";
        }
    }

    public String checkMyAppointment(Long appointmentId) {
        Appointments appointment = appointmentRepository.findByAppointmentId(appointmentId);
        if (appointment.getApproved() != null && appointment.getApproved()) {
            return "Your appointment at " + appointment.getAppointmentDate() + " is approved!";
        } else if (appointment.getApproved() != null && !appointment.getApproved()) {
            return "Your appointment at " + appointment.getAppointmentDate() + " is not approved! Please contact doctor to schedule again!";
        } else {
            return "Still waiting for doctor to approve the appointment. Please check later again!";
        }
    }

    public void deleteMyAppointment(Long appointmentId) throws ValidateDataException {
        Appointments appointment = appointmentRepository.findByAppointmentId(appointmentId);
        if (appointment == null) {
            throw new ValidateDataException("Appointment with id " + appointmentId + " doesn't exist! Please enter a valid id!");
        }
        appointmentRepository.delete(appointment);
    }


}
