package main.healthcare.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DOCTORS")
public class Doctors {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "DOCTORS_SEQ")
    @SequenceGenerator(name = "DOCTORS_SEQ", sequenceName = "DOCTORS_SEQ", allocationSize = 1)
    @Column(name = "DOCTOR_ID")
    private Long doctorId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "ROLE")
    private String role;

    @Column(name = "SPECIALIZATION")
    private String specialization;

    @Column(name = "NR_PATIENTS")
    private Integer nrPatients;

    @Column(name = "START_VAC_DATE")
    private Date startVacDate;

    @Column(name = "END_VAC_DATE")
    private Date endVacDate;

    @OneToMany
    @JoinColumn(name = "doctor_id")
    private List<Patients> patientsList;

    @OneToMany
    @JoinColumn(name = "doctor_id")
    private List<Appointments> appointmentsList;

    @OneToMany(mappedBy = "doctor")
    private List<Prescription> prescriptions;

    @ManyToMany
    @JoinTable(name = "article_doctors",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<Articles> articles;


    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public List<Patients> getPatientsList() {
        return patientsList;
    }

    public void setPatientsList(List<Patients> patientsList) {
        this.patientsList = patientsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getNrPatients() {
        return nrPatients;
    }

    public void setNrPatients(Integer nrPatients) {
        this.nrPatients = nrPatients;
    }

    public Date getStartVacDate() {
        return startVacDate;
    }

    public void setStartVacDate(Date startVacDate) {
        this.startVacDate = startVacDate;
    }

    public Date getEndVacDate() {
        return endVacDate;
    }

    public void setEndVacDate(Date endVacDate) {
        this.endVacDate = endVacDate;
    }

    public List<Appointments> getAppointmentsList() {
        return appointmentsList;
    }

    public void setAppointmentsList(List<Appointments> appointmentsList) {
        this.appointmentsList = appointmentsList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }
}

