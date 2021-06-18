/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.fei.ca.presentation;

import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.fei.ca.domain.Book;
import mx.fei.ca.domain.Integrant;

/**
 * Clase para representar el controlador del FXML WindowBookData
 * @author Gloria Mendoza González
 * @version 18-06-2021
 */

public class WindowBookDataController implements Initializable {

    @FXML
    private Label lbUser;
    @FXML
    private Label lbTitleEvidence;
    @FXML
    private Label lbAuthor;
    @FXML
    private Label lbIsbn;
    @FXML
    private Label lbParticipationType;
    @FXML
    private Label lbActualState;
    @FXML
    private Label lbPublicationDate;
    @FXML
    private Label lbEditorial;
    @FXML
    private Label lbEdition;
    @FXML
    private Label lbInvestigationProject;
    @FXML
    private Label lbFileRoute;
    @FXML
    private Label lbNumPages;
    @FXML
    private Label lbPrinting;
    @FXML
    private Label lbCountry;
    @FXML
    private Label lbTypeEvidence;
    @FXML
    private Label lbImpactCA;
    private Integrant integrant;
    private Book book;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * Método que establece el integrante loggeado al sistema, permitiendo proyectar su nombre en la GUI
     * @param integrant Define el integrante a establecer en la GUI
     */
    
    public void setIntegrant(Integrant integrant){
        this.integrant = integrant;
        lbUser.setText(integrant.getNameIntegrant());  
    }
    
    /**
     * Método que muestra la información del libro en la GUI
     * @param book Define el libro con la información a mostrar
     */
    
    public void showBookData(Book book){
        this.book = book;
        lbTypeEvidence.setText("Libro");
        lbTitleEvidence.setText(book.getTitleEvidence());
        lbAuthor.setText(book.getAuthor());
        lbIsbn.setText(book.getIsbn());
        lbParticipationType.setText(book.getParticipationType());
        lbActualState.setText(book.getActualState());
        lbEditorial.setText(book.getEditorial());
        lbEdition.setText(book.getEdition());
        lbInvestigationProject.setText(book.getInvestigationProject().getTittleProject());        
        lbFileRoute.setText(book.getFileRoute());
        lbNumPages.setText(String.valueOf(book.getNumPages()));
        lbPrinting.setText(String.valueOf(book.getPrinting()));
        lbCountry.setText(book.getCountry());
        lbImpactCA.setText(book.getImpactCA());
        if(book.getPublicationDate() != null){
            lbPublicationDate.setText(convertDateToString(book.getPublicationDate()));
        }    
    }
    
    
    /**
     * Método que convierte una variable de tipo Date a String con la finalidad de poder mostrar la fecha en la GUI
     * @param date Define la fecha a convertir de Date a String
     * @return String con la fecha convertida
     */
    
    private String convertDateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = dateFormat.format(date);
        return stringDate;
    }
    
    /**
     * Método que manda a abrir la ventana de modificación de libro
     * @param event Define el evento generado
     */
    
    @FXML
    private void modifyBook(ActionEvent event) {
    }
    
    /**
     * Método que cierra la ventana actual "Datos de libro"
     * @param event Define el evento generado
     */

    @FXML
    private void closeBookData(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
}
