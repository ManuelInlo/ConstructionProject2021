package mx.fei.ca.domain;

import java.sql.Date;

/**
 *
 * @author inigu
 */
public class PreliminaryProject {
    private int idPreliminaryProject;
    private int idProject;
    private int idCollaborator;
    private String tittlePreliminaryProject;
   // private Date startDate;
    private String preliminaryProjectCondition;
    private String duration;
    private String modality;
    private String preliminaryProjectDescription;
    
    public PreliminaryProject(){
        //Default constructor
    }

    public PreliminaryProject(int idPreliminaryProject, int idProject, int idCollaborator, String tittlePreliminaryProject/*, Date startDate*/
            , String preliminaryProjectCondition, String duration, String modality, String preliminaryProjectDescription) {
        this.idPreliminaryProject = idPreliminaryProject;
        this.idProject = idProject;
        this.idCollaborator = idCollaborator;
        this.tittlePreliminaryProject = tittlePreliminaryProject;
        //this.startDate = startDate;
        this.preliminaryProjectCondition = preliminaryProjectCondition;
        this.duration = duration;
        this.modality = modality;
        this.preliminaryProjectDescription = preliminaryProjectDescription;
    }

    public int getIdPreliminaryProject() {
        return idPreliminaryProject;
    }

    public void setIdPreliminaryProject(int idPreliminaryProject) {
        this.idPreliminaryProject = idPreliminaryProject;
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
/*
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
*/
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
        return "PreliminaryProject{" + "idPreliminaryProject=" + idPreliminaryProject + ", idProject=" + idProject + ", idCollaborator=" + idCollaborator + ", tittlePreliminaryProject=" + tittlePreliminaryProject + /*", startDate=" + startDate + */", preliminaryProjectCondition=" + preliminaryProjectCondition + ", duration=" + duration + ", modality=" + modality + ", preliminaryProjectDescription=" + preliminaryProjectDescription + '}';
    }
    
    
}
