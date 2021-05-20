
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;

public class IntegrantDAO implements IIntegrantDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;    
 
    public IntegrantDAO(){
        dataBaseConnection = new DataBaseConnection();       
    }
    
    @Override
    public boolean saveIntegrant(Integrant integrant) throws BusinessConnectionException{
        String sql = "INSERT INTO Integrant (curp, role, nameIntegrant, studyDegree, studyDiscipline, prodepParticipation, typeTeaching, eisStudyDegree, institutionalMail, numberPhone, dateBirthday, statusIntegrant)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, integrant.getCurp());
            preparedStatement.setString(2, integrant.getRole()); 
            preparedStatement.setString(3, integrant.getNameIntegrant());
            preparedStatement.setString(4, integrant.getStudyDegree());
            preparedStatement.setString(5, integrant.getStudyDiscipline()); 
            preparedStatement.setString(6, integrant.getProdepParticipation());
            preparedStatement.setString(7, integrant.getTypeTeaching()); 
            preparedStatement.setString(8, integrant.getIesStudyDegree());
            preparedStatement.setString(9, integrant.getInstitutionalMail()); 
            preparedStatement.setString(10, integrant.getNumberPhone());
            preparedStatement.setDate(11, integrant.getDateBirthday()); 
            preparedStatement.setString(12, integrant.getStatusIntegrant());            
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
    public boolean updateIntegrant(Integrant integrant, String curp) throws BusinessConnectionException{
        String sql = "UPDATE Integrant SET curp = ?, role = ?, nameIntegrant = ?, studyDegree = ?, studyDiscipline = ?, prodepParticipation = ?, typeTeaching = ?, eisStudyDegree = ?, institutionalMail = ?, numberPhone = ?, dateBirthday = ?, statusIntegrant = ?"
                + " WHERE curp = ?";
        boolean updateResult = false;                         
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, integrant.getCurp());
            preparedStatement.setString(2, integrant.getRole()); 
            preparedStatement.setString(3, integrant.getNameIntegrant());
            preparedStatement.setString(4, integrant.getStudyDegree());
            preparedStatement.setString(5, integrant.getStudyDiscipline()); 
            preparedStatement.setString(6, integrant.getProdepParticipation());
            preparedStatement.setString(7, integrant.getTypeTeaching()); 
            preparedStatement.setString(8, integrant.getIesStudyDegree());
            preparedStatement.setString(9, integrant.getInstitutionalMail()); 
            preparedStatement.setString(10, integrant.getNumberPhone());
            preparedStatement.setDate(11, integrant.getDateBirthday()); 
            preparedStatement.setString(12, integrant.getStatusIntegrant());            
            preparedStatement.setString(13, curp);
            preparedStatement.executeUpdate();
            updateResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }      
        return updateResult;       
    }
            
    @Override
    public boolean deleteIntegrantByCurp(String curp) throws BusinessConnectionException{
        String sql = "DELETE FROM Integrant WHERE curp = ?";
        boolean deleteResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);
            preparedStatement.executeUpdate();
            deleteResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return deleteResult;
    }
}
