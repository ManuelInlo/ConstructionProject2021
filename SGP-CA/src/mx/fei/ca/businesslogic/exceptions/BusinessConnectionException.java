
package mx.fei.ca.businesslogic.exceptions;

/**
 * Clase para representar excepción personalizada 
 * @author David Alexander Mijangos Paredes
 * @version 17-06-2021
 */

public class BusinessConnectionException extends Exception{
    
    /**
     * Constructor para la creación de la excepción personalizada BusinessConnectionException
     * @param message Define el mensaje de la excepción
     * @param ex Define el tipo de excepción 
     */
    
    public BusinessConnectionException(String message, Throwable ex){
        super(message, ex);
    }
}
