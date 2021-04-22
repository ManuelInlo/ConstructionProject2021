package mx.fei.ca.businesslogic.exceptions;

/**
 *
 * @author inigu
 */
public class BusinessDataException extends Exception{
    public BusinessDataException(String message, Throwable ex){
        super(message, ex);
    }
}
