package mx.fei.ca.dataaccess;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author inigu
 */
public class PropertyUtil {
    private static Properties property;
    
    private static void readFile(){
        try{
            property = new Properties();
            property.load(PropertyUtil.class.getResourceAsStream("dates.properties"));
        }catch(IOException ex){
           Logger.getLogger(PropertyUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getProperties(String name){
        readFile();
        return property.getProperty(name);
    }
    
}
