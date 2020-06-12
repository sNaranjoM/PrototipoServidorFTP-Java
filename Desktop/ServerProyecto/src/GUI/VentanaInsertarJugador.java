/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Utility.MyUtility;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import org.jdom.Element;

/**
 *
 * @author Steven Naranjo M
 */
public class VentanaInsertarJugador extends JFrame implements ActionListener, Runnable {

    JPanel panel;
    JTextField jtf_nombre;
    JPasswordField jtf_contrasena;
    JLabel lbl_nombre, lbl_contrasena;
    JButton btn_Selecionar, btn_Iniciar, btn_FileChooser;
    private JTextArea textarea1;

    private Thread hilo;
    boolean hilo_on;
    Connection con;

    Object[] vecImg;

    JComboBox combo;

    String ruta;

    public VentanaInsertarJugador() throws IOException, ClassNotFoundException, SQLException {

        super("Steven Naranjo");

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://163.178.107.10:1433;databaseName=b55008_proyectoRedes;user=laboratorios;password=Saucr.118;";
//        String connectionUrl = "jdbc:sqlserver://163.178.173.148;databaseName=b55008_proyectoRedes;user=estudiantesrp;password=estudiantesrp;";
        con = DriverManager.getConnection(connectionUrl);
        System.out.println("Nos conectamos al servidor de la base de datos");

        panel = new JPanel();
        panel.setLayout(null);
        this.panel.setSize(230, 185);
        this.setVisible(true);

        this.lbl_nombre = new JLabel("Archivos de los usuarios ");
        this.btn_Selecionar = new JButton("Selecionar");
        this.combo = new JComboBox();
        textarea1 = new JTextArea();
        llenaCombo();

        this.lbl_nombre.setBounds(130, 30, 150, 30);
        this.combo.setBounds(50, 70, 200, 25);
        this.btn_Selecionar.setBounds(250, 70, 100, 25);
        this.textarea1.setBounds(50, 100, 300, 200);

        this.panel.add(lbl_nombre);
        this.panel.add(btn_Selecionar);
        this.panel.add(combo);
        this.panel.add(textarea1);

        this.btn_Selecionar.addActionListener(this);
        this.hilo_on = true;
        this.panel.setVisible(true);
        add(panel);

        setLocationRelativeTo(null);
        setSize(415, 400);
        setVisible(true);

    } //constructor

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == btn_Selecionar) {
            listaArchivos();
        }

    }//actionPerformed

    public void listaArchivos() {

        this.textarea1.setText("");
        try {
            File miDir = new File(".");
            String ruta;

            ruta = miDir.getCanonicalPath() + "\\Archivos\\" + this.combo.getSelectedItem();

            System.out.println("ruta: " + ruta);

            File carpeta = new File(ruta);

            String[] listado = carpeta.list();

            if (listado == null || listado.length == 0) {
                System.out.println("No hay elementos dentro de la carpeta actual");

            } else {

                for (int i = 0; i < listado.length; i++) {
                    System.out.println(listado[i]);
                    this.textarea1.append(listado[i] + "\n");

                }
            }
        } catch (IOException ex) {
            Logger.getLogger(VentanaInsertarJugador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//listaArchivos

    public void llenaCombo() {
        try {

            Statement st;

            st = con.createStatement();

            //st.execute("insert  usuarios values ('"+ nombre+ "','" + contra + "','urlCarpeta');");
            ResultSet rs = st.executeQuery("select nombre, constrasena from usuarios");

            String nombreBS = null;
            String contraBS = null;
            while (rs.next()) {

                nombreBS = rs.getString(1);
                contraBS = rs.getString(2);
                System.out.println(nombreBS + "  " + contraBS);
                this.combo.addItem(nombreBS);

            }//while

        } catch (SQLException ex) {
            Logger.getLogger(VentanaInsertarJugador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//llenaCombo

    @Override
    public void addNotify() {
        super.addNotify();
        if (this.hilo == null) {
            this.hilo = new Thread(this);
//            this.addMouseListener(this);
            this.hilo_on = true;
            this.hilo.start();
        } //
    }// addNotify

    @Override
    public void run() {

        while (hilo_on) {

        }

    }//run

}//class
