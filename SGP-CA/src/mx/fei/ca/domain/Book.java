
package mx.fei.ca.domain;

import java.sql.Date;

public class Book extends Evidence{
   private String ISBN;
   private int printing;
   private int numPages;
   private String participationType;
   private String actualState;
   private String country;
   private Date publicationDate;
   private String editorial;
   private String edition;
   private String fileRoute;
   private int idProject;
   private String curp;
   
    public Book(Evidence evidence, String ISBN, int printing, int numPages, String participationType, String actualState, 
                String country, Date publicationDate, String editorial, String edition, String fileRoute, int idProject, String curp){
        super(evidence.getImpactCA(), evidence.getTitleEvidence(), evidence.getAuthor());
        this.ISBN = ISBN;
        this.printing = printing;
        this.numPages = numPages;
        this.participationType = participationType;
        this.actualState = actualState;
        this.country = country;
        this.publicationDate = publicationDate;
        this.editorial = editorial;
        this.edition = edition;
        this.fileRoute = fileRoute;
        this.idProject = idProject;
        this.curp = curp;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getPrinting() {
        return printing;
    }

    public int getNumPages() {
        return numPages;
    }

    public String getParticipationType() {
        return participationType;
    }

    public String getActualState() {
        return actualState;
    }

    public String getCountry() {
        return country;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public String getEditorial() {
        return editorial;
    }

    public String getEdition() {
        return edition;
    }

    public String getFileRoute() {
        return fileRoute;
    }

    public int getIdProject() {
        return idProject;
    }

    public String getCurp() {
        return curp;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setPrinting(int printing) {
        this.printing = printing;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public void setParticipationType(String participationType) {
        this.participationType = participationType;
    }

    public void setActualState(String actualState) {
        this.actualState = actualState;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setFileRoute(String fileRoute) {
        this.fileRoute = fileRoute;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }   
}
