
package mx.fei.ca.domain;

import java.sql.Date;


public class InvestigationProject {
    private int idProject;
    private String keyCode;
    private Date startDate;
    private Date endDate;
    private String tittleProject;
    private String description;

    public InvestigationProject() {
        //Default constructor
    }
    
    public InvestigationProject(int idProject, String keyCode, Date startDate, Date endDate, String tittleProject, String description) {
        this.idProject = idProject;
        this.keyCode = keyCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tittleProject = tittleProject;
        this.description = description;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
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

    public String getTittleProject() {
        return tittleProject;
    }

    public void setTittleProject(String tittleProject) {
        this.tittleProject = tittleProject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return tittleProject;
    }
      
}
