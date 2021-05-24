
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

    @Override
    public boolean existsAgendaPointTopic(String topic, int idMeeting) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM agendaPoint WHERE topic = ? AND idMeeting = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, topic);
            preparedStatement.setInt(2, idMeeting);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                exists = true;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return exists;
    }

    @Override
    public boolean existsAvailableHoursForAgendaPoint(Time startTime, Time endTime, int idMeeting) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM agendaPoint WHERE startTime = ? AND endTime = ? AND idMeeting = ?";
        boolean hoursAvailable = true;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTime(1, startTime);
            preparedStatement.setTime(2, endTime);
            preparedStatement.setInt(3, idMeeting);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                hoursAvailable = false;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return hoursAvailable;
    }

    @Override
    public boolean existsAgendaPointTopicForUpdate(String topic, int idAgendaPoint, int idMeeting) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM agendaPoint WHERE topic = ? AND idMeeting = ? AND idAgendaPoint<> ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, topic);
            preparedStatement.setInt(2, idMeeting);
            preparedStatement.setInt(3, idAgendaPoint);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                exists = true;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return exists;
    }

    @Override
    public boolean existsAvailableHoursForAgendaPointForUpdate(AgendaPoint agendaPoint, int idAgendaPoint, int idMeeting) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM agendaPoint WHERE startTime = ? AND endTime = ? AND idMeeting = ? AND idAgendaPoint <> ?";
        boolean hoursAvailable = true;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTime(1, agendaPoint.getStartTime());
            preparedStatement.setTime(2, agendaPoint.getEndTime());
            preparedStatement.setInt(3, idMeeting);
            preparedStatement.setInt(4, idAgendaPoint);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                hoursAvailable = false;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return hoursAvailable;
    }
}
