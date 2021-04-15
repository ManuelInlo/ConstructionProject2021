
package mx.fei.ca.domain;


public class ChapterBook extends Evidence{
    private int homepage;
    private int endPage;
    private int id;

    public ChapterBook(int homepage, int endPage, String impactCA, String titleEvidence, String author) {
        super(impactCA, titleEvidence, author);
        this.homepage = homepage;
        this.endPage = endPage;
    }

    public int getHomepage() {
        return homepage;
    }

    public int getEndPage() {
        return endPage;
    }

    public int getId() {
        return id;
    }

    public void setHomepage(int homepage) {
        this.homepage = homepage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public void setId(int id) {
        this.id = id;
    }
}
