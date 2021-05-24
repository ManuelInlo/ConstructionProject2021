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
import mx.fei.ca.domain.InvestigationProject;


public class InvestigationProjectDAO implements IInvestigationProjectDAO {
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public InvestigationProjectDAO(){
        dataBaseConnection = new DataBaseConnection();
    }

    @Override
    public boolean savedInvestigationProject(mx.fei.ca.domain.InvestigationProject investigationproject, int keycode) throws BusinessConnectionException, BusinessDataException {
        String sql = "INSERT INTO investigationProject (idProject, keyCode, endDate, startDate, tittleProject, description)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        boolean saveResult = false;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, investigationproject.getIdProject());
            preparedStatement.setInt(2, keycode);
            preparedStatement.setDate(3, investigationproject.getEndDate());
            preparedStatement.setDate(4, investigationproject.getStartDate());
            preparedStatement.setString(5, investigationproject.getTittleProject());
            preparedStatement.setString(6, investigationproject.getDescription());  
            preparedStatement.executeUpdate();
            preparedStatement = null;
            saveResult = true;
        } catch (SQLException e) {
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", e);
        } finally{
            dataBaseConnection.closeConnection();
        }
       return saveResult;
    }
    
    @Override
    public boolean updateInvestigationproject(InvestigationProject investigationproject, int keycode) throws BusinessConnectionException, BusinessDataException {
        String sql = "UPDATE investigationProject SET idProject = ?, keyCode = ?, endDate = ?, startDate = ?, tittleProject = ?, "
                + "description = ? WHERE idProject = ?";
        boolean updateResult = false;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, investigationproject.getIdProject());
            preparedStatement.setInt(2, keycode);
            preparedStatement.setDate(3, investigationproject.getEndDate());
            preparedStatement.setDate(4, investigationproject.getStartDate());
            preparedStatement.setString(5, investigationproject.getTittleProject());
            preparedStatement.setString(6, investigationproject.getDescription());
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
    public InvestigationProject findInvestigationProjectById(int idProject) throws BusinessConnectionException {
        String sql = "SELECT * FROM investigationProject WHERE idProject = ?";
        InvestigationProject investigationProject = null;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setInt(1, idProject);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String keyCode = resultSet.getString("keycode");
                Date startDate = resultSet.getDate("startDate");
                Date endDate  = resultSet.getDate("endDate");
                String tittleProject = resultSet.getString("tittleproject");
                String description = resultSet.getString("description");
                investigationProject = new InvestigationProject(idProject, keyCode, startDate, endDate, tittleProject, description);
            }
        }catch (SQLException e) {
            throw new BusinessConnectionException("Perdida de conexión con la base de datos",e);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return investigationProject;
    }

    @Override
    public InvestigationProject findInvestigationProjectByName(String tittleProject) throws BusinessConnectionException {
        String sql = "SELECT * FROM investigationProject WHERE tittleProject = ?";
        InvestigationProject investigationProject = null;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, tittleProject);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idProject = resultSet.getInt("idProject");
                String keyCode = resultSet.getString("keycode");
                Date startDate = resultSet.getDate("startDate");
                Date endDate  = resultSet.getDate("endDate");
                String description = resultSet.getString("description");
                investigationProject = new InvestigationProject(idProject, keyCode, startDate, endDate, tittleProject, description);
            }
        }catch (SQLException e) {
            throw new BusinessConnectionException("Perdida de conexión con la base de datos",e);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return investigationProject;
    }

    @Override
    public ArrayList<InvestigationProject> findAllInvestigationProjects() throws BusinessConnectionException {
        String sql = "SELECT * FROM investigationProject";
        ArrayList<InvestigationProject> investigationProjects = new ArrayList<>();
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                InvestigationProject investigationProject;
                int idProject = resultSet.getInt("idProject");
                String keyCode = resultSet.getString("keycode");
                Date startDate = resultSet.getDate("startDate");
                Date endDate  = resultSet.getDate("endDate");
                String tittleProject = resultSet.getString("tittleproject");
                String description = resultSet.getString("description");
                investigationProject = new InvestigationProject(idProject, keyCode, startDate, endDate, tittleProject, description);
                investigationProjects.add(investigationProject);
            }
        } catch (SQLException e) {
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", e);
        } finally{
            dataBaseConnection.closeConnection();
        }
        return investigationProjects;
    }

}
