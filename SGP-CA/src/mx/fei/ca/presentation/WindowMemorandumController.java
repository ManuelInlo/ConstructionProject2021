package mx.fei.ca.presentation;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import mx.fei.ca.businesslogic.MeetingDAO;
import mx.fei.ca.businesslogic.MemorandumApproverDAO;
import mx.fei.ca.businesslogic.MemorandumDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.domain.AgendaPoint;
import mx.fei.ca.domain.Agreement;
import mx.fei.ca.domain.Integrant;
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
    private ListView<MemorandumApprover> listViewMemorandumApprovers;
    @FXML
    private CheckBox checkBoxApprove;
    @FXML
    private Label lbUser;
    @FXML
    private ListView<MemorandumApprover> listMemorandumApprovers;
    private Integrant integrant;
    private Memorandum memorandum;
    private Meeting meeting;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setIntegrant(Integrant integrant){
        this.integrant = integrant;
        lbUser.setText(integrant.getNameIntegrant());
    }
    
    public void showMeetingData(Meeting meeting){
        this.meeting = meeting;
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
            this.memorandum = memorandum;
            AgreementDAO agreementDAO = new AgreementDAO();
            ArrayList<Agreement> agreements = agreementDAO.findAgreementsByIdMemorandum(memorandum.getIdMemorandum());
            fillAgreementsTable(agreements);
            MemorandumApproverDAO memorandumApproverDAO = new MemorandumApproverDAO();
            ArrayList<MemorandumApprover> memorandumApprovers = memorandumApproverDAO.findMemorandumApproversByIdMemorandum(memorandum.getIdMemorandum());
            fillListMemorandumApprovers(memorandumApprovers);
            this.memorandum.setAgreements(agreements);
            this.memorandum.setApprovers(memorandumApprovers);
            //Mandar a verificar si se encuentra en la lista de aprobadores y si si, que se desactive el checkbox
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
    
    private void fillListMemorandumApprovers(ArrayList<MemorandumApprover> memorandumApprovers){
        ObservableList<MemorandumApprover> listMemorandumApprovers = FXCollections.observableArrayList(memorandumApprovers);
        listViewMemorandumApprovers.setItems(listMemorandumApprovers);
    }
    
    @FXML
    private void closeMemorandum(ActionEvent event) throws BusinessConnectionException{
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
    
    @FXML
    private void editMemorandum(ActionEvent event) throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        if(meetingDAO.getCurpOfResponsibleMeeting(this.meeting.getIdMeeting()).equals(integrant.getCurp())){ 
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowEditMemorandum.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException ex) {
                Logger.getLogger(WindowMemorandumController.class.getName()).log(Level.SEVERE, null, ex);
            }
            WindowEditMemorandumController windowEditMemorandumController = fxmlLoader.getController();
            windowEditMemorandumController.setIntegrant(integrant);
            windowEditMemorandumController.fillMemorandumData(this.memorandum);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Acción inválida");
            alert.setContentText("Solo el responsable de la reunión puede modificar la minuta");
            alert.showAndWait();
        }  
    }
    
    
    private void savedMemorandumApprover() throws BusinessConnectionException{
        MemorandumApproverDAO memorandumApproverDAO = new MemorandumApproverDAO();
        MemorandumApprover memorandumApprover = new MemorandumApprover(integrant); 
        boolean savedMemorandumApprover = memorandumApproverDAO.savedMemorandumApprover(memorandumApprover, this.memorandum.getIdMemorandum());
        if(!savedMemorandumApprover){
            showLostConnectionAlert();
        }
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
