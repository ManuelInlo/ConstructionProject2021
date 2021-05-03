
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.domain.MeetingAssistant;

public class MeetingAssistantDAO implements IMeetingAssistantDAO {
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public MeetingAssistantDAO(){
        dataBaseConnection = new DataBaseConnection();
    }

    @Override
    public int saveMeetingAssistant(MeetingAssistant meetingAssistant, int idMeeting) throws BusinessConnectionException{
        String sql = "INSERT INTO meetingAssistant (curp, idMeeting, role) VALUES (?, ?, ?)";
        int saveResult;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,meetingAssistant.getIntegrant().getCurp());
            preparedStatement.setInt(2,idMeeting);
            preparedStatement.setString(3, meetingAssistant.getRole());
            saveResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
    
    @Override 
    public int updateRoleOfMeetingAssistant(MeetingAssistant meetingAssistant, int idMeeting) throws BusinessConnectionException{
        String sql = "UPDATE meetingAssistant SET role = ? WHERE curp = ? and idMeeting = ?";
        int updateResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, meetingAssistant.getRole());
            preparedStatement.setString(2, meetingAssistant.getIntegrant().getCurp());
            preparedStatement.setInt(3, idMeeting);
            updateResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }

    @Override
    public ArrayList<MeetingAssistant> findMeetingAssistantsByIdMeeting(int idMeeting) throws BusinessConnectionException {
        String sql = "SELECT curp, role FROM meetingAssistant WHERE idMeeting = ?";
        ArrayList<MeetingAssistant> meetingAssistants = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMeeting);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String curp = resultSet.getString("curp");
                String role = resultSet.getString("role");
                Integrant integrant = new Integrant(curp); //Acá debe mandar a llamar método buscar integrante por curp 
                MeetingAssistant meetingAssistant = new MeetingAssistant(integrant, role);
                meetingAssistants.add(meetingAssistant);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return meetingAssistants;
    }

    @Override
    public boolean existsMeetingAssistantRole(String role, int idMeeting) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM meetingAssistant WHERE idMeeting = ? AND role = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMeeting);
            preparedStatement.setString(2, role);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                exists = true;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return exists;
    }
    
    @Override
    public boolean existsMeetingAssistantRoleForUpdate(String role, int idMeeting, String curp) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM meetingAssistant WHERE idMeeting = ? AND role = ? AND curp <> ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMeeting);
            preparedStatement.setString(2, role);
            preparedStatement.setString(3, curp);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                exists = true;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return exists;
    }
    
}
