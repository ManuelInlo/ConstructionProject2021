
package mx.fei.ca.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Clase para representar el controlador del FXML WindowProductionCA
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

public class WindowProductionCAController implements Initializable {

    @FXML
    private Label lbUser;
    @FXML
    private TableView<?> tbReceptionWorks;
    @FXML
    private TableColumn<?, ?> columnTitleReceptionWork;
    @FXML
    private TableColumn<?, ?> columnActualStateReceptionWork;
    @FXML
    private TableColumn<?, ?> columnAuthorReceptionWork;
    @FXML
    private TableColumn<?, ?> columnStartDate;
    @FXML
    private TableView<?> tbArticles;
    @FXML
    private TableColumn<?, ?> columnTitleArticle;
    @FXML
    private TableColumn<?, ?> columnActualStateArticle;
    @FXML
    private TableColumn<?, ?> columnArticle;
    @FXML
    private TableColumn<?, ?> columnIssn;
    @FXML
    private TableView<?> tbBooks;
    @FXML
    private TableColumn<?, ?> columnTitleBook;
    @FXML
    private TableColumn<?, ?> columnActualStateBook;
    @FXML
    private TableColumn<?, ?> columnAuthorBook;
    @FXML
    private TableColumn<?, ?> columnIsbn;
    @FXML
    private TableView<?> tbChapterBooks;
    @FXML
    private TableColumn<?, ?> columnTitleChapterBook;
    @FXML
    private TableColumn<?, ?> columnNumberChapterBook;
    @FXML
    private TableColumn<?, ?> columnTitleBookOfChapterBook;
    @FXML
    private DatePicker dpDate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void searchProductionCA(ActionEvent event) {
    }

    @FXML
    private void closeProductionCA(ActionEvent event) {
    }
    
    
    
}
