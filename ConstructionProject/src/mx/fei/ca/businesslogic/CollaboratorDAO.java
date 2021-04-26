package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.businesslogic.interfaces.collaboratorInterface;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Collaborator;

/**
 *
 * @author inigu
 */
public class CollaboratorDAO implements collaboratorInterface {
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public CollaboratorDAO(){
        dataBaseConnection = new DataBaseConnection();
    }

    @Override
    public int saveCollaborator(Collaborator collaborator) throws BusinessConnectionException, BusinessDataException {
        String sql = "INSERT INTO collaborator (idCollaborator, nameCollaborator, position) VALUES (?, ?, ?)";
        int saveResult = 0;
        try {
            connection = dataBaseConnection.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, collaborator.getIdCollaborator());
            ps.setString(2, collaborator.getNameCollaborator());
            ps.setString(3, collaborator.getPosition());
            saveResult = ps.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessConnectionException("Failed connection with database sgpca", e);
        } finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }

    @Override
    public int updateCollaborator(Collaborator collaborator, int idCollaborator, String nameCollaborator, String position) throws BusinessConnectionException {
        String sql = "UPDATE collaborator set idCollaborator=?, nameCollaborator = ?, position = ?";
        int updateResult;
        try {
            connection = dataBaseConnection.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, collaborator.getIdCollaborator());
            ps.setString(2, collaborator.getNameCollaborator());
            ps.setString(3, collaborator.getPosition());
            updateResult = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new BusinessConnectionException("Failed connection with database", e);
        } finally{
            dataBaseConnection.closeConnection();
        }
        return updateResult;
    }

    @Override
    public int deleteAgreementById(String idCollaborator) throws BusinessConnectionException, BusinessDataException {
        String sql = "DELETE FROM collaborator where idCollaborator = ?";
        int deleteCollaborator = 0;
        try {
            connection = dataBaseConnection.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, idCollaborator);
            deleteCollaborator = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new BusinessConnectionException("Failed connection with database", e);
        }finally{
            ps = null;
            dataBaseConnection.closeConnection();
        }
        return deleteCollaborator;
    }
    
    
}
