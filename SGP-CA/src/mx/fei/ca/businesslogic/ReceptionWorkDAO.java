
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
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
    public int saveReceptionWork(ReceptionWork receptionWork) throws BusinessConnectionException{
        String sql = "INSERT INTO receptionwork (impactCA, titleReceptionWork, fileRoute, startDate, endDate,"
                     + "grade, workType, actualState, idProject, curp, idCollaborator) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int saveResult = 0;
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
            saveResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }

    @Override
    public int updateReceptionWorkById(ReceptionWork receptionWork, int id) throws BusinessConnectionException {
        String sql = "UPDATE receptionWork SET impactCA = ?, titleReceptionWork = ?, fileRoute = ?, startDate = ?, endDate = ?, "
                    + "grade = ?, workType = ?, actualState = ?, idProject = ?, curp = ?, idCollaborator = ? WHERE id = ?";
        int updateResult = 0;
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
            preparedStatement.setInt(12, id);
            updateResult = preparedStatement.executeUpdate();
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
                ReceptionWork receptionWork = null;
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
    public ArrayList<ReceptionWork> findReceptionWorksByCurpIntegrant(String curp) throws BusinessConnectionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ReceptionWork findReceptionWorkByCurpAndTitle(String titleReceptionWork, String curp) throws BusinessConnectionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
