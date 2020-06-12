/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;


import Utility.MyUtility;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author laboratorios
 */
public class Cliente extends Thread {

    private Socket socket;
    private BufferedReader receive;
    private ArrayList<PrintStream> sends;

    private PrintStream mySend;
    private int sumaDados;
    private String pos;
    Connection con;

    String urlCarpeta;

    //transferencia de archivos
    ServerSocket server;
    Socket connection;
    
    BufferedInputStream bis;
    BufferedOutputStream bos;
    DataOutputStream output;
    DataInputStream dis;
    byte[] receivedData;

    byte[] byteArray;
    
    int in;
    String file;

    public Cliente(Socket socket) throws IOException, ClassNotFoundException, SQLException {

 
        this.sumaDados = 0;

        this.socket = socket;
        receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));//flujo entrada
        this.mySend = new PrintStream(socket.getOutputStream());
//        System.out.println("se declara mi send");

        this.sends = new ArrayList<PrintStream>();

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://163.178.107.10:1433;databaseName=b55008_proyectoRedes;user=laboratorios;password=Saucr.118;";
//        String connectionUrl = "jdbc:sqlserver://163.178.173.148;databaseName=b55008_proyectoRedes;user=estudiantesrp;password=estudiantesrp;";

        con = DriverManager.getConnection(connectionUrl);
        System.out.println("Nos conectamos");
//        mySend.println("hola mundo");

        this.start();

    }//constructor

    //setters and getters
    public Socket getSocket() {
        return socket;
    }//getSocket

    public void setSocket(Socket socket) {
        this.socket = socket;
    }//setSocket

    public void addSend(PrintStream send) {

        this.sends.add(send);

    }//addReceive

    public void anularArray() {

        this.sends = null;

    }//anularArray


    public PrintStream getMySend() {

//        System.out.println(this.usuario.getNombre() + " en el get de my send ");
        return mySend;
    }

    public void setMySend(PrintStream mySend) {
        this.mySend = mySend;
    }

    @Override
    public void run() {

        while (true) {
            try {
//                sleep(250);

//                System.out.println("Inicio");

                String entrada = receive.readLine();

//                System.out.println("llegp mensaje");
                System.out.println("entrada: " + entrada);
                if (entrada != null) {

                    Element nuevo = stringToXML(entrada);

                    switch (nuevo.getName()) {

                        case "envio":
                            extraeArchivo(nuevo);
                            break;
                        case "registrarUsuario":
                            registrarUsuario(nuevo);
                            break;
                        case "ingresarUsuario":
                            ingresarUsuario(nuevo);
                            break;
                        case "listarArchivos":
                            listarArchivos(nuevo);
                            break;
                        case "peticionArchivo":
                            enviarArchivo(nuevo);
                            break;

                    }
                } else {
                    sleep(500);
                }

            } catch (IOException ex) {
//                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JDOMException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            //connection = server.accept();
//            System.out.println("Fin");

        }//while

    }//run

    public void enviarArchivo(Element element) throws FileNotFoundException, IOException, InterruptedException {

        String nombre = element.getChild("nombre").getValue();
        System.out.println("_______________________________________________________________");
        System.out.println("nombre: " + nombre);

        File miDir = new File(".");
        String ruta = miDir.getCanonicalPath() + "\\" + "Archivos" + "\\" + nombre;
        System.out.println("Ruta para extraer el archivo: " + ruta);



        final File localFile = new File(ruta);
//            Socket client = new Socket("localhost", 5000);
        bis = new BufferedInputStream(new FileInputStream(localFile));
        bos = new BufferedOutputStream(this.socket.getOutputStream());
        //Enviamos el nombre del fichero
        DataOutputStream dos = new DataOutputStream(this.socket.getOutputStream());
        dos.writeUTF(localFile.getName());
        //Enviamos el fichero
        byteArray = new byte[8192];

        while ((in = bis.read(byteArray)) != -1) {
            System.out.println("Cargando... \n in: " + in);
            bos.write(byteArray, 0, in);
        }

        bis.close();
        bos.close();
        sleep(2000);
        
        
//        this.mySend.close();
//        this.receive.close();
////        this.socket.shutdownInput();
////        this.socket.shutdownOutput();
//        this.socket.close();

//        this.socket = new Socket(this.address, MyUtility.SOCKETPORTNUMBER);
//        this.send = new PrintStream(socket.getOutputStream());
//        this.receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        
    }//enviarArchivo

    public void listarArchivos(Element element) throws IOException {

        String nombre = element.getChild("nombre").getValue();

        File miDir = new File(".");
        String ruta = miDir.getCanonicalPath() + "\\Archivos\\" + nombre;
        System.out.println("ruta: " + ruta);

        File carpeta = new File(ruta);

        String[] listado = carpeta.list();

        Element eSalida = new Element("listaArchivos");

//        Element eGana = new Element("cantidad");
//        eGana.addContent(String.valueOf(listado.length));
//        eSalida.addContent(eGana);
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");

        } else {

            for (int i = 0; i < listado.length; i++) {
                System.out.println(listado[i]);

                Element ePierde = new Element("paquete");
                ePierde.addContent(listado[i]);
                eSalida.addContent(ePierde);

//                eSalida.setAttribute(listado[i]);
            }
        }

        this.mySend.println(this.xmlToString(eSalida));

    }//listarArchivos

    public void registrarUsuario(Element element) throws SQLException, IOException {

        String nombre = element.getChild("nombre").getValue();
        String contra = element.getChild("contrasena").getValue();

        System.out.println("nombre: " + nombre);
        System.out.println("contra: " + contra);

        Statement st = con.createStatement();
        //st.execute("insert  usuarios values ('"+ nombre+ "','" + contra + "','urlCarpeta');");
        ResultSet rs = st.executeQuery("select nombre, constrasena from usuarios where nombre= '" + nombre + "'");

        String nombreBS = null;
        String contraBS = null;
        while (rs.next()) {

            nombreBS = rs.getString(1);
            contraBS = rs.getString(2);
            System.out.println(nombre + "  " + contra);

        }//while

        if (nombreBS != null) {
            System.out.println("Si  hay usuarios registrados con ese nombre y tiene la misma contra");
//            File directorio = new File("Carpeta prueba");

            Element eSalida = new Element("Denegado");
            this.mySend.println(this.xmlToString(eSalida));

        } else {

            System.out.println("No hay usuarios registrados con ese nombre");

            File miDir = new File(".");

            String ruta = miDir.getCanonicalPath() + "\\Archivos\\" + nombre;
            File directorios = new File(ruta);
            if (!directorios.exists()) {
                if (directorios.mkdirs()) {
                    System.out.println("<<<<<<<<<<<<<<<<<<  Carpeta creada   >>>>>>>>>>>>>>>>>>>>>");
                } else {
                    System.out.println("Error al crear directorios");
                }
            }
            st.execute("insert  usuarios values ('" + nombre + "','" + contra + "','" + ruta + "');");

            Element eSalida = new Element("Aprovado");
            this.mySend.println(this.xmlToString(eSalida));

        }

//        ResultSet rs = st.executeQuery()
    }//registrarUsuario

    public void ingresarUsuario(Element element) throws SQLException, IOException {

        String nombre = element.getChild("nombre").getValue();
        String contra = element.getChild("contrasena").getValue();

        System.out.println("nombre: " + nombre);
        System.out.println("contra: " + contra);

        Statement st = con.createStatement();
        //st.execute("insert  usuarios values ('"+ nombre+ "','" + contra + "','urlCarpeta');");
        ResultSet rs = st.executeQuery("select nombre, constrasena from usuarios where nombre= '" + nombre + "'");

        String nombreBS = null;
        String contraBS = null;
        while (rs.next()) {

            nombreBS = rs.getString(1);
            contraBS = rs.getString(2);
            System.out.println(nombre + "  " + contra);

        }//while

        if (nombreBS != null && contraBS.equals(contra)) {
            System.out.println("Si  hay usuarios registrados con ese nombre y tiene la misma contrasena");

//            File directorio = new File("Carpeta prueba");
//            mySend.println("Aprovado");
            Element eSalida = new Element("Aprovado");
            this.mySend.println(this.xmlToString(eSalida));

        } else {
            System.out.println("No existen usuarios registrados con ese nombre y contraseña");
//            mySend.println("Denegado");
            Element eSalida = new Element("Denegado");
            this.mySend.println(this.xmlToString(eSalida));
        }

//        ResultSet rs = st.executeQuery()
    }//registrarUsuario

    private void extraeArchivo(Element element) throws FileNotFoundException, IOException {

        //Buffer de 1024 bytes
        String urlNombre = element.getChild("nombre").getValue();

        // ruta //
        File miDir = new File(".");

        String ruta = miDir.getCanonicalPath() + "\\Archivos\\" + urlNombre + "\\";
        File directorios = new File(ruta);
        if (!directorios.exists()) {
            if (directorios.mkdirs()) {
                System.out.println("<<<<<<<<<<<<<<<<<<  Carpeta creada   >>>>>>>>>>>>>>>>>>>>>");
            } else {
                System.out.println("Problema al crear la carpeta");
            }
        } else {
            System.out.println("El directorio ya existe");
        }

        receivedData = new byte[1024];
        bis = new BufferedInputStream(this.socket.getInputStream());

        dis = new DataInputStream(this.socket.getInputStream());

        //Recibimos el nombre del fichero
        file = dis.readUTF();
        file = file.substring(file.indexOf('\\') + 1, file.length());

        ruta += file;

        System.out.println("ruta" + ruta);

        //Para guardar fichero recibido
        bos = new BufferedOutputStream(new FileOutputStream(ruta));
        while ((in = bis.read(receivedData)) != -1) {
            System.out.println("(in = bis.read(receivedData)) " + (in));
            bos.write(receivedData, 0, in);

            System.out.println("Descargando...");
        }
        System.out.println("despues del while");
        System.out.println("(in = bis.read(receivedData)) " + (in));
        bos.close();
        dis.close();

        System.out.println("FINALIZA extraccion");
//            mySend.println("hola mundo");

    }//extraeArchivo

    private Element stringToXML(String stringMensaje) throws JDOMException, IOException {

        SAXBuilder saxBuilder = new SAXBuilder();
        StringReader stringReader = new StringReader(stringMensaje);
        Document doc = saxBuilder.build(stringReader);
        return doc.getRootElement();

    } // stringToXML   

    private String xmlToString(Element element) {

        XMLOutputter output = new XMLOutputter(Format.getCompactFormat());
        String xmlStringElement = output.outputString(element);
        xmlStringElement = xmlStringElement.replace("\n", "");
        return xmlStringElement;

    }//xmlToString 

    public int getSumaDados() {
        return sumaDados;
    }

    public void setSumaDados(int sumaDados) {
        this.sumaDados = sumaDados;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

}//class
