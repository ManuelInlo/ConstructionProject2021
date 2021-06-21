
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
 * Clase para representar el controlador del FXML WindowModifyArticle
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

public class WindowModifyArticleController implements Initializable {

    @FXML
    private Label lbUser;
    @FXML
    private TextField tfTitleEvidence;
    @FXML
    private TextField tfIssn;
    @FXML
    private TextField tfMagazineName;
    @FXML
    private TextField tfEndPage;
    @FXML
    private TextField tfEditorial;
    @FXML
    private TextField tfCountry;
    @FXML
    private TextField tfFileRoute;
    @FXML
    private DatePicker dpPublicationDate;
    @FXML
    private TextField tfVolume;
    @FXML
    private ComboBox<?> cbImpactCA;
    @FXML
    private ComboBox<?> cbInvestigationProject;
    @FXML
    private ComboBox<?> cbActualState;
    @FXML
    private TextField tfDescription;
    @FXML
    private TextField tfHomePage;
    @FXML
    private TextField tfAuthor;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modifyArticle(ActionEvent event) {
    }

    @FXML
    private void closeArticleModify(ActionEvent event) {
    }
    
}
