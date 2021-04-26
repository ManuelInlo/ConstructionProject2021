package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.businesslogic.interfaces.Integrantinterface;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Integrant;

/**
 *
 * @author inigu
 */

public class IntegrantDAO implements Integrantinterface {
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public IntegrantDAO(){
        dataBaseConnection = new DataBaseConnection();
    }

    @Override
    public int saveIntegrant(Integrant integrant) throws BusinessConnectionException, BusinessDataException {
        String sql = "INSERT INTO integrant (curp, role, \n" +
"            nameIntegrant, studyDegree, \n" +
"            studyDicipline,prodepParticipation, \n" +
"            typeTeaching, eisStudyDegree, institucionalMail, \n" +
"            numberPhone, Date dateBirthday) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int saveResult = 0;
        try {
            connection = dataBaseConnection.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, integrant.getCurp());
            ps.setString(2, integrant.getRole());
            ps.setString(3, integrant.getNameIntegrant());
            ps.setString(4, integrant.getStudyDegree());
            ps.setString(5, integrant.getStudyDicipline());
            ps.setString(6, integrant.getProdepParticipation());
            ps.setString(7, integrant.getTypeTeaching());
            ps.setString(8, integrant.getEisStudyDegree());
            ps.setString(9, integrant.getInstitucionalMail());
            ps.setString(11, integrant.getNumberPhone());
            ps.setDate(10, integrant.getDateBirthday());
            saveResult = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new BusinessConnectionException("Failed connection with database sgpca", e);
        } finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;       
    }

    @Override
    public int updateIntegrant(Integrant integrant, String curp, String role, String nameIntegrant, String studyDegree, String studyDicipline, String prodepParticipation, String typeTeaching, String eisStudyDegree, String institucionalMail, String numberPhone, Date dateBirthday) throws BusinessConnectionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteIntegrantByCurp(String curp) throws BusinessConnectionException, BusinessDataException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
