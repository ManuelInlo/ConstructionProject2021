
package mx.fei.ca.presentation;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;

public class SGPCA extends Application {
    
    @Override
    public void start(Stage stage) throws IOException, BusinessConnectionException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WindowAddIntegrant.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
