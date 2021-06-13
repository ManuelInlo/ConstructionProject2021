/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.fei.ca.presentation;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.AgreementDAO;
import mx.fei.ca.businesslogic.MemorandumApproverDAO;
import mx.fei.ca.businesslogic.MemorandumDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.domain.AgendaPoint;
import mx.fei.ca.domain.Agreement;
import mx.fei.ca.domain.Meeting;
import mx.fei.ca.domain.MeetingAssistant;
import mx.fei.ca.domain.Memorandum;
import mx.fei.ca.domain.MemorandumApprover;
import mx.fei.ca.domain.Prerequisite;

/**
 * FXML Controller class
 *
 * @author david
 */
public class WindowMemorandumController implements Initializable {

    @FXML
    private TableView<MeetingAssistant> tbIntegrants;
    @FXML
    private TableColumn<MeetingAssistant, String> columnIntegrant;
    @FXML
    private TableColumn<MeetingAssistant, String> columnRole;
    @FXML
    private TableView<Prerequisite> tbPrerequisites;
    @FXML
    private TableColumn<Prerequisite, String> columnDescription;
    @FXML
    private TableColumn<Prerequisite, String> columnPrerequisiteManager;
    @FXML
    private TableView<AgendaPoint> tbAgendaPoints;
    @FXML
    private TableColumn<AgendaPoint, Time> columnTimeStart;
    @FXML
    private TableColumn<AgendaPoint, Time> columnTimeEnd;
    @FXML
    private TableColumn<AgendaPoint, String> columnTopic;
    @FXML
    private TableColumn<AgendaPoint, String> columnLeaderDiscussion;
    @FXML
    private Label lbNameProject;
    @FXML
    private Label lbMeetingPlace;
    @FXML
    private Label lbAffair;
    @FXML
    private Label lbMeetingDate;
    @FXML
    private Label lbMeetingTime;
    @FXML
    private Label lbState;
    @FXML
    private TableView<Agreement> tbAgreements;
    @FXML
    private TableColumn<Agreement, String> columnAgreement;
    @FXML
    private TableColumn<Agreement, String> columnDate;
    @FXML
    private TextArea taNotes;
    @FXML
    private TextArea taPendings;
    @FXML
    private Label lbProjectName;
    @FXML
    private ListView<MemorandumApprover> listMemorandumApprovers;
    @FXML
    private CheckBox checkBoxApprove;
    
    private Memorandum memorandum;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void showMeetingData(Meeting meeting){
        lbNameProject.setText(meeting.getProjectName());
        lbMeetingPlace.setText(meeting.getMeetingPlace());
        lbAffair.setText(meeting.getAffair());
        lbMeetingDate.setText(convertDateToString(meeting.getMeetingDate()));
        lbMeetingTime.setText(convertTimeToString(meeting.getMeetingTime()));
        lbState.setText(meeting.getState());
        fillMeetingAssistantsTable(meeting.getAssistants());
        fillPrerequisitesTable(meeting.getPrerequisites());
        fillAgendaPointsTable(meeting.getAgendaPoints());
        MemorandumDAO memorandumDAO = new MemorandumDAO();
        Memorandum memorandum = null;
        try {
            memorandum = memorandumDAO.findMemorandumByIdMeeting(meeting.getIdMeeting());
            AgreementDAO agreementDAO = new AgreementDAO();
            ArrayList<Agreement> agreements = agreementDAO.findAgreementsByIdMemorandum(memorandum.getIdMemorandum());
            fillAgreementsTable(agreements);
            MemorandumApproverDAO memorandumApproverDAO = new MemorandumApproverDAO();
            ArrayList<MemorandumApprover> memorandumApprovers = memorandumApproverDAO.findMemorandumApproversByIdMemorandum(memorandum.getIdMemorandum());
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }
        this.memorandum = memorandum;
    }
    
    private void fillMeetingAssistantsTable(ArrayList<MeetingAssistant> meetingAssistants){
        columnIntegrant.setCellValueFactory(new PropertyValueFactory("nameAssistant"));
        columnRole.setCellValueFactory(new PropertyValueFactory("role"));
        ObservableList<MeetingAssistant> listMeetingAssistants = FXCollections.observableArrayList(meetingAssistants);
        tbIntegrants.setItems(listMeetingAssistants);
    }
    
    private void fillPrerequisitesTable(ArrayList<Prerequisite> prerequisites){
        columnDescription.setCellValueFactory(new PropertyValueFactory("description"));
        columnPrerequisiteManager.setCellValueFactory(new PropertyValueFactory("prerequisiteManager"));
        ObservableList<Prerequisite> listPrerequisites = FXCollections.observableArrayList(prerequisites);
        tbPrerequisites.setItems(listPrerequisites);
    }
    
    private void fillAgendaPointsTable(ArrayList<AgendaPoint> agendaPoints){
        columnTimeStart.setCellValueFactory(new PropertyValueFactory("startTime"));
        columnTimeEnd.setCellValueFactory(new PropertyValueFactory("endTime"));
        columnTopic.setCellValueFactory(new PropertyValueFactory("topic"));
        columnLeaderDiscussion.setCellValueFactory(new PropertyValueFactory("leader"));
        ObservableList<AgendaPoint> listAgendaPoints = FXCollections.observableArrayList(agendaPoints);
        tbAgendaPoints.setItems(listAgendaPoints);
    }
    
    private void fillAgreementsTable(ArrayList<Agreement> agreements){
        columnAgreement.setCellValueFactory(new PropertyValueFactory("description"));
        columnIntegrant.setCellValueFactory(new PropertyValueFactory("responsible"));
        columnDate.setCellValueFactory(new PropertyValueFactory("dateAgreement"));
        ObservableList<Agreement> listAgreements = FXCollections.observableArrayList(agreements);
        tbAgreements.setItems(listAgreements);
    }
    
    @FXML
    private void closeMemorandum(ActionEvent event){
        if(checkBoxApprove.isSelected()){
            savedMemorandumApprover();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }else{
            Optional<ButtonType> action = showMissingApprovalAlert();
            if(action.get() == ButtonType.OK){
               Node source = (Node) event.getSource();
               Stage stage = (Stage) source.getScene().getWindow();
               stage.close();
            }
        }
        
    }
    
    private void savedMemorandumApprover(){
        
    }
    
    private String convertDateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = dateFormat.format(date);
        return stringDate;
    }
    
    private String convertTimeToString(Time time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String stringTime = simpleDateFormat.format(time);
        return stringTime;
    }
    
    private Optional<ButtonType> showMissingApprovalAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Advertencia aprobación");
        alert.setContentText("¿Estas seguro que no deseas aprobar la minuta?");
        Optional<ButtonType> action = alert.showAndWait();
        return action;
    }
    
    
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo guardar. Intente más tarde");
        alert.showAndWait();
    }
    
}
