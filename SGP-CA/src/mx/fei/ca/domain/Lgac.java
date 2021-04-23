
package mx.fei.ca.domain;

public class Lgac {
    private String keyCode;
    private String name;
    
    public Lgac(String keyCode, String name){
        this.keyCode = keyCode;
        this.name = name;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public String getName() {
        return name;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
