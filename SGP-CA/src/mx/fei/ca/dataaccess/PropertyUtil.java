package mx.fei.ca.dataaccess;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que representa la lectura de un archivo de propiedades
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class PropertyUtil{
    private static Properties property;
    
    /**
     * Método que permite leer un archivo 
     */
    
    private static void readFile(){
        try{
            property = new Properties();
            property.load(PropertyUtil.class.getResourceAsStream("dates.properties"));
        }catch(IOException ex){
           Logger.getLogger(PropertyUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método que devulve las propiedades de un archivo
     * @param name Define el nombre de la propiedad a recuperar
     * @return La propiedad consultada
     */
    
    public static String getProperties(String name){
        readFile();
        String propertyResult = property.getProperty(name);
        return propertyResult;
    }
}
