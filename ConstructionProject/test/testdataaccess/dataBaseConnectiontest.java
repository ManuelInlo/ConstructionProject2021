package testdataaccess;

import com.mysql.jdbc.Connection;
import java.sql.SQLException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author inigu
 */
public class dataBaseConnectiontest {
    
    @Test
    public void testDataBaseConnection() throws SQLException, ClassNotFoundException{
        Connection currentConnection = (Connection) new DataBaseConnection().getConnection();
        Assert.assertNotNull(currentConnection);
    }
}
/*
public void dataBaseConnectiontest() throws SQLException, ClassNotFoundException{
Connection currentConnection = (Connection) new dataBaseConnection().co;
Assert.assertNotNull(currentConnection);
}
*/

