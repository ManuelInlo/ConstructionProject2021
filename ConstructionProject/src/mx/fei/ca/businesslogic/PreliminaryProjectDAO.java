package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.businesslogic.interfaces.PreliminaryProjectInterface;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.PreliminaryProject;

/**
 *
 * @author inigu
 */
public class PreliminaryProjectDAO implements PreliminaryProjectInterface {
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public PreliminaryProjectDAO(){
        dataBaseConnection = new DataBaseConnection();
    }

    @Override
    public int savePreliminaryProject(PreliminaryProject preliminaryproject) throws BusinessConnectionException, BusinessDataException {
        String sql = "INSERT INTO preliminaryProject (idPreliminaryProject, idProject, idCollaborator, tittlePreliminaryProject\n" +
"            , preliminaryProjectCondition, duration, modality, preliminaryProjectDescription) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int saveResult = 0;
        try {
            connection = dataBaseConnection.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, preliminaryproject.getIdCollaborator());
            ps.setInt(2, preliminaryproject.getIdProject());
            ps.setInt(3, preliminaryproject.getIdCollaborator());
            ps.setString(4, preliminaryproject.getTittlePreliminaryProject());
            //ps.setDate(5, preliminaryproject.getStartDate());
            ps.setString(5, preliminaryproject.getPreliminaryProjectCondition());
            ps.setString(6, preliminaryproject.getDuration());
            ps.setString(7, preliminaryproject.getModality());
            ps.setString(8, preliminaryproject.getPreliminaryProjectDescription());
            saveResult = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new BusinessConnectionException("Failed connection with database sgpca", e);
        }finally{
            ps = null;
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }

    @Override
    public int updatePreliminaryProject(PreliminaryProject preliminaryproject, int idPreliminaryProject
            , int idProject, int idCollaborator, String tittlePreliminaryProject/*, Date startDate*/
            , String preliminaryProjectCondition, String duration, String modality
            , String preliminaryProjectDescription) throws BusinessConnectionException {
        String sql = "UPDATE preliminaryProject SET idPreliminaryProject = ?\n" +
"            , idProject = ?, idCollaborator = ?, tittlePreliminaryProject = ?\n" +
"            , preliminaryProjectCondition = ?, duration = ?, modality = ?\n" +
"            , preliminaryProjectDescription = ?";
        int updateResult;
        try {
            connection = dataBaseConnection.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, preliminaryproject.getIdCollaborator());
            ps.setInt(2, preliminaryproject.getIdProject());
            ps.setInt(3, preliminaryproject.getIdCollaborator());
            ps.setString(4, preliminaryproject.getTittlePreliminaryProject());
            //ps.setDate(5, preliminaryproject.getStartDate());
            ps.setString(5, preliminaryproject.getPreliminaryProjectCondition());
            ps.setString(6, preliminaryproject.getDuration());
            ps.setString(7, preliminaryproject.getModality());
            ps.setString(8, preliminaryproject.getPreliminaryProjectDescription());
            updateResult = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new BusinessConnectionException("Failed connection with database sgpca", e);
        }finally{
            ps = null;
            dataBaseConnection.closeConnection();
        }
         return updateResult;
    }

    @Override
    public int deletePreliminaryprojectById(int idPreliminaryproject) throws BusinessDataException, BusinessConnectionException {
        String sql = "DELETE FROM preliminaryProject WHERE idPreliminaryProject = ?";
        int deleteResult = 0;
        try {
            connection = dataBaseConnection.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idPreliminaryproject);
            deleteResult = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new BusinessConnectionException("Failed connection with databse", e);
        }finally{
            ps = null;
            dataBaseConnection.closeConnection();
        }       
        return deleteResult;
    }
}
