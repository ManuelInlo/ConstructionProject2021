
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;

/**
 * Clase para representar el controlador del FXML WindowIntegrants
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

public class WindowIntegrantsController implements Initializable {

    @FXML
    private TextField tfNameIntegrant;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnRegistration;
    @FXML
    private TableView<Integrant> tbIntegrants;
    @FXML
    private TableColumn<Integrant, String> columnNameIntegrant;
    @FXML
    private TableColumn<Integrant, String> columnStudyDegree;
    @FXML
    private TableColumn<Integrant, String> columnProdepParticipation;
    @FXML
    private TableColumn<Integrant, String> columnRole;
    @FXML
    private TableColumn<Integrant, String> columnCurp;
    @FXML   
    private Label lbUser;
    private Integrant integrant;
    
    /**
     * Enumerado que representa los tipos de errores específicos al usar el campo de texto para buscar un integrante
     */
    
    private enum TypeError{
        EMPTYFIELD, INVALIDSTRING;
    }   
    
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
        try {  
            recoverIntegrants();
        }catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }           
    } 
    
    /**
     * Método que manda a recuperar a los integrantes activos del CA y los muestra en la GUI
     * El método invoca a otro clase y método de la capa lógica para la obtención de los integrantes en la base de datos
     * @throws BusinessConnectionException 
     */
    
    public void recoverIntegrants() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        ArrayList<Integrant> integrants = integrantDAO.findAllIntegrants(); 
        fillIntegrantTable(integrants);      
    }   
       
    /**
     * Método que llena la tabla de los integrantes activos del CA
     * @param integrants Define la lista de integrantes a mostrar en la GUI
     */
    
    private void fillIntegrantTable(ArrayList<Integrant> integrants){
        columnNameIntegrant.setCellValueFactory(new PropertyValueFactory("nameIntegrant"));
        columnStudyDegree.setCellValueFactory(new PropertyValueFactory("studyDegree"));
        columnProdepParticipation.setCellValueFactory(new PropertyValueFactory("prodepParticipation"));
        columnRole.setCellValueFactory(new PropertyValueFactory("role"));
        columnCurp.setCellValueFactory(new PropertyValueFactory("curp"));
        ObservableList<Integrant> listIntegrants = FXCollections.observableArrayList(integrants);
        tbIntegrants.setItems(listIntegrants);
    }    
    
    /**
     * Método que manda a abrir la ventana de currículum personal de acuerdo al integrante seleccionado de la tabla
     * @throws BusinessConnectionException 
     */
    
    private void openIntegrantData() throws BusinessConnectionException{
        tbIntegrants.setOnMouseClicked((MouseEvent event) -> {
            Integrant integrant = tbIntegrants.getItems().get(tbIntegrants.getSelectionModel().getSelectedIndex());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowIntegrantData.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException ex) {
                Logger.getLogger(WindowMemberProductionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage stage = new Stage();
            stage.setScene(scene);
            WindowIntegrantDataController windowIntegrantDataController = (WindowIntegrantDataController) fxmlLoader.getController();
            windowIntegrantDataController.setIntegrant(this.integrant);
            //windowIntegrantDataController.showReceptionWorkData(receptionWork);
            stage.showAndWait();
            try {
                recoverIntegrants();
            } catch (BusinessConnectionException ex) {
                showLostConnectionAlert();
            }
        });      
    }    
    
    /**
     * Método que cierra la ventana actual "Integrantes"
     * @param event Define el evento generado
     */

    @FXML
    private void closeWindowIntegrants(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que manda a buscar un integrante de acuerdo a las iniciales del título ingresado en el campo de la GUI
     * @param event Define el evento generado
     */

    @FXML
    private void searchIntegrant(ActionEvent event) {
        if(!existsInvalidField()){
            try {
                ArrayList<Integrant> integrants = recoverSearchIntegrants();                               
                if(integrants.isEmpty()){  
                    showNoMatchAlert();
                }
            } catch (BusinessConnectionException ex) {
                showLostConnectionAlert();
                closeWindowIntegrants(event);
            }
  
        }
    }
    
    /**
     * Método que manda a recuperar específicamente los integrantes de acuerdo a las iniciales del nombre ingresado en la GUI
     * @return ArrayList con los integrantes que coincidieron con el texto
     * @throws BusinessConnectionException 
     */
    
    private ArrayList<Integrant> recoverSearchIntegrants() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        String nameIntegrant = tfNameIntegrant.getText();
        ArrayList<Integrant> integrants = integrantDAO.findIntegrantsByInitialesOfTitle(nameIntegrant);
        if(!integrants.isEmpty()){
            fillIntegrantTable(integrants);
        }
        return integrants;
    }

   /**
    * Método que manda a abrir la ventana para registrar a un nuevo integrante
    * @param event Define el evento generado
    */ 

    @FXML
    private void addIntegrant(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowAddIntegrant.fxml"));
            Parent root = fxmlLoader.load();
            WindowAddIntegrantController windowAddIntegrantController = fxmlLoader.getController();
            windowAddIntegrantController.setIntegrant(integrant);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(WindowHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método que verifica si existen campos inválidos
     * @return Booleano con el resultado de la verificación, devuelve true si existen campos inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidField(){
        boolean invalidField = false;
        if(existsEmptyField() || existsInvalidString()){
            invalidField = true;
        }
        return invalidField;
    }    
    
    /**
     * Método que verifica si existen campos de la GUI que estén vacíos
     * @return Booleano con el resultado de la verificación, devuelve true si existen vacíos, de lo contrario, devuelve false
     */
    
    private boolean existsEmptyField(){
        boolean emptyField = false;
        if(tfNameIntegrant.getText().isEmpty()){
            emptyField = true;
            TypeError typeError = TypeError.EMPTYFIELD;
            showInvalidFieldAlert(typeError);
        }
        return emptyField;
    }
    
    /**
     * Método que verifica si existen cadenas inválidas en el texto del campo de la GUI
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidas, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidString(){
        boolean invalidString = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+$");
        Matcher matcher = pattern.matcher(tfNameIntegrant.getText());
        if(!matcher.find()){
           invalidString = true;
           TypeError typeError = TypeError.INVALIDSTRING;
           showInvalidFieldAlert(typeError);
        }
        return invalidString;
    }
    
    /**
     * Método que muestra alerta de campo inválido de acuerdo al tipo de error
     * @param typeError Define el tipo de error que encontró
     */
    
    private void showInvalidFieldAlert(TypeError typeError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        if(typeError == TypeError.EMPTYFIELD){
            alert.setContentText("Existe campo vacío, llena el campo para poder buscar un integrante");
        }
        if(typeError == TypeError.INVALIDSTRING){
            alert.setContentText("Existe caracter inválido, revisa el texto para poder buscar");
        }
        alert.showAndWait();
    }
    
    /**
     * Método que muestra alerta de perdida de conexión con la base de datos
     */
    
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo guardar. Intente más tarde");
        alert.showAndWait();
    }
    
    /**
     * Método que muestra alerta sin coincidencias
     */
    
    private void showNoMatchAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Sin coincidencias");
        alert.setContentText("No se encontró ningún nombre de integrante que coincida con el texto ingresado");
        alert.showAndWait();
    }    
    
}
