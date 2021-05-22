package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.dataaccess.DataBaseConnection;


public class InvestigationProject implements IInvestigationProject {
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public InvestigationProject(){
        dataBaseConnection = new DataBaseConnection();
    }

    @Override
    public boolean savedInvestigationProject(mx.fei.ca.domain.InvestigationProject investigationproject, int keycode) throws BusinessConnectionException, BusinessDataException {
        String sql = "INSERT INTO investigationProject (idProject, keyCode, endDate, startDate, tittleProject, description)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        boolean saveResult = false;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, investigationProject.ge);
        } catch (Exception e) {
        }
       
    }
    
    /*public boolean savedAgendaPoint(AgendaPoint agendaPoint, int idMeeting) throws BusinessConnectionException{
        String sql = "INSERT INTO agendaPoint (startTime, endTime, number, topic, leader, idMeeting) VALUES "
                     + "(?, ?, ?, ?, ?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTime(1, agendaPoint.getStartTime());
            preparedStatement.setTime(2, agendaPoint.getEndTime());
            preparedStatement.setInt(3, agendaPoint.getNumber());
            preparedStatement.setString(4, agendaPoint.getTopic());
            preparedStatement.setString(5, agendaPoint.getLeader());
            preparedStatement.setInt(6, idMeeting);
            preparedStatement.executeUpdate();
            saveResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }*/

    @Override
    public boolean updateInvestigationproject(mx.fei.ca.domain.InvestigationProject investigationproject, int keycode) throws BusinessConnectionException, BusinessDataException {
        return false;
        }

    @Override
    public mx.fei.ca.domain.InvestigationProject findInvestigationProjectById(int idInvestigationproject) throws BusinessConnectionException {
        //
    }
    
}
