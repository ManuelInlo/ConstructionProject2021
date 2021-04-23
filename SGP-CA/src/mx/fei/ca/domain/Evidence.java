    
package mx.fei.ca.domain;

public class Evidence {
    private long idEvidence;
    private String impactCA;
    private String titleEvidence;
    
    public Evidence(String impactCA, String titleEvidence){
        this.impactCA = impactCA;
        this.titleEvidence = titleEvidence;
    }

    public long getIdEvidence() {
        return idEvidence;
    }

    public String getImpactCA() {
        return impactCA;
    }

    public String getTitleEvidence() {
        return titleEvidence;
    }

    public void setIdEvidence(long idEvidence) {
        this.idEvidence = idEvidence;
    }

    public void setImpactCA(String impactCA) {
        this.impactCA = impactCA;
    }

    public void setTitleEvidence(String titleEvidence) {
        this.titleEvidence = titleEvidence;
    }
    
}
