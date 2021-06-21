
package mx.fei.ca.domain;

import java.sql.Date;

/**
 * Clase para representar un integrante del CA
 * @author Gloria Mendoza González
 * @version 16-06-2021
 */

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
    
    /**
     * Constructor para la creación de un integrante del CA
     * @param curp Define la curp del integrante
     * @param role Define el rol que toma el integrante dentro del CA
     * @param nameIntegrant Define el nombre del integrante
     * @param studyDegree Define el grado de estudios máximo del integrante 
     * @param studyDiscipline Define la disciplina de estudio del integrante
     * @param prodepParticipation Define si un integrante se encuentra participando en PRODEP
     * @param typeTeaching Define el tipo de docencia del integrante
     * @param iesStudyDegree Define el EIS de máximo grado de estudios del integrante
     * @param institutionalMail Define el correo institucional del
     * @param numberPhone Define el numero de teléfono del integrante
     * @param dateBirthday Define la fecha de nacimiento del integrante
     * @param statusIntegrant  Define el estado de actividad del integrante
     */
   
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
    
    /**
     * 
     * @return La curp del integrante
     */

    public String getCurp() {
        return curp;
    }
    
    /**
     * 
     * @return El rol que desempeña el integrante en el CA
     */

    public String getRole() {
        return role;
    }
    
    /**
     * 
     * @return El nombre del integrante
     */

    public String getNameIntegrant() {
        return nameIntegrant;
    }
    
    /**
     * 
     * @return El máximo grado de estudios del integrante
     */

    public String getStudyDegree() {
        return studyDegree;
    }
    
    /**
     * 
     * @return La disciplina de estudio del integrante
     */

    public String getStudyDiscipline() {
        return studyDiscipline;
    }
    
    /**
     * 
     * @return El estado de participación del integrante en PRODEP
     */

    public String getProdepParticipation() {
        return prodepParticipation;
    }
    
    /**
     * 
     * @return El tipo de docencia del integrante
     */

    public String getTypeTeaching() {
        return typeTeaching;
    }
    
    /**
     * 
     * @return El IES de máximo grado de estudios del integrante
     */

    public String getIesStudyDegree() {
        return iesStudyDegree;
    }
    
    /**
     * 
     * @return El correo institucional del integrante
     */

    public String getInstitutionalMail() {
        return institutionalMail;
    }
    
    /**
     * 
     * @return El número de teléfono del integrante
     */

    public String getNumberPhone() {
        return numberPhone;
    }
    
    /**
     * 
     * @return La fecha de nacimiento del integrante
     */

    public Date getDateBirthday() {
        return dateBirthday;
    }
    
    /**
     * 
     * @return El estado de actividad del integrante dentro del CA
     */
    
    public String getStatusIntegrant() {
        return statusIntegrant;
    }    
    
    /**
     * 
     * @return La contraseña de inicio de sesión del integrante
     */

    public String getPassword() {
        return password;
    }
    
    /**
     * 
     * @param curp La curp a establecer al integrante
     */
    
    public void setCurp(String curp) {
        this.curp = curp;
    }
    
    /**
     * 
     * @param role El rol dentro del CA a establecer al integrante
     */

    public void setRole(String role) {
        this.role = role;
    }
    
    /**
     * 
     * @param nameIntegrant El nombre a establecer al integrante
     */

    public void setNameIntegrant(String nameIntegrant) {
        this.nameIntegrant = nameIntegrant;
    }
    
    /**
     * 
     * @param studyDegree El grado de estudios a establecer al integrante
     */

    public void setStudyDegree(String studyDegree) {
        this.studyDegree = studyDegree;
    }
    
    /**
     * 
     * @param studyDiscipline  La disciplina de estudio a establecer al integrante
     */

    public void setStudyDiscipline(String studyDiscipline) {
        this.studyDiscipline = studyDiscipline;
    }
    
    /**
     * 
     * @param prodepParticipation  La participación en PRODEP a establecer al integrante
     */

    public void setProdepParticipation(String prodepParticipation) {
        this.prodepParticipation = prodepParticipation;
    }
    
    /**
     * 
     * @param typeTeaching El tipo de docencia a establecer al integrante
     */

    public void setTypeTeaching(String typeTeaching) {
        this.typeTeaching = typeTeaching;
    }
    
    /**
     * 
     * @param iesStudyDegree El IES de máximo grado de estudios a establecer al integrante
     */

    public void setIesStudyDegree(String iesStudyDegree) {
        this.iesStudyDegree = iesStudyDegree;
    }
    
    /**
     * 
     * @param institutionalMail El correo institucional a establecer al integrante
     */

    public void setInstitutionalMail(String institutionalMail) {
        this.institutionalMail = institutionalMail;
    }
    
    /**
     * 
     * @param numberPhone El número de teléfono a establecer al integrante
     */

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
    
    /**
     * 
     * @param dateBirthday La fecha de nacimiento a establecer al integrante
     */

    public void setDateBirthday(Date dateBirthday) {
        this.dateBirthday = dateBirthday;
    }   
    
    /**
     * 
     * @param statusIntegrant El estado de actividad dentro del CA a establecer al integrante
     */

    public void setStatusIntegrant(String statusIntegrant) {
        this.statusIntegrant = statusIntegrant;
    } 
    
    /**
     * 
     * @param password La contraseña de inicio de sesión a establecer al integrante
     */

    public void setPassword(String password) {
        this.password = password;
    }
    
}
