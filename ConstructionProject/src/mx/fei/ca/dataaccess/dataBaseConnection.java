package mx.fei.ca.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author inigu
 */
public class dataBaseConnection {
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/";
    private final String DB = "sgpca";
    private final String USER = "userCA";
    private final String PASSWORD = "userCA1a2b3c"; 
    
    public Connection chain;
    public static dataBaseConnection Instancia;
    
    private dataBaseConnection(){
        this.chain=null;
    }
    
    public Connection Connect(){
        try {
             Class.forName(DRIVER);
            this.chain = DriverManager.getConnection(URL+DB, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return chain;
    }
    
    public void Disconnect(){
        try {
            this.chain.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
    }
    
    //Applying Singelton
    public static dataBaseConnection getInstancia(){
        if(Instancia == null){
            Instancia = new dataBaseConnection();
        }
        return Instancia;
    }
}
