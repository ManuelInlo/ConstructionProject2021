
package mx.fei.ca.presentation;

import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    
    @FXML
    private Button btnExit;
    @FXML
    private Button btnModify;
    
    public void setReceptionWork(ReceptionWork receptionWork){
        this.receptionWork = receptionWork;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //showReceptionWorkData(receptionWork);
    }   
    
    public void showReceptionWorkData(ReceptionWork receptionWork){
        lbTypeEvidence.setText("Trabajo recepcional");
        lbTitleEvidence.setText(receptionWork.getTitleReceptionWork());
        lbImpactCA.setText(receptionWork.getImpactCA());
        lbTypeWork.setText(receptionWork.getWorkType());
        lbActualState.setText(receptionWork.getActualState());
        lbAuthor.setText(receptionWork.getCollaborator().getName());
        lbPositionAuthor.setText(receptionWork.getCollaborator().getPosition());
        lbStartDate.setText(convertDateToString(receptionWork.getStartDate()));
        lbGrade.setText(receptionWork.getGrade());
        //lbInvestigationProject.setText(receptionWork.getInvestigationProject().getName());
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
    private void modifyReceptionWork(ActionEvent event) {
    }

    @FXML
    private void closeReceptionWorkData(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
}
