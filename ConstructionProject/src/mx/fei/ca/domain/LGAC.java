package mx.fei.ca.domain;

/**
 *
 * @author inigu
 */
public class LGAC {
    private String keyCode;
    private String name;

    public LGAC() {
        //Default constructor
    }

    public LGAC(String keyCode, String name) {
        this.keyCode = keyCode;
        this.name = name;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LGAC{" + "keyCode=" + keyCode + ", name=" + name + '}';
    }
    
    
    
}
