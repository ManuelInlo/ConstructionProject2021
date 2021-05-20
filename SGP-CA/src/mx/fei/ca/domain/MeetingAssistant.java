
package mx.fei.ca.domain;

import javafx.scene.control.RadioButton;

public class MeetingAssistant {
    private Integrant integrant;
    private String role;
    private RadioButton rbLeaderRole;
    private RadioButton rbTimeTakerRole;
    private RadioButton rbSecretaryRole;
    private final String nameAssistant;

    public MeetingAssistant(Integrant integrant) {
        this.integrant = integrant;
        this.nameAssistant = integrant.getName();
        this.rbLeaderRole = new RadioButton();
        this.rbTimeTakerRole = new RadioButton();
        this.rbSecretaryRole = new RadioButton();
    }

    public Integrant getIntegrant() {
        return integrant;
    }

    public String getRole() {
        return role;
    }

    public RadioButton getRbLeaderRole() {
        return rbLeaderRole;
    }

    public RadioButton getRbTimeTakerRole() {
        return rbTimeTakerRole;
    }

    public RadioButton getRbSecretaryRole() {
        return rbSecretaryRole;
    }

    public String getNameAssistant() {
        return nameAssistant;
    }

    public void setIntegrant(Integrant integrant) {
        this.integrant = integrant;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setRbLeaderRole(RadioButton rbLeaderRole) {
        this.rbLeaderRole = rbLeaderRole;
    }

    public void setRbTimeTakerRole(RadioButton rbTimeTakerRole) {
        this.rbTimeTakerRole = rbTimeTakerRole;
    }

    public void setRbSecretaryRole(RadioButton rbSecretaryRole) {
        this.rbSecretaryRole = rbSecretaryRole;
    }
    

}

