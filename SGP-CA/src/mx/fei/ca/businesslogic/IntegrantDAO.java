
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
import java.util.ArrayList;
import java.util.Arrays;
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
import org.apache.commons.codec.binary.Base64;

/**
 * Clase para representar el Objeto de acceso a datos de un integrante
 * @author Gloria Mendoza González
 * @version 16-06-2021
 */

public class IntegrantDAO implements IIntegrantDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;    
    
    /**
     * Constructor para la creación de un IntegrantDAO permitiendo también la creación de la conexión a la base de datos
     */
 
    public IntegrantDAO(){
        dataBaseConnection = new DataBaseConnection();       
    }
    
    /**
     * Método que guarda un nuevo integrante en la base de datos
     * @param integrant Define el integrante a guardar
     * @return Booleano con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
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
            preparedStatement.setString(13, encryptPassword(integrant.getCurp()));
            preparedStatement.executeUpdate();
            saveResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
    
    /**
     * Método que modifica un integrante existente en la base de datos
     * @param integrant Define el integrante modificado
     * @param curp Define la curp del integrante a modificar
     * @return Booleano con el resultado de modificación, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
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
           
    /**
     * Método que elimina a un integrante existente en la base de datos
     * @param curp Define la curp del integrante a eliminar 
     * @return Booleano con el resultado de eliminación, devuelve true si eliminó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
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
    
    /**
     * Método que encripta la contraseña del integrante para guardar en la base de datos
     * @param password Define la contraseña a encriptar
     * @return String con el resultado de realizar la encriptación
     */

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
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | 
                 UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(IntegrantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encryptedPassword;
    }

    /**
     * Método que desencripta la contraseña del integrante 
     * @param encryptedPassword Define la contraseña encriptada a desencriptar
     * @return String con el resultado de realizar la desencriptación de la contraseña
     */
    
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
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | 
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(IntegrantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return decryptedPassword;
    }
    
    /**
     * Método que recupera de la base de datos un integrante de acuerdo a su correo institucional
     * @param institutionalMail Define el correo electrónico a buscar del integrante
     * @return Integrant que concida con el correo proporcionado, en el caso de no haber coincidencias devuelve null
     * @throws BusinessConnectionException 
     */

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
    
    /**
     * Método que modifica la contraseña de un integrante existente en la base de datos
     * @param password Define la nueva contraseña
     * @param curp Define la curp del integrante a modificar
     * @return Boolean con el resultado de modificación, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

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

    /**
     * Método que recupera de la base de datos a un integrante de acuerdo a su curp
     * @param curp Define la curp del integrante a recuperar
     * @return Integrant que concida con la curp proporcionada, en el caso de no haber coincidencias devuelve null
     * @throws BusinessConnectionException 
     */
    
    @Override
    public Integrant findIntegrantByCurp(String curp) throws BusinessConnectionException {
        String sql = "SELECT * FROM integrant WHERE curp = ?";
        Integrant integrant = null;
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
                integrant = new Integrant(curp, role, nameIntegrant, studyDegree, studyDiscipline, prodepParticipation, typeTeaching,
                                          eisStudyDegree, institutionalMail, numberPhone, dateBirthday, statusIntegrant);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return integrant;
    }
    
    /**
     * Método que recupera de la base de datos a un integrante de acuerdo a su nombre
     * @param name Define el nombre del integrante a recuperar
     * @return Integrant que concida con el nombre proporcionado, en el caso de no haber coincidencias devuelve null
     * @throws BusinessConnectionException 
     */
    
    @Override
    public Integrant findIntegrantByName(String name) throws BusinessConnectionException {
        String sql = "SELECT * FROM integrant WHERE name = ?";
        Integrant integrant = null;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String role = resultSet.getString("role");
                String curp = resultSet.getString("curp");
                String studyDegree = resultSet.getString("studyDegree");
                String studyDiscipline = resultSet.getString("studyDiscipline");
                String prodepParticipation = resultSet.getString("prodepParticipation");
                String typeTeaching = resultSet.getString("typeTeaching");
                String eisStudyDegree = resultSet.getString("eisStudyDegree");
                String institutionalMail = resultSet.getString("institutionalMail");
                String numberPhone = resultSet.getString("numberPhone");
                Date dateBirthday = resultSet.getDate("dateBirthday");
                String statusIntegrant = resultSet.getString("statusIntegrant");
                integrant = new Integrant(curp, role, name, studyDegree, studyDiscipline, prodepParticipation, typeTeaching,
                                          eisStudyDegree, institutionalMail, numberPhone, dateBirthday, statusIntegrant);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return integrant;
    }
    
    /**
     * Método que verifica si existe el nombre de un integrante en la base de datos
     * @param name Define el nombre a verificar existencia
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

    @Override
    public boolean existsIntegrantName(String name) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM integrant WHERE nameIntegrant = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
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
    
    /**
     * Método que verifica si existe la curp de un integrante en la base de datos
     * @param curp Define la curp a verificar existencia
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

    @Override
    public boolean existsIntegrantCurp(String curp) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM integrant WHERE curp = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);
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
    
    /**
     * Método que verifica si existe el correo electrónico de un integrante en la base de datos
     * @param institutionalMail Define el correo electrónico a verificar existencia
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

    @Override
    public boolean existsIntegrantEmail(String institutionalMail) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM integrant WHERE institutionalMail = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, institutionalMail);
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
    
    /**
     * Método que recupera de la base de datos los integrantes activos del CA
     * @return ArrayList con los integrantes activos del CA
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<Integrant> findAllIntegrants() throws BusinessConnectionException {
        String sql = "SELECT * FROM integrant WHERE statusIntegrant = 'Activo'";
        ArrayList<Integrant> integrants = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Integrant integrant;
                String curp = resultSet.getString("curp");
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
                integrant = new Integrant(curp, role, nameIntegrant, studyDegree, studyDiscipline, prodepParticipation, typeTeaching,
                                          eisStudyDegree, institutionalMail, numberPhone, dateBirthday, statusIntegrant);
                integrants.add(integrant);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return integrants;
    }  
    
    /**
     * Método que recupera de la base de datos los integrantes activos del CA de acuerdo a las iniciales de su nombre
     * @param InitialesNameIntegrant Define las iniciales del nombre del integrante a recuperar
     * @return ArrayList con los integrantes activos del CA
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<Integrant> findIntegrantsByInitialesOfTitle(String InitialesNameIntegrant) throws BusinessConnectionException {
        String sql = "SELECT * FROM integrant WHERE nameIntegrant LIKE CONCAT('%',?,'%') AND statusIntegrant = 'Activo'";
        ArrayList<Integrant> integrants = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, InitialesNameIntegrant);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Integrant integrant;
                String curp = resultSet.getString("curp");
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
                integrant = new Integrant(curp, role, nameIntegrant, studyDegree, studyDiscipline, prodepParticipation, typeTeaching,
                                          eisStudyDegree, institutionalMail, numberPhone, dateBirthday, statusIntegrant);
                integrants.add(integrant);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return integrants;
    }      
    
}
