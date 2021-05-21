
package mx.fei.ca.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.ReceptionWorkDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
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
    private TableView<ReceptionWork> tbReceptionWorks;
    @FXML
    private TableView<?> tbArticles;
    @FXML
    private TableView<?> tbBooks;
    @FXML
    private TableView<?> tbChapterBooks;
    @FXML
    private TableColumn<ReceptionWork, String> columnImpactCAReceptionWork;
    @FXML
    private TableColumn<ReceptionWork, String> columnNameReceptionWork;
    @FXML
    private TableColumn<?, ?> columnImpactCAArticle;
    @FXML
    private TableColumn<?, ?> columnNameArticle;
    @FXML
    private TableColumn<?, ?> columnImpactCABook;
    @FXML
    private TableColumn<?, ?> columnNameBook;
    @FXML
    private TableColumn<?, ?> columnImpactCAChapterBook;
    @FXML
    private TableColumn<?, ?> columnNameChapterBook;

    private enum TypeError{
        EMPTYFIELD, INVALIDSTRING;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {  
            recoverEvidences();
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }
        openReceptionWorkData();
    } 
    
    private void recoverEvidences() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        ArrayList<ReceptionWork> receptionWorks = receptionWorkDAO.findLastTwoReceptionWorksByCurpIntegrant("JCPA940514RDTREOP1"); //En realidad debe pasar la curp del integrante que está loggeado
        fillReceptionWorkTable(receptionWorks);
    }
    
    private void fillReceptionWorkTable(ArrayList<ReceptionWork> receptionWorks){
        columnImpactCAReceptionWork.setCellValueFactory(new PropertyValueFactory("impactCA"));
        columnNameReceptionWork.setCellValueFactory(new PropertyValueFactory("titleReceptionWork"));
        ObservableList<ReceptionWork> listReceptionWorks = FXCollections.observableArrayList(receptionWorks);
        tbReceptionWorks.setItems(listReceptionWorks);
    }
    
    public void openReceptionWorkData(){
        tbReceptionWorks.setOnMouseClicked((MouseEvent event) -> {
            ReceptionWork receptionWork = tbReceptionWorks.getItems().get(tbReceptionWorks.getSelectionModel().getSelectedIndex());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowReceptionWorkData.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException ex) {
                Logger.getLogger(WindowMemberProductionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage stage = new Stage();
            stage.setScene(scene);
            WindowReceptionWorkDataController windowReceptionWorkDataController = (WindowReceptionWorkDataController) fxmlLoader.getController();
            windowReceptionWorkDataController.showReceptionWorkData(receptionWork);
            stage.show();
        });
    }

    @FXML
    private void searchEvidence(ActionEvent event){
        if(!existsInvalidField()){
            try {
                //Mandar a recuperar listas de las evidencias que coincidan con el texto ->QUITAR ESTE COMENTARIO DESPUES
                ArrayList<ReceptionWork> receptionWorks = recoverReceptionWorks();
                
                if(receptionWorks.isEmpty()){  //DEBE PREGUNTAR SI TODAS LAS LISTAS RECUPERADAS ESTAN VACIAS
                    showNoMatchAlert();
                }
            } catch (BusinessConnectionException ex) {
                showLostConnectionAlert();
            }
  
        }
    }
    
    @FXML
    private ArrayList<ReceptionWork> recoverReceptionWorks() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        String titleReceptionWork = tfEvidenceName.getText();
        ArrayList<ReceptionWork> receptionWorks = receptionWorkDAO.findReceptionWorkByInitialesOfTitle(titleReceptionWork);
        if(receptionWorks.size() > 0){
            fillReceptionWorkTable(receptionWorks);
        }
        return receptionWorks;
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowAddReceptionWork.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void closeMemberProduction(ActionEvent event){
        Stage stage = (Stage) this.btnExit.getScene().getWindow();
        stage.close();
    }
    
    private boolean existsInvalidField(){
        boolean invalidField = false;
        if(existsEmptyField() || existsInvalidString()){
            invalidField = true;
        }
        return invalidField;
    }
    
    private boolean existsEmptyField(){
        boolean emptyField = false;
        if(tfEvidenceName.getText().isEmpty()){
            emptyField = true;
            TypeError typeError = TypeError.EMPTYFIELD;
            showInvalidFieldAlert(typeError);
        }
        return emptyField;
    }
    
    private boolean existsInvalidString(){
        boolean invalidString = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+$");
        Matcher matcher = pattern.matcher(tfEvidenceName.getText());
        if(!matcher.find()){
           invalidString = true;
           TypeError typeError = TypeError.INVALIDSTRING;
           showInvalidFieldAlert(typeError);
        }
        return invalidString;
    }
    
    private void showInvalidFieldAlert(TypeError typeError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        if(typeError == TypeError.EMPTYFIELD){
            alert.setContentText("Existe campo vacío, llena el campo para poder buscar reunión");
        }
        if(typeError == TypeError.INVALIDSTRING){
            alert.setContentText("Existe caracter inválido, revisa el texto para poder buscar reunión");
        }
        alert.showAndWait();
    }
    
    @FXML
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo guardar. Intente más tarde");
        alert.showAndWait();
    }
    
    @FXML
    private void showNoMatchAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Sin coincidencias");
        alert.setContentText("No se encontró ningún tipo de evidencia que coincida con el texto ingresado");
        alert.showAndWait();
    }
    
}
