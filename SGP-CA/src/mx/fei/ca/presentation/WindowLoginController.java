/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;

/**
 * FXML Controller class
 *
 * @author david
 */
public class WindowLoginController implements Initializable {

    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField pfPassword;
    
    private enum TypeError{
        EMPTYFIELD, INVALIDEMAIL, NONEXISTENINTEGRANT;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void openHomePage(ActionEvent event) throws BusinessConnectionException, IOException {
        if(!existsInvalidFields()){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowHome.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            closeWindowLogin(event);
            //WindowHomeController windowHomeController = (WindowHomeController) fxmlLoader.getController();
            //windowHomeController.setIntegrantSession(getIntegrantByEmail());
            stage.showAndWait();
        }
    }

    @FXML
    private void closeWindowLogin(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private boolean existsInvalidFields() throws BusinessConnectionException{
        boolean invalidField = false;
        if(existsEmptyFields() || existsInvalidEmail()|| !existsEmailAndPassword()){
            invalidField = true;
        }
        return invalidField;
    }
    
    @FXML
    private boolean existsEmptyFields(){
        boolean emptyFields = false;
        if(tfEmail.getText().isEmpty() || pfPassword.getText().isEmpty()){
            emptyFields = true;
            TypeError typeError = TypeError.EMPTYFIELD;
            showInvalidFieldAlert(typeError);
        }
        return emptyFields;
    }
    
    @FXML
    private boolean existsEmailAndPassword() throws BusinessConnectionException{
        boolean exists = true;
        Integrant integrant = getIntegrantByEmail();
        if(integrant == null){
            exists = false;
        }else if(!integrant.getPassword().equals(pfPassword.getText())){
            exists = false;
        }
        if(!exists){
            TypeError typeError = TypeError.NONEXISTENINTEGRANT;
            showInvalidFieldAlert(typeError);
        }
        return exists;
    }
    
    @FXML
    private Integrant getIntegrantByEmail() throws BusinessConnectionException{
        IntegrantDAO integrantDAO = new IntegrantDAO();
        Integrant integrant = integrantDAO.getIntegrantByInstitutionalMail(tfEmail.getText());
        return integrant;
    }
    
    @FXML
    private boolean existsInvalidEmail(){
        boolean invalidEmail = false;
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(tfEmail.getText());
        if(!matcher.find()){
            invalidEmail = true;
            TypeError typeError = TypeError.INVALIDEMAIL;
            showInvalidFieldAlert(typeError);
        }
        
        return invalidEmail;
    }
    
    @FXML 
    private void showInvalidFieldAlert(TypeError typeError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        if(typeError == TypeError.EMPTYFIELD){
          alert.setContentText("Existen campos vacíos, llena los campos para poder ingresar");  
        }
        
        if(typeError == TypeError.INVALIDEMAIL){
            alert.setContentText("Existen carácteres inválidos en el correo, revisa por favor");  
        }
        
        if(typeError == TypeError.NONEXISTENINTEGRANT){
            alert.setContentText("Correo o contraseña incorrecta, revisa por favor"); 
        }
        
        alert.showAndWait();
    }
    
    
}
