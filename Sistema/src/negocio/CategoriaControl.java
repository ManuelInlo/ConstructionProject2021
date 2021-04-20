package negocio;

import datos.IntegrantDAO;
import entidades.Integrant;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author inigu
 */
public class CategoriaControl {
    private final IntegrantDAO DATOS;
    private Integrant obj;
    private DefaultTableModel modelotabla;
    
    public CategoriaControl(){
        this.DATOS = new IntegrantDAO();
        this.obj = new Integrant();
    }
    
    public DefaultTableModel listar(String texto){
        List<Integrant> lista =  new ArrayList();
    }
    
    public String insertar(String nombre, String descripcion){
        
    }
    
    public String actualizar(int id, String nombre, String nombreAnt, String descripcion){
        
    }
    
    public String desactivar(int id){
        
    }
    
    public String activat(int id){
        
    }
    
    public int total(){
        
    }
}
