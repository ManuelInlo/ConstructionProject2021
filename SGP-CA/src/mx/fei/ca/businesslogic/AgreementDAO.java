
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Agreement;

public class AgreementDAO implements IAgreementDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    
    public AgreementDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    @Override
    public int saveAgreement(Agreement agreement, int idMemorandum) throws BusinessConnectionException{
        String sql = "INSERT INTO agreement (number, description, dateAgreement, responsible, idMemorandum) "
                     + "VALUES (?, ?, ?, ?, ?)";
        int saveResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, agreement.getNumber());
            preparedStatement.setString(2, agreement.getDescription());
            preparedStatement.setDate(3, agreement.getDateAgreement());
            preparedStatement.setString(4, agreement.getResponsible());
            preparedStatement.setInt(5, idMemorandum);
            saveResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
    
    @Override
    public ArrayList<Agreement> findAgreementsByIdMemorandum(int idMemorandum)throws BusinessConnectionException, BusinessDataException{
        ArrayList<Agreement> agreements = new ArrayList<>();
        String sql = "Select * from agreement where idMemorandum = ?";
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMemorandum);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idAgreement = resultSet.getInt("idAgreement");
                int number = resultSet.getInt("number");
                String description = resultSet.getString("description");
                Date dateAgreement = resultSet.getDate("dateAgreement");
                String responsible = resultSet.getString("responsible");
                Agreement agreement = new Agreement(number, description, dateAgreement, responsible);
                agreement.setIdAgreement(idAgreement);
                agreements.add(agreement);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return agreements;
    }
    
    @Override
    public int deleteAgreementById(int idAgreement) throws BusinessConnectionException{
        String sql = "DELETE FROM agreement WHERE idAgreement = ?";
        int deleteResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idAgreement);
            deleteResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return deleteResult;
    }
    
    @Override
    public int updateAgreement(Agreement agreement, int idAgreement, int idMemorandum) throws BusinessConnectionException{
        String sql = "UPDATE agreement SET number = ?, description = ?, dateAgreement = ?, responsible = ?, idMemorandum = ?"
                     + " WHERE idAgreement = ?";
        int updateResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, agreement.getNumber());
            preparedStatement.setString(2, agreement.getDescription());
            preparedStatement.setDate(3, agreement.getDateAgreement());
            preparedStatement.setString(4, agreement.getResponsible());
            preparedStatement.setInt(5, idMemorandum);
            preparedStatement.setInt(6, idAgreement);
            updateResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }

    @Override
    public boolean existsAgreementDescription(String description, int idMemorandum) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM agreement WHERE description = ? AND idMemorandum = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, description);
            preparedStatement.setInt(2, idMemorandum);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                exists = true;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return exists;
    }

    @Override
    public boolean existsAgreementDescriptionForUpdate(String description, int idMemorandum, int idAgreement) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM agreement WHERE description = ? AND idMemorandum = ? AND idAgreement <> ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, description);
            preparedStatement.setInt(2, idMemorandum);
            preparedStatement.setInt(3, idAgreement);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                exists = true;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return exists;
    }
    
}
