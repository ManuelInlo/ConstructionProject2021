package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            preparedStatement.setInt(1, target.getIdTarget());
            preparedStatement.setInt(2, keyCodePlan);
            preparedStatement.setString(3, target.getTargetTittle());
            preparedStatement.setString(4, target.getTargetDescription());
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
    public boolean updatedTarget(Target target, int keyCodePlan, int idTarget) throws BusinessConnectionException {
        String sql = "UPDATE target SET idTarget = ?, keycodePlan = ?, targetTittle = ?, targetDescription = ? WHERE idTarget = ?";
        boolean updateResult = false;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, target.getIdTarget());
            preparedStatement.setInt(2, idTarget);
            preparedStatement.setString(3, target.getTargetTittle());
            preparedStatement.setString(4, target.getTargetTittle());
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
