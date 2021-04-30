
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


public class MemorandumApproverDAO implements IMemorandumApproverDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public MemorandumApproverDAO(){
        dataBaseConnection = new DataBaseConnection();
    }

    @Override
    public int saveMemorandumApprover(MemorandumApprover memorandumApprover, int idMemorandum) throws BusinessConnectionException {
        String sql = "INSERT INTO memorandumApprover (curp, idMemorandum) VALUES (?, ?)";
        int saveResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memorandumApprover.getIntegrant().getCurp());
            preparedStatement.setInt(2, idMemorandum);
            saveResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }

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
                Integrant integrant = new Integrant(curp); //Acá debe mandar a llamar método buscar integrante por curp 
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
    
}
