package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Target;


public class TargetDAO implements ITargetDAO {
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public TargetDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    @Override
    public boolean savedTarget(Target target, int keyCodePlan) throws BusinessConnectionException {
        String sql = "INSERT INTO target (idTarget, keycodePlan, targetTittle, targetDescription) VALUES (?, ?, ?, ?)";
        boolean saveResult = false;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            //prrep
        } catch (Exception e) {
            
        } finally{
            
        }
        return saveResult;
    }
    
    /*
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
    */

    @Override
    public boolean updatedTarget(Target target, int keyCodePlan, int idTarget) throws BusinessConnectionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
