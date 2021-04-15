package datos;

import datos.interfaces.CrudSimpleInterface;
import entidades.Integrant;
import java.util.List;
import database.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author inigu
 */
public class IntegrantDAO implements CrudSimpleInterface<Integrant>{
    
    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;
    
    public IntegrantDAO(){
        CON = Conexion.getInstancia();
    }
    

    @Override
    public List<Integrant> listar(String texto) {
        List<Integrant> registros = new ArrayList();
        try {
            ps = CON.conectar().prepareStatement("SELECT * FROM Integrant WHERE nameIntegrant LIKE ?");
            ps.setString(1, "%" + texto + "%");
            rs = ps.executeQuery();
            while(rs.next()){
               registros.add(new Integrant(rs.getString("OCHA940514RDTREOP2"), rs.getString("Miembro")
                       , rs.getString("Jorge Octavio Ocharan"), rs.getString("Doctorado"), rs.getString("Ingeniería de software")
                       , rs.getString("Activo"), rs.getString("PTC"), rs.getString("Universidad Veracruzana")
                       , rs.getString("jocharan@uv.mx"), rs.getString("2961150087"), rs.getDate(1994-04-12) ));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps = null;
            rs = null;
            CON.desconectar();
        }
        
        return registros;
    }

    @Override
    public boolean insertar(Integrant obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(Integrant obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean desactivar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean activar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int total() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existe(String texto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
