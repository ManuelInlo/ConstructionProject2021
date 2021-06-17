
package testdataaccess;

import java.sql.Connection;
import java.sql.SQLException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import org.junit.Assert;
import org.junit.Test;

/**
 * Clase para representar test de la clase DataBaseConnection
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class DataBaseConnectionTest {
   
    /**
     * Metodo que realiza test de la conexión a la base de datos
     * @throws SQLException
     */
    
    @Test
    public void DataBaseConnectionTest() throws SQLException{
        Connection currentConnection = (Connection) new DataBaseConnection().getConnection();
        Assert.assertNotNull(currentConnection);
    } 
}
