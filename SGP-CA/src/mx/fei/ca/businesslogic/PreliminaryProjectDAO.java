package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.PreliminaryProject;


public class PreliminaryProjectDAO implements IPreliminaryProjectDAO{
    
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet; 
    
    public PreliminaryProjectDAO(){
        dataBaseConnection = new DataBaseConnection();
    }

    @Override
    public boolean savedPreliminaryProject(PreliminaryProject preliminaryproject, int idProject, int idCollaborator) throws BusinessConnectionException, BusinessDataException {
       String sql = "INSERT INTO preliminaryProject (idPreliminaryproject, idProject, idCollaborator, tittlePreliminaryProject, \n" +
                    "preliminaryProjectCondition, duration, modality, preliminaryProjectDescription, startDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";  
       boolean saveResult = false;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, preliminaryproject.getIdPreliminaryproject());
            preparedStatement.setInt(2, idProject);
            preparedStatement.setInt(3, idCollaborator);
            preparedStatement.setString(4, preliminaryproject.getTittlePreliminaryProject());
            preparedStatement.setString(5, preliminaryproject.getPreliminaryProjectCondition());
            preparedStatement.setString(6, preliminaryproject.getDuration());
            preparedStatement.setString(7, preliminaryproject.getModality());
            preparedStatement.setString(8, preliminaryproject.getPreliminaryProjectDescription());
            preparedStatement.setDate(9, preliminaryproject.getStartDate());
            preparedStatement.executeUpdate();
            preparedStatement = null;
            saveResult = true;          
        } catch (SQLException e) {
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", e);
        } finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
    
    @Override
    public boolean updatePreliminaryProject(PreliminaryProject preliminaryproject, int idProject, int idCollaborator) throws BusinessConnectionException, BusinessDataException {
        String sql = "UPDATE preliminaryproject SET idPreliminaryproject = ?, idProject = ?, idCollaborator = ?, tittlePreliminaryProject = ?, \n"
                + "preliminaryProjectCondition = ?, duration = ?, modality = ?, preliminaryProjectDescription = ?, startDate = ?"
                + "WHERE idPreliminaryProject = ?";
        boolean updateResult = false;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setInt(1, preliminaryproject.getIdPreliminaryproject());
            preparedStatement.setInt(2, idProject);
            preparedStatement.setInt(3, idCollaborator);
            preparedStatement.setString(4, preliminaryproject.getTittlePreliminaryProject());
            preparedStatement.setString(5, preliminaryproject.getPreliminaryProjectCondition());
            preparedStatement.setString(6, preliminaryproject.getDuration());
            preparedStatement.setString(7, preliminaryproject.getModality());
            preparedStatement.setString(8, preliminaryproject.getPreliminaryProjectDescription());
            preparedStatement.setDate(9, preliminaryproject.getStartDate());
            preparedStatement.executeUpdate();
            preparedStatement = null;
            updateResult = true;
        } catch (SQLException e) {
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", e);
        } finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }

    @Override
    public PreliminaryProject findpreliminaryProjectById(int id) throws BusinessConnectionException {
        String sql = "SELECT * FROM preliminaryProject WHERE idPreliminaryProject = ?";
        PreliminaryProject preliminaryProject = null;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idPreliminaryproject;
                int idProject;
                int idCollaborator;
                String tittlePreliminaryProject;
                String preliminaryProjectCondition;
                String duration;
                String modality;
                String preliminaryProjectDescription;
                Date startDate;
            }
        } catch (Exception e) {
        }
    }
    /*
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
    */
    
}
