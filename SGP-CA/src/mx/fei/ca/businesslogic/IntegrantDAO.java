
package mx.fei.ca.businesslogic;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Integrant;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import org.apache.commons.codec.binary.Base64;

public class IntegrantDAO implements IIntegrantDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;    
 
    public IntegrantDAO(){
        dataBaseConnection = new DataBaseConnection();       
    }
    
    @Override
    public boolean savedIntegrant(Integrant integrant) throws BusinessConnectionException{
        String sql = "INSERT INTO Integrant (curp, role, nameIntegrant, studyDegree, studyDiscipline, prodepParticipation, typeTeaching, eisStudyDegree, institutionalMail, numberPhone, dateBirthday, statusIntegrant, password)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            preparedStatement.setString(13, integrant.getCurp());
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
    public boolean updatedIntegrant(Integrant integrant, String curp) throws BusinessConnectionException{
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

    @Override
    public String encryptPassword(String password) {
        String encryptedPassword = null;
        String key = "Proyecto Construcción";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] keyPassword = null;
            keyPassword = messageDigest.digest(key.getBytes("utf-8"));
            byte[] bytesKey = Arrays.copyOf(keyPassword, 24);
            SecretKey secretKey = new SecretKeySpec(bytesKey, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] plainTextBytes = password.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            encryptedPassword = new String(base64Bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(IntegrantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encryptedPassword;
    }

    @Override
    public String decryptPassword(String encryptedPassword){
        String key = "Proyecto Construcción";
        String decryptedPassword = null;
        try {
            byte[] message = Base64.decodeBase64(encryptedPassword.getBytes("utf-8"));
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = messageDigest.digest(key.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] plainText = decipher.doFinal(message);
            decryptedPassword = new String(plainText, "UTF-8");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(IntegrantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return decryptedPassword;
    }

    @Override
    public Integrant getIntegrantByInstitutionalMail(String institutionalMail) throws BusinessConnectionException {
        String sql = "SELECT * FROM Integrant WHERE institutionalMail = ?";
        Integrant integrant = null;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, institutionalMail);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String curp = resultSet.getString("curp");
                String role = resultSet.getString("role");
                String nameIntegrant = resultSet.getString("nameIntegrant");
                String studyDegree = resultSet.getString("studyDegree");
                String studyDiscipline = resultSet.getString("studyDiscipline");
                String prodepParticipation = resultSet.getString("prodepParticipation");
                String typeTeaching = resultSet.getString("typeTeaching");
                String eisStudyDegree = resultSet.getString("eisStudyDegree");
                String numberPhone = resultSet.getString("numberPhone");
                Date dateBirthday = resultSet.getDate("dateBirthday");
                String password = resultSet.getString("password");
                String statusIntegrant = resultSet.getString("statusIntegrant");
                integrant = new Integrant(curp, role, nameIntegrant, studyDegree, studyDiscipline, prodepParticipation, typeTeaching,
                                          eisStudyDegree, institutionalMail, numberPhone, dateBirthday, statusIntegrant);
                integrant.setPassword(decryptPassword(password));
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos",ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return integrant;
    }

    @Override
    public boolean changedPasswordIntegrant(String password, String curp) throws BusinessConnectionException {
        String sql = "UPDATE Integrant SET password = ? WHERE curp = ?";
        boolean changedResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, encryptPassword(password));
            preparedStatement.setString(2, curp);
            preparedStatement.executeUpdate();
            changedResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos",ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return changedResult;
    }

    @Override
    public boolean findIntegrantByCurp(String curp) throws BusinessConnectionException {
        String sql = "SELECT * FROM integrant WHERE curp = ?";
        boolean findResult = false; 
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String role = resultSet.getString("role");
                String nameIntegrant = resultSet.getString("nameIntegrant");
                String studyDegree = resultSet.getString("studyDegree");
                String studyDiscipline = resultSet.getString("studyDiscipline");
                String prodepParticipation = resultSet.getString("prodepParticipation");
                String typeTeaching = resultSet.getString("typeTeaching");
                String eisStudyDegree = resultSet.getString("eisStudyDegree");
                String institutionalMail = resultSet.getString("institutionalMail");
                String numberPhone = resultSet.getString("numberPhone");
                Date dateBirthday = resultSet.getDate("dateBirthday");
                String statusIntegrant = resultSet.getString("statusIntegrant");
                Integrant integrant = new Integrant(curp, role, nameIntegrant, studyDegree, studyDiscipline, prodepParticipation, typeTeaching,
                                          eisStudyDegree, institutionalMail, numberPhone, dateBirthday, statusIntegrant);
                findResult = true;
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return findResult;
    }
}
