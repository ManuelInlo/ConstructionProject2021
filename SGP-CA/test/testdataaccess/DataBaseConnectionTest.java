
package testdataaccess;

import java.sql.Connection;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
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
     * @throws mx.fei.ca.businesslogic.exceptions.BusinessConnectionException
     */
    
    @Test
    public void dataBaseConnectionTest() throws BusinessConnectionException{
        Connection currentConnection = (Connection) new DataBaseConnection().getConnection();
        Assert.assertNotNull(currentConnection);
    } 
}
