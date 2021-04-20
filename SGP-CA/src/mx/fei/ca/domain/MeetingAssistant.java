
package mx.fei.ca.domain;

public class MeetingAssistant {
    private Integrant integrant;
    String role;

    public MeetingAssistant(Integrant integrant, String role) {
        this.integrant = integrant;
        this.role = role;
    }

    public Integrant getIntegrant() {
        return integrant;
    }

    public String getRole() {
        return role;
    }

    public void setIntegrant(Integrant integrant) {
        this.integrant = integrant;
    }

    public void setRole(String role) {
        this.role = role;
    }
 
}

