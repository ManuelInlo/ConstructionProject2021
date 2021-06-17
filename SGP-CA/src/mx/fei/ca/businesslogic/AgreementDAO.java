package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.Agreement;

/**
 * Clase para representar el Objeto de acceso a datos de un acuerdo
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class AgreementDAO implements IAgreementDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    /**
     * Constructor para la creación de un AgreementDAO, permitiendo también la obtención de la conexión a la base de datos
     */
    
    public AgreementDAO(){
        dataBaseConnection = new DataBaseConnection();
    }
    
    /**
     * Método que guarda un nuevo acuerdo de minuta en la base de datos
     * @param agreement Define el acuerdo de minuta a guardar en la base de datos
     * @param idMemorandum Define el identificador de la minuta de la cual se guardará el acuerdo
     * @return Booleano con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean savedAgreement(Agreement agreement, int idMemorandum) throws BusinessConnectionException{
        String sql = "INSERT INTO agreement (description, dateAgreement, responsible, idMemorandum) "
                     + "VALUES (?, ?, ?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, agreement.getDescription());
            preparedStatement.setString(2, agreement.getDateAgreement());
            preparedStatement.setString(3, agreement.getResponsible());
            preparedStatement.setInt(4, idMemorandum);
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
     * Método que recupera los acuerdos de una minuta
     * @param idMemorandum Define el identificador de la minuta de la cual se quieren recuperar los acuerdos
     * @return Un ArrayList con los acuerdos de minuta
     * @throws BusinessConnectionException 
     */
    
    @Override
    public ArrayList<Agreement> findAgreementsByIdMemorandum(int idMemorandum)throws BusinessConnectionException{
        ArrayList<Agreement> agreements = new ArrayList<>();
        String sql = "Select * from agreement where idMemorandum = ?";
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMemorandum);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int idAgreement = resultSet.getInt("idAgreement");
                String description = resultSet.getString("description");
                String dateAgreement = resultSet.getString("dateAgreement");
                String responsible = resultSet.getString("responsible");
                Agreement agreement = new Agreement(description, dateAgreement, responsible);
                agreement.setIdAgreement(idAgreement);
                agreements.add(agreement);
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return agreements;
    }
    
    /**
     * Método que elimina un acuerdo de minuta existente en la base de datos
     * @param idAgreement Define el identificador del acuerdo a eliminar
     * @return Booleano con el resultado de eliminación, devuelve true si eliminó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean deletedAgreementById(int idAgreement) throws BusinessConnectionException{
        String sql = "DELETE FROM agreement WHERE idAgreement = ?";
        boolean deleteResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idAgreement);
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
     * Método que modifica un acuerdo de reunión existente en la base de datos
     * @param agreement Define el acuerdo modificado
     * @param idAgreement Define el identificador del acuerdo a modificar
     * @param idMemorandum Define el identificador de la minuta que contiene el acuerdo a modificar
     * @return Booleano con el resultado de modificación, devuelve true si modificó, de lo contrario, devuelve false
     * @throws BusinessConnectionException 
     */
    
    @Override
    public boolean updatedAgreement(Agreement agreement, int idAgreement, int idMemorandum) throws BusinessConnectionException{
        String sql = "UPDATE agreement SET description = ?, dateAgreement = ?, responsible = ?, idMemorandum = ?"
                     + " WHERE idAgreement = ?";
        boolean updateResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, agreement.getDescription());
            preparedStatement.setString(2, agreement.getDateAgreement());
            preparedStatement.setString(3, agreement.getResponsible());
            preparedStatement.setInt(4, idMemorandum);
            preparedStatement.setInt(5, idAgreement);
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
     * Método que devuelve el identificador de un acuerdo de reunión de acuerdo a su descripción
     * @param description Define la descripción del acuerdo a encontrar
     * @return Entero con el identificador del acuerdo de minuta que coincide con la descripción. Devuelve 0 si no encotró coincidencias
     * @throws BusinessConnectionException 
     */

    @Override
    public int getIdAgreementByDescription(String description) throws BusinessConnectionException {
        int idAgreement = 0;
        String sql = "SELECT idAgreement FROM Agreement WHERE description = ?";
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, description);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                idAgreement = resultSet.getInt("idAgreement");
            }
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexión con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return idAgreement;
    }
}
