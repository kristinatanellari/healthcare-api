package main.healthcare.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DIAGNOSIS")
public class Diagnose {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "DIAGNOSE_SEQ")
    @SequenceGenerator(name = "DIAGNOSE_SEQ", sequenceName = "DIAGNOSE_SEQ", allocationSize = 1)
    @Column(name = "DIAGNOSE_ID")
    private Long diagnoseId;

    @Column(name = "PATIENT_ID")
    private Long patientId;

    @Column(name = "DOCTOR_ID")
    private Long doctorId;

    @Column(name = "DIAGNOSE")
    private String diagnose;

    @Column(name = "DATE")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID", insertable = false, updatable = false)
    private Patients patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCTOR_ID", insertable = false, updatable = false)
    private Doctors doctor;

    public Long getDiagnoseId() {
        return diagnoseId;
    }

    public void setDiagnoseId(Long diagnoseId) {
        this.diagnoseId = diagnoseId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Patients getPatient() {
        return patient;
    }

    public void setPatient(Patients patient) {
        this.patient = patient;
    }

    public Doctors getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctors doctor) {
        this.doctor = doctor;
    }
}
