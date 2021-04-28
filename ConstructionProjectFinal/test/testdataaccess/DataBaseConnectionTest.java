/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class DataBaseConnectionTest {
    @Test
    public void testDataBaseConnection() throws SQLException, ClassNotFoundException{
        Connection currentConnection = (Connection) new DataBaseConnection().getConnection();
        Assert.assertNotNull(currentConnection);
    }
}