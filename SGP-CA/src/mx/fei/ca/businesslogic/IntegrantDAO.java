
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
    public int saveIntegrant(Integrant integrant) throws BusinessConnectionException{
        String sql = "INSERT INTO Integrant (curp, role, nameIntegrant, studyDegree, studyDiscipline, prodepParticipation, typeTeaching, eisStudyDegree, institutionalMail, numberPhone, dateBirthday)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int saveResult = 0;
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
            saveResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
    
    @Override
    public int updateIntegrant(Integrant integrant, String curp) throws BusinessConnectionException{
        String sql = "UPDATE Integrant SET curp = ?, role = ?, nameIntegrant = ?, studyDegree = ?, studyDiscipline = ?, prodepParticipation = ?, typeTeaching = ?, eisStudyDegree = ?, institutionalMail = ?, numberPhone = ?, dateBirthday = ?"
                + " WHERE curp = ?" ;
        int updateResult = 0;                         
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
            preparedStatement.setString(12, curp);
            updateResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }      
        return updateResult;       
    }
            
    @Override
    public int deleteIntegrantByCurp(String curp) throws BusinessConnectionException{
        String sql = "DELETE FROM Integrant WHERE curp = ?";
        int deleteResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);
            deleteResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return deleteResult;
    }
}
