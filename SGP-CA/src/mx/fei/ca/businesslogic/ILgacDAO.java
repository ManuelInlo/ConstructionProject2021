
package mx.fei.ca.businesslogic;

import java.sql.SQLException;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;

public interface ILgacDAO {
    public boolean applyLgacByIntegrant (String curp, String lgac) throws BusinessConnectionException, SQLException;
    public boolean deletedLgacOfIntegrant (String curp, String lgac) throws BusinessConnectionException;
}
