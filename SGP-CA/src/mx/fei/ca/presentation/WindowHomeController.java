package mx.fei.ca.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.domain.Integrant;

/**
 * Clase para representar el controlador del FXML WindowHome
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class WindowHomeController implements Initializable {
   
    @FXML
    private Text lbUser;
     private Integrant integrant;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }     
    
    /**
     * Método que establece el integrante loggeado y proyecta su nombre en la GUI
     * @param integrant Define el integrante a establecer en la GUI
     */
    
    public void setIntegrant(Integrant integrant){
        this.integrant = integrant;
        lbUser.setText(integrant.getNameIntegrant());
    }
    
    /**
     * Método que manda a abrir la ventana de historial de reuniones
     * @param event Define el evento generado
     */
    
    @FXML
    private void clickMettings(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowMeetingHistory.fxml"));
            Parent root = fxmlLoader.load();
            WindowMeetingHistoryController windowMeetingHistoryController = fxmlLoader.getController();
            windowMeetingHistoryController.setIntegrant(integrant);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(WindowHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método que manda a abrir la ventana de lista de evidencias del integrante
     * @param event Define el evento generado
     */

    @FXML
    private void clickEvidences(MouseEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowMemberProduction.fxml"));
            Parent root = fxmlLoader.load();
            WindowMemberProductionController windowMemberProductionController = fxmlLoader.getController();
            windowMemberProductionController.setIntegrant(integrant);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(WindowHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método que cierra la ventana actual "Inicio"
     * @param event Define el evento generado
     */

    @FXML
    private void signOff(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que manda a abrir la ventana de producción del cuerpo académico
     * @param event Define el evento generado
     */

    @FXML
    private void clickProductionCA(MouseEvent event) {
    }
    

    /**
     * Método que manda a abrir la ventana de integrantes que funciona para el responsable
     * @param event Define el evento generado
     */
    @FXML
    private void clickIntegrants(MouseEvent event) {
        try {
            IntegrantDAO integrantDAO = new IntegrantDAO();
            if("Responsable".equals(integrant.getRole())){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowIntegrants.fxml"));
                Parent root = fxmlLoader.load();
                WindowIntegrantsController windowIntegrantsController = fxmlLoader.getController();
                windowIntegrantsController.setIntegrant(integrant);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.showAndWait();
            }else{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowIntegrantsData.fxml"));
                Parent root = fxmlLoader.load();
                WindowIntegrantDataController windowIntegrantDataController = fxmlLoader.getController();
                windowIntegrantDataController.setIntegrant(integrant);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.showAndWait();
            }
        } catch (IOException ex) {
            Logger.getLogger(WindowHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
