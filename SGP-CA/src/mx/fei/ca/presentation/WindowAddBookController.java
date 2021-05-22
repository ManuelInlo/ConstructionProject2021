
package mx.fei.ca.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class WindowAddBookController implements Initializable {

    @FXML
    private TextField tfISBN;
    @FXML
    private TextField tfPrinting;
    @FXML
    private TextField tfNumPages;
    @FXML
    private TextField tfParticipationType;
    @FXML
    private TextField tfCountry;
    @FXML
    private TextField tfEdition;
    @FXML
    private TextField tfEditorial;
    @FXML
    private TextField tfFileRoute;
    @FXML
    private DatePicker dpActualState;
    @FXML
    private ComboBox<?> cbActualState;
    @FXML
    private TextField tfTitleEvidence;
    @FXML
    private TextField tfAuthor;
    @FXML
    private ComboBox<?> cbImpactCA;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
