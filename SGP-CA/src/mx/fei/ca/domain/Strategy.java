package mx.fei.ca.domain;


public class Strategy {
    private int idStrategy;
    private int idTarget;
    private int strategyNumber;
    private String strategyDescription;
    private String goal;
    private String strategyAction;
    private String result;

    public Strategy() {
        //Default constructor
    }

    public Strategy(int idStrategy, int idTarget, int strategyNumber, String strategyDescription, String goal, String strategyAction, String result) {
        this.idStrategy = idStrategy;
        this.idTarget = idTarget;
        this.strategyNumber = strategyNumber;
        this.strategyDescription = strategyDescription;
        this.goal = goal;
        this.strategyAction = strategyAction;
        this.result = result;
    }

    public int getIdStrategy() {
        return idStrategy;
    }

    public void setIdStrategy(int idStrategy) {
        this.idStrategy = idStrategy;
    }

    public int getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(int idTarget) {
        this.idTarget = idTarget;
    }

    public int getStrategyNumber() {
        return strategyNumber;
    }

    public void setStrategyNumber(int strategyNumber) {
        this.strategyNumber = strategyNumber;
    }

    public String getStrategyDescription() {
        return strategyDescription;
    }

    public void setStrategyDescription(String strategyDescription) {
        this.strategyDescription = strategyDescription;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getStrategyAction() {
        return strategyAction;
    }

    public void setStrategyAction(String strategyAction) {
        this.strategyAction = strategyAction;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Strategy{" + "idStrategy=" + idStrategy + ", idTarget=" + idTarget + ", strategyNumber=" + strategyNumber + ", strategyDescription=" + strategyDescription + ", goal=" + goal + ", strategyAction=" + strategyAction + ", result=" + result + '}';
    }
        
}
