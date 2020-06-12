/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nelson
 */
public class MyUtility {
   public static String RUTAARCHIVO="archivo.xml";
   public static int SOCKETPORTNUMBER=5026;
   
   //protocolo
   
   public static String GUERREROMOVE="guerreroMove";
   
   public static String INGRESARUSUARIO="ingresarUsuario";
    public static String  ENOMBRE = "eNombre";
    public static String  ECONTRASENA = "eContrasena";
    public static String  ERUTA = "eRuta";
   
   
   public static String REGISTRARUSUARIO="registrarUsuario";
   public static String RESPUESTADATA="respuestaData";
   public static String VALIDARUSUARIO="validarUsuario";
   
   public static String SHA256 = "SHA-256";
   
   private static String convertirAHexadecimal(byte[] digest) {
        String hash = "";
        for (byte aux : digest) {
            int b = aux & 0xff;//para escribir en hexadecimal 0x
            if (Integer.toHexString(b).length() == 1) {
                hash += "0";
            }
            hash += Integer.toHexString(b);
        }
        return hash;
    }

    public static String obtenerPasswordCifrada(String mensaje, String algoritmo) {
        byte[] digest = null;
        try {

            byte[] buffer = mensaje.getBytes();//String equivalente a bytes
            MessageDigest messageDigest = MessageDigest.getInstance(algoritmo);//crea objecto del tipo de manera dinamica
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MyUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return convertirAHexadecimal(digest);
    }

   
} // fin clase
