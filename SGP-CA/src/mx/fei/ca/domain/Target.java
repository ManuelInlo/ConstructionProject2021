package mx.fei.ca.domain;


public class Target {
    private int idTarget;
    private int keyCodePlan;
    private String targetTittle;
    private String targetDescription;

    public Target() {
       //Default constructor
    }
    
    
    public Target(int idTarget, int keyCodePlan, String targetTittle, String targetDescription) {
        this.idTarget = idTarget;
        this.keyCodePlan = keyCodePlan;
        this.targetTittle = targetTittle;
        this.targetDescription = targetDescription;
    }

    public int getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(int idTarget) {
        this.idTarget = idTarget;
    }

    public int getKeyCodePlan() {
        return keyCodePlan;
    }

    public void setKeyCodePlan(int keyCodePlan) {
        this.keyCodePlan = keyCodePlan;
    }

    public String getTargetTittle() {
        return targetTittle;
    }

    public void setTargetTittle(String targetTittle) {
        this.targetTittle = targetTittle;
    }

    public String getTargetDescription() {
        return targetDescription;
    }

    public void setTargetDescription(String targetDescription) {
        this.targetDescription = targetDescription;
    }

    @Override
    public String toString() {
        return "Target{" + "idTarget=" + idTarget + ", keyCodePlan=" + keyCodePlan + ", targetTittle=" + targetTittle + ", targetDescription=" + targetDescription + '}';
    }
          
}
