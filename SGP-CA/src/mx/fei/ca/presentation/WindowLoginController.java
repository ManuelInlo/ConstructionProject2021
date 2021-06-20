
package mx.fei.ca.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;

/**
 * Clase para representar el controlador del FXML WindowLoginController
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class WindowLoginController implements Initializable {

    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField passwordFieldPassword;
    private Integrant integrant;
    
    /**
     * Enumerado que representa los tipos de errores específicos al iniciar sesión
     */
    
    private enum TypeError{
        EMPTYFIELD, INVALIDEMAIL, NONEXISTENINTEGRANT;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
    }    
    
    /**
     * Método que manda a abrir la ventana de home 
     * @param event Define el evento generado
     * @throws BusinessConnectionException
     * @throws IOException 
     */

    @FXML
    private void openHomePage(ActionEvent event){
        try {
            if(!existsInvalidFields()){
                if(existsInsecurePassword()){
                    openChangePassword();
                }else{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowHome.fxml"));
                    Parent root = fxmlLoader.load();
                    WindowHomeController windowHomeController = fxmlLoader.getController();
                    windowHomeController.setIntegrant(integrant);
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    closeWindowLogin(event);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(WindowLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método que manda a abrir la ventana de cambio de contraseña
     */
    
    private void openChangePassword(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowChangePassword.fxml"));
            Parent root = fxmlLoader.load();
            WindowChangePasswordController windowChangePasswordController = fxmlLoader.getController();
            windowChangePasswordController.setIntegrant(integrant);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            cleanFields();
        } catch (IOException ex) {
            Logger.getLogger(WindowLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método que cierra la ventana actual "Incio de sesión"
     * @param event Define el evento generado
     */

    @FXML
    private void closeWindowLogin(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que verifica si la contraseña del integrante es la misma a la contraseña asignada por defecto al registrarlo
     * @return Booleano con el resultado de verificación, devuelve true si la contraseña es la misma, de lo contrario, devuelve false
     */
    
    private boolean existsInsecurePassword(){
        boolean existsInsecurePassword = false;
        if(this.integrant.getPassword().equals(integrant.getCurp())){
            existsInsecurePassword = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Contraseña insegura");
            alert.setContentText("Tu contraseña no puede seguir siendo tu CURP, por favor cambia tu contraseña");
            alert.showAndWait();
        }
        return existsInsecurePassword;
        
    }
    
    /**
     * Método que verifica si existen campos inválidos en la GUI. El método invoca a otros métodos con validaciones más específicas
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean existsInvalidFields(){
        boolean invalidField = false;
        try {
            if(existsEmptyFields() || existsInvalidEmail()|| !existsEmailAndPassword()){
                invalidField = true;
            }
            
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }
        return invalidField;
    }
    
    /**
     * Método que verifica si existen campos vacíos en la GUI
     * @return Booleano con el resultado de verificación, devuelve true si existen vacíos, de lo contrario, devuelve false
     */
    
    private boolean existsEmptyFields(){
        boolean emptyFields = false;
        if(tfEmail.getText().isEmpty() || passwordFieldPassword.getText().isEmpty()){
            emptyFields = true;
            TypeError typeError = TypeError.EMPTYFIELD;
            showInvalidFieldAlert(typeError);
        }
        return emptyFields;
    }
    
    /**
     * Método que manda a verificar si el correo y contraseña ingresados existen en la base de datos
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean existsEmailAndPassword() throws BusinessConnectionException{
        boolean exists = true;
        integrant = getIntegrantByEmail();
        if(integrant == null){
            exists = false;
        }else if(!integrant.getPassword().equals(passwordFieldPassword.getText())){
            exists = false;
        }
        if(!exists){
            TypeError typeError = TypeError.NONEXISTENINTEGRANT;
            showInvalidFieldAlert(typeError);
        }
        return exists;
    }
    
    /**
     * Método que devuelve el integrante que tenga el correo ingresado
     * @return Objeto integrante
     * @throws BusinessConnectionException 
     */
    
    private Integrant getIntegrantByEmail(){
        try {
            IntegrantDAO integrantDAO = new IntegrantDAO();
            this.integrant = integrantDAO.getIntegrantByInstitutionalMail(tfEmail.getText());
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }
        return integrant;
    }
    
    /**
     * Método que verifica si existen caracteres en el correo electrónico que sean inválidos
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
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
    
    /**
     * Método que muestra alerta de campo inválido de acuerdo al tipo de error
     * @param typeError Define el tipo de error que encontró
     */
    
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
     * Método que limpia los campos de la GUI
     */
    
    private void cleanFields(){
        tfEmail.clear();
        passwordFieldPassword.clear();
    }
    
}
