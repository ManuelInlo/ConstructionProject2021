
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.dataaccess.DataBaseConnection;

/**
 * Clase para representar el Objeto de acceso a datos de una LGAC
 * @author Gloria Mendoza González
 * @version 17-06-2021
 */
public class LgacDAO implements ILgacDAO{
    private final DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet; 
    
    /**
     * Constructor para la creación de un LgacDAO permitiendo también la creación de la conexión a la base de datos
     */
    
    public LgacDAO(){
        dataBaseConnection = new DataBaseConnection(); 
    }
    
    /**
     * Método para guardar la relación entre el Integrante del CA y el LGAC al que pertenece
     * @param curp Define la curp del integrante a asociar
     * @param lgac Define el LGAC a asociar
     * @return Booleano con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException
     */
    
    @Override
    public boolean applyLgacByIntegrant (String curp, String lgac) throws BusinessConnectionException{
       String sql = "INSERT INTO participationlgac (curp, keyCode) VALUES (?, ?)";
        boolean saveResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);
            preparedStatement.setString(2, lgac);             
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
     * Método para eliminar la relación entre el Integrante del CA y el LGAC al que pertenece
     * @param curp Define la curp del integrante a buscar
     * @param lgac Define el LGAC a eliminar
     * @return Booleano con el resultado de guardado, devuelve true si guardó, de lo contrario, devuelve false
     * @throws BusinessConnectionException
     */
    
    @Override
    public boolean deletedLgacOfIntegrant (String curp, String lgac) throws BusinessConnectionException{
        String sql = "DELETE FROM participationlgac WHERE curp = ? and keyCode = ?";
        boolean deleteResult = false;
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);
            preparedStatement.setString(2, lgac);             
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
     * Método que recupera de la base de datos las LGAC al que pertenece un integrante
     * @param curp Define la curp del integrante a buscar
     * @return ArrayList con el nombre de la LGAC a la que pertenece un integrante
     * @throws BusinessConnectionException
     */
    
    @Override
    public ArrayList<String> findLgacOfIntegrant (String curp) throws BusinessConnectionException{
        String sql = "SELECT keyCode FROM participationlgac WHERE curp = ?";
        ArrayList<String> lgacs = new ArrayList<>();
        try{
            connection = dataBaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, curp);             
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String lgac = resultSet.getString("keyCode");
                lgacs.add(lgac);
            }            
        }catch(SQLException ex){
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return lgacs;
    }    

}
