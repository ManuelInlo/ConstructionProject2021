package negocio;

import datos.IntegrantDAO;
import entidades.Integrant;
import java.util.ArrayList;
import java.util.List;
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
        lista.addAll(DATOS.listar(texto));
        
        String[] titulos ={};
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
