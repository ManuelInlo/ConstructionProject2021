
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Meeting;

public class MeetingDAO implements IMeetingDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public MeetingDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    @Override
    public int saveMeeting(Meeting meeting, String curp) throws BusinessConnectionException{
        String sql = "INSERT INTO meeting (meetingDate, meetingTime, meetingPlace, affair, projectName, curp)"
                     + "VALUES (?, ?, ?, ?, ?, ?)";
        int saveResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, meeting.getMeetingDate());
            preparedStatement.setTime(2, meeting.getMeetingTime());
            preparedStatement.setString(3, meeting.getMeetingPlace());
            preparedStatement.setString(4, meeting.getAffair());
            preparedStatement.setString(5, meeting.getProjectName());
            preparedStatement.setString(6, curp);
            saveResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
    
    @Override 
    public ArrayList<Meeting> findMeetingsByProjectName(String projectName) throws BusinessConnectionException{
        ArrayList<Meeting> meetings = new ArrayList<>();
        String sql = "Select * from meeting where projectName = ?";
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, projectName);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idMeeting = resultSet.getInt("idMeeting");
                Date meetingDate = resultSet.getDate("meetingDate");
                Time meetingTime = resultSet.getTime("meetingTime");
                String meetingPlace = resultSet.getString("meetingPlace");
                String affair = resultSet.getString("affair");
                Meeting meeting = new Meeting(meetingDate, meetingTime, meetingPlace, affair, projectName);
                meeting.setIdMeeting(idMeeting);
                meetings.add(meeting);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return meetings;
    }
    
    @Override
    public ArrayList<Meeting> findMeetingsByProjectNameAndDate(String projectName, Date meetingDate) throws BusinessConnectionException{
        ArrayList<Meeting> meetings = new ArrayList<>();
        String sql = "Select * from meeting where projectName = ? and meetingDate = ?";
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, projectName);
            preparedStatement.setDate(2, meetingDate);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idMeeting = resultSet.getInt("idMeeting");
                Time meetingTime = resultSet.getTime("meetingTime");
                String meetingPlace = resultSet.getString("meetingPlace");
                String affair = resultSet.getString("affair");
                Meeting meeting = new Meeting(meetingDate, meetingTime, meetingPlace, affair, projectName);
                meeting.setIdMeeting(idMeeting);
                meetings.add(meeting);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return meetings;
    }
    
    @Override
    public int updateMeeting(Meeting meeting, int idMeeting)throws BusinessConnectionException{
        String sql = "UPDATE meeting SET meetingDate = ?, meetingTime = ?, meetingPlace = ?, affair = ?, projectName = ?"
                    + "WHERE idMeeting = ?";
        int updateResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, meeting.getMeetingDate());
            preparedStatement.setTime(2, meeting.getMeetingTime());
            preparedStatement.setString(3, meeting.getMeetingPlace());
            preparedStatement.setString(4, meeting.getAffair());
            preparedStatement.setString(5, meeting.getProjectName());
            preparedStatement.setInt(6, idMeeting);
            updateResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }

    @Override
    public String getCurpOfResponsibleMeeting(int idMeeting) throws BusinessConnectionException {
        String sql = "SELECT curp FROM meeting where idMeeting = ?";
        String resultingCurp = null;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMeeting);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                resultingCurp = resultSet.getString("curp");
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return resultingCurp;
    }
 
}
