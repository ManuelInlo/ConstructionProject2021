
package mx.fei.ca.presentation;

import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.BookDAO;
import mx.fei.ca.businesslogic.ChapterBookDAO;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Book;
import mx.fei.ca.domain.ChapterBook;
import mx.fei.ca.domain.Evidence;
import mx.fei.ca.domain.Integrant;

/**
 * Clase para representar el controlador del FXML WindowAddChapterBook
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */

public class WindowAddChapterBookController implements Initializable {

    @FXML
    private TextField tfTitleEvidence;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextField tfChapterNumber;
    @FXML
    private TextField tfEndPage;
    @FXML
    private ComboBox cbImpactCA;
    @FXML
    private TextField tfHomePage;
    @FXML
    private ComboBox<Book> cbBook;
    @FXML
    private Label lbUser;
    private Integrant integrant;
    
    /**
     * Enumerado que representa los tipos de errores específicos de GUI al agregar un capítulo de libro
     */
    
    private enum TypeError{
        EMPTYFIELD, INVALIDSTRING, MISSINGSELECTION, TITLEDUPLICATE, NUMBERDUPLICATE, INVALIDLENGTH, INCONSISTENTPAGE, INCONSISTENTPAGEBOOK;
    }  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxImpactCA();     
        try {
            fillComboBoxBook();                     
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
     * Método que llena el ComboBox de impacto al CA
     */

    private void fillComboBoxImpactCA() {
        ObservableList<String> listImpactCA = FXCollections.observableArrayList("SI","NO");
        cbImpactCA.setItems(listImpactCA);
    }     
    
    /**
     * Método que llena el ComboBox de libros recuperados de la base de datos
     * @throws BusinessConnectionException 
     */
    
    private void fillComboBoxBook() throws BusinessConnectionException{
        BookDAO bookDAO = new BookDAO();
        ArrayList<Book> books = bookDAO.findAllBooks();
        ObservableList<Book> listBooks = FXCollections.observableArrayList(books);
        cbBook.setItems(listBooks);
    }       
    /**
     * Método que manda a guardar un capítulo de libro de acuerdo a la información obtenida de los campos de la GUI
     * @param event Define el evento generado
     * @throws BusinessConnectionException 
     */

    @FXML
    private void saveChapterBook(ActionEvent event) throws BusinessConnectionException{
        if(!existsInvalidFields()){         
            String impactCA = cbImpactCA.getSelectionModel().getSelectedItem().toString();
            int homePage = Integer.parseInt(tfHomePage.getText());   
            int endPage = Integer.parseInt(tfEndPage.getText()); 
            int chapterNumber = Integer.parseInt(tfChapterNumber.getText());             
            Book book = cbBook.getSelectionModel().getSelectedItem();
            String author = tfAuthor.getText(); 
            String titleBook = tfTitleEvidence.getText();  
            Evidence evidence = new Evidence(impactCA, titleBook, author);
            ChapterBook chapterBook = new ChapterBook(evidence, chapterNumber, homePage, endPage);
            chapterBook.setInvestigationProject(book.getInvestigationProject());
            chapterBook.setBook(book);
            chapterBook.setIntegrant(integrant);
            chapterBook.setCurp(integrant.getCurp());
            ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
            int saveResult = chapterBookDAO.saveAndReturnIdNewChapterBook(chapterBook);
            if(saveResult != 0){
                showConfirmationAlert();
                closeChapterBookRegistration(event);
            }else{
                showLostConnectionAlert(); 
                closeChapterBookRegistration(event);
            }
        }                  
    }

    /**
     * Método que cierra la GUI actual
     * @param event Define el evento generado
     */
    
    @FXML
    private void closeChapterBookRegistration(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();        
    }
    
    /**
     * Método que devuelve si existen o no campos inválidos
     * @return Booleano con el resultado de la verificación, devuelve true si existen campos inválidos, de lo contrario devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean existsInvalidFields() throws BusinessConnectionException{
        boolean invalidFields = false;
        if(existsEmptyFields() || existsInvalidStrings() ||  existsMissingSelection() || existsDuplicateValues() || 
           existsInvalidLength() || existInconsistentNumberPage()){
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
        if(tfTitleEvidence.getText().length() > 255 || tfAuthor.getText().length() > 255 || tfChapterNumber.getText().length() > 5 ||
           tfHomePage.getText().length() > 8 || tfEndPage.getText().length() > 8){
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
        if(tfTitleEvidence.getText().isEmpty() || tfAuthor.getText().isEmpty() || tfChapterNumber.getText().isEmpty() ||
           tfHomePage.getText().isEmpty() || tfEndPage.getText().isEmpty()){
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
        if(existsInvalidCharactersForTitle(tfTitleEvidence.getText()) || existsInvalidCharactersForName(tfAuthor.getText()) || existsInvalidCharactersForNumber(tfChapterNumber.getText()) ||
           existsInvalidCharactersForNumber(tfHomePage.getText()) || existsInvalidCharactersForNumber(tfEndPage.getText())){
            invalidStrings = true;
            TypeError typeError = TypeError.INVALIDSTRING;
            showInvalidFieldAlert(typeError);
        }
        return invalidStrings;
    }
    
    /**
     * Método que verifica si existen caracteres inválidos en nombre del autor
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
     * Método que verifica si existen caracteres inválidos en el capítulo de libro
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
     * Método que verifica si existen selecciones de campos faltantes en la GUI
     * @return Booleano con el resultado de verificación, devuelve true si existen faltantes, de lo contrario, devuelve false
     */
    
    private boolean existsMissingSelection(){
        boolean missingSelection = false;
        if(cbImpactCA.getSelectionModel().getSelectedIndex() < 0 || cbBook.getSelectionModel().getSelectedIndex() < 0){
            missingSelection = true;
            TypeError typeError = TypeError.MISSINGSELECTION;
            showInvalidFieldAlert(typeError);
        }
        return missingSelection;
    }
    
    /**
     * Método que verifica si existen inconsistencias con el número de páginas
     * @return Booleano con el resultado de verificación, devuelve true si existe incosistencia, de lo contrario, devuelve false
     */
    
    private boolean existInconsistentNumberPage(){
        boolean inconsistentFields = false;
        boolean inconsistentPages = false; 
        boolean inconsistentPagesBook = false;         
        int numPageBook = cbBook.getSelectionModel().getSelectedItem().getNumPages();        
        int homePage = Integer.parseInt(tfHomePage.getText());
        int endPage = Integer.parseInt(tfEndPage.getText());
        if(homePage > endPage){
            inconsistentPages = true;
            TypeError typeError = TypeError.INCONSISTENTPAGE;
            showInvalidFieldAlert(typeError);
        }

        if(homePage > numPageBook || endPage > numPageBook){
            inconsistentPagesBook = true;
            TypeError typeError = TypeError.INCONSISTENTPAGEBOOK;
            showInvalidFieldAlert(typeError);
        }  
        
        if(inconsistentPages || inconsistentPagesBook){
            inconsistentFields = true;
        }        
        
        return inconsistentFields;
    }    
    
    /**
     * Método que manda a verificar si valores obtenidos de la GUI que no pueden duplicarse ya existen en la base de datos
     * El método manda a llamar a otros métodos que se encargan de la verificación en la capa lógica     
     * @return Booleano con el resultado de la verificación, devuelve true si existe valor duplicado, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    private boolean existsDuplicateValues() throws BusinessConnectionException{ 
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        String isbn = cbBook.getSelectionModel().getSelectedItem().getIsbn();
        int chapterNumber = Integer.parseInt(tfChapterNumber.getText());
        boolean duplicateValues = false;
        boolean chapterBookTitleDuplicate = false;     
        boolean chapterNumberDuplicate = false;          
        if(chapterBookDAO.existsChapterBookTitle(tfTitleEvidence.getText())){  
            chapterBookTitleDuplicate = true;
            TypeError typeError = TypeError.TITLEDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
           
        if(chapterBookDAO.existsNumberChapterByBook(chapterNumber, isbn)){
            chapterNumberDuplicate = true;
            TypeError typeError = TypeError.NUMBERDUPLICATE;
            showInvalidFieldAlert(typeError);
        }
        
        if(chapterBookTitleDuplicate || chapterNumberDuplicate){
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
          alert.setContentText("Existen campos vacíos, llena los campos para poder guardar");  
        }
        
        if(typeError == TypeError.INVALIDSTRING){
            alert.setContentText("Existen caracteres inválidos, revisa los textos para poder guardar");
        }
        
        if(typeError == TypeError.MISSINGSELECTION){
            alert.setContentText("Existe selección de campo faltante, selecciona los campos para poder guardar");
        }
        
        if(typeError == TypeError.TITLEDUPLICATE){
            alert.setContentText("El título del capítulo de libro ya se encuentra registrado en el sistema");
        }
        
        if(typeError == TypeError.NUMBERDUPLICATE){
            alert.setContentText("El número del capítulo de libro ya se encuentra registrado en el libro seleccionado");
        } 
        
        if(typeError == TypeError.INCONSISTENTPAGE){
            alert.setContentText("La página de inicio es mayor que la página final, corrige el campo para poder guardar");
        } 

        if(typeError == TypeError.INCONSISTENTPAGEBOOK){
            alert.setContentText("El libro no cuenta con ese número de páginas, corrige los campo para poder guardar");
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
        alert.setContentText("La información del capítulo del libro fue guardada con éxito");
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
