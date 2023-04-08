package main.healthcare.api.dto;

public class ProcessPrescriptionDTO {
    private Boolean processed;
    private String pharmacistName;

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public String getPharmacistName() {
        return pharmacistName;
    }

    public void setPharmacistName(String pharmacistName) {
        this.pharmacistName = pharmacistName;
    }
}
