
package mx.fei.ca.domain;

import java.sql.Date;

/**
 * Clase para representar una evidencia de tipo artículo de un integrante del CA
 * @author Gloria Mendoza González
 * @version 16-06-2021
 */

public class Article extends Evidence{
   private String issn;
   private String fileRoute;
   private int homePage;
   private int endPage;
   private String actualState;
   private String magazineName;
   private String country;
   private Date publicationDate;
   private int volume;
   private String editorial;
   private String description;
   private InvestigationProject investigationProject;
   private String curp;
   
   /**
    * Constructor para la creación de una evidencia de tipo artículo con estado actual de "Publicado"
    * @param evidence Define la clase padre de un artículo que contiene los atributos de impacto al CA, nombre de la evidencia y autor
    * @param issn Define la clave única del artículo
    * @param fileRoute Define la ruta del artículo
    * @param homePage Define el inicio de página del artículo en una revista
    * @param endPage Define el final de página del artículo en una revista
    * @param actualState Define al estado actual del artículo
    * @param magazineName Define el nombre de la revista en la que aparecerá el artículo
    * @param country Define el país de la revista donde se realizo el artículo
    * @param publicationDate Define la fecha de publicación del artículo
    * @param volume Define el volumen de la revista en la que se realizo el artículo
    * @param editorial Define la editorial de la revista en la que se realizo el artículo
    * @param description Define la descripción general del artículo
    */
   
    public Article(Evidence evidence, String issn, String fileRoute, int homePage,
                  int endPage, String actualState, String magazineName, String country, Date publicationDate, int volume, 
                  String editorial, String description){
       super(evidence.getImpactCA(), evidence.getTitleEvidence(), evidence.getAuthor());
       this.issn = issn;
       this.fileRoute = fileRoute;
       this.homePage = homePage;
       this.endPage = endPage;
       this.actualState = actualState;
       this.magazineName = magazineName;
       this.country = country;
       this.publicationDate = publicationDate;
       this.volume = volume;
       this.editorial = editorial;
       this.description = description;
    }
    
       
   /**
    * Constructor para la creación de una evidencia de tipo artículo con estado actual de "En progreso o Terminado"
    * @param evidence Define la clase padre de un artículo que contiene los atributos de impacto al CA, nombre de la evidencia y autor
    * @param issn Define la clave única del artículo
    * @param fileRoute Define la ruta del artículo
    * @param homePage Define el inicio de página del artículo en una revista
    * @param endPage Define el final de página del artículo en una revista
    * @param actualState Define al estado actual del artículo
    * @param magazineName Define el nombre de la revista en la que aparecerá el artículo
    * @param country Define el país de la revista donde se realizo el artículo
    * @param volume Define el volumen de la revista en la que se realizo el artículo
    * @param editorial Define la editorial de la revista en la que se realizo el artículo
    * @param description Define la descripción general del artículo
    */
   
    public Article(Evidence evidence, String issn, String fileRoute, int homePage,
                  int endPage, String actualState, String magazineName, String country, int volume, 
                  String editorial, String description){
       super(evidence.getImpactCA(), evidence.getTitleEvidence(), evidence.getAuthor());
       this.issn = issn;
       this.fileRoute = fileRoute;
       this.homePage = homePage;
       this.endPage = endPage;
       this.actualState = actualState;
       this.magazineName = magazineName;
       this.country = country;
       this.volume = volume;
       this.editorial = editorial;
       this.description = description;
    }
    
    
    /**
     * 
     * @return La clave única del artículo
     */
    
    public String getIssn() {
        return issn;
    }
    
    /**
     * 
     * @return La ruta del archivo del artículo
     */
    
    public String getFileRoute() {
        return fileRoute;
    }

    /**
     * 
     * @return La página de inicio del artículo en una revista
     */
    
    public int getHomepage() {
        return homePage;
    }

    /**
     * 
     * @return La página final del artículo en una revista
     */
    
    public int getEndPage() {
        return endPage;
    }

    /**
     * 
     * @return El estado actual del artículo
     */
    
    public String getActualState() {
        return actualState;
    }
    
    /**
     * 
     * @return El nombre de la revista en la que se publico el artículo
     */

    public String getMagazineName() {
        return magazineName;
    }

    /**
     * 
     * @return El país de la revista donde se publico el artículo
     */
    
    public String getCountry() {
        return country;
    }
    
    /**
     * 
     * @return La fecha de publicación del artículo
     */

    public Date getPublicationDate() {
        return publicationDate;
    }
    
    /**
     * 
     * @return El número de volumen de la revista donde se publico el artículo
     */

    public int getVolume() {
        return volume;
    }
    
    /**
     * 
     * @return La editorial de la revista donde se publico el artículo
     */

    public String getEditorial() {
        return editorial;
    }

    /**
     * 
     * @return La descrición general del artículo
     */
    
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @return El proyecto de investigación asociado a la evidencia de tipo artículo
     */
    
    public InvestigationProject getInvestigationProject() {
        return investigationProject;
    }

    /**
     * 
     * @return La curp del integrante asociado a la evidencia de tipo artículo
     */
    
    public String getCurp() {
        return curp;
    }
        
    /**
     * 
     * @param issn La clave única a establecer al artículo
     */
    
    public void setIssn(String issn) {
        this.issn = issn;
    }

    /**
     * 
     * @param fileRoute La ruta del archivo a establecer al artículo
     */
    
    public void setFileRoute(String fileRoute) {
        this.fileRoute = fileRoute;
    }
    
    /**
     * 
     * @param homepage La página de inicio a establecer al artículo
     */

    public void setHomepage(int homepage) {
        this.homePage = homepage;
    }
    
    /**
     * 
     * @param endPage La página final a establecer al artículo
     */

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
    
    /**
     * 
     * @param actualState El estado actual a establecer al artículo
     */

    public void setActualState(String actualState) {
        this.actualState = actualState;
    }
    
    /**
     * 
     * @param magazineName El nombre de la revista a establecer al artículo
     */

    public void setMagazineName(String magazineName) {
        this.magazineName = magazineName;
    }
    
    /**
     * 
     * @param country El país de la revista a establecer al artículo
     */

    public void setCountry(String country) {
        this.country = country;
    }
    
    /**
     * 
     * @param publicationDate La fecha de publicación a establecer al artículo
     */

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
    
    /**
     * 
     * @param volume El volumen de la revista a establecer al artículo
     */

    public void setVolume(int volume) {
        this.volume = volume;
    }
    
    /**
     * 
     * @param editorial La editorial de la revista a establecer al artículo
     */

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    
    /**
     * 
     * @param description La descripción general del tema a tratar a establecer al artíclo 
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @param investigationProject El proyecto de investigación a establecer al artículo
     */

    public void setInvestigationProject(InvestigationProject investigationProject) {
        this.investigationProject = investigationProject;
    }
    
    /**
     * 
     * @param curp La curp del integrante a establecer al artículo
     */
    
    public void setCurp(String curp) {
        this.curp = curp;
    }    
    
}
