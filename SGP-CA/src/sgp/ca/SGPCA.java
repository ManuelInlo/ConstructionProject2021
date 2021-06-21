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
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.presentation.WindowLoginController;
public class SGPCA extends Application {
    
    @Override
    public void start(Stage stage) throws IOException, BusinessConnectionException {
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

    public static void main(String[] args) {
        launch(args);
    }
    
}
