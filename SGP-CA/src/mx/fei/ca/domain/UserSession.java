
package mx.fei.ca.domain;

public final class UserSession {
    private static UserSession userSession;
    private Integrant integrant;
    
    private UserSession(Integrant integrant){
        this.integrant = integrant;
    }
    
    public static UserSession getUserSession(Integrant integrant){
        if(userSession == null){
            userSession = new UserSession(integrant);
        }
        return userSession;
    }
    
    public void cleanUserSession(){
        integrant = null;
    }

    public Integrant getIntegrant() {
        return integrant;
    }
    
}
