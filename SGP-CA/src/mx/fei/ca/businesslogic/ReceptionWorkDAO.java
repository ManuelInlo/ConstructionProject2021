
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Collaborator;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.InvestigationProject;
import mx.fei.ca.domain.ReceptionWork;

public class ReceptionWorkDAO implements IReceptionWorkDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public ReceptionWorkDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    @Override
    public boolean savedReceptionWork(ReceptionWork receptionWork) throws BusinessConnectionException{
        String sql = "INSERT INTO receptionwork (impactCA, titleReceptionWork, fileRoute, startDate, endDate,"
                     + "grade, workType, actualState, idProject, curp, idCollaborator) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, receptionWork.getImpactCA());
            preparedStatement.setString(2, receptionWork.getTitleReceptionWork());
            preparedStatement.setString(3, receptionWork.getFileRoute());
            preparedStatement.setDate(4, receptionWork.getStartDate());
            preparedStatement.setDate(5, receptionWork.getEndDate());
            preparedStatement.setString(6, receptionWork.getGrade());
            preparedStatement.setString(7, receptionWork.getWorkType());
            preparedStatement.setString(8, receptionWork.getActualState());
            preparedStatement.setInt(9, receptionWork.getInvestigationProject().getIdProject());
            preparedStatement.setString(10, receptionWork.getIntegrant().getCurp());
            preparedStatement.setInt(11, receptionWork.getCollaborator().getIdCollaborator());
            preparedStatement.executeUpdate();
            saveResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }

    @Override
    public boolean updatedReceptionWorkById(ReceptionWork receptionWork, int id) throws BusinessConnectionException {
        String sql = "UPDATE receptionWork SET impactCA = ?, titleReceptionWork = ?, fileRoute = ?, startDate = ?, endDate = ?, "
                    + "grade = ?, workType = ?, actualState = ?, idProject = ?, idCollaborator = ? WHERE id = ?";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, receptionWork.getImpactCA());
            preparedStatement.setString(2, receptionWork.getTitleReceptionWork());
            preparedStatement.setString(3, receptionWork.getFileRoute());
            preparedStatement.setDate(4, receptionWork.getStartDate());
            preparedStatement.setDate(5, receptionWork.getEndDate());
            preparedStatement.setString(6, receptionWork.getGrade());
            preparedStatement.setString(7, receptionWork.getWorkType());
            preparedStatement.setString(8, receptionWork.getActualState());
            preparedStatement.setInt(9, receptionWork.getInvestigationProject().getIdProject());
            preparedStatement.setInt(11, receptionWork.getCollaborator().getIdCollaborator());
            preparedStatement.setInt(12, id);
            preparedStatement.executeUpdate();
            updateResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }
    
    @Override
    public ArrayList<ReceptionWork> findReceptionWorksByPositiveImpactCA() throws BusinessConnectionException {
        String sql = "SELECT * FROM receptionWork WHERE impactCA = 'SI'";
        ArrayList<ReceptionWork> receptionWorks = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
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
                if(endDate != null){
                    receptionWork = new ReceptionWork(impactCA, titleReceptionWork, fileRoute, startDate, endDate, grade, workType, actualState);
                }else{
                    receptionWork = new ReceptionWork(impactCA, titleReceptionWork, fileRoute, startDate, grade, workType, actualState);
                }
                receptionWork.setId(id);
                receptionWorks.add(receptionWork);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return receptionWorks;
    }

    @Override
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

    @Override
    public ArrayList<ReceptionWork> findReceptionWorkByInitialesOfTitle(String InitialesTitleReceptionWork, String curp) throws BusinessConnectionException {
        String sql = "SELECT * FROM receptionWork WHERE titleReceptionWork LIKE CONCAT('%',?,'%') AND curp = ?";
        ArrayList<ReceptionWork> receptionWorks = new ArrayList<>();
        
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, InitialesTitleReceptionWork);
            preparedStatement.setString(2, curp);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ReceptionWork receptionWork;
                int id = resultSet.getInt("id");
                String impactCA = resultSet.getString("impactCA");
                String fileRoute = resultSet.getString("fileRoute");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                String grade = resultSet.getString("grade");
                String workType = resultSet.getString("workType");
                String actualState = resultSet.getString("actualState");
                int idProject = resultSet.getInt("idProject");
                int idCollaborator = resultSet.getInt("idCollaborator");
                String titleReceptionWork = resultSet.getString("titleReceptionWork");
                if(endDate != null){
                    receptionWork = new ReceptionWork(impactCA, titleReceptionWork, fileRoute, startDate, endDate, grade, workType, actualState);
                }else{
                    receptionWork = new ReceptionWork(impactCA, titleReceptionWork, fileRoute, startDate, grade, workType, actualState);
                }
                //ProyectoInvestigacionDAO proyectoInvestigacionDAO = new ProyectoInvestigacionDAO;
                InvestigationProject investigationProject = new InvestigationProject(idProject); //En vez de esto, debe mandar a llamar metodo buscar proyecto por id
                CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
                Collaborator collaborator = collaboratorDAO.findCollaboratorByIdCollaborator(idCollaborator);
                receptionWork.setId(id);
                receptionWork.setInvestigationProject(investigationProject);
                receptionWork.setCollaborator(collaborator);
                receptionWorks.add(receptionWork);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return receptionWorks;
    }

    @Override
    public boolean existsReceptionWorkTitle(String titleReceptionWork) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM receptionWork WHERE titleReceptionWork = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, titleReceptionWork);
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
    public boolean existsReceptionWorkFileRoute(String fileRoute) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM receptionWork WHERE fileRoute = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fileRoute);
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
    public boolean existsReceptionWorkTitleForUpdate(String titleReceptionWork, int id) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM receptionWork WHERE titleReceptionWork = ? AND id <> ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, titleReceptionWork);
            preparedStatement.setInt(2, id);
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
    public boolean existsReceptionWorkFileRouteForUpdate(String fileRoute, int id) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM receptionWork WHERE fileRoute = ? AND id <> ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fileRoute);
            preparedStatement.setInt(2, id);
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
