
package mx.fei.ca.domain;

import java.sql.Date;

/**
 * Clase para representar una evidencia de tipo libro de un integrante del CA
 * @author Gloria Mendoza González
 * @version 16-06-2021
 */

public class Book extends Evidence{
   private String isbn;
   private int printing;
   private int numPages;
   private String participationType;
   private String actualState;
   private String country;
   private Date publicationDate;
   private String editorial;
   private String edition;
   private String fileRoute;
   private InvestigationProject investigationProject;
   private String curp;
   
   /**
    * Constructor para la creación de una evidencia de tipo libro con estado actual de "Publicado"
    * @param evidence Define la clase padre de un libro que contiene los atributos de impacto al CA, nombre de la evidencia y autor
    * @param isbn Define la clave única del libro
    * @param printing Define el número de impresión del libro 
    * @param numPages Define el número total de páginas del libro
    * @param participationType Define el tipo de participación del autor en la realización del libro
    * @param actualState Define al estado actual del libro
    * @param country Define el país donde se realizo el libro
    * @param publicationDate Define la fecha de publicación del libro
    * @param editorial Define la editorial del libro
    * @param edition Define la edición del libro
    * @param fileRoute Define la ruta del libro
    */
   
    public Book(Evidence evidence, String isbn, int printing, int numPages, String participationType, String actualState, 
                String country, Date publicationDate, String editorial, String edition, String fileRoute){
        super(evidence.getImpactCA(), evidence.getTitleEvidence(), evidence.getAuthor());
        this.isbn = isbn;
        this.printing = printing;
        this.numPages = numPages;
        this.participationType = participationType;
        this.actualState = actualState;
        this.country = country;
        this.publicationDate = publicationDate;
        this.editorial = editorial;
        this.edition = edition;
        this.fileRoute = fileRoute;
    }
    
       /**
    * Constructor para la creación de una evidencia de tipo libro con estado actual de "En progreso o Terminado"
    * @param evidence Define la clase padre de un libro que contiene los atributos de impacto al CA, nombre de la evidencia y autor
    * @param isbn Define la clave única del libro
    * @param printing Define el número de impresión del libro 
    * @param numPages Define el número total de páginas del libro
    * @param participationType Define el tipo de participación del autor en la realización del libro
    * @param actualState Define al estado actual del libro
    * @param country Define el país donde se realizo el libro
    * @param editorial Define la editorial del libro
    * @param edition Define la edición del libro
    * @param fileRoute Define la ruta del libro
    */
   
    public Book(Evidence evidence, String isbn, int printing, int numPages, String participationType, String actualState, 
                String country, String editorial, String edition, String fileRoute){
        super(evidence.getImpactCA(), evidence.getTitleEvidence(), evidence.getAuthor());
        this.isbn = isbn;
        this.printing = printing;
        this.numPages = numPages;
        this.participationType = participationType;
        this.actualState = actualState;
        this.country = country;
        this.editorial = editorial;
        this.edition = edition;
        this.fileRoute = fileRoute;
    }

    /**
     * 
     * @return La clave única del libro
     */
    
    public String getIsbn() {
        return isbn;
    }
    
    /**
     * 
     * @return El número de impresión del libro
     */

    public int getPrinting() {
        return printing;
    }
    
    /**
     * 
     * @return El número de páginas del libro
     */

    public int getNumPages() {
        return numPages;
    }
    
    /**
     * 
     * @return El tipo de participación del autor en la elaboración del libro
     */

    public String getParticipationType() {
        return participationType;
    }
    
    /**
     * 
     * @return El estado actual del libro
     */

    public String getActualState() {
        return actualState;
    }
    
    /**
     * 
     * @return El país donde se realizo el libro
     */

    public String getCountry() {
        return country;
    }
    
    /**
     * 
     * @return La fecha de publicación del libro
     */

    public Date getPublicationDate() {
        return publicationDate;
    }
    
    /**
     * 
     * @return La editorial del libro
     */

    public String getEditorial() {
        return editorial;
    }
    
    /**
     * 
     * @return La edición del libro
     */

    public String getEdition() {
        return edition;
    }
    
    /**
     * 
     * @return La ruta del archivo del libro
     */

    public String getFileRoute() {
        return fileRoute;
    }
    
    /**
     * 
     * @return El proyecto de investigación asociado a la evidencia de tipo libro
     */

    public InvestigationProject getInvestigationProject() {
        return investigationProject;
    }

    /**
     * 
     * @return La curp del integrante asociado a la evidencia de tipo libro
     */
    
    public String getCurp() {
        return curp;
    }
    
    /**
     * 
     * @param isbn La clave única a establecer al libro
     */

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    /**
     * 
     * @param printing El número de impresión a establecer al libro
     */

    public void setPrinting(int printing) {
        this.printing = printing;
    }
    
    /**
     * 
     * @param numPages El número de páginas a establecer al libro
     */

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }
    
    /**
     * 
     * @param participationType El tipo de participación del autor a establecer al libro
     */

    public void setParticipationType(String participationType) {
        this.participationType = participationType;
    }
    
    /**
     * 
     * @param actualState El estado actual a establecer al libro
     */

    public void setActualState(String actualState) {
        this.actualState = actualState;
    }
    
    /**
     * 
     * @param country El país a establecer al libro
     */

    public void setCountry(String country) {
        this.country = country;
    }
    
    /**
     * 
     * @param publicationDate La fecha de publicación a establecer al libro
     */

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
    
    /**
     * 
     * @param editorial La editorial a establecer al libro
     */

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    
    /**
     * 
     * @param edition La edición a establecer al libro
     */

    public void setEdition(String edition) {
        this.edition = edition;
    }
    
    /**
     * 
     * @param fileRoute La ruta de archivo a establecer al libro
     */

    public void setFileRoute(String fileRoute) {
        this.fileRoute = fileRoute;
    }

    /**
     * 
     * @param investigationProject El proyecto de investigación a establecer al libro
     */

    public void setInvestigationProject(InvestigationProject investigationProject) {
        this.investigationProject = investigationProject;
    }

    /**
     * 
     * @param curp La curp del integrante a establecer al libro
     */
    
    public void setCurp(String curp) {
        this.curp = curp;
    }   
}
