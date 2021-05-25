package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public ArrayList<WorkPlan> findAllWorkPlans() throws BusinessConnectionException {
        String sql = "SELECT * FROM WorkPlan";
        ArrayList<WorkPlan> workPlans = new ArrayList<>();
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                WorkPlan workPlan;
                int keyCodePlan = resultSet.getInt("keyCodePlan");
                String curp = resultSet.getString("curp");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                workPlan = new WorkPlan(keyCodePlan, curp, startDate, endDate);
                workPlans.add(workPlan);             
            }
        } catch (SQLException e) {
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", e);
        } finally{
            dataBaseConnection.closeConnection();
        }        
        return workPlans;
    }    
}
