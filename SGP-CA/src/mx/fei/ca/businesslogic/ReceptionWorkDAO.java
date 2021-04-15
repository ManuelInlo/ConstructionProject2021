
package mx.fei.ca.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.ca.dataaccess.DataBaseConnection;
import mx.fei.ca.domain.ReceptionWork;

public class ReceptionWorkDAO implements IReceptionWorkDAO{
    private DataBaseConnection dataBaseConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    
    @Override
    public int saveReceptionWork(ReceptionWork receptionWork){
        dataBaseConnection = new DataBaseConnection();
        try {
            connection = dataBaseConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionWorkDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        int saveResult = 0;
        String sql = "INSERT INTO evidence (impactCA, titleReceptionWork, workAddress, startDate, endDate, numStudents,"
                     + "grade, workType, actualState, idProject, curp, idCollaborator) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, receptionWork.getImpactCA());
            preparedStatement.setString(2, receptionWork.getTitleReceptionWork());
            preparedStatement.setString(3, receptionWork.getWorkAddress());
            preparedStatement.setDate(4, receptionWork.getStartDate());
            preparedStatement.setDate(5, receptionWork.getEndDate());
            preparedStatement.setInt(6, receptionWork.getNumStudents());
            preparedStatement.setString(7, receptionWork.getGrade());
            preparedStatement.setString(8, receptionWork.getWorkType());
            preparedStatement.setString(9, receptionWork.getActualState());
           // preparedStatement.setInt(10, idProject);
           // preparedStatement.setString(11, curp);
           // preparedStatement.setInt(12, idCollaborator);
           
            saveResult = preparedStatement.executeUpdate();
            
        }catch(SQLException ex){
            Logger.getLogger(ReceptionWorkDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            dataBaseConnection.closeConnection();
        }
        return saveResult;
    }
}
