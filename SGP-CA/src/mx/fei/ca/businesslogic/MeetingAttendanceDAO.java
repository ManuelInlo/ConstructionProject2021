
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.MeetingAttendance;

public class MeetingAttendanceDAO implements IMeetingAttendanceDAO {
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public MeetingAttendanceDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    @Override
    public int saveMeetingAttendance(MeetingAttendance meetingAttendance, int idMeeting, String curp) throws BusinessConnectionException{
        String sql = "INSERT INTO meetingAttendance (curp, idMeeting, role) VALUES (?, ?, ?)";
        int saveResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,curp);
            preparedStatement.setInt(2,idMeeting);
            preparedStatement.setString(3, meetingAttendance.getRole());
            saveResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos",ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
}
