
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
import mx.fei.ca.domain.AgendaPoint;
import mx.fei.ca.domain.Meeting;
import mx.fei.ca.domain.MeetingAssistant;
import mx.fei.ca.domain.Memorandum;
import mx.fei.ca.domain.Prerequisite;

public class MeetingDAO implements IMeetingDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public MeetingDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    @Override
    public boolean savedMeeting(Meeting meeting, String curp) throws BusinessConnectionException{
        String sql = "INSERT INTO meeting (meetingDate, meetingTime, meetingPlace, affair, projectName, curp)"
                     + "VALUES (?, ?, ?, ?, ?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, meeting.getMeetingDate());
            preparedStatement.setTime(2, meeting.getMeetingTime());
            preparedStatement.setString(3, meeting.getMeetingPlace());
            preparedStatement.setString(4, meeting.getAffair());
            preparedStatement.setString(5, meeting.getProjectName());
            preparedStatement.setString(6, curp);
            preparedStatement.executeUpdate();
            saveResult = true; 
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
    
    @Override
    public ArrayList<Meeting> findLastFiveMeetings() throws BusinessConnectionException {
        ArrayList<Meeting> meetings = new ArrayList<>();
        String sql = "Select * from meeting;";
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            int meetingCounter = 0;
            AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
            MemorandumDAO  memorandumDAO = new MemorandumDAO();
            PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
            MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
            while(resultSet.next() && meetingCounter < 5){
                int idMeeting = resultSet.getInt("idMeeting");
                Date meetingDate = resultSet.getDate("meetingDate");
                Time meetingTime = resultSet.getTime("meetingTime");
                String meetingPlace = resultSet.getString("meetingPlace");
                String affair = resultSet.getString("affair");
                String projectName = resultSet.getString("projectName");
                Meeting meeting = new Meeting(meetingDate, meetingTime, meetingPlace, affair, projectName);
                ArrayList<AgendaPoint> agendaPoints = agendaPointDAO.findAgendaPointsByIdMeeting(idMeeting);
                ArrayList<Prerequisite> prerequisites = prerequisiteDAO.findPrerequisitesByIdMeeting(idMeeting);
                ArrayList<MeetingAssistant> assistants = meetingAssistantDAO.findMeetingAssistantsByIdMeeting(idMeeting);
                Memorandum memorandum = memorandumDAO.findMemorandumByIdMeeting(idMeeting);
                meeting.setIdMeeting(idMeeting);
                meeting.setAgendaPoints(agendaPoints);
                meeting.setPrerequisites(prerequisites);
                meeting.setAssistants(assistants);
                meeting.setMemorandum(memorandum);
                meetings.add(meeting);
                meetingCounter++;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return meetings;
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
            AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
            MemorandumDAO  memorandumDAO = new MemorandumDAO();
            PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
            MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
            while(resultSet.next()){
                int idMeeting = resultSet.getInt("idMeeting");
                Date meetingDate = resultSet.getDate("meetingDate");
                Time meetingTime = resultSet.getTime("meetingTime");
                String meetingPlace = resultSet.getString("meetingPlace");
                String affair = resultSet.getString("affair");
                Meeting meeting = new Meeting(meetingDate, meetingTime, meetingPlace, affair, projectName);
                ArrayList<AgendaPoint> agendaPoints = agendaPointDAO.findAgendaPointsByIdMeeting(idMeeting);
                ArrayList<Prerequisite> prerequisites = prerequisiteDAO.findPrerequisitesByIdMeeting(idMeeting);
                ArrayList<MeetingAssistant> assistants = meetingAssistantDAO.findMeetingAssistantsByIdMeeting(idMeeting);
                Memorandum memorandum = memorandumDAO.findMemorandumByIdMeeting(idMeeting);
                meeting.setIdMeeting(idMeeting);
                meeting.setAgendaPoints(agendaPoints);
                meeting.setPrerequisites(prerequisites);
                meeting.setAssistants(assistants);
                meeting.setMemorandum(memorandum);
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
            AgendaPointDAO agendaPointDAO = new AgendaPointDAO();
            MemorandumDAO  memorandumDAO = new MemorandumDAO();
            PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
            MeetingAssistantDAO meetingAssistantDAO = new MeetingAssistantDAO();
            while(resultSet.next()){
                int idMeeting = resultSet.getInt("idMeeting");
                Time meetingTime = resultSet.getTime("meetingTime");
                String meetingPlace = resultSet.getString("meetingPlace");
                String affair = resultSet.getString("affair");
                Meeting meeting = new Meeting(meetingDate, meetingTime, meetingPlace, affair, projectName);
                ArrayList<AgendaPoint> agendaPoints = agendaPointDAO.findAgendaPointsByIdMeeting(idMeeting);
                ArrayList<Prerequisite> prerequisites = prerequisiteDAO.findPrerequisitesByIdMeeting(idMeeting);
                ArrayList<MeetingAssistant> assistants = meetingAssistantDAO.findMeetingAssistantsByIdMeeting(idMeeting);
                Memorandum memorandum = memorandumDAO.findMemorandumByIdMeeting(idMeeting);
                meeting.setIdMeeting(idMeeting);
                meeting.setAgendaPoints(agendaPoints);
                meeting.setPrerequisites(prerequisites);
                meeting.setAssistants(assistants);
                meeting.setMemorandum(memorandum);
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
    public boolean updatedMeeting(Meeting meeting, int idMeeting)throws BusinessConnectionException{
        String sql = "UPDATE meeting SET meetingDate = ?, meetingTime = ?, meetingPlace = ?, affair = ?, projectName = ?"
                    + "WHERE idMeeting = ?";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, meeting.getMeetingDate());
            preparedStatement.setTime(2, meeting.getMeetingTime());
            preparedStatement.setString(3, meeting.getMeetingPlace());
            preparedStatement.setString(4, meeting.getAffair());
            preparedStatement.setString(5, meeting.getProjectName());
            preparedStatement.setInt(6, idMeeting);
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

    @Override
    public boolean existsMeetingAffair(String meetingAffair) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM meeting WHERE affair = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, meetingAffair);
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
    public boolean existsDateAndTimeAvailable(Date meetingDate, Time meetingTime) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM meeting WHERE meetingDate = ? and meetingTime = ?";
        boolean available = true;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, meetingDate);
            preparedStatement.setTime(2, meetingTime);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                available = false;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return available;
    }

    @Override
    public boolean existsMeetingAffairForUpdate(String meetingAffair, int idMeeting) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM meeting WHERE affair = ? and idMeeting<> ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, meetingAffair);
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
    public boolean existsDateAndTimeAvailableForUpdate(Date meetingDate, Time meetingTime, int idMeeting) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM meeting WHERE meetingDate = ? and meetingTime = ? and idMeeting <> ?";
        boolean available = true;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, meetingDate);
            preparedStatement.setTime(2, meetingTime);
            preparedStatement.setInt(3, idMeeting);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                available = false;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return available;
    }

}
