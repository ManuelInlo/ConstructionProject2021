
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Prerequisite;

/**
 * Clase para representar el Objeto de acceso a datos de un prerequisito
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class PrerequisiteDAO implements IPrerequisiteDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    /**
     * Constructor para la creación de un PrerequisiteDAO, permitiendo también la obtención de la conexión a la base de datos 
     */
    
    public PrerequisiteDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    /**
     * Método que guarda un nuevo prerequisito de reunión en la base de datos
     * @param prerequisite Define el prerequisito a guardar en la base de datos
     * @param idMeeting Define el identificador de la reunión de la cual se quiere guardar el prerequisito
     * @return Boolean con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean savedPrerequisite(Prerequisite prerequisite, int idMeeting) throws BusinessConnectionException{
        String sql = "INSERT INTO prerequisite (description, prerequisiteManager, idMeeting) VALUES (?, ?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, prerequisite.getDescription());
            preparedStatement.setString(2, prerequisite.getPrerequisiteManager());
            preparedStatement.setInt(3, idMeeting);
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
     * Método que modifica un prerequisito de reunión existente en la base de datos
     * @param prerequisite Define el prerequisito modificado
     * @param idPrerequisite Define el identificador del prerequisito a modificar
     * @param idMeeting Define el identificador de la reunión a la cual pertenece el prequisito a modificar
     * @return Booleano con el resultado de modificación, devuelve true si modificó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean updatedPrerequisite(Prerequisite prerequisite, int idPrerequisite, int idMeeting) throws BusinessConnectionException{
        String sql = "UPDATE prerequisite SET description = ?, prerequisiteManager = ?, idMeeting = ? "
                     + "WHERE idPrerequisite = ?";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, prerequisite.getDescription());
            preparedStatement.setString(2, prerequisite.getPrerequisiteManager());
            preparedStatement.setInt(3, idMeeting);
            preparedStatement.setInt(4, idPrerequisite);
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
     * Método que elimina un prerequisito específico de la base de datos
     * @param idPrerequisite Define el identificador del prerequisito a eliminar
     * @return Booleano con el resultado de eliminación, devuelve true si eliminó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean deletedPrerequisiteById(int idPrerequisite) throws BusinessConnectionException{
        String sql = "DELETE FROM prerequisite WHERE idPrerequisite = ?";
        boolean deleteResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idPrerequisite);
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
     * Método que recupera los prerequisitos de una reunión específica
     * @param idMeeting Define el identificador de la reunión de la cual se quiere recuperar los prerequisitos
     * @return ArrayList con los prerequisitos recuperados
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<Prerequisite> findPrerequisitesByIdMeeting(int idMeeting) throws BusinessConnectionException{
        ArrayList<Prerequisite> prerequisites = new ArrayList<>();
        String sql = "SELECT * FROM prerequisite WHERE idMeeting = ?";
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMeeting);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idPrerequisite = resultSet.getInt("idPrerequisite");
                String description = resultSet.getString("description");
                String prerequisiteManager = resultSet.getString("prerequisiteManager");
                Prerequisite prerequisite = new Prerequisite(description, prerequisiteManager);
                prerequisite.setIdPrerequisite(idPrerequisite);
                prerequisites.add(prerequisite);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return prerequisites;
    }
    
    /**
     * Método que devuelve el identificador de un prerequisito de acuerdo a su descripción e identificador de reunión
     * @param description Define la descripción del prerequisito a buscar
     * @param idMeeting Define el identificador de la reunión a la que pertence el prerequisito
     * @return Entero con el identificador del prerequisito 
     * @throws BusinessConnectionException 
     */

    @Override
    public int getIdPrerequisiteByDescription(String description, int idMeeting) throws BusinessConnectionException {
        int idPrerequisite = 0;
        String sql = "SELECT idPrerequisite FROM Prerequisite WHERE description = ? AND idMeeting = ?";
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, description);
            preparedStatement.setInt(2, idMeeting);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                idPrerequisite = resultSet.getInt("idPrerequisite");
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return idPrerequisite;
    }
}

