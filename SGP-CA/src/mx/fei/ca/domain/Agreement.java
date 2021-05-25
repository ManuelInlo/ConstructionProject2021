
package mx.fei.ca.domain;


public class Agreement {
    private int idAgreement;
    private String description;
    private String dateAgreement;
    private String responsible;

    public Agreement(String description, String dateAgreement, String responsible) {
        this.description = description;
        this.dateAgreement = dateAgreement;
        this.responsible = responsible;
    }

    public int getIdAgreement() {
        return idAgreement;
    }
    
    public String getDescription() {
        return description;
    }

    public String getDateAgreement() {
        return dateAgreement;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setIdAgreement(int idAgreement) {
        this.idAgreement = idAgreement;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateAgreement(String dateAgreement) {
        this.dateAgreement = dateAgreement;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }   
}
