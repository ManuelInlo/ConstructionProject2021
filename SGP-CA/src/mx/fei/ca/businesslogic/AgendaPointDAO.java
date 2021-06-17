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

/**
 * Clase para representar el Objeto de acceso a datos de un punto de agenda 
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class AgendaPointDAO implements IAgendaPointDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    /**
     * Constructor para la creación de un AgendaPointDAO permitiendo también la creación de la conexión a la base de datos
     */
    
    public AgendaPointDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    /**
     * Método que guarda un punto de agenda de una reunión en la base de datos
     * @param agendaPoint Define el punto de agenda a guardar
     * @param idMeeting Define el identificador de la reunión de la cual se guardará el punto de agenda
     * @return Booleano con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
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
    
    /**
     * Método que modifica un punto de agenda de una reunión existente en la base de datos
     * @param agendaPoint Define el punto de agenda modificado
     * @param idAgendaPoint Define el identificador del punto de agenda a modificar
     * @param idMeeting Define el identificador de la reunión que contiene el punto de agenda a modificar
     * @return Booleano con el resultado de modificación, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
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
    
    /**
     * Método para eliminar un punto de agenda de una reunión existente en la base de datos
     * @param idAgendaPoint Define el identificador del punto de agenda a eliminar
     * @return booleano con el resultado de la eliminación, devuelve true si eliminó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
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
    
    /**
     * Método que recupera los puntos de agenda de una reunión
     * @param idMeeting Define el identificador de la reunión de la cual se quiere recuperar los puntos de agenda
     * @return Un ArrayList con los puntos de agenda de la reunión
     * @throws BusinessConnectionException 
     */
    
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
    
    /**
     * Método que devuelve el identificador de un punto de agenda de acuerdo a su tema
     * @param topic Define el tema del punto de agenda a buscar
     * @return Entero con el identificador del punto de agenda de una reunión que coincide con el tema. Devuelve 0 si no encotró coincidencias
     * @throws BusinessConnectionException 
     */
    
    @Override
    public int getIdAgendaPointByTopic(String topic) throws BusinessConnectionException{
        int idAgendaPoint = 0;
        String sql = "SELECT idAgendaPoint FROM agendaPoint WHERE topic = ?";
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, topic);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                idAgendaPoint = resultSet.getInt("idAgendaPoint");
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return idAgendaPoint;
    }
}
