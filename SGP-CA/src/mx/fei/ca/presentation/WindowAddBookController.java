
package mx.fei.ca.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class WindowAddBookController implements Initializable {

    @FXML
    private TextField tfTitleEvidence;
    @FXML
    private TextField tfAuthor;
    @FXML
    private ComboBox<?> cbImpactCA;
    @FXML
    private ComboBox<?> cbInvestigationProject;
    @FXML
    private TextField tfChapterNumber;
    @FXML
    private TextField tfEndPage;
    @FXML
    private TextField tfHomePage;
    @FXML
    private ComboBox<?> cbBook;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void saveChapterBook(ActionEvent event) {
    }

    @FXML
    private void closeChapterBookRegistration(ActionEvent event) {
    }
    
}
