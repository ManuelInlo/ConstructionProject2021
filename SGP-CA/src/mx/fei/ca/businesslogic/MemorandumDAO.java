package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Memorandum;

/**
 * Clase para representar el Objeto de acceso a datos de una minuta
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class MemorandumDAO implements IMemorandumDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    /**
     * Constructor para la creación de un MemorandumDAO, permitiendo también la obtención de la conexión a la base de datos 
     */
    
    public MemorandumDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    /**
     * Método que guarda una nueva minuta en la base de datos y devuelve su identificador
     * @param memorandum Define la minuta a guardar en la base de datos
     * @param idMeeting Define el identificador de la reunión de la cual se guardará la minuta
     * @return Booleano con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public int saveAndReturnIdMemorandum(Memorandum memorandum, int idMeeting) throws BusinessConnectionException{
        String sql = "INSERT INTO memorandum (pending, note, idMeeting) VALUES (?, ?, ?)";
        int idMemorandumResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,memorandum.getPending());
            preparedStatement.setString(2, memorandum.getNote());
            preparedStatement.setInt(3, idMeeting);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                idMemorandumResult = resultSet.getInt(1);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos",ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return idMemorandumResult;
    }
    
    /**
     * Método que modifica una minuta existente en la base de datos
     * @param memorandum Define la minuta modificada
     * @param idMemorandum Define el identificador de la minuta a modificar
     * @return Booleano con el resultado de modificación, devuelve true si modificó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean updatedMemorandum(Memorandum memorandum, int idMemorandum) throws BusinessConnectionException{
        String sql = "UPDATE memorandum SET pending = ?, note = ? WHERE idMemorandum = ?";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memorandum.getPending());
            preparedStatement.setString(2, memorandum.getNote());
            preparedStatement.setInt(3, idMemorandum);
            preparedStatement.executeUpdate();
            updateResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos",ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }
    
    /**
     * Método que recupera la minuta de una reunión de acuerdo al identificador de la reunión
     * @param idMeeting Define el identificador de la reunión de la cual se busca su minuta
     * @return Objeto de tipo minuta de la reunión
     * @throws BusinessConnectionException 
     */
    
    @Override
    public Memorandum findMemorandumByIdMeeting(int idMeeting) throws BusinessConnectionException{
        String sql = "SELECT * FROM memorandum WHERE idMeeting = ?";
        Memorandum memorandum = null;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMeeting);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idMemorandum = resultSet.getInt("idMemorandum");
                String pending = resultSet.getString("pending");
                String note = resultSet.getString("note");
                memorandum = new Memorandum(pending, note);
                memorandum.setIdMemorandum(idMemorandum);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos",ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return memorandum;
    }
}
