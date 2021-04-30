
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
    public int saveAgendaPoint(AgendaPoint agendaPoint, int idMeeting) throws BusinessConnectionException{
        String sql = "INSERT INTO agendaPoint (startTime, endTime, number, topic, leader, idMeeting) VALUES "
                     + "(?, ?, ?, ?, ?, ?)";
        int saveResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTime(1, agendaPoint.getStartTime());
            preparedStatement.setTime(2, agendaPoint.getEndTime());
            preparedStatement.setInt(3, agendaPoint.getNumber());
            preparedStatement.setString(4, agendaPoint.getTopic());
            preparedStatement.setString(5, agendaPoint.getLeader());
            preparedStatement.setInt(6, idMeeting);
            saveResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
    
    @Override
    public int updateAgendaPoint(AgendaPoint agendaPoint, int idAgendaPoint, int idMeeting) throws BusinessConnectionException{
        String sql = "UPDATE agendaPoint SET startTime = ?, endTime = ?, number = ?, topic =?, leader = ?, idMeeting = ? "
                     + "WHERE idAgendaPoint = ?";
        int updateResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTime(1, agendaPoint.getStartTime());
            preparedStatement.setTime(2, agendaPoint.getEndTime());
            preparedStatement.setInt(3, agendaPoint.getNumber());
            preparedStatement.setString(4, agendaPoint.getTopic());
            preparedStatement.setString(5, agendaPoint.getLeader());
            preparedStatement.setInt(6, idMeeting);
            preparedStatement.setInt(7, idAgendaPoint);
            updateResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }
    
    @Override
    public int deleteAgendaPointById(int idAgendaPoint) throws BusinessConnectionException{
        String sql = "DELETE FROM agreement WHERE idAgendaPoint = ?";
        int deleteResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idAgendaPoint);
            deleteResult = preparedStatement.executeUpdate();
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
                int number = resultSet.getInt("number");
                String topic = resultSet.getString("topic");
                String leader = resultSet.getString("leader");
                AgendaPoint agendaPoint = new AgendaPoint(startTime, endTime, number, topic, leader);
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
    public boolean validateExistenceOfAgendaPointTopic(String topic, int idAgendaPoint) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM agendaPoint WHERE topic = ? AND idAgendaPoint = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, topic);
            preparedStatement.setInt(2, idAgendaPoint);
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
    public boolean validateAvailableHoursForAgendaPoint(Time startTime, Time endTime, int idAgendaPoint) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM agendaPoint WHERE startTime = ? AND endTime = ? AND idAgendaPoint = ?";
        boolean hoursAvailable = true;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTime(1, startTime);
            preparedStatement.setTime(2, endTime);
            preparedStatement.setInt(3, idAgendaPoint);
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
