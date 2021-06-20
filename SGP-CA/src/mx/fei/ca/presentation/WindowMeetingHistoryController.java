
package mx.fei.ca.presentation;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.MeetingDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.Meeting;

/**
 * Clase para representar el controlador del FXML WindowMeetingHistory
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class WindowMeetingHistoryController implements Initializable {

    @FXML
    private TextField tfMeetingProject;
    @FXML
    private DatePicker dpMeetingDate;
    @FXML
    private TableView<Meeting> tbMeetingHistory;
    @FXML
    private TableColumn<Meeting, String> columnProject;
    @FXML
    private TableColumn<Meeting, Date> columnDate;
    @FXML
    private TableColumn<Meeting, Time> columnTime;   
    @FXML
    private Label lbUser;
    private Integrant integrant;
    
    /**
     * Enumerado que representa los tipos de errores específicos al usar el campo de texto para buscar una reunión
     */
    
    private enum TypeError{
        EMPTYFIELD, INVALIDSTRING;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        recoverMeetings();
        openMeetingAgenda();
    }  
    
    /**
     * Método que establece el integrante loggeado al sistema, permitiendo proyectar su nombre en la GUI
     * @param integrant Define el integrante a establecer a la GUI
     */
    
    public void setIntegrant(Integrant integrant){
        this.integrant = integrant;
        lbUser.setText(integrant.getNameIntegrant());
    }
    
    /**
     * Método que manda a recuperar las últimas 5 reuniones y las muestra en la GUI
     */
    
    private void recoverMeetings(){
        try{
            MeetingDAO meetingDAO = new MeetingDAO();
            ArrayList<Meeting> listMeetings = meetingDAO.findLastFiveMeetings();
            fillMeetingHistory(listMeetings);
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }
    }
    
    /**
     * Método que llena la tabla de reuniones de la GUI
     * @param listMeetings Define la lista de reuniones recuperadas que se mostrarán en la GUI
     * @throws BusinessConnectionException 
     */
    
    private void fillMeetingHistory(ArrayList<Meeting> listMeetings) throws BusinessConnectionException{
        columnProject.setCellValueFactory(new PropertyValueFactory("projectName"));
        columnDate.setCellValueFactory(new PropertyValueFactory("meetingDate"));
        columnTime.setCellValueFactory(new PropertyValueFactory("meetingTime"));
        ObservableList<Meeting> meetings = FXCollections.observableArrayList(listMeetings);
        tbMeetingHistory.setItems(meetings);
    }
    
    /**
     * Método que manda a buscar una reunión de acuerdo a su nombre de proyecto o nombre de proyecto y fecha
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */
    
    @FXML
    private void searchMeeting(ActionEvent event) throws BusinessConnectionException{
        MeetingDAO meetingDAO = new MeetingDAO();
        ArrayList<Meeting> listMeetings;
        if(!existsInvalidField()){
            if(dpMeetingDate.getValue() == null){
                listMeetings = meetingDAO.findMeetingsByProjectName(tfMeetingProject.getText());
            }else{
                Date meetingDate = parseToSqlDate(java.util.Date.from(dpMeetingDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                listMeetings = meetingDAO.findMeetingsByProjectNameAndDate(tfMeetingProject.getText(), meetingDate);
            }
            if(listMeetings.isEmpty()){
                showNoMatchAlert();
            }else{
                fillMeetingHistory(listMeetings);
            }
            cleanFields();
        }
    }
    
    /**
     * Método que manda a abrir la ventana agenda de reunión de acuerdo a la reunión seleccionada de la tabla
     */
    
    public void openMeetingAgenda(){
        tbMeetingHistory.setOnMouseClicked((MouseEvent event) -> {
            Meeting meeting = tbMeetingHistory.getItems().get(tbMeetingHistory.getSelectionModel().getSelectedIndex());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowMeetingAgenda.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException ex) {
                Logger.getLogger(WindowMeetingHistoryController.class.getName()).log(Level.SEVERE, null, ex);
            }   Stage stage = new Stage();
            stage.setScene(scene);
            WindowMeetingAgendaController windowMeetingAgendaController = (WindowMeetingAgendaController) fxmlLoader.getController();
            windowMeetingAgendaController.setIntegrant(integrant);
            windowMeetingAgendaController.showMeetingData(meeting);
            stage.show();
            recoverMeetings();
        });
    }
    
    /**
     * Método que cambia una variable util.Date a sql.Date porque se necesita para mandar a buscar una reunión
     * @param date Define la variable de tipo util.Date obtenida de la GUI
     * @return Variable de tipo sql.Date
     */
    
    private java.sql.Date parseToSqlDate(java.util.Date date){
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }
    
    /**
     * Método que manda a abrir la ventana para agendar una nueva reunión
     * @param event Define el evento generado
     */

    @FXML
    private void scheduleMeeting(ActionEvent event){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowNewMeeting.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            Logger.getLogger(WindowMeetingHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        WindowNewMeetingController windowNewMeetingController = (WindowNewMeetingController) fxmlLoader.getController();
        windowNewMeetingController.setIntegrant(integrant);
        stage.show();
        exitMeetingHistory(event);
    }
    
    /**
     * Metódo que cierra la ventana actual historial de reuniones
     * @param event Define el evento generado
     */

    @FXML
    private void exitMeetingHistory(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /** 
     * Método que verifica si existen campos inválidos. Este método invoca a otros métodos de verificación más específicos
     * @return con el resultado de la verificación, devuelve true si existen campos inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidField(){
        boolean invalidField = false;
        if(existsEmptyField() || existsInvalidString()){
            invalidField = true;
        }
        return invalidField;
    }
    
    /**
     * Método que verifica si existen campos de la GUI que estén vacíos
     * @return Booleano con el resultado de la verificación, devuelve true si existen vacíos, de lo contrario, devuelve false
     */
    
    private boolean existsEmptyField(){
        boolean emptyField = false;
        if(tfMeetingProject.getText().isEmpty()){
            emptyField = true;
            TypeError typeError = TypeError.EMPTYFIELD;
            showInvalidFieldAlert(typeError);
        }
        return emptyField;
    }
    
    /**
     * Método que verifica si existe cadena inválida de acuerdo al texto obtenido de la GUI
     * @return Booleano con el resultado de la verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidString(){
        boolean invalidString = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+$");
        Matcher matcher = pattern.matcher(tfMeetingProject.getText());
        if(!matcher.find()){
           invalidString = true;
           TypeError typeError = TypeError.INVALIDSTRING;
           showInvalidFieldAlert(typeError);
        }
        return invalidString;
    }
    
    /**
     * Método que muestra alerta de campo inválido de acuerdo al tipo de error
     * @param typeError Define el tipo de error que encontró
     */
    
    private void showInvalidFieldAlert(TypeError typeError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Campo inválido");
        if(typeError == TypeError.EMPTYFIELD){
            alert.setContentText("Existe campo vacío, llena el campo para poder buscar reunión");
        }
        if(typeError == TypeError.INVALIDSTRING){
            alert.setContentText("Existe caracter inválido, revisa el texto para poder buscar reunión");
        }
        alert.showAndWait();
    }
    
    /**
     * Método que muestra alerta sin coincidencias
     */
    
    private void showNoMatchAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Sin coincidencias");
        alert.setContentText("No se encontrarón coincidencias con la reunión buscada");
        alert.showAndWait();
    }
    
    /**
     * Método que limpia los campos de buscado de reunión
     */
    
    private void cleanFields(){
        tfMeetingProject.clear();
        dpMeetingDate.getEditor().clear();
    }
    
    /**
     * Método que muestra alerta de perdida de conexión con la base de datos
     */
    
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo guardar. Intente más tarde");
        alert.showAndWait();
    }
    
}