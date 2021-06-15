
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Prerequisite;


public class PrerequisiteDAO implements IPrerequisiteDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public PrerequisiteDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    @Override
    public boolean savedPrerequisite(Prerequisite prerequisite, int idMeeting) throws BusinessConnectionException{
        String sql = "INSERT INTO prerequisite (description, prerequisiteManager, idMeeting) VALUES (?, ?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, prerequisite.getDescription());
            preparedStatement.setString(2, prerequisite.getPrerequisiteManager());
            preparedStatement.setInt(3, idMeeting);
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
    public boolean updatedPrerequisite(Prerequisite prerequisite, int idPrerequisite, int idMeeting) throws BusinessConnectionException{
        String sql = "UPDATE prerequisite SET description = ?, prerequisiteManager = ?, idMeeting = ? "
                     + "WHERE idPrerequisite = ?";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, prerequisite.getDescription());
            preparedStatement.setString(2, prerequisite.getPrerequisiteManager());
            preparedStatement.setInt(3, idMeeting);
            preparedStatement.setInt(4, idPrerequisite);
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
    public boolean deletedPrerequisiteById(int idPrerequisite) throws BusinessConnectionException{
        String sql = "DELETE FROM prerequisite WHERE idPrerequisite = ?";
        boolean deleteResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idPrerequisite);
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
    public ArrayList<Prerequisite> findPrerequisitesByIdMeeting(int idMeeting) throws BusinessConnectionException{
        ArrayList<Prerequisite> prerequisites = new ArrayList<>();
        String sql = "SELECT * FROM prerequisite WHERE idMeeting = ?";
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMeeting);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idPrerequisite = resultSet.getInt("idPrerequisite");
                String description = resultSet.getString("description");
                String prerequisiteManager = resultSet.getString("prerequisiteManager");
                Prerequisite prerequisite = new Prerequisite(description, prerequisiteManager);
                prerequisite.setIdPrerequisite(idPrerequisite);
                prerequisites.add(prerequisite);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return prerequisites;
    }
}

