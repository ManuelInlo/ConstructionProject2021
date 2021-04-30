
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Collaborator;


public class CollaboratorDAO implements ICollaboratorDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public CollaboratorDAO(){
        dataBaseConnection = new DataBaseConnection();
    }

    @Override
    public int saveCollaborator(Collaborator collaborator) throws BusinessConnectionException{
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
    
    @Override
    public int updateCollaboratorByIdCollaborator(Collaborator collaborator, int idCollaborator) throws BusinessConnectionException{
        String sql = "UPDATE collaborator SET nameCollaborator = ?, position = ?";
        int updateResult = 0;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, collaborator.getName());
            preparedStatement.setString(2, collaborator.getPosition());
            updateResult = preparedStatement.executeUpdate();
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }
    
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

    @Override
    public boolean validateExistenceOfCollaboratorName(String nameCollaborator) throws BusinessConnectionException {
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
}
