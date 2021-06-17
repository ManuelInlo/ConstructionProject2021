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

/**
 * Clase para representar el Objeto de acceso a datos de una reunión
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class MeetingDAO implements IMeetingDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    /**
     * Constructor para la creación de un MeetingDAO, permitiendo también la obtención de la conexión a la base de datos
     */
    
    public MeetingDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    /**
     * Método que guarda una nueva reunión en la base de datos y retorna su identificador
     * @param meeting Define la reunión a guardar en la base de datos
     * @param curp Define la curp del integrante responsable de la reunión
     * @return Entero con el identificador generado de la reunión guardada en la base de datos. Devuelve 0 si falló en el guardado
     * @throws BusinessConnectionException 
     */
    
    @Override
    public int saveAndReturnIdNewMeeting(Meeting meeting, String curp) throws BusinessConnectionException{
        String sql = "INSERT INTO meeting (meetingDate, meetingTime, meetingPlace, affair, projectName, state, curp)"
                     + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        int idMeetingResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, meeting.getMeetingDate());
            preparedStatement.setTime(2, meeting.getMeetingTime());
            preparedStatement.setString(3, meeting.getMeetingPlace());
            preparedStatement.setString(4, meeting.getAffair());
            preparedStatement.setString(5, meeting.getProjectName());
            preparedStatement.setString(6, meeting.getState());
            preparedStatement.setString(7, curp);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                idMeetingResult = resultSet.getInt(1);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return idMeetingResult;
    }
    
    /**
     * Método que devuelve las útlimas 5 reuniones almacenadas en la base de datos
     * @return Un ArrayList con 5 reuniones
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<Meeting> findLastFiveMeetings() throws BusinessConnectionException {
        ArrayList<Meeting> meetings = new ArrayList<>();
        String sql = "Select * from meeting order by idMeeting DESC LIMIT 5;";
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
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
                String projectName = resultSet.getString("projectName");
                String state = resultSet.getString("state");
                Meeting meeting = new Meeting(meetingDate, meetingTime, meetingPlace, affair, projectName, state);
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
    
    /**
     * Método que devuelve reuniones que coincidan con un nombre de proyecto
     * @param projectName Define el nombre de proyecto de la o las reuniones a buscar
     * @return Un ArrayList con la o las reuniones que coincidieron
     * @throws BusinessConnectionException 
     */
    
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
                String state = resultSet.getString("state");
                Meeting meeting = new Meeting(meetingDate, meetingTime, meetingPlace, affair, projectName,state);
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
    
    /**
     * Método que devuelve reuniones que coincidan con un nombre de proyecto y una fecha determinada
     * @param projectName Define el nombre del proyecto de la o las reuniones a buscar
     * @param meetingDate Defina la fecha de la o las reuniones a buscar
     * @return Un ArrayList con la o las reuniones que coincidieron 
     * @throws BusinessConnectionException 
     */
    
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
                String state = resultSet.getString("state");
                Meeting meeting = new Meeting(meetingDate, meetingTime, meetingPlace, affair, projectName, state);
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
    
    /**
     * Método que modifica una reunión existente en la base de datos
     * @param meeting Define la reunión modificada
     * @param idMeeting Define el identificador de la reunión a modificar
     * @return Booleano con el resultado de modificación, devuelve true si modificó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
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
    
    /**
     * Método que modifica el estado de una reunión existente en la base de datos
     * @param state Define el estado modificado de la reunión
     * @param idMeeting Define el identificador de la reunión a la cual se modificará el estado
     * @return Booleano con el resultado de modificación, devuelve true si modificó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override 
    public boolean updatedStateOfMeeting(String state, int idMeeting)throws BusinessConnectionException{
        String sql = "UPDATE meeting SET state = ? WHERE idMeeting = ?";
        boolean updateStateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, state);
            preparedStatement.setInt(2, idMeeting);
            preparedStatement.executeUpdate();
            updateStateResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return updateStateResult;
    }
    
    /**
     * Método que devuelve la curp del integrante responsable de la reunión 
     * @param idMeeting Define el identificador de la reunión de la cual se quiere recuperar la curp del responsable
     * @return String con la curp del integrante responsable de la reunión
     * @throws BusinessConnectionException 
     */

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
    
    /**
     * Método que verifica la existencia de un asunto de reunión en la base de datos
     * @param meetingAffair Define el asunto de reunión a verificar existencia
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

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
    
    /**
     * Método que verifica en la base de datos fecha y hora disponible para una nueva reunión
     * @param meetingDate Define la fecha de la reunión a verificar disponibilidad
     * @param meetingTime Define la hora de la reunión a verificar disponibilidad
     * @return Booleano con el resultado de verificación, devuelve true si están disponibles, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

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
    
    /**
     * Método que verifica la existencia de un asunto de reunión en la base de datos para modificación
     * Se implementa el método porque verifica con todas las reuniones excepto la reunión que se está modificando
     * @param meetingAffair Define el asunto de reunión a verificar existencia
     * @param idMeeting Define el identificador de la reunión que se está modificando
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

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
    
    /**
     * Método que verifica en la base de datos fecha y hora disponible para modificación de reunión
     * Se implementa el método porque verifica con todas las reuniones excepto la reunión que se está modificando
     * @param meetingDate Define la fecha de la reunión a verificar disponibilidad
     * @param meetingTime Define la hora de la reunión a verificar disponibilidad
     * @param idMeeting Define el identificador de la reunión que se está modificando
     * @return Booleano con el resultado de la verificación, devuelve true si están disponibles, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

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
