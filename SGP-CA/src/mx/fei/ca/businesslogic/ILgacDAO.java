
package mx.fei.ca.businesslogic;

import java.sql.SQLException;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;

/**
 * Interface del objeto de acceso a datos de LGAC
 * @author Gloria Mendoza González
 * @version 16-06-2021
 */

public interface ILgacDAO {
    public boolean applyLgacByIntegrant (String curp, String lgac) throws BusinessConnectionException, SQLException;
    public boolean deletedLgacOfIntegrant (String curp, String lgac) throws BusinessConnectionException;
}
