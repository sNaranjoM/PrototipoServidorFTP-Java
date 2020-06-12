/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import GUI.VentanaInsertarJugador;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author Nelson
 */
public class MyServerP extends Thread {

    private int socketPortNumber;

    

    public MyServerP(int socketPortNumber) throws IOException, ClassNotFoundException, SQLException {

        super("Hilo Servidor");
    this.socketPortNumber = socketPortNumber;
        VentanaInsertarJugador ven = new VentanaInsertarJugador();

    } // constructor

    public void run() {// se encarga unicamente de aceptar nuevos clientes y darles un equipo
        try {

            ServerSocket serverSocket = new ServerSocket(this.socketPortNumber);

            do {

                System.out.println("Servidor ejecutando");
                
                Socket socket = serverSocket.accept();
                System.out.println("Cliente aceptado");
                Cliente cliente = new Cliente(socket);
                
              
            } while (true);

        } catch (IOException ex) {
            Logger.getLogger(MyServerP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyServerP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MyServerP.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    } // run

} // fin clase
