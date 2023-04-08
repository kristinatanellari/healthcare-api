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
@Table(name = "PRESCRIPTIONS")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PRESCRIPTION_SEQ")
    @SequenceGenerator(name = "PRESCRIPTION_SEQ", sequenceName = "PRESCRIPTION_SEQ", allocationSize = 1)
    @Column(name = "PRESCRIPTION_ID")
    private Long prescriptionId;

    @Column(name = "PATIENT_ID")
    private Long patientId;

    @Column(name = "DOCTOR_ID")
    private Long doctorId;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "PHARMACIST_NAME")
    private String pharmacistName;

    @Column(name = "PROCESSED")
    private Boolean processed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PATIENT_ID", insertable = false, updatable = false)
    private Patients patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DOCTOR_ID", insertable = false, updatable = false)
    private Doctors doctor;

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long id) {
        this.prescriptionId = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getPharmacistName() {
        return pharmacistName;
    }

    public void setPharmacistName(String pharmacistName) {
        this.pharmacistName = pharmacistName;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

}

/*
* <column name="patient_id" type="BIGINT">
                <constraints nullable="false" foreignKeyName="fk_patient_prescription" referencedTableName="patients" referencedColumnNames="patient_id" deleteCascade="true"/>/>
            </column>
            <column name="doctor_id" type="BIGINT">
                <constraints nullable="false" foreignKeyName="fk_doctor_prescription" referencedTableName="doctors" referencedColumnNames="doctor_id" deleteCascade="true"/>/>
            </column>
            * */
