package mx.fei.ca.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;

/**
 * Clase para representar la conexión a la base de datos
 * Cada conexión esta determinada de una Conexión, un url, usuario y contraseña
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class DataBaseConnection {
    private Connection connection;
    private final String url;
    private final String user;
    private final String password;
    
    /**
     * Constructor para la creación de la conexión a la base de datos
     */
    
    public DataBaseConnection(){
       this.url = PropertyUtil.getProperties("dates.url") + "?useSSL=false";
       this.user = PropertyUtil.getProperties("dates.user");
       this.password = PropertyUtil.getProperties("dates.password");
    }
    
    /**
     * Método que devuelve la conexión con la base de datos
     * @return La conexión a la base de datos 
     * @throws mx.fei.ca.businesslogic.exceptions.BusinessConnectionException 
     */
   
    public Connection getConnection() throws BusinessConnectionException{
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }
       return connection;
    }
    
    /**
     * Método que cierra la conexión con la base de datos
     */
    
    public void closeConnection(){
       if(connection != null){
           try{
               if(!connection.isClosed()){
                   connection.close();
               }
           }catch(SQLException ex){
               Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    } 
   
    
}
