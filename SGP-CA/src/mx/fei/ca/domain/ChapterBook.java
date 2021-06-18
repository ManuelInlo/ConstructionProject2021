
package mx.fei.ca.domain;

/**
 * 
 * @author Gloria
 */

public class ChapterBook extends Evidence{
    private int chapterNumber;
    private int homePage;
    private int endPage;
    private Book book;
    private InvestigationProject investigationProject;
    private String curp;
    
    public ChapterBook(Evidence evidence, int chapterNumber, int homePage, int endPage){
        super(evidence.getImpactCA(), evidence.getTitleEvidence(), evidence.getAuthor());
        this.chapterNumber = chapterNumber;
        this.homePage = homePage;
        this.endPage = endPage;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public int getHomePage() {
        return homePage;
    }

    public int getEndPage() {
        return endPage;
    }

    public Book getBook() {
        return book;
    }

    public InvestigationProject getInvestigationProject() {
        return investigationProject;
    }

    public String getCurp() {
        return curp;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public void setHomePage(int homePage) {
        this.homePage = homePage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setInvestigationProject(InvestigationProject investigationProject) {
        this.investigationProject = investigationProject;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }
       
}