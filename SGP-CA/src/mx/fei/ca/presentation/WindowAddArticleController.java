
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
        EMPTYFIELD, INVALIDSTRING, MISSINGSELECTION, MISSINGDATE, TITLEDUPLICATE, FILEROUTEDUPLICATE;
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
            int idProject = investigationProject.getIdProject();
            if(cbActualState.getSelectionModel().getSelectedItem().toString().equals("Publicado")){
               publicationDate = parseToSqlDate(java.util.Date.from(dpPublicationDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())); 
            }        
            Evidence evidence = new Evidence(impactCA, titleArticle, author);            
            Article article = new Article(evidence, issn, fileRoute, homePage, endPage, actualState, magazineName, country, publicationDate,
                                          volume, editorial, description);
            article.setInvestigationProject(investigationProject); 
            article.setIntegrant(integrant); 
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
           existsInvalidCharactersForFileRoute(tfFileRoute.getText()) || existsInvalidCharactersForTitle(tfMagazineName.getText()) || existsInvalidCharactersForTitle(tfDescription.getText())){
            invalidStrings = true;
            TypeError typeError = TypeError.INVALIDSTRING;
            showInvalidFieldAlert(typeError);
        }
        return invalidStrings;
    }
    
    /**
     * Método que verifica si existen caracteres inválidos en campos de nombres propios
     * @param textToValidate Define el nombre del autor a validar 
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
     * Método que verifica si existen caracteres inválidos en campos de nombres propios
     * @param textToValidate Define el nombre del autor a validar 
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
     * Método que verifica si existen caracteres inválidos en el artículo
     * @param textToValidate Define la información del campo a validar
     * @return Booleano con el resultado de verificación, devuelve true si existen inválidos, de lo contrario, devuelve false
     */
    
    private boolean existsInvalidCharactersForNumber(String textToValidate){
        boolean invalidCharacters = false;
        Pattern pattern = Pattern.compile("[0-9]*");
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
            alert.setContentText("Existe fecha faltante, selecciona las fechas para poder guardar");
        }
        
        if(typeError == TypeError.INCONSISTENTDATE){
            alert.setContentText("La fecha de inicio es mayor a la fecha de fin, corrige campos para poder guardar");
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
    
    
    @FXML
    private void showConfirmationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación de guardado");
        alert.setContentText("La información del artículo fue guardada con éxito");
        alert.showAndWait();
    }
    
    
    @FXML    
    private void showLostConnectionAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Perdida de conexión");
        alert.setContentText("Perdida de conexión con la base de datos, no se pudo guardar. Intente más tarde");
        alert.showAndWait();
    }
    
}
