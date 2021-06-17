package mx.fei.ca.presentation;

import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.CollaboratorDAO;
import mx.fei.ca.businesslogic.InvestigationProjectDAO;
import mx.fei.ca.businesslogic.ReceptionWorkDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Collaborator;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.InvestigationProject;
import mx.fei.ca.domain.ReceptionWork;

/**
 * Clase para representar el controlador del FXML WindowModifyReceptionWork
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class WindowModifyReceptionWorkController implements Initializable {

    @FXML
    private TextField tfTitleReceptionWork;
    @FXML
    private TextField tfFileRoute;
    @FXML
    private TextField tfAuthor;
    @FXML
    private ComboBox cbImpactCA;
    @FXML
    private ComboBox<InvestigationProject> cbInvestigationProject;
    @FXML
    private ComboBox cbType;
    @FXML
    private ComboBox cbActualState;
    @FXML
    private DatePicker dpStartDate;
    @FXML
    private DatePicker dpEndDate;
    @FXML
    private ComboBox cbGrade;
    @FXML
    private ComboBox cbPositionAuthor;
    @FXML
    private Label lbUser;
    private Integrant integrant;
    private int idReceptionWorkToModify;
    private int idCollaboratorToModify;
    
    /**
     * Enumerado que representa los tipos de errores específicos de GUI al modificar un trabajo recepcional
     */

    private enum TypeError{
        EMPTYFIELD, INVALIDSTRING, MISSINGSELECTION, MISSINGDATE, OVERDATE, INCONSISTENTDATE, TITLEDUPLICATE, 
        FILEROUTEDUPLICATE, COLLABORATORDUPLICATE;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxImpactCA();
        fillComboBoxGrade();
        fillComboBoxActualState();
        fillComboBoxType();
        fillComboBoxPositionAuthor();
        try {
            fillComboBoxInvestigationProject();
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        }
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
     * Método que llena el ComboBox de impactoCA
     */
    
    private void fillComboBoxImpactCA(){
        ObservableList<String> listImpactCA = FXCollections.observableArrayList("SI","NO");
        cbImpactCA.setItems(listImpactCA);
    }
    
    /**
     * Método que llena el ComboBox grado del trabajo recepcional
     */
    
    private void fillComboBoxGrade(){
        ObservableList<String> listImpactCA = FXCollections.observableArrayList("Licenciatura");
        cbGrade.setItems(listImpactCA);
    }
    
    /**
     * Método que llena el ComboBox estado actual del trabajo recepcional 
     */
    
    private void fillComboBoxActualState(){
        ObservableList<String> listActualState = FXCollections.observableArrayList("En proceso","Terminado");
        cbActualState.setItems(listActualState);
    }
    
    /**
     * Método que llena el ComboBox tipo de trabajo recepcional
     */
    
    private void fillComboBoxType(){
        ObservableList<String> listType = FXCollections.observableArrayList("Tesis","Monografía", "Práctico");
        cbType.setItems(listType);
        
    }
    
    /**
     * Método que llena el ComboBox cargo del autor
     */
    
    private void fillComboBoxPositionAuthor(){
        ObservableList<String> listPositionAuthor = FXCollections.observableArrayList("Estudiante");
        cbPositionAuthor.setItems(listPositionAuthor);
    }
    
    /**
     * Método que llena el ComboBox proyectos de investigación recuperando de la base de datos
     * @throws BusinessConnectionException 
     */
    
    private void fillComboBoxInvestigationProject() throws BusinessConnectionException{
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        ArrayList<InvestigationProject> investigationProjects = investigationProjectDAO.findAllInvestigationProjects();
        ObservableList<InvestigationProject> listInvestigationProject = FXCollections.observableArrayList(investigationProjects);
        cbInvestigationProject.setItems(listInvestigationProject);
    }
    
    /**
     * Método que llena los campos de la GUI con la información del trabajo recepcional a modificar
     * @param receptionWork Define el trabajo recepcional a modificar
     */
    
    public void fillFieldsReceptionWork(ReceptionWork receptionWork){
        idReceptionWorkToModify = receptionWork.getId();
        idCollaboratorToModify = receptionWork.getCollaborator().getIdCollaborator();
        tfTitleReceptionWork.setText(receptionWork.getTitleReceptionWork());
        tfFileRoute.setText(receptionWork.getFileRoute());
        tfAuthor.setText(receptionWork.getCollaborator().getName());
        cbImpactCA.getSelectionModel().select(receptionWork.getImpactCA());
        cbInvestigationProject.getSelectionModel().select(receptionWork.getInvestigationProject());
        cbType.getSelectionModel().select(receptionWork.getWorkType());
        cbActualState.getSelectionModel().select(receptionWork.getActualState());
        dpStartDate.setValue(receptionWork.getStartDate().toLocalDate());
        if(receptionWork.getEndDate() != null){
            dpEndDate.setValue(receptionWork.getEndDate().toLocalDate());
        }
        cbGrade.getSelectionModel().select(receptionWork.getGrade());
        cbPositionAuthor.getSelectionModel().select(receptionWork.getCollaborator().getPosition());   
    }
    
    /**
     * Método que manda a modificar el trabajo recepcional con la información obtenida de los campos de la GUI
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */

    @FXML
    private void modifyReceptionWork(ActionEvent event) throws BusinessConnectionException{
        if(!existsInvalidFields()){
            Date endDate = null;
            String impactCA = cbImpactCA.getSelectionModel().getSelectedItem().toString();
            String titleReceptionWork = tfTitleReceptionWork.getText();
            String fileRoute = tfFileRoute.getText();
            Date startDate = parseToSqlDate(java.util.Date.from(dpStartDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            String grade = cbGrade.getSelectionModel().getSelectedItem().toString();
            String workType = cbType.getSelectionModel().getSelectedItem().toString();
            String actualState = cbActualState.getSelectionModel().getSelectedItem().toString();
            String nameAuthor = tfAuthor.getText();
            String positionAuthor = cbPositionAuthor.getSelectionModel().getSelectedItem().toString();
            InvestigationProject investigationProject = cbInvestigationProject.getSelectionModel().getSelectedItem();
            if(cbActualState.getSelectionModel().getSelectedItem().toString().equals("Terminado")){
               endDate = parseToSqlDate(java.util.Date.from(dpEndDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())); 
            }
            Collaborator collaborator = new Collaborator(nameAuthor, positionAuthor);
            CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
            boolean updatedCollaborator = collaboratorDAO.updatedCollaboratorByIdCollaborator(collaborator, idCollaboratorToModify);
            if(updatedCollaborator){
                collaborator.setIdCollaborator(idCollaboratorToModify);
                ReceptionWork receptionWork = new ReceptionWork(impactCA, titleReceptionWork, fileRoute, startDate, endDate, grade,
                                                                workType, actualState);
                receptionWork.setCollaborator(collaborator);
                receptionWork.setInvestigationProject(investigationProject);
                receptionWork.setIntegrant(integrant);
                ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
                boolean updatedResult = receptionWorkDAO.updatedReceptionWorkById(receptionWork, idReceptionWorkToModify);
                if(updatedResult){
                    showConfirmationAlert();
                    closeReceptionWorkModify(event);
                }else{
                    showLostConnectionAlert();
                    closeReceptionWorkModify(event);
                }
            }else{
                showLostConnectionAlert();
                closeReceptionWorkModify(event);
            }
        }  
    }
    
    /**
     * Método que cierra la ventana actual de modificar trabajo recepcional
     * @param event Define el evento generado
     */
    
    @FXML
    private void closeReceptionWorkModify(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método que cambia una variable util.Date a sql.Date porque se necesita para guardar en la base de datos
     * @param date Define la variable de tipo util.Date obtenida de la GUI
     * @return Variable de tipo sql.Date
     */
    
    private java.sql.Date parseToSqlDate(java.util.Date date){
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }
    
    /**
     * Método que devuelve si existen o no campos inválidos
     * Con la finalidad de no extender el método, se invoca a otros métodos especificos para verficación de campos inválidos en la modificación del trabajo recepcional
     * @return Booleano con el resultado de la verificación, devuelve true si existen campos inválidos, de lo contrario devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean existsInvalidFields() throws BusinessConnectionException{
        boolean invalidFields = false;
        if(existsEmptyFields() || existsInvalidStrings() || existsMissingSelection() || existsInvalidDates()){
            invalidFields = true;
        }else if(existsDuplicateValues()){
            invalidFields = true;
        }
        return invalidFields;
    }
    
    /**
     * Método que verifica si existen campos de la GUI vacíos
     * @return Boolean con el resultado de la verificación, devuelve true si existen campos vacíos, de lo contrario, devuelve false
     */
    
    private boolean existsEmptyFields(){
        boolean emptyFields = false;
        if(tfTitleReceptionWork.getText().isEmpty() || tfFileRoute.getText().isEmpty() || tfAuthor.getText().isEmpty()){
            emptyFields = true;
            TypeError typeError = TypeError.EMPTYFIELD;
            showInvalidFieldAlert(typeError);
        }
        return emptyFields;
    }
    
    /**
     * Método que verifica si existen cadenas inválidas
     * @return Booleano con el resultado de la verificación, devuelve true si existen cadenas inválidas, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidStrings(){
        boolean invalidStrings = false;
        if(existsInvalidCharactersForTitle(tfTitleReceptionWork.getText()) || existsInvalidCharactersForAuthor(tfAuthor.getText())){
            invalidStrings = true;
            TypeError typeError = TypeError.INVALIDSTRING;
            showInvalidFieldAlert(typeError);
        }
        return invalidStrings;
    }
    
    /**
     * Método que verifica si existen caracteres inválidos en el nombre del autor
     * @param textToValidate Define el nombre del autor a validar 
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidCharactersForAuthor(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }
    
    /**
     * Método que verifica si existen caracteres inválidos en el título del trabajo
     * Se implementa el método porque un título puede tener ciertos caracteres válidos a diferencia de un nombre personal
     * @param textToValidate Define el título del trabajo a validar
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidCharactersForTitle(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s\\.,:]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }
    
    /**
     * Método que verifica si existen selecciones de campos faltantes en la GUI
     * @return Booleano con el resultado de verificación, devuelve true si existen faltantes, de lo contrario, devuelve false
     */
    
    private boolean existsMissingSelection(){
        boolean missingSelection = false;
        if(cbImpactCA.getSelectionModel().getSelectedItem().equals("") || cbInvestigationProject.getSelectionModel().getSelectedItem() == null ||
           cbType.getSelectionModel().getSelectedItem().equals("") || cbActualState.getSelectionModel().getSelectedItem().equals("") ||
           cbGrade.getSelectionModel().getSelectedItem().equals("") || cbPositionAuthor.getSelectionModel().getSelectedItem().equals("")){
            missingSelection = true;
            TypeError typeError = TypeError.MISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }
        return missingSelection;
    }
    
    /**
     * Método que verifica si existen fechas inválidas en los campos de la GUI
     * Con la finalidad de no extender demasiado, el método invoca a otros métodos específicos
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidDates(){
        boolean invalidDates = false;
        if(cbActualState.getSelectionModel().getSelectedItem().toString().equals("Terminado")){
            if(existMissingDate(dpStartDate) || existMissingDate(dpEndDate) || existInconsistentDates()){
                invalidDates = true;
            }
        }else{
            if(existMissingDate(dpStartDate) || existLeftOverDateSelection(dpEndDate)){
                invalidDates = true;
            }
        }
        return invalidDates;
    }
    
    /**
     * Método que verifica si existe selección de fecha faltante de campo de tipo DatePicker
     * @param datePicker Define la fecha seleccionada a verificar del campo de tipo DatePicker
     * @return Booleano con el resultado de verificación, devuelve true si existe faltante, de lo contrario, devuelve false
     */
    
    private boolean existMissingDate(DatePicker datePicker){
        boolean missingDate = false;
        if(datePicker.getValue() == null){
            missingDate = true;
            TypeError typeError = TypeError.MISSINGDATE;
            showInvalidFieldAlert(typeError);
        }
        return missingDate;
    }
    
    /**
     * Método que verifica si existe una selección de fecha sobrante
     * @param datePicker Define el valor del campo de tipo DatePicker a verificar
     * @return Booleano con el valor de verificación, devuelve true si existe fecha sobrante, de lo contrario, devuelve false
     */
    
    private boolean existLeftOverDateSelection(DatePicker datePicker){
        boolean selection = false;
        if(datePicker.getValue() != null){
            selection = true;
            TypeError typeError = TypeError.OVERDATE;
            showInvalidFieldAlert(typeError);
        }
        return selection;
    }
    
    /**
     * Método que verifica si existen fechas incosistentes, o bien, fecha de inicio mayor a la fecha de fin
     * @return Booleano con el resultado de verificación, devuelve true si existe incosistencia, de lo contrario, devuelve false
     */
    
    private boolean existInconsistentDates(){
        boolean inconsistentDates = false;
        int startDay = dpStartDate.getValue().getDayOfMonth();
        int startMonth = dpStartDate.getValue().getMonthValue();
        int startYear = dpStartDate.getValue().getYear();
        int endDay = dpEndDate.getValue().getDayOfMonth();
        int endMonth = dpEndDate.getValue().getMonthValue();
        int endYear = dpEndDate.getValue().getYear();
        if(startDay > endDay && startMonth >= endMonth && startYear >= endYear){
            inconsistentDates = true;
            TypeError typeError = TypeError.INCONSISTENTDATE;
            showInvalidFieldAlert(typeError);
        }
        return inconsistentDates;
    }
    
    /**
     * Método que manda a verificar si valores obtenidos de la GUI que no pueden duplicarse ya existen en la base de datos
     * El método manda a llamar a otros métodos que se encargan de la verificación en la capa lógica
     * @return Booleano con el resultado de la verificación, devuelve true si existe valor duplicado, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean existsDuplicateValues() throws BusinessConnectionException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
        boolean duplicateValues = false;
        boolean receptionWorkTitleDuplicate = false;
        boolean fileRouteDuplicate = false;
        boolean collaboratorNameDuplicate = false;
        if(receptionWorkDAO.existsReceptionWorkTitleForUpdate(tfTitleReceptionWork.getText(), idReceptionWorkToModify)){  //DEBE HACER LO MISMO CON LAS OTRAS EVIDENCIAS
            receptionWorkTitleDuplicate = true;
            TypeError typeError = TypeError.TITLEDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
           
        if(receptionWorkDAO.existsReceptionWorkFileRouteForUpdate(tfFileRoute.getText(), idReceptionWorkToModify)){
            fileRouteDuplicate = true;
            TypeError typeError = TypeError.FILEROUTEDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
        
        if(collaboratorDAO.existsCollaboratorNameForUpdate(tfAuthor.getText(), idCollaboratorToModify)){
            collaboratorNameDuplicate = true;
            TypeError typeError = TypeError.COLLABORATORDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
        
        if(receptionWorkTitleDuplicate || fileRouteDuplicate || collaboratorNameDuplicate){
            duplicateValues = true;
        }
        
        return duplicateValues;
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
          alert.setContentText("Existen campos vacíos, llena los campos para poder modificar");  
        }
        
        if(typeError == TypeError.INVALIDSTRING){
            alert.setContentText("Existen caracteres inválidos, revisa los textos para poder modificar");
        }
        
        if(typeError == TypeError.MISSINGSELECTION){
            alert.setContentText("Existe selección de campo faltante, selecciona los campos para poder modificar");
        }
        
        if(typeError == TypeError.MISSINGDATE){
            alert.setContentText("Existe fecha faltante, selecciona las fechas para poder modificar");
        }
        
        if(typeError == TypeError.INCONSISTENTDATE){
            alert.setContentText("La fecha de inicio es mayor a la fecha de fin, corrige campos para poder modificar");
        }
        
        if(typeError == TypeError.OVERDATE){
            alert.setContentText("El trabajo recepcional no está terminado, por lo tanto no puedes guardar una fecha de fin");
        }
        
        if(typeError == TypeError.TITLEDUPLICATE){
            alert.setContentText("El título del trabajo recepcional ya se encuentra registrado en el sistema");
        }
        
        if(typeError == TypeError.FILEROUTEDUPLICATE){
            alert.setContentText("La ruta de archivo del trabajo recepcional ya se encuentra registrado en otro trabajo recepcional");
        }

        if(typeError == TypeError.COLLABORATORDUPLICATE){
            alert.setContentText("El estudiante ya cuenta con un trabajo recepcional registrado en el sistema");
        }
        alert.showAndWait();    
    }
    
    /**
     * Método que muestra alerta de confirmación de guardado
     */
    
    private void showConfirmationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación de guardado");
        alert.setContentText("La información fue modificada con éxito");
        alert.showAndWait();
    }
    
    /**
     * Método que muestra alerta de perdida de conexión con la base de datos
     */
    
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo modificar. Intente más tarde");
        alert.showAndWait();
    }
    
}
