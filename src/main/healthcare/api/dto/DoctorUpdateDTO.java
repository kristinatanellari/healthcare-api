package main.healthcare.api.dto;

import java.util.Date;

public class DoctorUpdateDTO {

    private String name;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String specialization;
    private Date startVacDate;
    private Date endVacDate;

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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
