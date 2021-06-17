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

/**
 * Clase para representar el Objeto de acceso a datos de un asistente de reunión
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class MeetingAssistantDAO implements IMeetingAssistantDAO {
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    /**
     * Constructor para la creación de un MeetingAssistantDAO, permitiendo también la obtención de la conexión a la base de datos 
     */
    
    public MeetingAssistantDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    /**
     * Método que guarda un nuevo asistente de reunión en la base de datos
     * @param meetingAssistant Define el asistente de reunión a guardar en la base de datos
     * @param idMeeting Define el identificador de la reunión de la cual se guardará el asistente de reunión
     * @return Booleano con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

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
    
    /**
     * Método que modifica el rol de un asistente de reunión existente en la base de datos
     * @param meetingAssistant Define el asistente de reunión con el rol modificado
     * @param idMeeting Define el identificador de la reunión que contiene el asistente de reunión a modificar su rol
     * @return Booleano con el resultado de modificación, devuelve true si modificó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
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
    
    /**
     * Método que recupera los asistentes de una reunión determinada
     * @param idMeeting Define el identificador de la reunión de la cual se quiere recuperar los asistentes
     * @return Un ArrayList con los asistentes de reunión
     * @throws BusinessConnectionException 
     */

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
                IntegrantDAO integrantDAO = new IntegrantDAO();
                Integrant integrant = integrantDAO.findIntegrantByCurp(curp);  
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
    }

}
