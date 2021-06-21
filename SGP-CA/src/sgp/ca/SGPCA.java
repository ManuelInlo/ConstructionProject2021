package sgp.ca;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.fei.ca.presentation.WindowLoginController;

/**
 * Clase principal del Sistema Gestor de Productividad De Cuerpo Académico
 * @author David Alexander Mijangos Paredes
 * @version 20-06-2021
 */

public class SGPCA extends Application {
    
    /**
     * Método de inicio que manda a abrir la ventana login del sistema
     * @param stage Define el escenario principal
     */
    
    @Override
    public void start(Stage stage){
        try{   
            URL url = new File("src/mx/fei/ca/presentation/WindowLogin.fxml").toURI().toURL();
            try{
                FXMLLoader loader = new FXMLLoader(url);
                loader.setLocation(url);
                loader.load();
                WindowLoginController windowLoginController = loader.getController();
                Parent root = loader.getRoot();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                
            } catch (IOException ex) {
               Logger.getLogger(SGPCA.class.getName()).log(Level.SEVERE, null, ex);
            }
            stage.show();
        } catch (MalformedURLException ex) {
               Logger.getLogger(SGPCA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método main del sistema que invoca el arranque de la aplicación
     * @param args Define el array de string que es necesario para el método main
     */

    public static void main(String[] args) {
        launch(args);
    }
    
}
