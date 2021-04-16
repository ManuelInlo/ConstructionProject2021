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
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("INSERT INTO Integrant (curp, role, nameIntegrant, studyDegree, studyDiscipline, prodepParticipation, typeTeaching, eisStudydegree, institucionalMail, numberPhone, dateBirthday) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, obj.getCurp());
            ps.setString(2, obj.getRole());
            ps.setString(3, obj.getNameIntegrant());
            ps.setString(4, obj.getStudyDegree());
            ps.setString(5, obj.getStudyDicipline());
            ps.setString(6, obj.getProdepParticipation());
            ps.setString(7, obj.getTypeTeaching());
            ps.setString(8, obj.getEisStudyDegree());
            ps.setString(9, obj.getInstitucionalMail());
            ps.setString(10, obj.getNumberPhone());
            ps.setDate(11, obj.getDateBirthday());
            if (ps.executeUpdate()>0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps = null;
            CON.desconectar();
        }
        return resp;
    }

    @Override
    public boolean actualizar(Integrant obj) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("UPDATE Integrant SET curp=?, role=?, nameIntengrant=?, studyDegree=?, studyDiscipline=?, prodepParticipation=?, typeTeaching=?, eisStudyDegree=?, institucionalMail=?, numberPhone=?, dateBirthday=? WHERE curp=?");
            ps.setString(1, obj.getCurp());
            ps.setString(2, obj.getRole());
            ps.setString(3, obj.getNameIntegrant());
            ps.setString(4, obj.getStudyDegree());
            ps.setString(5, obj.getStudyDicipline());
            ps.setString(6, obj.getProdepParticipation());
            ps.setString(7, obj.getTypeTeaching());
            ps.setString(8, obj.getEisStudyDegree());
            ps.setString(9, obj.getInstitucionalMail());
            ps.setString(10, obj.getNumberPhone());
            ps.setDate(11, obj.getDateBirthday());
            ps.setString(12, obj.getCurp());
            if (ps.executeUpdate()>0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps = null;
            CON.desconectar();
        }
        return resp;
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
