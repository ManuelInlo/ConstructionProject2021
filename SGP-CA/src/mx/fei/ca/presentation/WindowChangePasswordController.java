package mx.fei.ca.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;

/**
 * Clase para representar el controlador del FXML WindowChangePassword
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class WindowChangePasswordController implements Initializable {

    @FXML
    private PasswordField passwordFieldNewPassword;
    @FXML
    private PasswordField passwordFieldConfirmPassword;
    private Integrant integrant;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    
    /**
     * Método que establece el integrante loggeado al sistema
     * @param integrant Define el integrante a establecer en la GUI
     */
    
    public void setIntegrant(Integrant integrant){
        this.integrant = integrant;
    }
    
    /**
     * Método que manda a guardar la nueva contraseña del integrante loggeado en el sistema
     * @param event Define el evento generado
     */

    @FXML
    private void saveNewPassword(ActionEvent event){
        if(!existsInvalidPassword()){
            IntegrantDAO integrantDAO = new IntegrantDAO();
            try {
                if(integrantDAO.changedPasswordIntegrant(passwordFieldConfirmPassword.getText(), integrant.getCurp())){
                    showConfirmationAlert();
                    closeChangedPassword(event);
                }
            } catch (BusinessConnectionException ex) {
                showLostConnectionAlert();
            }
        }
    
    }
    
    /**
     * Método que verifica si la nueva contraseña es inválida tras no cumplir con ciertas especificaciones o si es inválida porque no coincide con la confirmación
     * @return Booleano con el resultado de verificación, devuelve true si la contraseña es inválida, de lo contrario, devuelve false
     */
    private boolean existsInvalidPassword(){
        boolean invalidPassword = false;
        Pattern pattern = Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$");
        Matcher matcher = pattern.matcher(passwordFieldNewPassword.getText());
        if(!matcher.find()){
            invalidPassword = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Contraseña inválida");
            alert.setContentText("La contraseña debe tener entre 8 y 16 caracteres \n"
                                 + "La contraseña debe tener por lo menos un digito \n"
                                 + "La contraseña debe tener por lo menos una letra mayúscula \n"
                                 + "La contraseña debe tener por lo menos una letra minúscula");
            alert.showAndWait();
        }else if(!passwordFieldNewPassword.getText().equals(passwordFieldConfirmPassword.getText())){
            invalidPassword = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Contraseñas diferentes");
            alert.setContentText("La contraseñas no coinciden, verifica para poder guardar");
            alert.showAndWait();
        }
        return invalidPassword;
    }
    
    /**
     * Método que cierra la ventana actual "Cambio contraseña"
     * @param event Define el evento generado
     */
    
    @FXML
    private void closeChangedPassword(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que muestra alerta de confirmación de guardado en la base de datos
     */
    
    private void showConfirmationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación de guardado");
        alert.setContentText("La contraseña fue modificada con éxito, vuelve a ingresar en el sistema por favor");
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
    
}
