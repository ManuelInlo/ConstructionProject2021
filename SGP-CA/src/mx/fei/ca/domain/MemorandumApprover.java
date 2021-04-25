
package mx.fei.ca.domain;


public class MemorandumApprover {
    private Integrant integrant;

    public MemorandumApprover(Integrant integrant) {
        this.integrant = integrant;
    }

    public Integrant getIntegrant() {
        return integrant;
    }

    public void setIntegrant(Integrant integrant) {
        this.integrant = integrant;
    }
    
}
