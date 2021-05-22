
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Memorandum;


public class MemorandumDAO implements IMemorandumDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public MemorandumDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    @Override
    public boolean saveAndReturnIdMemorandum(Memorandum memorandum, int idMeeting) throws BusinessConnectionException{
        String sql = "INSERT INTO memorandum (pending, note, idMeeting) VALUES (?, ?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,memorandum.getPending());
            preparedStatement.setString(2, memorandum.getNote());
            preparedStatement.setInt(3, idMeeting);
            preparedStatement.executeUpdate();
            saveResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos",ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
    
    @Override
    public boolean updatedMemorandum(Memorandum memorandum, int idMemorandum, int idMeeting) throws BusinessConnectionException{
        String sql = "UPDATE memorandum SET pending = ?, note = ?, idMeeting = ? WHERE idMemorandum = ?";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memorandum.getPending());
            preparedStatement.setString(2, memorandum.getNote());
            preparedStatement.setInt(3, idMeeting);
            preparedStatement.setInt(4, idMemorandum);
            preparedStatement.executeUpdate();
            updateResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos",ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }
    
    @Override
    public Memorandum findMemorandumByIdMeeting(int idMeeting) throws BusinessConnectionException{
        String sql = "SELECT * FROM memorandum WHERE idMeeting = ?";
        Memorandum memorandum = null;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMeeting);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idMemorandum = resultSet.getInt("idMemorandum");
                String pending = resultSet.getString("pending");
                String note = resultSet.getString("note");
                memorandum = new Memorandum(pending, note);
                memorandum.setIdMemorandum(idMemorandum);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos",ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return memorandum;
    }
}
