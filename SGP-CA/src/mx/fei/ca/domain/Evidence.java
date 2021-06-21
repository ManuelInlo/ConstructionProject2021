package mx.fei.ca.domain;

/**
 * Clase para representar una evidencia de un integrante del CA
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class Evidence {
    private String impactCA;
    private String titleEvidence;
    private String author;
    private Integrant integrant;
    
    /**
     * Constructor para la creación de una evidencia de un integrante del CA
     * @param impactCA Define el impacto de la evidencia hacia el CA
     * @param titleEvidence Define el título de la evidencia 
     * @param author Define el autor de la evidencia
     */
    
    public Evidence(String impactCA, String titleEvidence, String author){
        this.impactCA = impactCA;
        this.titleEvidence = titleEvidence;
        this.author = author;
    }
    
    /**
     * 
     * @return El impacto de la evidencia hacia el CA 
     */
    
    public String getImpactCA() {
        return impactCA;
    }
    
    /**
     * 
     * @return El título de la evidencia 
     */

    public String getTitleEvidence() {
        return titleEvidence;
    }
    
    /**
     * 
     * @return El autor de la evidencia
     */

    public String getAuthor() {
        return author;
    }
    
    /**
     * El integrante asociado a la evidencia
     * @return 
     */

    public Integrant getIntegrant() {
        return integrant;
    }
    
    /**
     * 
     * @param impactCA El impacto hacia el CA a establecer a la evidencia
     */
    
    public void setImpactCA(String impactCA) {
        this.impactCA = impactCA;
    }
    
    /**
     * 
     * @param titleEvidence El título a establecer a la evidencia
     */
    
    public void setTitleEvidence(String titleEvidence) {
        this.titleEvidence = titleEvidence;
    }
    
    /**
     * 
     * @param author El autor a establecer a la evidencia
     */

    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 
     * @param integrant El integrante a establecer a la evidencia
     */
    public void setIntegrant(Integrant integrant) {
        this.integrant = integrant;
    }
    
    /**
     * Método que devuelve el título de la evidencia para su posterior uso en la capa de presentación
     * @return El título de la evidencia
     */

    @Override
    public String toString() {
        return titleEvidence;
    }
    
}
