package mx.fei.ca.domain;

/**
 * Clase para representar un colaborador participante del CA
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class Collaborator {
    private int idCollaborator;
    private String name;
    private String position;
    
    /**
     * Constructor para la creación de un colaborador participante del CA
     * @param name Define el nombre del colaborador
     * @param position Define el cargo que tiene el colaborador
     */
    
    public Collaborator(String name, String position) {
        this.name = name;
        this.position = position;
    }

    /**
     * 
     * @return El identificador del colaborador
     */
    
    public int getIdCollaborator() {
        return idCollaborator;
    }
    
    /**
     * 
     * @return El nombre del colaborador
     */

    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return El cargo del colaborador
     */
    
    public String getPosition() {
        return position;
    }
    
    /**
     * 
     * @param idCollaborator Identificador a establecer al colaborador
     */

    public void setIdCollaborator(int idCollaborator) {
        this.idCollaborator = idCollaborator;
    }
    
    /**
     * 
     * @param name Nombre a establecer al colaborador 
     */

    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @param position Cargo a establecer al colaborador
     */

    public void setPosition(String position) {
        this.position = position;
    }
    
}
