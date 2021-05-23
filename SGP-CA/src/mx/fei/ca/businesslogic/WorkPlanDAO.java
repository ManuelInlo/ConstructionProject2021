package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.WorkPlan;


public class WorkPlanDAO implements IWorkPlanDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public WorkPlanDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    @Override
    public boolean savedWorkPlan(WorkPlan workPlan, String curp) throws BusinessConnectionException {
       String sql = "INSERT INTO workPlan (keyCodePlan, curp, startDate, endDate) VALUES (?, ?, ?, ?)";
       boolean saveResult = false;
        try {
         connection = dataBaseConnection.getConnection();
         preparedStatement = connection.prepareStatement(sql);
         preparedStatement.setInt(1, workPlan.getKeyCodePlan());
         preparedStatement.setString(2, curp);
         preparedStatement.setDate(3, workPlan.getStartDate());
         preparedStatement.setDate(4, workPlan.getEndDate());
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
    public boolean updatedWorkPlan(WorkPlan workPlan, String curp, int keyCodePlan) throws BusinessConnectionException {
        String sql = "UPDATE workPlan SET keyCodePlan = ?, curp = ?, startDate = ?, endDate = ? WHERE keyCodePlan = ?";
        boolean updateResult = false;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, keyCodePlan);
            preparedStatement.setString(2, curp);
            preparedStatement.setDate(3, workPlan.getStartDate());
            preparedStatement.setDate(4, workPlan.getEndDate());
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
    
}
