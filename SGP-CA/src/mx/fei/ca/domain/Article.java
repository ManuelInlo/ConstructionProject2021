
package mx.fei.ca.domain;

import java.sql.Date;

public class Article extends Evidence{
   private String ISSN;
   private String fileRoute;
   private int homepage;
   private int endPage;
   private String actualState;
   private String magazineName;
   private String country;
   private Date publicationDate;
   private int volume;
   private String editorial;
   private String author;
   private String description;
   
   public Article(String impactCA, String titleEvidence, String ISSN, String fileRoute, int homepage,
                  int endPage, String actualState, String magazineName, String country, Date publicationDate, int volume, 
                  String editorial, String author, String description){
       
       super(impactCA, titleEvidence);
       this.ISSN = ISSN;
       this.fileRoute = fileRoute;
       this.homepage = homepage;
       this.endPage = endPage;
       this.actualState = actualState;
       this.magazineName = magazineName;
       this.country = country;
       this.publicationDate = publicationDate;
       this.volume = volume;
       this.editorial = editorial;
       this.author = author;
       this.description = description;
   }

    public String getISSN() {
        return ISSN;
    }

    public String getFileRoute() {
        return fileRoute;
    }

    public int getHomepage() {
        return homepage;
    }

    public int getEndPage() {
        return endPage;
    }

    public String getActualState() {
        return actualState;
    }

    public String getMagazineName() {
        return magazineName;
    }

    public String getCountry() {
        return country;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public int getVolume() {
        return volume;
    }

    public String getEditorial() {
        return editorial;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    public void setFileRoute(String fileRoute) {
        this.fileRoute = fileRoute;
    }

    public void setHomepage(int homepage) {
        this.homepage = homepage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public void setActualState(String actualState) {
        this.actualState = actualState;
    }

    public void setMagazineName(String magazineName) {
        this.magazineName = magazineName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
