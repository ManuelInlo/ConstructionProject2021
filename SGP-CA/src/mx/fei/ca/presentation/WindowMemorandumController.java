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
import mx.fei.ca.businesslogic.IntegrantDAO;
import mx.fei.ca.businesslogic.MeetingDAO;
import mx.fei.ca.businesslogic.MemorandumApproverDAO;
import mx.fei.ca.businesslogic.MemorandumDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.AgendaPoint;
import mx.fei.ca.domain.Agreement;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.Meeting;
import mx.fei.ca.domain.MeetingAssistant;
import mx.fei.ca.domain.Memorandum;
import mx.fei.ca.domain.MemorandumApprover;
import mx.fei.ca.domain.Prerequisite;

/**
 * Clase para representar el controlador del FXML WindowMemorandum
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class WindowMemorandumController implements Initializable {

    @FXML
    private TableView<MeetingAssistant> tbIntegrants;
    @FXML
    private TableColumn<MeetingAssistant, String> columnAssistant;
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
    private TableColumn<Agreement, String> columnIntegrant;
    @FXML
    private TableColumn<Agreement, String> columnDate;
    @FXML
    private TextArea taNotes;
    @FXML
    private TextArea taPendings;
    @FXML
    private ListView<MemorandumApprover> listViewMemorandumApprovers;
    @FXML
    private CheckBox checkBoxApprove;
    @FXML
    private Label lbUser;
    @FXML
    private Label lbResponsibleMeeting;
    private Integrant integrant;
    private Memorandum memorandum;
    private Meeting meeting;
    private String curpResponsible;    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    /**
     * M??todo que establece el integrante loggeado al sistema, permitiendo proyectar su nombre en la GUI
     * @param integrant Define el integrante a establecer en la GUI
     */
    
    public void setIntegrant(Integrant integrant){
        this.integrant = integrant;
        lbUser.setText(integrant.getNameIntegrant());
    }
    
    /**
     * M??todo que muestra la informaci??n de la reuni??n en la GUI. El m??todo invoca a otros m??todos para el lleano de otras secciones
     * @param meeting Define la reuni??n de la cual se mostrar?? la informaci??n
     */
    
    public void showMeetingData(Meeting meeting){
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
        try {
            this.memorandum = memorandumDAO.findMemorandumByIdMeeting(meeting.getIdMeeting());
            AgreementDAO agreementDAO = new AgreementDAO();
            ArrayList<Agreement> agreements = agreementDAO.findAgreementsByIdMemorandum(memorandum.getIdMemorandum());
            fillAgreementsTable(agreements);
            MemorandumApproverDAO memorandumApproverDAO = new MemorandumApproverDAO();
            ArrayList<MemorandumApprover> memorandumApprovers = memorandumApproverDAO.findMemorandumApproversByIdMemorandum(memorandum.getIdMemorandum());
            fillListMemorandumApprovers(memorandumApprovers);
            this.memorandum.setAgreements(agreements);
            this.memorandum.setApprovers(memorandumApprovers);
            if(checkMemorandumApproverExistence()){
                checkBoxApprove.setSelected(true);
                checkBoxApprove.setDisable(true);
            }
            MeetingDAO meetingDAO = new MeetingDAO();
            this.curpResponsible = meetingDAO.getCurpOfResponsibleMeeting(meeting.getIdMeeting());
            IntegrantDAO integrantDAO = new IntegrantDAO();
            lbResponsibleMeeting.setText(integrantDAO.findIntegrantByCurp(curpResponsible).getNameIntegrant());
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }
        taNotes.setText(this.memorandum.getNote());
        taPendings.setText(this.memorandum.getPending());
        taNotes.setEditable(false);
        taPendings.setEditable(true);
        this.meeting = meeting;
    }
    
    /** 
     * M??todo que verifica si aprobador de minuta ya existe de acuerdo a la curp del integrante loggeado
     * @return Booleano con el resultado de verificaci??n, true si ya existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean checkMemorandumApproverExistence() throws BusinessConnectionException{
        MemorandumApproverDAO memorandumApproverDAO = new MemorandumApproverDAO();
        boolean exists = memorandumApproverDAO.existsMemorandumApproverByCurp(integrant.getCurp(), memorandum.getIdMemorandum());
        return exists;
    }
    
    /**
     * M??todo que llena la tabla de asistentes de reuni??n de la GUI
     * @param meetingAssistants Define la lista de asistentes de reuni??n a mostrar
     */
    
     private void fillMeetingAssistantsTable(ArrayList<MeetingAssistant> meetingAssistants){
        columnAssistant.setCellValueFactory(new PropertyValueFactory("nameAssistant"));
        columnRole.setCellValueFactory(new PropertyValueFactory("role"));
        ObservableList<MeetingAssistant> listMeetingAssistants = FXCollections.observableArrayList(meetingAssistants);
        tbIntegrants.setItems(listMeetingAssistants);
    }
     
    /**
     * M??todo que llena la tabla de prerequisitos de la GUI
     * @param prerequisites Define la lista de prerequisitos a mostrar
     */
    
    private void fillPrerequisitesTable(ArrayList<Prerequisite> prerequisites){
        columnDescription.setCellValueFactory(new PropertyValueFactory("description"));
        columnPrerequisiteManager.setCellValueFactory(new PropertyValueFactory("prerequisiteManager"));
        ObservableList<Prerequisite> listPrerequisites = FXCollections.observableArrayList(prerequisites);
        tbPrerequisites.setItems(listPrerequisites);
    }
    
    /**
     * M??todo que llena la tabla de puntos de agenda de la GUI
     * @param agendaPoints Define la lista de puntos de agenda a mostrar
     */
    
    private void fillAgendaPointsTable(ArrayList<AgendaPoint> agendaPoints){
        columnTimeStart.setCellValueFactory(new PropertyValueFactory("startTime"));
        columnTimeEnd.setCellValueFactory(new PropertyValueFactory("endTime"));
        columnTopic.setCellValueFactory(new PropertyValueFactory("topic"));
        columnLeaderDiscussion.setCellValueFactory(new PropertyValueFactory("leader"));
        ObservableList<AgendaPoint> listAgendaPoints = FXCollections.observableArrayList(agendaPoints);
        tbAgendaPoints.setItems(listAgendaPoints);
    }
    
    /**
     * M??todo que llena la tabla de acuerdos de la GUI
     * @param agreements Define la lista de acuerdos a mostrar
     */
    
    private void fillAgreementsTable(ArrayList<Agreement> agreements){
        columnAgreement.setCellValueFactory(new PropertyValueFactory("description"));
        columnIntegrant.setCellValueFactory(new PropertyValueFactory("responsible"));
        columnDate.setCellValueFactory(new PropertyValueFactory("dateAgreement"));
        ObservableList<Agreement> listAgreements = FXCollections.observableArrayList(agreements);
        tbAgreements.setItems(listAgreements);
    }
    
    /**
     * M??todo que llena una ListView con los aprobadores de minuta
     * @param memorandumApprovers Define la lista de aprobadores de minuta a mostrar
     */
    
    private void fillListMemorandumApprovers(ArrayList<MemorandumApprover> memorandumApprovers){
        ObservableList<MemorandumApprover> listMemorandumApprovers = FXCollections.observableArrayList(memorandumApprovers);
        listViewMemorandumApprovers.setItems(listMemorandumApprovers);
    }
    
    /**
     * M??todo que cierra la ventana actual minuta de acuerdo a ciertos criterios 
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */
    
    @FXML
    private void closeMemorandum(ActionEvent event) throws BusinessConnectionException{
        if(checkMemorandumApproverExistence()){
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();  
        }else if(checkBoxApprove.isSelected()){
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
    
    /**
     * M??todo que manda a abrir la ventana para editar la minuta de acuerdo a cierto criterio
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */
    
    @FXML
    private void editMemorandum(ActionEvent event) throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        if(curpResponsible.equals(integrant.getCurp())){ 
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
            stage.show();
            closeMemorandum(event);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Acci??n inv??lida");
            alert.setContentText("Solo el responsable de la reuni??n puede modificar la minuta");
            alert.showAndWait();
        }  
    }
    
    /**
     * M??todo que manda a guardar un nuevo aprobador de la minuta
     * @throws BusinessConnectionException 
     */
    
    private void savedMemorandumApprover() throws BusinessConnectionException{
        MemorandumApproverDAO memorandumApproverDAO = new MemorandumApproverDAO();
        MemorandumApprover memorandumApprover = new MemorandumApprover(integrant); 
        boolean savedMemorandumApprover = memorandumApproverDAO.savedMemorandumApprover(memorandumApprover, this.memorandum.getIdMemorandum());
        if(!savedMemorandumApprover){
            showLostConnectionAlert();
        }
    }
    
    /**
     * M??todo que convierte una variable de tipo Date hacia String
     * @param date Define la fecha a converit de Date a String
     * @return String con la fecha convertida
     */
    
    private String convertDateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = dateFormat.format(date);
        return stringDate;
    }
    
    /**
     * M??todo que convierte una variable de tipo Time hacia String
     * @param time Define la hora a convertir de Time a String
     * @return String con la hora convertida
     */
    
    private String convertTimeToString(Time time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String stringTime = simpleDateFormat.format(time);
        return stringTime;
    }
    
    /**
     * M??todo que muestra la alerta de selecci??n de CheckBox faltante
     * @return El tipo de bot??n seleccionado por parte del usuario
     */
    
    private Optional<ButtonType> showMissingApprovalAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Advertencia aprobaci??n");
        alert.setContentText("??Estas seguro que no deseas aprobar la minuta?");
        Optional<ButtonType> action = alert.showAndWait();
        return action;
    }
    
    /**
     * M??todo que muestra la alerta de perdida de conexi??n con la base de datos
     */
    
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexi??n");
        alert.setContentText("Perdida de conexi??n con la base de datos, no se pudo guardar. Intente m??s tarde");
        alert.showAndWait();
    }

}
