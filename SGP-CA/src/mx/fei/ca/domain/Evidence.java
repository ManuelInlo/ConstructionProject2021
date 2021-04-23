    
package mx.fei.ca.domain;

public class Evidence {
    private long idEvidence;
    private String impactCA;
    private String titleEvidence;
    private String author;
    
    public Evidence(String impactCA, String titleEvidence, String author){
        this.impactCA = impactCA;
        this.titleEvidence = titleEvidence;
        this.author = author;
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

    public String getAuthor(){
        return author;
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
    
    public void setAuthor(String author){
        this.author = author;
    }
    
}
