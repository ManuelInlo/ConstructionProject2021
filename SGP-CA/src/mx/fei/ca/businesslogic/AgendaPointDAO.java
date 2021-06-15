
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.AgendaPoint;

public class AgendaPointDAO implements IAgendaPointDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public AgendaPointDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    @Override
    public boolean savedAgendaPoint(AgendaPoint agendaPoint, int idMeeting) throws BusinessConnectionException{
        String sql = "INSERT INTO agendaPoint (startTime, endTime, topic, leader, idMeeting) VALUES "
                     + "(?, ?, ?, ?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTime(1, agendaPoint.getStartTime());
            preparedStatement.setTime(2, agendaPoint.getEndTime());
            preparedStatement.setString(3, agendaPoint.getTopic());
            preparedStatement.setString(4, agendaPoint.getLeader());
            preparedStatement.setInt(5, idMeeting);
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
    public boolean updatedAgendaPoint(AgendaPoint agendaPoint, int idAgendaPoint, int idMeeting) throws BusinessConnectionException{
        String sql = "UPDATE agendaPoint SET startTime = ?, endTime = ?, topic =?, leader = ?, idMeeting = ? "
                     + "WHERE idAgendaPoint = ?";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTime(1, agendaPoint.getStartTime());
            preparedStatement.setTime(2, agendaPoint.getEndTime());
            preparedStatement.setString(3, agendaPoint.getTopic());
            preparedStatement.setString(4, agendaPoint.getLeader());
            preparedStatement.setInt(5, idMeeting);
            preparedStatement.setInt(6, idAgendaPoint);
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
    public boolean deletedAgendaPointById(int idAgendaPoint) throws BusinessConnectionException{
        String sql = "DELETE FROM agendapoint WHERE idAgendaPoint = ?";
        boolean deleteResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idAgendaPoint);
            preparedStatement.executeUpdate();
            deleteResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return deleteResult;
    }
    
    @Override
    public ArrayList<AgendaPoint> findAgendaPointsByIdMeeting(int idMeeting) throws BusinessConnectionException{
        ArrayList<AgendaPoint> agendaPoints = new ArrayList<>();
        String sql = "SELECT * FROM agendaPoint WHERE idMeeting = ?";
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMeeting);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idAgendaPoint = resultSet.getInt("idAgendaPoint");
                Time startTime = resultSet.getTime("startTime");
                Time endTime = resultSet.getTime("endTime");
                String topic = resultSet.getString("topic");
                String leader = resultSet.getString("leader");
                AgendaPoint agendaPoint = new AgendaPoint(startTime, endTime, topic, leader);
                agendaPoint.setIdAgendaPoint(idAgendaPoint);
                agendaPoints.add(agendaPoint);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return agendaPoints;
    }
}
