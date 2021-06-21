package mx.fei.ca.domain;

import javafx.scene.control.RadioButton;

/**
 * Clase para representar a un asistente de una reunión del CA
 * Cada asistente se determina por el integrante asociado, un rol, RadioButtons pertenecientes a los roles y el nombre del asistente
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class MeetingAssistant {
    private Integrant integrant;
    private String role;
    private RadioButton rbLeaderRole;
    private RadioButton rbTimeTakerRole;
    private RadioButton rbSecretaryRole;
    private final String nameAssistant;

    /**
     * Constructor para la creación de un asistente de reunión
     * @param integrant Define el integrante asociado al asistente de reunión
     */
    public MeetingAssistant(Integrant integrant){
        this.integrant = integrant;
        this.nameAssistant = integrant.getNameIntegrant();
        this.rbLeaderRole = new RadioButton();
        this.rbTimeTakerRole = new RadioButton();
        this.rbSecretaryRole = new RadioButton();
    }
    
    /**
     * 
     * @return El integrante asociado al asistente de reunión
     */
    
    public Integrant getIntegrant() {
        return integrant;
    }

    /**
     * 
     * @return El rol del asistente de reunión
     */
    
    public String getRole() {
        return role;
    }

    /**
     * 
     * @return El RadioButton del rol de líder del asistente
     */
    
    public RadioButton getRbLeaderRole() {
        return rbLeaderRole;
    }
    
    /**
     * 
     * @return El RadioButton del rol de tomador de tiempo del asistente
     */

    public RadioButton getRbTimeTakerRole() {
        return rbTimeTakerRole;
    }
    
    /**
     * 
     * @return El RadioButton del rol de secretario del asistente
     */

    public RadioButton getRbSecretaryRole() {
        return rbSecretaryRole;
    }
    
    /**
     * 
     * @return El nombre del asistente de reunión
     */

    public String getNameAssistant() {
        return this.getIntegrant().getNameIntegrant();
    }
    
    /**
     * 
     * @param integrant El integrante a establecer al asistente de reunión
     */
    
    public void setIntegrant(Integrant integrant) {
        this.integrant = integrant;
    }
    
    /**
     * 
     * @param role El rol a establecer al asistente de reunión
     */

    public void setRole(String role) {
        this.role = role;
    }
    
    /**
     * 
     * @param rbLeaderRole El RadioButton de rol líder a establecer al asistente de reunión
     */
    
    public void setRbLeaderRole(RadioButton rbLeaderRole) {
        this.rbLeaderRole = rbLeaderRole;
    }
    
    /**
     * 
     * @param rbTimeTakerRole El RadioButton del rol tomador de tiempo a establecer al asistente de reunión
     */

    public void setRbTimeTakerRole(RadioButton rbTimeTakerRole) {
        this.rbTimeTakerRole = rbTimeTakerRole;
    }
    
    /**
     * 
     * @param rbSecretaryRole El RadioButton del rol secretario a establecer al asistente de reunión
     */
        
    public void setRbSecretaryRole(RadioButton rbSecretaryRole) {
        this.rbSecretaryRole = rbSecretaryRole;
    }
    
}

