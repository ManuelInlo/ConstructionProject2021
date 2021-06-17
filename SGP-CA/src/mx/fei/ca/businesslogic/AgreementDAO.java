package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
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
    public boolean savedAgreement(Agreement agreement, int idMemorandum) throws BusinessConnectionException{
        String sql = "INSERT INTO agreement (description, dateAgreement, responsible, idMemorandum) "
                     + "VALUES (?, ?, ?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, agreement.getDescription());
            preparedStatement.setString(2, agreement.getDateAgreement());
            preparedStatement.setString(3, agreement.getResponsible());
            preparedStatement.setInt(4, idMemorandum);
            preparedStatement.executeUpdate();
            saveResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
    
    @Override
    public ArrayList<Agreement> findAgreementsByIdMemorandum(int idMemorandum)throws BusinessConnectionException{
        ArrayList<Agreement> agreements = new ArrayList<>();
        String sql = "Select * from agreement where idMemorandum = ?";
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMemorandum);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idAgreement = resultSet.getInt("idAgreement");
                String description = resultSet.getString("description");
                String dateAgreement = resultSet.getString("dateAgreement");
                String responsible = resultSet.getString("responsible");
                Agreement agreement = new Agreement(description, dateAgreement, responsible);
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
    public boolean deletedAgreementById(int idAgreement) throws BusinessConnectionException{
        String sql = "DELETE FROM agreement WHERE idAgreement = ?";
        boolean deleteResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idAgreement);
            preparedStatement.executeUpdate();
            deleteResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return deleteResult;
    }
    
    @Override
    public boolean updatedAgreement(Agreement agreement, int idAgreement, int idMemorandum) throws BusinessConnectionException{
        String sql = "UPDATE agreement SET description = ?, dateAgreement = ?, responsible = ?, idMemorandum = ?"
                     + " WHERE idAgreement = ?";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, agreement.getDescription());
            preparedStatement.setString(2, agreement.getDateAgreement());
            preparedStatement.setString(3, agreement.getResponsible());
            preparedStatement.setInt(4, idMemorandum);
            preparedStatement.setInt(5, idAgreement);
            preparedStatement.executeUpdate();
            updateResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }

    @Override
    public int getIdAgreementByDescription(String description) throws BusinessConnectionException {
        int idAgreement = 0;
        String sql = "SELECT idAgreement FROM Agreement WHERE description = ?";
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, description);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                idAgreement = resultSet.getInt("idAgreement");
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return idAgreement;
    }
}
