package mx.fei.ca.businesslogic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.interfaces.LGACInterface;
import mx.fei.ca.dataaccess.dataBaseConnection;
import mx.fei.ca.domain.LGAC;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;

/**
 *
 * @author inigu
 */
public class LGACDAO implements LGACInterface{
    
    private final dataBaseConnection CON;
    private PreparedStatement ps;
    private ResultSet rs;
   
    
    public LGACDAO(){
        CON = dataBaseConnection.getInstancia();
    }

    @Override
    public int saveLGAC(LGAC lgac) throws BusinessConnectionException, BusinessDataException {
        String sql = "INSERT INTO lgac (clabe, nombre) VALUES (?, ?)";
        int saveResult = 0;
        try {
           ps = CON.Connect().prepareStatement(sql);
           ps.setString(1, lgac.getKeyCode());
           ps.setString(2, lgac.getName());
           saveResult = ps.executeUpdate();   
           ps.close();
        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, e.getMessage());
            throw new BusinessConnectionException("Failed connection with database sgpca", e);
        }finally{
            ps = null;
            CON.Disconnect();
        }
        return saveResult;
    }
/*
    @Override
    public ArrayList<LGAC> findLGACByClave(String clabe) throws BusinessConnectionException, BusinessDataException {
        ArrayList<LGAC> lgac = new ArrayList<>();
        String sql = "SELECT * FROM LGAC WHERE clabe LIKE ?";
        try {
           ps =CON.Connect().prepareStatement(sql);
           ps.setString(1, "%"+clabe+"%");
           rs = ps.executeQuery();
           while(rs.next()){
               
           }
        } catch (Exception e) {
        }
    }
*/   

    @Override
    public int updateLGAC(LGAC lgac, String keyCode, String name) throws BusinessConnectionException {
        String sql = "UPDATE lgac SET keyCode = ?, name = ?";
        int updateresult;
        try {
            ps = CON.Connect().prepareStatement(sql);
            ps.setString(1, lgac.getKeyCode());
            ps.setString(2, lgac.getName());
            updateresult = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            throw new BusinessConnectionException("Perdida de conexion con la base de datos", e);
        } finally{
            ps = null;
            CON.Disconnect();
        }
            return updateresult;
    }

    @Override
    public int deleteAgreementById(String keyCode) throws BusinessConnectionException, BusinessDataException {
        String sql = "DELETE FROM lgac WHERE clabe = ?";
        int deleteResult = 0;
        try{
            ps = CON.Connect().prepareStatement(sql);
            ps.setString(1, keyCode);
            deleteResult = ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            throw new BusinessConnectionException("Failed connection with databse", e);
        }finally{
            ps = null;
            CON.Disconnect();
        }
        return deleteResult;
    }
       
}
