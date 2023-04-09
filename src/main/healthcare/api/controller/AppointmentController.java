package main.healthcare.api.controller;

import main.healthcare.api.dto.AppointmentCreateDTO;
import main.healthcare.api.dto.AppointmentUpdateDTO;
import main.healthcare.api.exception.ValidateDataException;
import main.healthcare.api.model.Appointments;
import main.healthcare.api.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppointmentController {

    static Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/patient/scheduleAppointment")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointments scheduleAppointment(@RequestBody AppointmentCreateDTO appointmentCreateDTO) throws ValidateDataException {
        logger.info("Scheduling an appointment...");
        return appointmentService.scheduleAppointment(appointmentCreateDTO);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/doctor/get/myAppointments")
    public List<Appointments> getMyAppointmentsD() {

        return appointmentService.getMyAppointmentsD();
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @GetMapping("/get/checkMyAppointment/appointmentId={appointmentId}")
    public String checkMyAppointment(@PathVariable Long appointmentId) {
        return appointmentService.checkMyAppointment(appointmentId);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @GetMapping("/patient/get/myAppointments")
    public List<Appointments> getMyAppointmentsP() {

        return appointmentService.getMyAppointmentsP();
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PutMapping("/doctor/approveAppointment/appointmentId={appointmentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public String approveAppointment(@PathVariable Long appointmentId, @RequestBody AppointmentUpdateDTO appointmentUpdateDTO) throws ValidateDataException {
        logger.info("Approving an appointment in database...");
        return appointmentService.approveAppointment(appointmentId, appointmentUpdateDTO);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @DeleteMapping("/patient/deleteMyAppointment/appointmentId={appointmentId}")
    public ResponseEntity<Void> deleteMyAppointment(@PathVariable Long appointmentId) throws ValidateDataException {
        logger.info("Deleting appointment data in database...");
        appointmentService.deleteMyAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }
}
