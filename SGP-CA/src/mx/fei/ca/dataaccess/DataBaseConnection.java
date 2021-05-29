
package mx.fei.ca.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DataBaseConnection {
    private Connection connection;
    private final String url;
    private final String user;
    private final String password;
    
    public DataBaseConnection(){
       this.url = PropertyUtil.getProperties("dates.url") + "?useSSL=false";
       this.user = PropertyUtil.getProperties("dates.user");
       this.password = PropertyUtil.getProperties("dates.password");
    }
   
    public Connection getConnection() throws SQLException{
       connection = DriverManager.getConnection(url, user, password);
       return connection;
    }
    
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
