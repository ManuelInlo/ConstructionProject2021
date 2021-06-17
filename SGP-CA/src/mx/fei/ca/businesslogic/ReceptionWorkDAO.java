package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Collaborator;
import mx.fei.ca.domain.InvestigationProject;
import mx.fei.ca.domain.ReceptionWork;

/**
 * Clase para representar el Objeto de acceso a datos de un trabajo recepcional
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class ReceptionWorkDAO implements IReceptionWorkDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    /**
     * Constructor para la creación de un ReceptionWorkDAO, permitiendo también la obtención de la conexión a la base de datos 
     */
    
    public ReceptionWorkDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    /**
     * Método que guarda un nuevo trabajo recepcional en la base de datos
     * @param receptionWork Define el trabajo recepcional a guardar en la base de datos
     * @return Booleano con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean savedReceptionWork(ReceptionWork receptionWork) throws BusinessConnectionException{
        String sql = "INSERT INTO receptionwork (impactCA, titleReceptionWork, fileRoute, startDate, endDate,"
                     + "grade, workType, actualState, idProject, curp, idCollaborator) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, receptionWork.getImpactCA());
            preparedStatement.setString(2, receptionWork.getTitleReceptionWork());
            preparedStatement.setString(3, receptionWork.getFileRoute());
            preparedStatement.setDate(4, receptionWork.getStartDate());
            preparedStatement.setDate(5, receptionWork.getEndDate());
            preparedStatement.setString(6, receptionWork.getGrade());
            preparedStatement.setString(7, receptionWork.getWorkType());
            preparedStatement.setString(8, receptionWork.getActualState());
            preparedStatement.setInt(9, receptionWork.getInvestigationProject().getIdProject());
            preparedStatement.setString(10, receptionWork.getIntegrant().getCurp());
            preparedStatement.setInt(11, receptionWork.getCollaborator().getIdCollaborator());
            preparedStatement.executeUpdate();
            saveResult = true;
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
    
    /**
     * Método que modifica un trabajo recepcional específico existente en la base de datos
     * @param receptionWork Define el trabajo recepcional modificado
     * @param id Define el identificador del trabajo recepcional a modificar
     * @return Booleano con el resultado de modificación, devuelve true si modificó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

    @Override
    public boolean updatedReceptionWorkById(ReceptionWork receptionWork, int id) throws BusinessConnectionException {
        String sql = "UPDATE receptionWork SET impactCA = ?, titleReceptionWork = ?, fileRoute = ?, startDate = ?, endDate = ?, "
                    + "grade = ?, workType = ?, actualState = ?, idProject = ?, idCollaborator = ? WHERE id = ?";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, receptionWork.getImpactCA());
            preparedStatement.setString(2, receptionWork.getTitleReceptionWork());
            preparedStatement.setString(3, receptionWork.getFileRoute());
            preparedStatement.setDate(4, receptionWork.getStartDate());
            preparedStatement.setDate(5, receptionWork.getEndDate());
            preparedStatement.setString(6, receptionWork.getGrade());
            preparedStatement.setString(7, receptionWork.getWorkType());
            preparedStatement.setString(8, receptionWork.getActualState());
            preparedStatement.setInt(9, receptionWork.getInvestigationProject().getIdProject());
            preparedStatement.setInt(10, receptionWork.getCollaborator().getIdCollaborator());
            preparedStatement.setInt(11, id);
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
     * Método que recupera de la base de datos los trabajos recepcionales que impactan al CA
     * @return ArrayList con trabajos recepcionales que impactan al CA
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<ReceptionWork> findReceptionWorksByPositiveImpactCA() throws BusinessConnectionException {
        String sql = "SELECT * FROM receptionWork WHERE impactCA = 'SI'";
        ArrayList<ReceptionWork> receptionWorks = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ReceptionWork receptionWork;
                int id = resultSet.getInt("id");
                String impactCA = resultSet.getString("impactCA");
                String titleReceptionWork = resultSet.getString("titleReceptionWork");
                String fileRoute = resultSet.getString("fileRoute");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                String grade = resultSet.getString("grade");
                String workType = resultSet.getString("workType");
                String actualState = resultSet.getString("actualState");
                if(endDate != null){
                    receptionWork = new ReceptionWork(impactCA, titleReceptionWork, fileRoute, startDate, endDate, grade, workType, actualState);
                }else{
                    receptionWork = new ReceptionWork(impactCA, titleReceptionWork, fileRoute, startDate, grade, workType, actualState);
                }
                receptionWork.setId(id);
                receptionWorks.add(receptionWork);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return receptionWorks;
    }
    
    /**
     * Método que recupera de la base de datos los últimos dos trabajos recepcionales de un integrante
     * @param curp Define la curp del integrante del cual se quieren recuperar los trabajos recepcionales
     * @return ArrayList con máximo dos trabajos recepcionales
     * @throws BusinessConnectionException 
     */

    @Override
    public ArrayList<ReceptionWork> findLastTwoReceptionWorksByCurpIntegrant(String curp) throws BusinessConnectionException {
        String sql = "SELECT * FROM receptionWork WHERE curp = ? ORDER by id DESC LIMIT 2";
        ArrayList<ReceptionWork> receptionWorks = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ReceptionWork receptionWork;
                int id = resultSet.getInt("id");
                String impactCA = resultSet.getString("impactCA");
                String titleReceptionWork = resultSet.getString("titleReceptionWork");
                String fileRoute = resultSet.getString("fileRoute");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                String grade = resultSet.getString("grade");
                String workType = resultSet.getString("workType");
                String actualState = resultSet.getString("actualState");
                int idProject = resultSet.getInt("idProject");
                int idCollaborator = resultSet.getInt("idCollaborator");
                if(endDate != null){
                    receptionWork = new ReceptionWork(impactCA, titleReceptionWork, fileRoute, startDate, endDate, grade, workType, actualState);
                }else{
                    receptionWork = new ReceptionWork(impactCA, titleReceptionWork, fileRoute, startDate, grade, workType, actualState);
                }
                InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
                InvestigationProject investigationProject = investigationProjectDAO.findInvestigationProjectById(idProject); 
                CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
                Collaborator collaborator = collaboratorDAO.findCollaboratorByIdCollaborator(idCollaborator);
                receptionWork.setId(id);
                receptionWork.setInvestigationProject(investigationProject);
                receptionWork.setCollaborator(collaborator);
                receptionWorks.add(receptionWork);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return receptionWorks;
    }
    
    /**
     * Método que recupera trabajos recepcionales de acuerdo a las iniciales del título
     * @param InitialesTitleReceptionWork Define las iniciales del título del trabajo recepcional a recuperar
     * @param curp Define la curp del integrante del cual se quiere recuperar los trabajos recepcionales
     * @return ArrayList con trabajos recepcionales que coincidieron
     * @throws BusinessConnectionException 
     */

    @Override
    public ArrayList<ReceptionWork> findReceptionWorkByInitialesOfTitle(String InitialesTitleReceptionWork, String curp) throws BusinessConnectionException {
        String sql = "SELECT * FROM receptionWork WHERE titleReceptionWork LIKE CONCAT('%',?,'%') AND curp = ?";
        ArrayList<ReceptionWork> receptionWorks = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, InitialesTitleReceptionWork);
            preparedStatement.setString(2, curp);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ReceptionWork receptionWork;
                int id = resultSet.getInt("id");
                String impactCA = resultSet.getString("impactCA");
                String fileRoute = resultSet.getString("fileRoute");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                String grade = resultSet.getString("grade");
                String workType = resultSet.getString("workType");
                String actualState = resultSet.getString("actualState");
                int idProject = resultSet.getInt("idProject");
                int idCollaborator = resultSet.getInt("idCollaborator");
                String titleReceptionWork = resultSet.getString("titleReceptionWork");
                if(endDate != null){
                    receptionWork = new ReceptionWork(impactCA, titleReceptionWork, fileRoute, startDate, endDate, grade, workType, actualState);
                }else{
                    receptionWork = new ReceptionWork(impactCA, titleReceptionWork, fileRoute, startDate, grade, workType, actualState);
                }
                InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
                InvestigationProject investigationProject = investigationProjectDAO.findInvestigationProjectById(idProject); 
                CollaboratorDAO collaboratorDAO = new CollaboratorDAO();
                Collaborator collaborator = collaboratorDAO.findCollaboratorByIdCollaborator(idCollaborator);
                receptionWork.setId(id);
                receptionWork.setInvestigationProject(investigationProject);
                receptionWork.setCollaborator(collaborator);
                receptionWorks.add(receptionWork);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return receptionWorks;
    }
    
    /**
     * Método que verifica si existe el título de un trabajo recepcional en la base de datos
     * @param titleReceptionWork Define el título a verificar existencia
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

    @Override
    public boolean existsReceptionWorkTitle(String titleReceptionWork) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM receptionWork WHERE titleReceptionWork = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, titleReceptionWork);
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
     * Método que verifica si existe la ruta de archivo de un trabajo recepcional en la base de datos
     * @param fileRoute Define la ruta del archivo a verificar existencia
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

    @Override
    public boolean existsReceptionWorkFileRoute(String fileRoute) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM receptionWork WHERE fileRoute = ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fileRoute);
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
     * Método que verifica si existe el título de un trabajo recepcional en la base de datos para modificación
     * Se implementa el método porque verifica todos los títulos de los trabajos recepcionales excepto del que se está moficando para no causar conflictos
     * @param titleReceptionWork Define el título del trabajo recepcinal a verificar existencia
     * @param id Define el identificador del trabajo recepcional a modificar
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario devuelve false
     * @throws BusinessConnectionException 
     */

    @Override
    public boolean existsReceptionWorkTitleForUpdate(String titleReceptionWork, int id) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM receptionWork WHERE titleReceptionWork = ? AND id <> ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, titleReceptionWork);
            preparedStatement.setInt(2, id);
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
     * Método que verifica si existe la ruta de archivo de un trabajo recepcional en la base de datos
     * Se implementa el método porque verifica todas las rutas de archivo de los trabajos recepcionales excepto del que se está moficando para no causar conflictos
     * @param fileRoute Define la ruta del archivo a verificar existencia
     * @param id Define el identificador del trabajo recepcioanl a modificar
     * @return Booleano con el resultado de verificación, devuelve true si existe, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */

    @Override
    public boolean existsReceptionWorkFileRouteForUpdate(String fileRoute, int id) throws BusinessConnectionException {
        String sql = "SELECT 1 FROM receptionWork WHERE fileRoute = ? AND id <> ?";
        boolean exists = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fileRoute);
            preparedStatement.setInt(2, id);
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
