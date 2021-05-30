
package mx.fei.ca.presentation;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.fei.ca.domain.ReceptionWork;

/**
 * FXML Controller class
 *
 * @author david
 */
public class WindowReceptionWorkDataController implements Initializable {

    @FXML
    private Label lbTitleEvidence;
    @FXML
    private Label lbTypeWork;
    @FXML
    private Label lbActualState;
    @FXML
    private Label lbAuthor;
    @FXML
    private Label lbPositionAuthor;
    @FXML
    private Label lbStartDate;
    @FXML
    private Label lbEndDate;
    @FXML
    private Label lbGrade;
    @FXML
    private Label lbInvestigationProject;
    @FXML
    private Label lbFileRoute;
    @FXML
    private Label lbTypeEvidence;
    @FXML
    private Label lbImpactCA;
    
    private ReceptionWork receptionWork;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    
    public void showReceptionWorkData(ReceptionWork receptionWork){
        this.receptionWork = receptionWork;
        lbTypeEvidence.setText("Trabajo recepcional");
        lbTitleEvidence.setText(receptionWork.getTitleReceptionWork());
        lbImpactCA.setText(receptionWork.getImpactCA());
        lbTypeWork.setText(receptionWork.getWorkType());
        lbActualState.setText(receptionWork.getActualState());
        lbAuthor.setText(receptionWork.getCollaborator().getName());
        lbPositionAuthor.setText(receptionWork.getCollaborator().getPosition());
        lbStartDate.setText(convertDateToString(receptionWork.getStartDate()));
        lbGrade.setText(receptionWork.getGrade());
        lbInvestigationProject.setText(receptionWork.getInvestigationProject().getTittleProject());
        lbFileRoute.setText(receptionWork.getFileRoute());
        if(receptionWork.getEndDate() != null){
            lbEndDate.setText(convertDateToString(receptionWork.getEndDate()));
        }
    }
    
    private String convertDateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = dateFormat.format(date);
        return stringDate;
    }

    @FXML
    private void modifyReceptionWork(ActionEvent event){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowModifyReceptionWork.fxml"));
            Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(WindowReceptionWorkDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        WindowModifyReceptionWorkController windowModifyReceptionWorkController = fxmlLoader.getController();
        windowModifyReceptionWorkController.fillFieldsReceptionWork(receptionWork);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        closeReceptionWorkData(event);
    }

    @FXML
    private void closeReceptionWorkData(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
}
