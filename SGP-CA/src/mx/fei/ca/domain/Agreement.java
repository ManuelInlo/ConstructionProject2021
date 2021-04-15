
package mx.fei.ca.domain;

import java.sql.Date;

public class Agreement {
    private int idAgreement;
    private int number;
    private String description;
    private Date dateAgreement;
    private String responsible;

    public Agreement(int number, String description, Date dateAgreement, String responsible) {
        this.number = number;
        this.description = description;
        this.dateAgreement = dateAgreement;
        this.responsible = responsible;
    }

    public int getIdAgreement() {
        return idAgreement;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateAgreement() {
        return dateAgreement;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setIdAgreement(int idAgreement) {
        this.idAgreement = idAgreement;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateAgreement(Date dateAgreement) {
        this.dateAgreement = dateAgreement;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }   
}
