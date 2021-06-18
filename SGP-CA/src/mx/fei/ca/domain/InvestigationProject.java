
package mx.fei.ca.domain;

import java.sql.Date;

/**
 * Clase para representar un proyecto de investigación del CA
 * Cada proyecto de investigación está determinado por su identificador, clave, fecha inicio, fecha fin, título del proyecto y descripción
 * @author David Alexander Mijangos Paredes
 */

public class InvestigationProject {
    private int idProject;
    private String keyCode;
    private Date startDate;
    private Date endDate;
    private String tittleProject;
    private String description;
    
    /**
     * Constructor por defecto para la creación de un proyecto de investigación
     */
    
    public InvestigationProject(){
        
    }
    
    /**
     * Constructor para la creación de un proyecto de investigación
     * @param idProject Define el identificador del proyecto de investigación
     * @param keyCode Define la clave asociada al proyecto de investigación
     * @param startDate Define la fecha de inicio del proyecto de investigación
     * @param endDate Define la fecha de fin del proyecto de investigación
     * @param tittleProject Define el título del proyecto de investigación
     * @param description Define la descripción  del proyecto de investigación 
     */

    public InvestigationProject(int idProject, String keyCode, Date startDate, Date endDate, String tittleProject, String description) {
        this.idProject = idProject;
        this.keyCode = keyCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tittleProject = tittleProject;
        this.description = description;
    }
    
    /**
     * 
     * @return El identificador del proyecto de investigación
     */

    public int getIdProject() {
        return idProject;
    }
    
    /**
     * 
     * @param idProject El identificador a establecer al proyecto de investigación
     */

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }
    
    /**
     * 
     * @return La clave del proyecto de investigación
     */

    public String getKeyCode() {
        return keyCode;
    }
    
    /**
     * 
     * @param keyCode La clave a establecer al proyecto de investigación
     */

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }
    
    /**
     * 
     * @return La fecha de inicio del proyecto de investigación
     */

    public Date getStartDate() {
        return startDate;
    }
    
    /**
     * 
     * @param startDate La fecha de inicio a establecer al proyecto de investigación
     */

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    /**
     * 
     * @return La fecha de fin del proyecto de investigación
     */

    public Date getEndDate() {
        return endDate;
    }
    
    /**
     * 
     * @param endDate La fecha de fin a establecer al proyecto de investigación
     */

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    /**
     * 
     * @return El título del proyecto de investigación
     */

    public String getTittleProject() {
        return tittleProject;
    }
    
    /**
     * 
     * @param tittleProject El título a establecer al proyecto de investigación
     */

    public void setTittleProject(String tittleProject) {
        this.tittleProject = tittleProject;
    }
    
    /**
     * 
     * @return La descripción del proyecto de investigación
     */

    public String getDescription() {
        return description;
    }
    
    /**
     * 
     * @param description La descripción a establecer al proyecto de investigación
     */

    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Método que devuelve el título de proyecto de investigación para su posterior uso en la capa de presentación
     * @return El título del proyecto de investigación 
     */

    @Override
    public String toString() {
        return tittleProject;
    }
      
}
