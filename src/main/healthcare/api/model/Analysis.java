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
@Table(name = "ANALYSIS")
public class Analysis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ANALYSIS_SEQ")
    @SequenceGenerator(name = "ANALYSIS_SEQ", sequenceName = "ANALYSIS_SEQ", allocationSize = 1)
    @Column(name = "ANALYSIS_ID")
    private Long analysisId;

    @Column(name = "PATIENT_ID")
    private Long patientId;

    @Column(name = "DOCTOR_NAME")
    private String doctorName;

    @Column(name = "AGE")
    private int age;

    @Column(name = "GENDER")
    private String gender;
    @Column(name = "RESULTS")
    private String results;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "INTERPRETED_BY")
    private String interpretedBy;

    @Column(name = "INTERPRETED")
    private Boolean interpreted;

    @Column(name = "PERFORMED_BY")
    private String performedBy;

    @Column(name = "GLUCOSE")
    private double glucose;

    @Column(name = "SODIUM")
    private double sodium;

    @Column(name = "POTASSIUM")
    private double potassium;

    @Column(name = "CHLORIDE")
    private double chloride;

    @Column(name = "CREATININE")
    private double creatinine;

    @Column(name = "URIC_ACID")
    private double uricAcid;

    @Column(name = "CALCIUM")
    private double calcium;

    @Column(name = "MAGNESIUM")
    private double magnesium;

    @Column(name = "CHOLESTEROL")
    private double cholesterol;

    @Column(name = "TRIGLYCERIDES")
    private double triglycerides;

    @Column(name = "ALBUMIN")
    private double albumin;

    @Column(name = "BILIRUBIN")
    private double bilirubin;

    @Column(name = "IRON")
    private double iron;

    @Column(name = "NOTES")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID", insertable = false, updatable = false)
    private Patients patient;


    public Long getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Long analysisId) {
        this.analysisId = analysisId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInterpretedBy() {
        return interpretedBy;
    }

    public void setInterpretedBy(String interpretedBy) {
        this.interpretedBy = interpretedBy;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getInterpreted() {
        return interpreted;
    }

    public void setInterpreted(Boolean interpreted) {
        this.interpreted = interpreted;
    }

    public double getGlucose() {
        return glucose;
    }

    public void setGlucose(double glucose) {
        this.glucose = glucose;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getChloride() {
        return chloride;
    }

    public void setChloride(double chloride) {
        this.chloride = chloride;
    }

    public double getCreatinine() {
        return creatinine;
    }

    public void setCreatinine(double creatinine) {
        this.creatinine = creatinine;
    }

    public double getUricAcid() {
        return uricAcid;
    }

    public void setUricAcid(double uricAcid) {
        this.uricAcid = uricAcid;
    }

    public double getCalcium() {
        return calcium;
    }

    public void setCalcium(double calcium) {
        this.calcium = calcium;
    }

    public double getMagnesium() {
        return magnesium;
    }

    public void setMagnesium(double magnesium) {
        this.magnesium = magnesium;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public double getTriglycerides() {
        return triglycerides;
    }

    public void setTriglycerides(double triglycerides) {
        this.triglycerides = triglycerides;
    }

    public double getAlbumin() {
        return albumin;
    }

    public void setAlbumin(double albumin) {
        this.albumin = albumin;
    }

    public double getBilirubin() {
        return bilirubin;
    }

    public void setBilirubin(double bilirubin) {
        this.bilirubin = bilirubin;
    }

    public double getIron() {
        return iron;
    }

    public void setIron(double iron) {
        this.iron = iron;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Patients getPatient() {
        return patient;
    }

    public void setPatient(Patients patient) {
        this.patient = patient;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }
}
