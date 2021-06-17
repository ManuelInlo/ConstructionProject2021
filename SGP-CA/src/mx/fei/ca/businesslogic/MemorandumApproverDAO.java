
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.MemorandumApprover;

/**
 * Clase para representar el Objeto de acceso a datos de un aprobador de minuta
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class MemorandumApproverDAO implements IMemorandumApproverDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    /**
     * Constructor para la creación de un MemorandumApproverDAO, permitiendo también la obtención de la conexión a la base de datos 
     */
    
    public MemorandumApproverDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    /**
     * Método que guarda un nuevo aprobador de minuta en la base de datos
     * @param memorandumApprover Define el aprobador de minuta a guardar en la base de datos
     * @param idMemorandum Define el identificador de la minuta de la cual se guardará el aprobador de minuta
     * @return Booleano con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

    @Override
    public boolean savedMemorandumApprover(MemorandumApprover memorandumApprover, int idMemorandum) throws BusinessConnectionException {
        String sql = "INSERT INTO memorandumApprover (curp, idMemorandum) VALUES (?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memorandumApprover.getIntegrant().getCurp());
            preparedStatement.setInt(2, idMemorandum);
            preparedStatement.executeUpdate();
            saveResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
    
    /**
     * Método que recupera los aprobadores de una minuta
     * @param idMemorandum Define el identificador de la minuta de la cual se quiere recuperar los aprobadores
     * @return ArrayList con los aprobadores de minuta
     * @throws BusinessConnectionException 
     */

    @Override
    public ArrayList<MemorandumApprover> findMemorandumApproversByIdMemorandum(int idMemorandum) throws BusinessConnectionException {
        String sql = "SELECT * FROM memorandumApprover WHERE idMemorandum = ?";
        ArrayList<MemorandumApprover> memorandumApprovers = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMemorandum);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String curp = resultSet.getString("curp");
                IntegrantDAO integrantDAO = new IntegrantDAO();
                Integrant integrant = integrantDAO.findIntegrantByCurp(curp); 
                MemorandumApprover memorandumApprover = new MemorandumApprover(integrant);
                memorandumApprovers.add(memorandumApprover);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return memorandumApprovers;
    }
    
    /**
     * Método para verificar la existencia de un aprobador de minuta de acuerdo a la curp del integrante
     * @param curp Define la curp del integrante a verificar
     * @param idMemorandum Define el identificador de la minuta de la cual se quiere verificar si existe el aprobador de minuta
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean existsMemorandumApproverByCurp(String curp, int idMemorandum) throws BusinessConnectionException{
        String sql = "SELECT 1 FROM memorandumApprover WHERE curp = ? AND idMemorandum = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);
            preparedStatement.setInt(2, idMemorandum);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                exists = true;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return exists;
    }
}
