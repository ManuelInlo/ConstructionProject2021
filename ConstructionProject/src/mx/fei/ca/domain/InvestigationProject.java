package mx.fei.ca.domain;

import java.sql.Date;

/**
 *
 * @author inigu
 */
public class InvestigationProject {
    private int idProject;
    private String projectTittle;
    private Date startDate;
    private Date endDate;
    private String description;
    
    public InvestigationProject(){
        //Default Constructor
    }

    public InvestigationProject(int idProject, String projectTittle, Date startDate, Date endDate, String description) {
        this.idProject = idProject;
        this.projectTittle = projectTittle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public String getProjectTittle() {
        return projectTittle;
    }

    public void setProjectTittle(String projectTittle) {
        this.projectTittle = projectTittle;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "InvestigationProject{" + "idProject=" + idProject 
                + ", projectTittle=" + projectTittle + ", startDate=" 
                + startDate + ", endDate=" + endDate + ", description=" 
                + description + '}';
    }
       
}
