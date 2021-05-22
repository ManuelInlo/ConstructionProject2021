
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
    public boolean savedMeetingAssistant(MeetingAssistant meetingAssistant, int idMeeting) throws BusinessConnectionException{
        String sql = "INSERT INTO meetingAssistant (curp, idMeeting, role) VALUES (?, ?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,meetingAssistant.getIntegrant().getCurp());
            preparedStatement.setInt(2,idMeeting);
            preparedStatement.setString(3, meetingAssistant.getRole());
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
    public boolean updatedRoleOfMeetingAssistant(MeetingAssistant meetingAssistant, int idMeeting) throws BusinessConnectionException{
        String sql = "UPDATE meetingAssistant SET role = ? WHERE curp = ? and idMeeting = ?";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, meetingAssistant.getRole());
            preparedStatement.setString(2, meetingAssistant.getIntegrant().getCurp());
            preparedStatement.setInt(3, idMeeting);
            preparedStatement.executeUpdate();
            updateResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }

    /*@Override
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
                MeetingAssistant meetingAssistant = new MeetingAssistant(integrant);
                meetingAssistant.setRole(role);
                meetingAssistants.add(meetingAssistant);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return meetingAssistants;
    }*/

    @Override
    public ArrayList<MeetingAssistant> findMeetingAssistantsByIdMeeting(int idMeeting) throws BusinessConnectionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
