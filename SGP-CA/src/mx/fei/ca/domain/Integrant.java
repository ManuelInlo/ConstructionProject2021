
package mx.fei.ca.domain;

import java.sql.Date;

public class Integrant {
    private String curp;
    private String role;
    private String nameIntegrant;
    private String studyDegree;
    private String studyDiscipline;  
    private String prodepParticipation;
    private String typeTeaching;
    private String iesStudyDegree;
    private String institutionalMail;
    private String numberPhone;
    private Date dateBirthday;
    private String statusIntegrant;
    private String password;
   
    public Integrant(String curp, String role, String nameIntegrant, String studyDegree, String studyDiscipline,
                    String prodepParticipation, String typeTeaching, String iesStudyDegree, String institutionalMail, String numberPhone, Date dateBirthday, String statusIntegrant){
        this.curp = curp;
        this.role = role;
        this.nameIntegrant = nameIntegrant;
        this.studyDegree = studyDegree;
        this.studyDiscipline = studyDiscipline;  
        this.prodepParticipation = prodepParticipation;
        this.typeTeaching = typeTeaching;
        this.iesStudyDegree = iesStudyDegree;
        this.institutionalMail = institutionalMail;
        this.numberPhone = numberPhone;
        this.dateBirthday = dateBirthday; 
        this.statusIntegrant = statusIntegrant;
    }

    public String getCurp() {
        return curp;
    }

    public String getRole() {
        return role;
    }

    public String getNameIntegrant() {
        return nameIntegrant;
    }

    public String getStudyDegree() {
        return studyDegree;
    }

    public String getStudyDiscipline() {
        return studyDiscipline;
    }

    public String getProdepParticipation() {
        return prodepParticipation;
    }

    public String getTypeTeaching() {
        return typeTeaching;
    }

    public String getIesStudyDegree() {
        return iesStudyDegree;
    }

    public String getInstitutionalMail() {
        return institutionalMail;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public Date getDateBirthday() {
        return dateBirthday;
    }
    
    public String getStatusIntegrant() {
        return statusIntegrant;
    }    

    public String getPassword() {
        return password;
    }
    
    public void setCurp(String curp) {
        this.curp = curp;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setNameIntegrant(String nameIntegrant) {
        this.nameIntegrant = nameIntegrant;
    }

    public void setStudyDegree(String studyDegree) {
        this.studyDegree = studyDegree;
    }

    public void setStudyDiscipline(String studyDiscipline) {
        this.studyDiscipline = studyDiscipline;
    }

    public void setProdepParticipation(String prodepParticipation) {
        this.prodepParticipation = prodepParticipation;
    }

    public void setTypeTeaching(String typeTeaching) {
        this.typeTeaching = typeTeaching;
    }

    public void setIesStudyDegree(String iesStudyDegree) {
        this.iesStudyDegree = iesStudyDegree;
    }

    public void setInstitutionalMail(String institutionalMail) {
        this.institutionalMail = institutionalMail;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public void setDateBirthday(Date dateBirthday) {
        this.dateBirthday = dateBirthday;
    }   

    public void setStatusIntegrant(String statusIntegrant) {
        this.statusIntegrant = statusIntegrant;
    } 

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String toString(){
        return "";
    }
}
