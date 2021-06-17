package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Collaborator;

/**
 * Clase para representar el Objeto de acceso a datos de un colaborador
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class CollaboratorDAO implements ICollaboratorDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    /**
     * Constructor para la creación de un CollaboratorDAO, permitiendo también la obtención de la conexión a la base de datos
     */
    
    public CollaboratorDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    /**
     * Método que guarda un nuevo colaborador en la base de datos
     * @param collaborator Define el colaborador a guardar en la base de datos
     * @return Booleano con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

    @Override
    public int saveCollaboratorAndReturnId(Collaborator collaborator) throws BusinessConnectionException{
        String sql = "INSERT INTO collaborator (nameCollaborator, position) VALUES (?, ?)";
        int idCollaboratorResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, collaborator.getName());
            preparedStatement.setString(2, collaborator.getPosition());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                idCollaboratorResult = resultSet.getInt(1);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return idCollaboratorResult;
    }
    
    /**
     * Método que modifica un colaborador existente en la base de datos
     * @param collaborator Define el colaborador modificado
     * @param idCollaborator Define el identificador del colaborador a modificar
     * @return Booleano con el resultado de modificación, devuelve true si modificó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean updatedCollaboratorByIdCollaborator(Collaborator collaborator, int idCollaborator) throws BusinessConnectionException{
        String sql = "UPDATE collaborator SET nameCollaborator = ?, position = ? WHERE idCollaborator = ?";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, collaborator.getName());
            preparedStatement.setString(2, collaborator.getPosition());
            preparedStatement.setInt(3, collaborator.getIdCollaborator());
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
     * Método que recupera un colaborador por medio de su identificador
     * @param idCollaborator Define el identificador del colaborador a recuperar
     * @return El objeto colaborador buscado
     * @throws BusinessConnectionException 
     */
    
    @Override
    public Collaborator findCollaboratorByIdCollaborator(int idCollaborator) throws BusinessConnectionException{
        String sql = "SELECT * FROM collaborator WHERE idCollaborator = ?";
        Collaborator collaborator = null;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idCollaborator);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String name = resultSet.getString("nameCollaborator");
                String position = resultSet.getString("position");
                collaborator = new Collaborator(name, position);
                collaborator.setIdCollaborator(idCollaborator);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return collaborator;
    }
    
    /**
     * Método que verifica si ya existe el nombre de un colaborador en la base de datos
     * @param nameCollaborator Define el nombre del colaborador a verificar su existencia
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

    @Override
    public boolean existsCollaboratorName(String nameCollaborator) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM collaborator WHERE nameCollaborator = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nameCollaborator);
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
     * Método que verifica si ya existe el nombre de un colaborador en la base de datos para modificación
     * Se ocupa este método porque verificará todos los colaboradores excepto el que se está modificando
     * @param nameCollaborator Define el nombre del colaborador a verificar existencia
     * @param idCollaborator Define el identificador del colaborador que no verificará
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

    @Override
    public boolean existsCollaboratorNameForUpdate(String nameCollaborator, int idCollaborator) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM collaborator WHERE nameCollaborator = ? AND idCollaborator <> ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nameCollaborator);
            preparedStatement.setInt(2, idCollaborator);
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
}
