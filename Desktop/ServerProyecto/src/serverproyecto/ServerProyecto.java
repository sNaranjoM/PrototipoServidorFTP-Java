/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverproyecto;

import Domain.MyServerP;
import Utility.MyUtility;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Steven Naranjo M
 */
public class ServerProyecto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        try {
            
            
            
            MyServerP server;
            server = new MyServerP(MyUtility.SOCKETPORTNUMBER);
            server.start();
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(ServerProyecto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }//main
    
}//class
