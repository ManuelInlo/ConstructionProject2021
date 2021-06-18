
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
import mx.fei.ca.domain.Article;
import mx.fei.ca.domain.Integrant;

/**
 * Clase para representar el controlador del FXML WindowArticleData
 * @author Gloria Mendoza González
 * @version 18-06-2021
 */

public class WindowArticleDataController implements Initializable {

    @FXML
    private Label lbUser;
    @FXML
    private Label lbTitleEvidence;
    @FXML
    private Label lbAuthor;
    @FXML
    private Label lbActualState;
    @FXML
    private Label lbIssn;
    @FXML
    private Label lbMagazineName;
    @FXML
    private Label lbCountry;
    @FXML
    private Label lbPublicationDate;
    @FXML
    private Label lbVolume;
    @FXML
    private Label lbInvestigationProject;
    @FXML
    private Label lbFileRoute;
    @FXML
    private Label lbHomePage;
    @FXML
    private Label lbEndPage;
    @FXML
    private Label lbTypeEvidence;
    @FXML
    private Label lbImpactCA;
    private Integrant integrant;
    private Article article;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
     * Método que muestra la información del artículo en la GUI
     * @param article Define el artículo con la información a mostrar
     */
    
    public void showArticleData(Article article){
        this.article = article;
        lbTypeEvidence.setText("Artículo");
        lbTitleEvidence.setText(article.getTitleEvidence());
        lbAuthor.setText(article.getAuthor());
        lbActualState.setText(article.getActualState());
        lbIssn.setText(article.getIssn());
        lbMagazineName.setText(article.getMagazineName());
        lbCountry.setText(article.getCountry());
        lbVolume.setText(String.valueOf(article.getVolume()));
        lbInvestigationProject.setText(article.getInvestigationProject().getTittleProject());
        lbFileRoute.setText(article.getFileRoute());
        lbHomePage.setText(String.valueOf(article.getHomepage()));
        lbEndPage.setText(String.valueOf(article.getEndPage()));
        lbImpactCA.setText(article.getImpactCA());
        if(article.getPublicationDate() != null){
            lbPublicationDate.setText(convertDateToString(article.getPublicationDate()));
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
     * Método que manda a abrir la ventana de modificación de artículo
     * @param event Define el evento generado
     */

    @FXML
    private void modifyArticle(ActionEvent event) {
    }

    /**
     * Método que cierra la ventana actual "Datos de artículo"
     * @param event Define el evento generado
     */
    
    @FXML
    private void closeArticleData(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
}
