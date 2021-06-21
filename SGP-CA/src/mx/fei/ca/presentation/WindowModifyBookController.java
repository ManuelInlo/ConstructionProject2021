
package mx.fei.ca.presentation;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.BookDAO;
import mx.fei.ca.businesslogic.InvestigationProjectDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Book;
import mx.fei.ca.domain.Evidence;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.InvestigationProject;

/**
 * Clase para representar el controlador del FXML WindowModifyBook
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

public class WindowModifyBookController implements Initializable {

    @FXML
    private Label lbUser;
    @FXML
    private TextField tfIsbn;
    @FXML
    private TextField tfPrinting;
    @FXML
    private TextField tfNumPages;
    @FXML
    private TextField tfCountry;
    @FXML
    private TextField tfEdition;
    @FXML
    private TextField tfEditorial;
    @FXML
    private TextField tfFileRoute;
    @FXML
    private DatePicker dpPublicationDate;
    @FXML
    private ComboBox cbActualState;
    @FXML
    private TextField tfTitleEvidence;
    @FXML
    private TextField tfAuthor;
    @FXML
    private ComboBox cbImpactCA;
    @FXML
    private ComboBox<InvestigationProject> cbInvestigationProject;
    @FXML
    private ComboBox cbParticipationType;
    private Integrant integrant;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxImpactCA();
        fillComboBoxActualState();
        fillComboBoxParticipationType();       
        try {
            fillComboBoxInvestigationProject();
        } catch (BusinessConnectionException ex) {
            showLostConnectionAlert();
        } 
    }   
    
    /**
     * Enumerado que representa los tipos de errores específicos de GUI al modificar un libro
     */
    
    private enum TypeError{
        EMPTYFIELD, INVALIDSTRING, MISSINGSELECTION, TITLEDUPLICATE, NUMBERDUPLICATE, INVALIDLENGTH, 
        INCONSISTENTPAGE, INVALIDPAGE, INCONSISTENTPAGEBOOK, INVALIDPRINTING, MISSINGDATE, INCONSISTENTDATE;
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
     * Método que llena el ComboBox de impacto al CA
     */

    private void fillComboBoxImpactCA() {
        ObservableList<String> listImpactCA = FXCollections.observableArrayList("SI","NO");
        cbImpactCA.setItems(listImpactCA);
    }
    
    /**
     * Método que llena el ComboBox estado actual del libro
     */
    
    private void fillComboBoxActualState() {
        ObservableList<String> listActualState = FXCollections.observableArrayList("En proceso","Terminado", "Publicado");
        cbActualState.setItems(listActualState);
    }

    /**
     * Método que llena el ComboBox tipo de participación en la elaboración del libro
     */
    
    private void fillComboBoxParticipationType() {
        ObservableList<String> listParticipationType = FXCollections.observableArrayList("Estudiante");
        cbParticipationType.setItems(listParticipationType);
    }      
    
    /**
     * Método que llena el ComboBox proyectos de investigación recuperados de la base de datos
     * @throws BusinessConnectionException 
     */
    
    private void fillComboBoxInvestigationProject() throws BusinessConnectionException{
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        ArrayList<InvestigationProject> investigationProjects = investigationProjectDAO.findAllInvestigationProjects();
        ObservableList<InvestigationProject> listInvestigationProject = FXCollections.observableArrayList(investigationProjects);
        cbInvestigationProject.setItems(listInvestigationProject);
    }
    
    /**
     * Método que llena los campos de la GUI con la información del libro a modificar
     * @param book Define el libro a modificar
     */
    
    public void fillFieldBook(Book book){         
        cbImpactCA.getSelectionModel().select(book.getImpactCA());
        tfIsbn.setText(book.getIsbn());
        tfPrinting.setText(String.valueOf(book.getPrinting()));   
        tfNumPages.setText(String.valueOf(book.getNumPages())); 
        cbParticipationType.getSelectionModel().select(book.getParticipationType());
        cbActualState.getSelectionModel().select(book.getActualState());        
        tfCountry.setText(book.getCountry());       
        tfEditorial.setText(book.getEditorial());           
        tfEdition.setText(book.getEdition());          
        tfFileRoute.setText(book.getFileRoute());
        tfAuthor.setText(book.getAuthor()); 
        tfTitleEvidence.setText(book.getTitleEvidence()); 
        if(book.getPublicationDate() != null){
            dpPublicationDate.setValue(book.getPublicationDate().toLocalDate()); 
        }   
        cbInvestigationProject.getSelectionModel().select(book.getInvestigationProject()); 
    }    
    
    /**
     * Método que manda a modificar el libro con la información obtenida de los campos de la GUI
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */       
    
    @FXML
    private void modifyBook(ActionEvent event) {    
        Date publicationDate = null;        
        String impactCA = cbImpactCA.getSelectionModel().getSelectedItem().toString();
        String isbn = tfIsbn.getText();
        int priting = Integer.parseInt(tfPrinting.getText());   
        int numPages = Integer.parseInt(tfNumPages.getText()); 
        String participationType = cbParticipationType.getSelectionModel().getSelectedItem().toString();
        String actualState = cbActualState.getSelectionModel().getSelectedItem().toString();        
        String country = tfCountry.getText();       
        String editorial = tfEditorial.getText();           
        String edition = tfEdition.getText();          
        String fileRoute = tfFileRoute.getText();
        String author = tfAuthor.getText(); 
        String titleBook = tfTitleEvidence.getText(); 
        if(cbActualState.getSelectionModel().getSelectedItem().toString().equals("Publicado")){
            publicationDate = parseToSqlDate(java.util.Date.from(dpPublicationDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())); 
        }   
        InvestigationProject investigationProject = cbInvestigationProject.getSelectionModel().getSelectedItem(); 
        Evidence evidence = new Evidence(impactCA, titleBook, author);
        Book book = new Book(evidence, isbn, priting, numPages, participationType, actualState, country, publicationDate,
                                editorial, edition, fileRoute);
        book.setInvestigationProject(investigationProject);
        book.setIntegrant(integrant);
        book.setCurp(integrant.getCurp());
        BookDAO bookDAO = new BookDAO();
        boolean updateResult = false;
        try {
            updateResult = bookDAO.updatedBook(book, isbn);
        } catch (BusinessConnectionException ex) {
            Logger.getLogger(WindowModifyBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(updateResult){
            showConfirmationAlert();
            closeModifyBook(event);
        }else{
            showLostConnectionAlert(); 
            closeModifyBook(event);
        }   
    }


    /**
     * Método que cierra la GUI actual 
     * @param event Define el evento generado
     */    
    
    @FXML
    private void closeModifyBook(ActionEvent event) {
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
     * Método que verifica si la longitud del campo excede el límite permitido
     * @return Boolean con el resultado de la verificación, devuelve true si existen campos vacíos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidLength(){
        boolean invalidLength = false;
        if(tfTitleEvidence.getText().length() > 255 || tfFileRoute.getText().length() > 500 || tfAuthor.getText().length() > 255 || tfPrinting.getText().length() > 5 ||
           tfNumPages.getText().length() > 5 || tfCountry.getText().length() > 255 || tfEdition.getText().length() > 225 || tfIsbn.getText().length() > 17 || 
           tfEditorial.getText().length() > 255){
            invalidLength = true;
            TypeError typeError = TypeError.INVALIDLENGTH;
            showInvalidFieldAlert(typeError);
        }
        return invalidLength;
    }
    
    /**
     * Método que verifica si existen campos de la GUI vacíos
     * @return Boolean con el resultado de la verificación, devuelve true si existen campos vacíos, de lo contrario, devuelve false
     */
    
    private boolean existsEmptyFields(){
        boolean emptyFields = false;
        if(tfTitleEvidence.getText().isEmpty() || tfFileRoute.getText().isEmpty() || tfAuthor.getText().isEmpty() || tfPrinting.getText().isEmpty() ||
           tfNumPages.getText().isEmpty() || tfCountry.getText().isEmpty() || tfEdition.getText().isEmpty() || tfIsbn.getText().isEmpty() ||
           tfEditorial.getText().isEmpty()){
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
        if(existsInvalidCharactersForTitle(tfTitleEvidence.getText()) || existsInvalidCharactersForName(tfAuthor.getText()) || existsInvalidCharactersForName(tfCountry.getText()) ||
           existsInvalidCharactersForFileRoute(tfFileRoute.getText()) || existsInvalidCharactersForTitle(tfEdition.getText()) || existsInvalidCharactersForTitle(tfEditorial.getText()) ||
           existsInvalidCharactersForNumber(tfNumPages.getText()) || existsInvalidCharactersForNumber(tfPrinting.getText())){
            invalidStrings = true;
            TypeError typeError = TypeError.INVALIDSTRING;
            showInvalidFieldAlert(typeError);
        }
        return invalidStrings;
    }
    
    /**
     * Método que verifica si existen caracteres inválidos en campos de nombres propios
     * @param textToValidate Define el nombre a validar 
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidCharactersForName(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s\\,]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }
    
    /**
     * Método que verifica si existen caracteres inválidos en un título
     * @param textToValidate Define el título a validar
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidCharactersForTitle(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^[A-Za-z0-9ÁÉÍÓÚáéíóúñÑ\\s\\.#-,:]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }   
    
    /**
     * Método que verifica si existen caracteres inválidos en el libro
     * @param textToValidate Define la información del campo a validar
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidCharactersForNumber(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }        
    
    /**
     * Método que verifica si existen caracteres inválidos para una ruta de archivo
     * @param fileRoute Define la ruta de archivo a guardar
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidCharactersForFileRoute(String fileRoute){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("[!#$%&'*+=?^_`{|}~]");
        Matcher mather = pattern.matcher(fileRoute);
        if(mather.find()){  
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
        if(cbImpactCA.getSelectionModel().getSelectedIndex() < 0 || cbInvestigationProject.getSelectionModel().getSelectedIndex() < 0 ||
           cbActualState.getSelectionModel().getSelectedIndex() < 0 || cbParticipationType.getSelectionModel().getSelectedIndex() < 0){
            missingSelection = true;
            TypeError typeError = TypeError.MISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }
        return missingSelection;
    }
    
    /**
     * Método que verifica si existen inconsistencias con los campos que tienen números
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidNumbers(){
        boolean invalidNumbers = false;
        int numPages = Integer.parseInt(tfNumPages.getText());
        int printing = Integer.parseInt(tfPrinting.getText());        
        boolean invalidPage = false;
        boolean invalidPrinting = false;      
        if(numPages < 49){  
            invalidPage = true;
            TypeError typeError = TypeError.INVALIDPAGE;
            showInvalidFieldAlert(typeError);
        }
           
        if(printing < 0){
            invalidPrinting = true;
           TypeError typeError = TypeError.INVALIDPRINTING;
            showInvalidFieldAlert(typeError);
        }
        
        if(invalidPage || invalidPrinting){
            invalidNumbers = true;
        }
        
        return invalidNumbers;
    }    
    
    
    /**
     * Método que verifica si existe selección de fecha faltante del campo de tipo DatePicker
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
     * Método que verifica si existen inconsistencias con la fecha de publicación
     * @return Booleano con el resultado de verificación, devuelve true si existe incosistencia, de lo contrario, devuelve false
     */
    
       private boolean existInconsistentDates(){
        boolean inconsistentDates = false;       
        LocalDate currentDate = LocalDate.now();  
        int datePublicationDay = dpPublicationDate.getValue().getDayOfMonth();
        int datePublicationMonth = dpPublicationDate.getValue().getMonthValue();       
        int datePublicationYear = dpPublicationDate.getValue().getYear();
        int currentDateDay = currentDate.getDayOfMonth();
        int currentDateMonth = currentDate.getMonthValue();        
        int currentDateYear = currentDate.getYear();
        if(datePublicationDay > currentDateDay && datePublicationMonth >= currentDateMonth && datePublicationYear >= currentDateYear){
            inconsistentDates = true;
            TypeError typeError = TypeError.INCONSISTENTDATE;
            showInvalidFieldAlert(typeError);
        }
        return inconsistentDates;
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
          alert.setContentText("Existen campos vacíos, llena los campos para poder guardar");  
        }
        
        if(typeError == TypeError.INVALIDSTRING){
            alert.setContentText("Existen caracteres inválidos, revisa los textos para poder guardar");
        }
        
        if(typeError == TypeError.MISSINGSELECTION){
            alert.setContentText("Existe selección de campo faltante, selecciona los campos para poder guardar");
        }
        
        if(typeError == TypeError.MISSINGDATE){
            alert.setContentText("Existe fecha faltante, selecciona la fecha de publicación para poder guardar");
        }
        
        if(typeError == TypeError.INCONSISTENTDATE){
            alert.setContentText("La fecha de publicación es mayor a la fecha actual, corrige el campo para poder guardar");
        }
        
        if(typeError == TypeError.TITLEDUPLICATE){
            alert.setContentText("El título del libro ya se encuentra registrado en el sistema");
        }
        
        if(typeError == TypeError.INVALIDPAGE){
            alert.setContentText("Un libro no puede tener menos de 49 páginas, corrige el campo para poder guardar");
        }        

        if(typeError == TypeError.INVALIDPRINTING){
            alert.setContentText("Un libro no puede contener menos de 49 páginas, corrige el campo para poder guardar");
        }    
                
        if(typeError == TypeError.INVALIDLENGTH){
            alert.setContentText("El número de carácteres excede el límite permitido, corrige los campos para poder guardar");
        }         
        
        alert.showAndWait();    
    }
    
    /**
     * Método que muestra alerta de confirmación de guardado de libro
     */
    
    private void showConfirmationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación de guardado");
        alert.setContentText("La información del libro fue guardada con éxito");
        alert.showAndWait();
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
   
    

