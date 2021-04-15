
package mx.fei.ca.domain;

import java.sql.Date;

public class ReceptionWork {
    private String impactCA;
    private String titleReceptionWork;
    private String workAddress;
    private Date startDate;
    private Date endDate;
    private int numStudents;
    private String grade;
    private String workType;
    private String actualState;
    private int id;
    private Collaborator collaborator;
    private InvestigationProject investigationProject;
    private Integrant integrant;

    public ReceptionWork(String impactCA, String titleReceptionWork, String workAddress, Date startDate, Date endDate,
                         int numStudents, String grade, String workType, String actualState) {
        this.impactCA = impactCA;
        this.titleReceptionWork = titleReceptionWork;
        this.workAddress = workAddress;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numStudents = numStudents;
        this.grade = grade;
        this.workType = workType;
        this.actualState = actualState;
    }

    public String getImpactCA() {
        return impactCA;
    }

    public String getTitleReceptionWork() {
        return titleReceptionWork;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getNumStudents() {
        return numStudents;
    }

    public String getGrade() {
        return grade;
    }

    public String getWorkType() {
        return workType;
    }

    public String getActualState() {
        return actualState;
    }

    public int getId() {
        return id;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public InvestigationProject getInvestigationProject() {
        return investigationProject;
    }

    public Integrant getIntegrant() {
        return integrant;
    }
    
    public void setImpactCA(String impactCA) {
        this.impactCA = impactCA;
    }

    public void setTitleReceptionWork(String titleReceptionWork) {
        this.titleReceptionWork = titleReceptionWork;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setNumStudents(int numStudents) {
        this.numStudents = numStudents;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public void setActualState(String actualState) {
        this.actualState = actualState;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public void setInvestigationProject(InvestigationProject investigationProject) {
        this.investigationProject = investigationProject;
    }

    public void setIntegrant(Integrant integrant) {
        this.integrant = integrant;
    }
    
}
