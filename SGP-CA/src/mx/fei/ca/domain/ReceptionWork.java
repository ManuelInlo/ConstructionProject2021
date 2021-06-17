package mx.fei.ca.domain;

import java.sql.Date;

/**
 * Clase para representar un trabajo recepcional de un integrante del CA
 * Cada trabajo recepcional se determina de un impacto al CA, título, ruta de archivo, fecha inicio, fecha fin, 
 * grado, tipo de trabajo, estado actual, identificador, colaborador asociado, proyecto de investigación asociado e integrante asociado
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class ReceptionWork {
    private String impactCA;
    private String titleReceptionWork;
    private String fileRoute;
    private Date startDate;
    private Date endDate;
    private String grade;
    private String workType;
    private String actualState;
    private int id;
    private Collaborator collaborator;
    private InvestigationProject investigationProject;
    private Integrant integrant;

    /**
     * Constructor para la creación de un trabajo recepcional con estado actual de "Terminado"
     * @param impactCA Define el impacto del trabajo recepcional hacía el CA
     * @param titleReceptionWork Define el título del trabajo recepcional
     * @param fileRoute Define la ruta del trabajo recepcional
     * @param startDate Define la fecha de inicio del trabajo recepcional
     * @param endDate Define la fecha de fin del trabajo recepcional
     * @param grade Define el grado del trabajo recepcional
     * @param workType Define el tipo de trabajo recepcional
     * @param actualState Define al estado actual del trabajo recepcional
     */
    
    public ReceptionWork(String impactCA, String titleReceptionWork, String fileRoute, Date startDate, Date endDate,
                         String grade, String workType, String actualState) {
        this.impactCA = impactCA;
        this.titleReceptionWork = titleReceptionWork;
        this.fileRoute = fileRoute;
        this.startDate = startDate;
        this.endDate = endDate;
        this.grade = grade;
        this.workType = workType;
        this.actualState = actualState;
    }
    
    /**
     * Constructor para la creación de un trabajo recepcional con estado actual de "En proceso"
     * @param impactCA Define el impacto del trabajo recepcional hacía el CA
     * @param titleReceptionWork Define el título del trabajo recepcional
     * @param fileRoute Define la ruta del trabajo recepcional
     * @param startDate Define la fecha de inicio del trabajo recepcional
     * @param grade Define el grado del trabajo recepcional
     * @param workType Define el tipo de trabajo recepcional
     * @param actualState Define al estado actual del trabajo recepcional
     */
    
    public ReceptionWork(String impactCA, String titleReceptionWork, String fileRoute, Date startDate,
                         String grade, String workType, String actualState){
        this.impactCA = impactCA;
        this.titleReceptionWork = titleReceptionWork;
        this.fileRoute = fileRoute;
        this.startDate = startDate;
        this.grade = grade;
        this.workType = workType;
        this.actualState = actualState;
    }
    
    /**
     * 
     * @return El impacto del trabajo recepcional hacia el CA
     */

    public String getImpactCA() {
        return impactCA;
    }
    
    /**
     * 
     * @return El título del trabajo recepcional 
     */

    public String getTitleReceptionWork() {
        return titleReceptionWork;
    }
    
    /**
     * 
     * @return La ruta del archivo del trabajo recepcional
     */

    public String getFileRoute() {
        return fileRoute;
    }
    
    /**
     * 
     * @return La fecha de inicio del trabajo recepcional
     */

    public Date getStartDate() {
        return startDate;
    }
    
    /**
     * 
     * @return La fecha de fin del trabajo recepcional
     */

    public Date getEndDate() {
        return endDate;
    }
    
    /**
     * 
     * @return El grado del trabajo recepcional
     */

    public String getGrade() {
        return grade;
    }
    
    /**
     * 
     * @return El tipo de trabajo recepcional
     */

    public String getWorkType() {
        return workType;
    }
    
    /**
     * 
     * @return El estado actual del trabajo recepcional
     */
    
    public String getActualState() {
        return actualState;
    }
    
    /**
     * 
     * @return El identificador del trabajo recepcional
     */

    public int getId() {
        return id;
    }
    
    /**
     * 
     * @return El colaborador asociado al trabajo recepcional
     */

    public Collaborator getCollaborator() {
        return collaborator;
    }
    
    /**
     * 
     * @return El proyecto de investigación asociado al trabajo recepcional
     */

    public InvestigationProject getInvestigationProject() {
        return investigationProject;
    }
    
    /**
     * 
     * @return El integrante asociado al trabajo recepcional
     */

    public Integrant getIntegrant() {
        return integrant;
    }
    
    /**
     * 
     * @param impactCA El impacto hacia el CA a establecer al trabajo recepcional
     */
    
    public void setImpactCA(String impactCA) {
        this.impactCA = impactCA;
    }
    
    /**
     * 
     * @param titleReceptionWork El titulo a establecer al trabajo recepcional
     */

    public void setTitleReceptionWork(String titleReceptionWork) {
        this.titleReceptionWork = titleReceptionWork;
    }
    
    /**
     * 
     * @param fileRoute La ruta del archivo a establecer al trabajo recepcional
     */

    public void setFileRoute(String fileRoute) {
        this.fileRoute = fileRoute;
    }
    
    /**
     * 
     * @param startDate La fecha de inicio a establecer al trabajo recepcional
     */

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    /**
     * 
     * @param endDate La fecha de fin a establecer al trabajo recepcional
     */

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    /**
     * 
     * @param grade El grado a establecer al trabajo recepcional
     */

    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    /**
     * 
     * @param workType El tipo de trabajo a establecer al trabajo recepcional
     */

    public void setWorkType(String workType) {
        this.workType = workType;
    }
    
    /**
     * 
     * @param actualState El estado actual a establecer al trabajo recepcional
     */

    public void setActualState(String actualState) {
        this.actualState = actualState;
    }
    
    /**
     * 
     * @param id El identificador a establecer al trabajo recepcional
     */

    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * 
     * @param collaborator El colaborador a establecer al trabajo recepcional
     */

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }
    
    /**
     * 
     * @param investigationProject El proyecto de investigación a establecer al trabajo recepcional
     */

    public void setInvestigationProject(InvestigationProject investigationProject) {
        this.investigationProject = investigationProject;
    }
    
    /**
     * 
     * @param integrant El integrante a establecer al trabajo recepcional
     */

    public void setIntegrant(Integrant integrant) {
        this.integrant = integrant;
    }
    
}
