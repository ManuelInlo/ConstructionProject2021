
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Strategy;


public class StrategyDAO implements IStrategyDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public StrategyDAO(){
        dataBaseConnection = new DataBaseConnection();
    }

    @Override
    public boolean saveStrategy(Strategy strategy, int idTarget) throws BusinessConnectionException, BusinessDataException {
        String sql = "INSERT INTO strategy (idStrategy, idTarget, StrategyNumber, strategyDescription, goal, strategyAction, result)";
        boolean saveResult = false;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, strategy.getIdStrategy());
            preparedStatement.setInt(2, idTarget);
            preparedStatement.setInt(3, strategy.getStrategyNumber());
            preparedStatement.setString(4, strategy.getStrategyDescription());
            preparedStatement.setString(5, strategy.getGoal());
            preparedStatement.setString(6, strategy.getStrategyAction());
            preparedStatement.setString(7, strategy.getResult());
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
    public boolean updateStrategy(Strategy strategy, int idTarget, int idStrategy) throws BusinessConnectionException, BusinessDataException {
        String sql = "UPDATE strategy SET idTarget = ?, StrategyNumber = ?, strategyDescription = ?, goal = ?"
                + ", strategyAction = ?, result = ? WHERE idStrategy = ?";
        boolean updateResult = false;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);               
            preparedStatement.setInt(1, idTarget);
            preparedStatement.setInt(2, strategy.getStrategyNumber());
            preparedStatement.setString(3, strategy.getStrategyDescription());
            preparedStatement.setString(4, strategy.getGoal());
            preparedStatement.setString(5, strategy.getStrategyAction());
            preparedStatement.setString(6, strategy.getResult());
            preparedStatement.setInt(7, strategy.getIdStrategy());
            preparedStatement.executeUpdate();
            preparedStatement = null;
        } catch (SQLException e) {
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", e);
        } finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }
    /*
    public boolean updateStrategy(Strategy strategy, int idTarget, int idStrategy) throws BusinessConnectionException, BusinessDataException {
        String sql = "UPDATE strategy SET idStrategy = ?, idTarget = ?, StrategyNumber = ?, strategyDescription = ?, goal = ?"
                + ", strategyAction = ?, result = ? WHERE idStrategy = ?";
        boolean updateResult = false;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);     
            preparedStatement.setInt(1, strategy.getIdStrategy());
            preparedStatement.setInt(2, idTarget);
            preparedStatement.setInt(3, strategy.getStrategyNumber());
            preparedStatement.setString(4, strategy.getStrategyDescription());
            preparedStatement.setString(5, strategy.getGoal());
            preparedStatement.setString(6, strategy.getStrategyAction());
            preparedStatement.setString(7, strategy.getResult());
            preparedStatement.executeUpdate();
            preparedStatement = null;
        } catch (SQLException e) {
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", e);
        } finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }
    */
}
