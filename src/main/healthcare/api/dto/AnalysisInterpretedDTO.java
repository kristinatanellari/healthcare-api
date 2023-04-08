package main.healthcare.api.dto;


public class AnalysisInterpretedDTO {
    private Long analysisId;
    private String interpretedBy;
    private Boolean interpreted;
    private String notes;

    public Long getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Long analysisId) {
        this.analysisId = analysisId;
    }

    public String getInterpretedBy() {
        return interpretedBy;
    }

    public void setInterpretedBy(String interpretedBy) {
        this.interpretedBy = interpretedBy;
    }

    public Boolean getInterpreted() {
        return interpreted;
    }

    public void setInterpreted(Boolean interpreted) {
        this.interpreted = interpreted;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
