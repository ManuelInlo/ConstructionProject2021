
package mx.fei.ca.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mx.fei.ca.domain.Book;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.InvestigationProject;

/**
 * Clase para representar el controlador del FXML WindowModifyChapterBook
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

public class WindowModifyChapterBookController implements Initializable {

    @FXML
    private Label lbUser;
    @FXML
    private TextField tfTitleEvidence;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextField tfChapterNumber;
    @FXML
    private TextField tfEndPage;
    @FXML
    private ComboBox cbImpactCA;
    @FXML
    private TextField tfHomePage;
    @FXML
    private ComboBox<Book> cbBook;
    @FXML
    private ComboBox<InvestigationProject> cbInvestigationProject;
    private Integrant integrant;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }  
    
    

    @FXML
    private void saveModifyChapterBook(ActionEvent event) {
    }

    @FXML
    private void closeModifyChapterBook(ActionEvent event) {
    }
    
}
