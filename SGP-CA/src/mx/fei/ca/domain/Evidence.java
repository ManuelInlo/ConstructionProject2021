
package mx.fei.ca.domain;

public class Evidence {
    private String impactCA;
    private String titleEvidence;
    private String author;
    private Integrant integrant;
    private InvestigationProject investigationProject;
    
    public Evidence(String impactCA, String titleEvidence, String author){
        this.impactCA = impactCA;
        this.titleEvidence = titleEvidence;
        this.author = author;
    }
    
    public String getImpactCA() {
        return impactCA;
    }

    public String getTitleEvidence() {
        return titleEvidence;
    }

    public String getAuthor() {
        return author;
    }

    public Integrant getIntegrant() {
        return integrant;
    }

    public InvestigationProject getInvestigationProject() {
        return investigationProject;
    }
    
    public void setImpactCA(String impactCA) {
        this.impactCA = impactCA;
    }

    public void setTitleEvidence(String titleEvidence) {
        this.titleEvidence = titleEvidence;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIntegrant(Integrant integrant) {
        this.integrant = integrant;
    }

    public void setInvestigationProject(InvestigationProject investigationProject) {
        this.investigationProject = investigationProject;
    }
    
}
