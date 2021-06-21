
package mx.fei.ca.presentation;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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
import mx.fei.ca.businesslogic.ArticleDAO;
import mx.fei.ca.businesslogic.InvestigationProjectDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Article;
import mx.fei.ca.domain.Evidence;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.InvestigationProject;

/**
 * Clase para representar el controlador del FXML WindowAddArticle
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

public class WindowAddArticleController implements Initializable {

    @FXML
    private TextField tfTitleEvidence;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextField tfEndPage;
    @FXML
    private TextField tfEditorial;
    @FXML
    private TextField tfCountry;
    @FXML
    private TextField tfFileRoute;
    @FXML
    private DatePicker dpPublicationDate;
    @FXML
    private TextField tfVolume;
    @FXML
    private ComboBox cbImpactCA;
    @FXML
    private ComboBox<InvestigationProject> cbInvestigationProject;
    @FXML
    private ComboBox cbActualState;
    @FXML
    private TextField tfDescription;
    @FXML
    private TextField tfHomePage;
    @FXML
    private TextField tfIssn;
    @FXML
    private TextField tfMagazineName;
    @FXML
    private Label lbUser;
    private Integrant integrant;

    /**
     * Enumerado que representa los tipos de errores específicos de GUI al agregar un artículo
     */
    
    private enum TypeError{
        EMPTYFIELD, INVALIDSTRING, MISSINGSELECTION, MISSINGDATE, TITLEDUPLICATE, FILEROUTEDUPLICATE, 
        INCONSISTENTDATE, OVERDATE, INCONSISTENTPAGE, INVALIDLENGTH, ISSNDUPLICATE;
    }    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxImpactCA();
        fillComboBoxActualState();
        try{
            fillComboBoxInvestigationProject();
        }catch (BusinessConnectionException ex) {
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
     * Método que llena el ComboBox de impacto al CA
     */

    private void fillComboBoxImpactCA() {
        ObservableList<String> listImpactCA = FXCollections.observableArrayList("SI","NO");
        cbImpactCA.setItems(listImpactCA);
    }
    
    /**
     * Método que llena el ComboBox estado actual del artículo
     */
    
    private void fillComboBoxActualState() {
        ObservableList<String> listActualState = FXCollections.observableArrayList("En proceso","Terminado", "Publicado");
        cbActualState.setItems(listActualState);
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
     * Método que manda a guardar un artículo de acuerdo a la información obtenida de los campos de la GUI
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */
    
    @FXML
    private void saveArticle(ActionEvent event) throws BusinessConnectionException {
        if(!existsInvalidFields()){    
            Date publicationDate = null;             
            String impactCA = cbImpactCA.getSelectionModel().getSelectedItem().toString();
            String issn = tfIssn.getText();
            String magazineName = tfMagazineName.getText();
            int endPage = Integer.parseInt(tfEndPage.getText());   
            int homePage = Integer.parseInt(tfHomePage.getText()); 
            String actualState = cbActualState.getSelectionModel().getSelectedItem().toString();        
            String country = tfCountry.getText();       
            String editorial = tfEditorial.getText();           
            int volume = Integer.parseInt(tfVolume.getText());     
            String description = tfDescription.getText();         
            String fileRoute = tfFileRoute.getText();
            String author = tfAuthor.getText(); 
            String titleArticle = tfTitleEvidence.getText();        
            InvestigationProject investigationProject = cbInvestigationProject.getSelectionModel().getSelectedItem(); 
            if(cbActualState.getSelectionModel().getSelectedItem().toString().equals("Publicado")){
               publicationDate = parseToSqlDate(java.util.Date.from(dpPublicationDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())); 
            }        
            Evidence evidence = new Evidence(impactCA, titleArticle, author);            
            Article article = new Article(evidence, issn, fileRoute, homePage, endPage, actualState, magazineName, country, publicationDate,
                                          volume, editorial, description);
            article.setInvestigationProject(investigationProject); 
            article.setIntegrant(integrant); 
            article.setCurp(integrant.getCurp());
            ArticleDAO articleDAO = new ArticleDAO();
            boolean saveResult = articleDAO.savedArticle(article);
            if(saveResult){
                showConfirmationAlert();
                closeArticleRegistration(event);
            }else{
                showLostConnectionAlert(); 
                closeArticleRegistration(event);
            }            
        }    
    }

    /**
     * Método que cierra la GUI actual 
     * @param event Define el evento generado
     */
    
    @FXML
    private void closeArticleRegistration(ActionEvent event)  {
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
     * @return Booleano con el resultado de la verificación, devuelve true si existen campos inválidos, de lo contrario devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean existsInvalidFields() throws BusinessConnectionException{
        boolean invalidFields = false;
        if(existsEmptyFields() || existsInvalidStrings() ||  existsMissingSelection() || existsDuplicateValues() || 
           existInconsistentNumberPage() || existsInvalidDates() || existsInvalidLength()){
            invalidFields = true;
        }    
        return invalidFields;
    }
    
    /**
     * Método que verifica si la longitud del campo excede el límite permitido
     * @return Boolean con el resultado de la verificación, devuelve true si existen campos vacíos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidLength(){
        boolean invalidLength = false;
        if(tfTitleEvidence.getText().length() > 255 || tfFileRoute.getText().length() > 500 || tfAuthor.getText().length() > 255 || tfDescription.getText().length() > 255 ||
           tfHomePage.getText().length() > 5 || tfEndPage.getText().length() > 5 || tfMagazineName.getText().length() > 225 || tfIssn.getText().length() > 9 ||
           tfCountry.getText().length() > 255 || tfVolume.getText().length() > 5 || tfEditorial.getText().length() > 255){
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
        if(tfTitleEvidence.getText().isEmpty() || tfFileRoute.getText().isEmpty() || tfAuthor.getText().isEmpty() || tfDescription.getText().isEmpty() ||
           tfHomePage.getText().isEmpty() || tfEndPage.getText().isEmpty() || tfMagazineName.getText().isEmpty() || tfIssn.getText().isEmpty() ||
           tfCountry.getText().isEmpty() || tfVolume.getText().isEmpty() || tfEditorial.getText().isEmpty()){
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
           existsInvalidCharactersForFileRoute(tfFileRoute.getText()) || existsInvalidCharactersForTitle(tfMagazineName.getText()) || existsInvalidCharactersForTitle(tfDescription.getText()) ||
           existsInvalidCharactersForNumber(tfHomePage.getText()) || existsInvalidCharactersForNumber(tfEndPage.getText()) || existsInvalidCharactersForNumber(tfVolume.getText()) || 
           existsInvalidCharactersForIssn(tfIssn.getText()) || existsInvalidCharactersForTitle(tfEditorial.getText())){
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
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+$");
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
        Pattern pattern = Pattern.compile("^[A-Za-z0-9ÁÉÍÓÚáéíóúñÑ\\s\\.#,:]+$");
        Matcher matcher = pattern.matcher(textToValidate);
        if(!matcher.find()){
           invalidCharacters = true; 
        }
        return invalidCharacters;
    }   
    
    /**
     * Método que verifica si existen caracteres inválidos en el artículo
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
     * Método que verifica si existen caracteres inválidos en el ISSN del artículo
     * @param textToValidate Define el ISSN del artículo a validar
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidCharactersForIssn(String textToValidate){
        boolean invalidCharacters = false;
        String validText = "[0-9]{4}[-]{1}[0-9X]{4}$";        
        Pattern pattern = Pattern.compile(validText);
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
           cbActualState.getSelectionModel().getSelectedIndex() < 0){
            missingSelection = true;
            TypeError typeError = TypeError.MISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }
        return missingSelection;
    }
    
    /**
     * Método que verifica si existen fechas inválidas en los campos de la GUI
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidDates(){
        boolean invalidDates = false;
        if(cbActualState.getSelectionModel().getSelectedItem().toString().equals("Publicado")){
            if(existMissingDate(dpPublicationDate) || existInconsistentDates()){
                invalidDates = true;
            }
        }else{
            if(existLeftOverDateSelection(dpPublicationDate)){
                invalidDates = true;
            }
        }
        return invalidDates;
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
     * Método que verifica si existen inconsistencias con el número de inicio y fin del artículo
     * @return Booleano con el resultado de verificación, devuelve true si existe incosistencia, de lo contrario, devuelve false
     */
    
    private boolean existInconsistentNumberPage(){
        boolean inconsistentPages = false;        
        int homePage = Integer.parseInt(tfHomePage.getText());
        int endPage = Integer.parseInt(tfEndPage.getText());
        if(homePage > endPage){
            inconsistentPages = true;
            TypeError typeError = TypeError.INCONSISTENTPAGE;
            showInvalidFieldAlert(typeError);
        }
        return inconsistentPages;
    }   
    
    /**
     * Método que verifica si existen inconsistencias con la fecha
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
     * Método que manda a verificar si valores obtenidos de la GUI que no pueden duplicarse ya existen en la base de datos
     * El método manda a llamar a otros métodos que se encargan de la verificación en la capa lógica     
     * @return Booleano con el resultado de la verificación, devuelve true si existe valor duplicado, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean existsDuplicateValues() throws BusinessConnectionException{
        ArticleDAO articleDAO = new ArticleDAO();
        boolean duplicateValues = false;
        boolean articleTitleDuplicate = false;
        boolean fileRouteDuplicate = false;
        boolean issnDuplicate = false;        
        if(articleDAO.existsArticleTitle(tfTitleEvidence.getText())){  
            articleTitleDuplicate = true;
            TypeError typeError = TypeError.TITLEDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
           
        if(articleDAO.existsArticleFileRoute(tfFileRoute.getText())){
            fileRouteDuplicate = true;
            TypeError typeError = TypeError.FILEROUTEDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
        
        if(articleDAO.existsArticleIssn(tfIssn.getText())){
            issnDuplicate = true;
            TypeError typeError = TypeError.ISSNDUPLICATE;
            showInvalidFieldAlert(typeError);
        }        
        
        if(articleTitleDuplicate || fileRouteDuplicate || issnDuplicate){
            duplicateValues = true;
        }
        
        return duplicateValues;
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
        
        if(typeError == TypeError.OVERDATE){
            alert.setContentText("El artículo no está publicado, por lo tanto no puedes guardar una fecha de publicación");
        }
        
        if(typeError == TypeError.TITLEDUPLICATE){
            alert.setContentText("El título del artículo ya se encuentra registrado en el sistema");
        }
        
        if(typeError == TypeError.FILEROUTEDUPLICATE){
            alert.setContentText("La ruta de archivo del artículo ya se encuentra registrada en otro artículo");
        }
        
        if(typeError == TypeError.ISSNDUPLICATE){
            alert.setContentText("El ISSN del artículo ya se encuentra registrado en otro artículo");
        }        
 
        if(typeError == TypeError.INCONSISTENTPAGE){
            alert.setContentText("La página de inicio es mayor que la página final, corrige el campo para poder guardar");
        }   
        
        if(typeError == TypeError.INVALIDLENGTH){
            alert.setContentText("El número de carácteres excede el límite permitido, corrige los campos para poder guardar");
        } 

        alert.showAndWait();    
    }
    
    /**
     * Método que muestra alerta de confirmación de guardado
     */
    
    @FXML
    private void showConfirmationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación de guardado");
        alert.setContentText("La información del artículo fue guardada con éxito");
        alert.showAndWait();
    }
    
    /**
     * Método que muestra alerta de perdida de conexión con la base de datos
     */
    
    @FXML    
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo guardar. Intente más tarde");
        alert.showAndWait();
    }
    
}
