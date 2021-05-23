package mx.fei.ca.domain;

import java.sql.Date;


public class PreliminaryProject {
    private int idPreliminaryproject;
    private int idProject;
    private int idCollaborator;
    private String tittlePreliminaryProject;
    private String preliminaryProjectCondition;
    private String duration;
    private String modality;
    private String preliminaryProjectDescription;
    private Date startDate;

    public PreliminaryProject() {
    }

    public PreliminaryProject(int idPreliminaryproject, int idProject, int idCollaborator, String tittlePreliminaryProject, String preliminaryProjectCondition, String duration, String modality, String preliminaryProjectDescription, Date startDate) {
        this.idPreliminaryproject = idPreliminaryproject;
        this.idProject = idProject;
        this.idCollaborator = idCollaborator;
        this.tittlePreliminaryProject = tittlePreliminaryProject;
        this.preliminaryProjectCondition = preliminaryProjectCondition;
        this.duration = duration;
        this.modality = modality;
        this.preliminaryProjectDescription = preliminaryProjectDescription;
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public int getIdPreliminaryproject() {
        return idPreliminaryproject;
    }

    public void setIdPreliminaryproject(int idPreliminaryproject) {
        this.idPreliminaryproject = idPreliminaryproject;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public int getIdCollaborator() {
        return idCollaborator;
    }

    public void setIdCollaborator(int idCollaborator) {
        this.idCollaborator = idCollaborator;
    }

    public String getTittlePreliminaryProject() {
        return tittlePreliminaryProject;
    }

    public void setTittlePreliminaryProject(String tittlePreliminaryProject) {
        this.tittlePreliminaryProject = tittlePreliminaryProject;
    }

    public String getPreliminaryProjectCondition() {
        return preliminaryProjectCondition;
    }

    public void setPreliminaryProjectCondition(String preliminaryProjectCondition) {
        this.preliminaryProjectCondition = preliminaryProjectCondition;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getPreliminaryProjectDescription() {
        return preliminaryProjectDescription;
    }

    public void setPreliminaryProjectDescription(String preliminaryProjectDescription) {
        this.preliminaryProjectDescription = preliminaryProjectDescription;
    }

    @Override
    public String toString() {
        return "PreliminaryProject{" + "idPreliminaryproject=" + idPreliminaryproject + ", idProject=" + idProject + ", idCollaborator=" + idCollaborator + ", tittlePreliminaryProject=" + tittlePreliminaryProject + ", preliminaryProjectCondition=" + preliminaryProjectCondition + ", duration=" + duration + ", modality=" + modality + ", preliminaryProjectDescription=" + preliminaryProjectDescription + ", startDate=" + startDate + '}';
    }

    
    
}
