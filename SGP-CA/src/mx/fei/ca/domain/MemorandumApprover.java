package mx.fei.ca.domain;

/**
 * Clase para representar un aprobador de una minuta
 * Cada aprobador se determina de un integrante del CA
 * @author David Alexander Mijangos Paredes
 * @version 16-06-2021
 */

public class MemorandumApprover {
    private Integrant integrant;
    
    /**
     * Constructor para la creación de un aprobador de minuta
     * @param integrant Define el integrante asociado al aprobador de minuta
     */

    public MemorandumApprover(Integrant integrant) {
        this.integrant = integrant;
    }
    
    /**
     * 
     * @return El integrante asociado al aprobador de minuta
     */

    public Integrant getIntegrant() {
        return integrant;
    }
    
    /**
     * 
     * @param integrant El integrante a establecer al aprobador de minuta
     */

    public void setIntegrant(Integrant integrant) {
        this.integrant = integrant;
    }
    
    /**
     * Método que devuelve el nombre del integrante asociado al aprobador de minuta para su ocupación en ListView
     * @return El nombre del integrante asociado al aprobador de minuta
     */
    
    @Override
    public String toString(){
        return this.integrant.getNameIntegrant();
    }
    
}
