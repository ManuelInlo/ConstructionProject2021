
package mx.fei.ca.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;

/**
 * Clase para representar el controlador del FXML WindowAddChapterBook
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

public class WindowAddChapterBookController implements Initializable {

    @FXML
    private TextField tfTitleEvidence;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextField tfChapterNumber;
    @FXML
    private TextField tfEndPage;
    @FXML
    private ComboBox<?> cbImpactCA;
    @FXML
    private ComboBox<?> cbInvestigationProject;
    @FXML
    private TextField tfHomePage;
    @FXML
    private ComboBox<?> cbBook;
    @FXML
    private Label lbUser;
    private Integrant integrant;

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
     * Método que manda a guardar un capítulo de libro de acuerdo a la información obtenida de los campos de la GUI
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */

    @FXML
    private void saveChapterBook(ActionEvent event) throws BusinessConnectionException{
    }

    /**
     * Método que cierra la GUI actual
     * @param event Define el evento generado
     */
    
    @FXML
    private void closeChapterBookRegistration(ActionEvent event) {
    }
    
}
