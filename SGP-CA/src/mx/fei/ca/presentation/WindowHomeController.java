
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mx.fei.ca.domain.Integrant;

/**
 * FXML Controller class
 *
 * @author inigu
 */
public class WindowHomeController implements Initializable {
    private Integrant integrant;
    
    @FXML
    private Pane pnBtnProyectod;
    @FXML
    private Text lbUser;
    
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
    private void clickWorkPlan(MouseEvent event) {
        
    }
    
}
