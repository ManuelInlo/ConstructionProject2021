
package mx.fei.ca.domain;

public class ChapterBook extends Evidence{
    private int chapterNumber;
    private int homepage;
    private int endPage;
    private String ISBN;
    private int idProject; 
    private String curp;
    
    public ChapterBook(Evidence evidence, int chapterNumber, int homepage, int endPage, String ISBN, int idProject, String curp){
        super(evidence.getImpactCA(), evidence.getTitleEvidence(), evidence.getAuthor());
        this.chapterNumber = chapterNumber;
        this.homepage = homepage;
        this.endPage = endPage;
        this.ISBN = ISBN;
        this.idProject = idProject;
        this.curp = curp;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }
   
    public int getHomepage() {
        return homepage;
    }

    public int getEndPage() {
        return endPage;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getIdProject() {
        return idProject;
    }

    public String getCurp() {
        return curp;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public void setHomepage(int homepage) {
        this.homepage = homepage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }
        
}