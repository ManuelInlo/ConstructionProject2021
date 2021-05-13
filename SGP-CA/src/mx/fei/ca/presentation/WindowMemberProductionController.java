
package mx.fei.ca.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.fei.ca.domain.Evidence;
import mx.fei.ca.domain.ReceptionWork;

/**
 * FXML Controller class
 *
 * @author david
 */
public class WindowMemberProductionController implements Initializable {

    @FXML
    private TextField tfEvidenceName;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnAddArticle;
    @FXML
    private Button btnAddBook;
    @FXML
    private Button btnAddChapterBook;
    @FXML
    private Button btnAddReceptionWork;
    @FXML
    private Button btnExit;
    @FXML
    private TableView<Evidence> tbEvidences;
    @FXML
    private TableColumn<Evidence, String> columnImpactCA;
    @FXML
    private TableColumn<Evidence, String> columnNameEvidence;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    @FXML
    private void fillEvidencesTable(){
        
    }

    @FXML
    private void searchEvidence(ActionEvent event){
        if(!existsEmptyField() && !existsInvalidString()){
            
        }
    }

    @FXML
    private void openArticleRegistration(ActionEvent event) {
    }

    @FXML
    private void openBookRegistration(ActionEvent event) {
    }

    @FXML
    private void openChapterBookRegistration(ActionEvent event) {
    }

    @FXML
    private void openReceptionWorkRegistration(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("WindowAddReceptionWork.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void closeMemberProduction(ActionEvent event){
        Stage stage = (Stage) this.btnExit.getScene().getWindow();
        stage.close();
    }
    
    private boolean existsEmptyField(){
        boolean emptyField = false;
        if(tfEvidenceName.getText().isEmpty()){
            emptyField = true;
            showEmptyFieldAlert();
        }
        return emptyField;
    }
    
    private boolean existsInvalidString(){
        boolean invalidString = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+$");
        Matcher matcher = pattern.matcher(tfEvidenceName.getText());
        if(!matcher.find()){
           invalidString = true;
           showInvalidStringAlert();
        }
        return invalidString;
    }
    
    private void showEmptyFieldAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        alert.setContentText("Existen campo vacío, llena el campo para poder buscar evidencia");
        alert.showAndWait();
    }
    
    private void showInvalidStringAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        alert.setContentText("Existe caracter inválido, revisa el texto para poder buscar evidencia");
        alert.showAndWait();
    }
    
}
