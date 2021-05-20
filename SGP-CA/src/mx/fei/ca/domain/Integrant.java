/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.fei.ca.domain;

/**
 *
 * @author david
 */
public class Integrant {
    private String curp;
    private String name;

    public Integrant(String curp) {
        this.curp = curp;
    }

    public String getCurp() {
        return curp;
    }

    public String getName() {
        return name;
    }
    
    public void setCurp(String curp) {
        this.curp = curp;
    }

    public void setName(String name) {
        this.name = name;
    }    
    
}
