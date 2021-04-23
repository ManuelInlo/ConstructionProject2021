
package mx.fei.ca.businesslogic;
import java.awt.Color;
import java.awt.Frame;

public class NewClass2 {
    public static void main(String args[]){           
        Frame ventana = new Frame();
        ventana.setBackground(Color.yellow);
        ventana.setBounds(200, 300, 400, 600);
        
        ventana.setVisible(true);
        ventana.setLocation(300, 300);
        
    } 
    //public void windowOpened (windowEvent e);            
}
