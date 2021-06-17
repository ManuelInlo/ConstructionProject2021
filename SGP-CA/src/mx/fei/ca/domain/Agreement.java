package mx.fei.ca.domain;

/**
 * Clase para representar un acuerdo de una minuta de una reunión
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class Agreement {
    private int idAgreement;
    private String description;
    private String dateAgreement;
    private String responsible;
    
    /**
     * Constructor para la creación de un acuerdo de una minuta de reunión
     * @param description Define la descripción del acuerdo de minuta
     * @param dateAgreement Define la fecha de realización estimada del acuerdo de minuta
     * @param responsible Define el responsable del acuerdo de minuta
     */
    
    public Agreement(String description, String dateAgreement, String responsible) {
        this.description = description;
        this.dateAgreement = dateAgreement;
        this.responsible = responsible;
    }
    
    /**
     * 
     * @return El identificador del acuerdo de minuta
     */
    
    public int getIdAgreement() {
        return idAgreement;
    }
    
    /**
     * 
     * @return La descripción del acuerdo de minuta
     */
    
    public String getDescription() {
        return description;
    }
    
    /**
     * 
     * @return La fecha de realización del acuerdo de minuta
     */

    public String getDateAgreement() {
        return dateAgreement;
    }
    
    /**
     * 
     * @return  El responsable del acuerdo de minuta
     */

    public String getResponsible() {
        return responsible;
    }
    
    /**
     * 
     * @param idAgreement Identificador a establecer al acuerdo de minuta
     */

    public void setIdAgreement(int idAgreement) {
        this.idAgreement = idAgreement;
    }

    /**
     * 
     * @param description Descripción a establecer al acuerdo de minuta
     */
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * 
     * @param dateAgreement Fecha de realización a establcer al acuerdo de minuta
     */

    public void setDateAgreement(String dateAgreement) {
        this.dateAgreement = dateAgreement;
    }
    
    /**
     * 
     * @param responsible Responsable a establecer al acuerdo de minuta
     */

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }   
}
