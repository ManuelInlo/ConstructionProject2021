
package mx.fei.ca.domain;

/**
 * Clase para representar un prerequisito de una reunión
 * Cada prerequisito se determina por su identificador, descripción y su responsable
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class Prerequisite {
    private int idPrerequisite;
    private String description;
    private String prerequisiteManager;
    
    /**
     * Constructor para la creación de un prerequisito
     * @param description Define la descripción del prerequisito
     * @param prerequisiteManager Define el responsable encargado del prerequisito
     */

    public Prerequisite(String description, String prerequisiteManager) {
        this.description = description;
        this.prerequisiteManager = prerequisiteManager;
    }
    
    /**
     * 
     * @return El identificador del prerequisito
     */

    public int getIdPrerequisite() {
        return idPrerequisite;
    }
    
    /**
     * 
     * @return La descripción del prerequisito
     */

    public String getDescription() {
        return description;
    }
    
    /**
     * 
     * @return El responsable del prerequisito
     */

    public String getPrerequisiteManager() {
        return prerequisiteManager;
    }
    
    /**
     * 
     * @param idPrerequisite El identificador a establecer al prerequisito
     */

    public void setIdPrerequisite(int idPrerequisite) {
        this.idPrerequisite = idPrerequisite;
    }
    
    /**
     * 
     * @param description La descripción a establecer al prerequisito
     */

    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * 
     * @param prerequisiteManager El responsable a establecer al prerequisito
     */

    public void setPrerequisiteManager(String prerequisiteManager) {
        this.prerequisiteManager = prerequisiteManager;
    }
    
}
