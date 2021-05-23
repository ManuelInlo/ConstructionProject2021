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
    public InvestigationProject findInvestigationProjectById(int idInvestigationproject) throws BusinessConnectionException {
        String sql = "SELECT * FROM investigationProject WHERE idInvestigationProject = ?";
        InvestigationProject investigationProject = null;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setInt(1, idInvestigationproject);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idProject = resultSet.getInt("idProject");
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
    public InvestigationProject findInvestigationProjectByName(String tittleproject) throws BusinessConnectionException {
        String sql = "SELECT * FROM investigationProject WHERE tittleProject = ?";
        InvestigationProject investigationProject = null;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, tittleproject);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idProject = resultSet.getInt("idProject");
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
    public ArrayList<InvestigationProject> findAllInvestigationProjects() throws BusinessConnectionException {
        String sql = "SELECT * FROM investigationProject";
        ArrayList<InvestigationProject> investigationProjects = new ArrayList<>();
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                InvestigationProject investigationProject = new InvestigationProject();
                int idProject = resultSet.getInt("idProject");
                String keyCode = resultSet.getString("keycode");
                Date startDate = resultSet.getDate("startDate");
                Date endDate  = resultSet.getDate("endDate");
                String tittleProject = resultSet.getString("tittleproject");
                String description = resultSet.getString("description");
                investigationProjects.add(investigationProject);
            }
        } catch (Exception e) {
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", e);
        } finally{
            dataBaseConnection.closeConnection();
        }
        return investigationProjects;
    }
    /*
    public ArrayList<ReceptionWork> findLastTwoReceptionWorksByCurpIntegrant(String curp) throws BusinessConnectionException {
        String sql = "SELECT * FROM receptionWork WHERE curp = ?";
        ArrayList<ReceptionWork> receptionWorks = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);
            resultSet = preparedStatement.executeQuery();
            int receptionWorksCounter = 0;
            while(resultSet.next() && receptionWorksCounter < 2){
                ReceptionWork receptionWork;
                int id = resultSet.getInt("id");
                String impactCA = resultSet.getString("impactCA");
                String titleReceptionWork = resultSet.getString("titleReceptionWork");
                String fileRoute = resultSet.getString("fileRoute");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                String grade = resultSet.getString("grade");
                String workType = resultSet.getString("workType");
                String actualState = resultSet.getString("actualState");
                int idProject = resultSet.getInt("idProject");
                int idCollaborator = resultSet.getInt("idCollaborator");
                if(endDate != null){
                    receptionWork = new ReceptionWork(impactCA, titleReceptionWork, fileRoute, startDate, endDate, grade, workType, actualState);
                }else{
                    receptionWork = new ReceptionWork(impactCA, titleReceptionWork, fileRoute, startDate, grade, workType, actualState);
                }
                //InvestigationProjectDAO investigatioProjectDAO = new InvestigationProjectDAO();
                InvestigationProject investigationProject = new InvestigationProject(idProject); //En vez de esto, debe mandar a llamar metodo buscar proyecto por id
                CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
                Collaborator collaborator = collaboratorDAO.findCollaboratorByIdCollaborator(idCollaborator);
                receptionWork.setId(id);
                receptionWork.setInvestigationProject(investigationProject);
                receptionWork.setCollaborator(collaborator);
                receptionWorks.add(receptionWork);
                receptionWorksCounter++;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return receptionWorks;
    }
    */
}
