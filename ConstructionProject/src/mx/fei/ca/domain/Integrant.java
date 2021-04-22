package mx.fei.ca.domain;

import java.sql.Date; //Exist other library with util.. java.util.Date

/**
 *
 * @author inigu
 */
public class Integrant {
    private String curp;
    private String role;
    private String nameIntegrant;
    private String studyDegree;
    private String studyDicipline;
    private String prodepParticipation;
    private String typeTeaching;
    private String eisStudyDegree;
    private String institucionalMail;
    private String numberPhone;
    private Date dateBirthday;

    public Integrant(String curp, String role, 
            String nameIntegrant, String studyDegree, 
            String studyDicipline, String prodepParticipation, 
            String typeTeaching, String eisStudyDegree, String institucionalMail, 
            String numberPhone, Date dateBirthday) {
        this.curp = curp;
        this.role = role;
        this.nameIntegrant = nameIntegrant;
        this.studyDegree = studyDegree;
        this.studyDicipline = studyDicipline;
        this.prodepParticipation = prodepParticipation;
        this.typeTeaching = typeTeaching;
        this.eisStudyDegree = eisStudyDegree;
        this.institucionalMail = institucionalMail;
        this.numberPhone = numberPhone;
        this.dateBirthday = dateBirthday;
    }
    
    public Integrant(){
        //default constructor
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNameIntegrant() {
        return nameIntegrant;
    }

    public void setNameIntegrant(String nameIntegrant) {
        this.nameIntegrant = nameIntegrant;
    }

    public String getStudyDegree() {
        return studyDegree;
    }

    public void setStudyDegree(String studyDegree) {
        this.studyDegree = studyDegree;
    }

    public String getStudyDicipline() {
        return studyDicipline;
    }

    public void setStudyDicipline(String studyDicipline) {
        this.studyDicipline = studyDicipline;
    }

    public String getProdepParticipation() {
        return prodepParticipation;
    }

    public void setProdepParticipation(String prodepParticipation) {
        this.prodepParticipation = prodepParticipation;
    }

    public String getTypeTeaching() {
        return typeTeaching;
    }

    public void setTypeTeaching(String typeTeaching) {
        this.typeTeaching = typeTeaching;
    }

    public String getEisStudyDegree() {
        return eisStudyDegree;
    }

    public void setEisStudyDegree(String eisStudyDegree) {
        this.eisStudyDegree = eisStudyDegree;
    }

    public String getInstitucionalMail() {
        return institucionalMail;
    }

    public void setInstitucionalMail(String institucionalMail) {
        this.institucionalMail = institucionalMail;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public Date getDateBirthday() {
        return dateBirthday;
    }

    public void setDateBirthday(Date dateBirthday) {
        this.dateBirthday = dateBirthday;
    }

    @Override
    public String toString() {
        return "Integrant{" + "curp=" + curp + ", role=" + role + ", nameIntegrant=" 
                + nameIntegrant + ", studyDegree=" + studyDegree + ", studyDicipline=" 
                + studyDicipline + ", prodepParticipation=" + prodepParticipation 
                + ", typeTeaching=" + typeTeaching + ", eisStudyDegree=" + eisStudyDegree 
                + ", institucionalMail=" + institucionalMail + ", numberPhone=" + numberPhone 
                + ", dateBirthday=" + dateBirthday + '}';
    }
    
    
}
