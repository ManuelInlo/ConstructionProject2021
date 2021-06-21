
package mx.fei.ca.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Clase para representar el controlador del FXML WindowModifyBook
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

public class WindowModifyBookController implements Initializable {

    @FXML
    private Label lbUser;
    @FXML
    private TextField tfIsbn;
    @FXML
    private TextField tfPrinting;
    @FXML
    private TextField tfNumPages;
    @FXML
    private TextField tfCountry;
    @FXML
    private TextField tfEdition;
    @FXML
    private TextField tfEditorial;
    @FXML
    private TextField tfFileRoute;
    @FXML
    private DatePicker dpPublicationDate;
    @FXML
    private ComboBox<?> cbActualState;
    @FXML
    private TextField tfTitleEvidence;
    @FXML
    private TextField tfAuthor;
    @FXML
    private ComboBox<?> cbImpactCA;
    @FXML
    private ComboBox<?> cbInvestigationProject;
    @FXML
    private ComboBox<?> cbParticipationType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modifyBook(ActionEvent event) {
    }

    @FXML
    private void closeModifyBook(ActionEvent event) {
    }
    
}
