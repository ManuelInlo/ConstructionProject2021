
package testdataaccess;

import java.sql.Connection;
import java.sql.SQLException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import org.junit.Assert;
import org.junit.Test;


public class DataBaseConnectionTest {
   
    @Test
    public void DataBaseConnectionTest() throws SQLException, ClassNotFoundException{
        Connection currentConnection = (Connection) new DataBaseConnection().getConnection();
        Assert.assertNotNull(currentConnection);
    } 
}
