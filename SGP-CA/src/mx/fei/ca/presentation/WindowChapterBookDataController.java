
package mx.fei.ca.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.fei.ca.domain.ChapterBook;
import mx.fei.ca.domain.Integrant;

/**
 * Clase para representar el controlador del FXML WindowChapterBookData
 * @author Gloria Mendoza González
 * @version 18-06-2021
 */

public class WindowChapterBookDataController implements Initializable {

    @FXML
    private Label lbUser;
    @FXML
    private Label lbTitleEvidence;
    @FXML
    private Label lbAuthor;
    @FXML
    private Label lbChapterNumber;
    @FXML
    private Label lbHomePage;
    @FXML
    private Label lbEndPage;
    @FXML
    private Label lbBook;
    @FXML
    private Label lbInvestigationProject;
    @FXML
    private Label lbTypeEvidence;
    @FXML
    private Label lbImpactCA;
    private Integrant integrant;
    private ChapterBook chapterBook;

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
     * Método que muestra la información del capítulo de libro en la GUI
     * @param chapterBook Define el capítulo de libro con la información a mostrar
     */
    
    public void showChapterBookData(ChapterBook chapterBook){
        this.chapterBook = chapterBook;
        lbTypeEvidence.setText("Capítulo de libro");
        lbTitleEvidence.setText(chapterBook.getTitleEvidence());
        lbAuthor.setText(chapterBook.getAuthor());
        lbChapterNumber.setText(String.valueOf(chapterBook.getChapterNumber()));
        lbHomePage.setText(String.valueOf(chapterBook.getHomePage()));
        lbEndPage.setText(String.valueOf(chapterBook.getEndPage()));
        lbBook.setText(chapterBook.getBook().getTitleEvidence());
        lbInvestigationProject.setText(chapterBook.getInvestigationProject().getTittleProject());
        lbImpactCA.setText(chapterBook.getImpactCA());
    }
    
    /**
     * Método que manda a abrir la ventana de modificación de capítulo de libro
     * @param event Define el evento generado
     */

    @FXML
    private void modifyChapterBook(ActionEvent event){
        
    }
    
    /**
     * Método que cierra la ventana actual "Datos de capítulo de libro"
     * @param event 
     */

    @FXML
    private void closeChapterBookData(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
}
