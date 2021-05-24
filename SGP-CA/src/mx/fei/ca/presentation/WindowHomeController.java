/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.fei.ca.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
        lbUser.setText(this.integrant.getNameIntegrant());
    }
    
    public Integrant getIntegrant(){
        return integrant;
    }
 
    @FXML
    private void clickProjects(MouseEvent event) {
        
    }

    @FXML
    private void clickMettings(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowMeetingHistory.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            Logger.getLogger(WindowMemberProductionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void clickEvidences(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowMemberProduction.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            Logger.getLogger(WindowMemberProductionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void clickWorkPlan(MouseEvent event) {
        
    }
    
}
