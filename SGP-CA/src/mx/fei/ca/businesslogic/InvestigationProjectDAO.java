package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.InvestigationProject;

/**
 * Clase para representar el Objeto de acceso a datos de una proyecto de investigación
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class InvestigationProjectDAO implements IInvestigationProjectDAO {
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    /**
     * Constructor para la creación de un InvestigationProjectDAO, permitiendo también la obtención de la conexión a la base de datos 
     */
    
    public InvestigationProjectDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    /**
     * Método que recupera un proyecto de investigación de la base de datos de acuerdo a su identificador
     * @param idProject Define el identificador del proyecto de investigación a recuperar
     * @return Objeto de tipo proyecto de investigación
     * @throws BusinessConnectionException 
     */

    @Override
    public InvestigationProject findInvestigationProjectById(int idProject) throws BusinessConnectionException {
        String sql = "SELECT * FROM investigationProject WHERE idProject = ?";
        InvestigationProject investigationProject = null;
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setInt(1, idProject);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String keyCode = resultSet.getString("keycode");
                Date startDate = resultSet.getDate("startDate");
                Date endDate  = resultSet.getDate("endDate");
                String tittleProject = resultSet.getString("tittleproject");
                String description = resultSet.getString("description");
                investigationProject = new InvestigationProject(idProject, keyCode, startDate, endDate, tittleProject, description);
            }
        }catch (SQLException e) {
            throw new BusinessConnectionException("Perdida de conexión con la base de datos",e);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return investigationProject;
    }
    
    /**
     * Método que recupera todos los proyectos de investigación almacenados en la base de datos
     * @return ArrayList con los proyectos de investigación recuperados
     * @throws BusinessConnectionException 
     */

    @Override
    public ArrayList<InvestigationProject> findAllInvestigationProjects() throws BusinessConnectionException {
        String sql = "SELECT * FROM investigationProject";
        ArrayList<InvestigationProject> investigationProjects = new ArrayList<>();
        try {
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                InvestigationProject investigationProject;
                int idProject = resultSet.getInt("idProject");
                String keyCode = resultSet.getString("keycode");
                Date startDate = resultSet.getDate("startDate");
                Date endDate  = resultSet.getDate("endDate");
                String tittleProject = resultSet.getString("tittleproject");
                String description = resultSet.getString("description");
                investigationProject = new InvestigationProject(idProject, keyCode, startDate, endDate, tittleProject, description);
                investigationProjects.add(investigationProject);
            }
        } catch (SQLException e) {
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", e);
        } finally{
            dataBaseConnection.closeConnection();
        }
        return investigationProjects;
    }

}
