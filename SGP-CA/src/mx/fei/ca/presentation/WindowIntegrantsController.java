
package mx.fei.ca.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    private Label lbNameIntegrant;
    @FXML
    private Button btnExit;
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
     * Initializes the controller class.
     */
    
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

    @FXML
    private void searchIntegrant(ActionEvent event) {
    }

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
    
}
