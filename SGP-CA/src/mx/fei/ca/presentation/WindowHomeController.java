
package mx.fei.ca.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mx.fei.ca.domain.Integrant;

/**
 * Clase para representar el controlador del FXML WindowHome
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class WindowHomeController implements Initializable {
    private Integrant integrant;
    
    @FXML
    private Pane pnBtnProyectod;
    @FXML
    private Text lbUser;
    @FXML
    private Pane pnBtnProyectod1;
    @FXML
    private ImageView imgLogOut;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }     
    
    public void setIntegrant(Integrant integrant){
        this.integrant = integrant;
        lbUser.setText(integrant.getNameIntegrant());
    }
    
    @FXML
    private void clickProjects(MouseEvent event) {
        
    }

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

    @FXML
    private void signOff(MouseEvent event) {
        
    }

    @FXML
    private void clickProductionCA(MouseEvent event) {
    }
    
    @FXML
    private void clickWorkPlan(MouseEvent event) {
    }    

    @FXML
    private void clickIntegrants(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowIntegrants.fxml"));
            Parent root = fxmlLoader.load();
            WindowIntegrantsController windowIntegrantsController = fxmlLoader.getController();
            windowIntegrantsController.setIntegrant(integrant);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(WindowHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
