
package mx.fei.ca.domain;

/**
 * Clase para representar una evidencia de tipo capítulo de libro de un integrante del CA
 * @author Gloria Mendoza González
 * @version 16-06-2021
 */

public class ChapterBook extends Evidence{
    private int idChapterBook;
    private int chapterNumber;
    private int homePage;
    private int endPage;
    private Book book;
    private InvestigationProject investigationProject;
    private String curp;
    
    /**
     * Constructor para la creación de una evidencia de tipo capítulo de libro
     * @param evidence Define la clase padre de un capítulo de libro que contiene los atributos de impacto al CA, nombre de la evidencia y autor
     * @param chapterNumber Define el número del capítulo de libro
     * @param homePage Define la página de inicio del capítulo de libro
     * @param endPage Define la página final del capítulo de libro
     */
    
    public ChapterBook(Evidence evidence, int chapterNumber, int homePage, int endPage){
        super(evidence.getImpactCA(), evidence.getTitleEvidence(), evidence.getAuthor());
        this.chapterNumber = chapterNumber;
        this.homePage = homePage;
        this.endPage = endPage;
    }
    
    /**
     * 
     * @return El identificador del capítulo de libro
     */
    
    public int getIdChapterBook() {
        return idChapterBook;
    }
    
    /**
     * 
     * @return El número del capítulo de libro
     */

    public int getChapterNumber() {
        return chapterNumber;
    }
    
    /**
     * 
     * @return El número de la página de inicio del capítulo de libro
     */

    public int getHomePage() {
        return homePage;
    }
    
    /**
     * 
     * @return El número de la página final del capítulo de libro
     */

    public int getEndPage() {
        return endPage;
    }
    
    /**
     * 
     * @return El libro al que esta asociado la evidencia de tipo capítulo de libro
     */

    public Book getBook() {
        return book;
    }
    
    /**
     * 
     * @return El proyecto de investigación asociado a la evidencia de tipo capítulo de libro
     */

    public InvestigationProject getInvestigationProject() {
        return investigationProject;
    }
    
    /**
     * 
     * @return La curp del integrante asociado a la evidencia de tipo capítulo de libro
     */

    public String getCurp() {
        return curp;
    }
    
    /**
     * 
     * @param idChapterBook El identificado a establecer al capítulo de libro
     */
    
    public void setIdChapterBook(int idChapterBook) {
        this.idChapterBook = idChapterBook;
    }
    
    /**
     * 
     * @param chapterNumber El número a establecer al capítulo de libro
     */

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }
    
    /**
     * 
     * @param homePage La página de inicio a establecer al capítulo de libro
     */

    public void setHomePage(int homePage) {
        this.homePage = homePage;
    }
    
    /**
     * 
     * @param endPage La página final a establecer al capítulo de libro
     */

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
    
    /**
     * 
     * @param book El libro a establecer al capítulo de libro
     */

    public void setBook(Book book) {
        this.book = book;
    }
    
    /**
     * 
     * @param investigationProject El proyecto de investigación a establecer al capítulo de libro
     */

    public void setInvestigationProject(InvestigationProject investigationProject) {
        this.investigationProject = investigationProject;
    }
    
    /**
     * 
     * @param curp La curp del integrante a establecer al capítulo de libro
     */

    public void setCurp(String curp) {
        this.curp = curp;
    }
       
}