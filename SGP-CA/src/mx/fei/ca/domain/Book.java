
package mx.fei.ca.domain;

import java.sql.Date;
import java.util.ArrayList;

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
    private ArrayList<ChapterBook> chaptersBook;

    public Book(String ISBN, int printing, int numPages, String participationType,
                String actualState, String country, Date publicationDate, String editorial, String edition, 
                String impactCA, String titleEvidence, String author){
        super(impactCA, titleEvidence, author);
        this.ISBN = ISBN;
        this.printing = printing;
        this.numPages = numPages;
        this.participationType = participationType;
        this.actualState = actualState;
        this.country = country;
        this.publicationDate = publicationDate;
        this.editorial = editorial;
        this.edition = edition;
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

    public ArrayList<ChapterBook> getChaptersBook() {
        return chaptersBook;
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

    public void setChaptersBook(ArrayList<ChapterBook> chaptersBook) {
        this.chaptersBook = chaptersBook;
    }
    
}
