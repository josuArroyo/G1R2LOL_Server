/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cipher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author 2dam
 */
public class HashContra {

    public static String hashContra(byte[] texto) {
        MessageDigest messageDigest;
        String encriptacion = "SHA";
        String mensaje = null;

        try {
            // Obtén una instancia de MessageDigest que usa SHA
            messageDigest = MessageDigest.getInstance(encriptacion);
            // Actualiza el MessageDigest con el array de bytes             
            messageDigest.update(texto);
            // Calcula el resumen (función digest)
            byte[] digest = messageDigest.digest();
            //mensaje = hexadecimal(digest);
        } catch (NoSuchAlgorithmException e) {
            
        }
        return mensaje;
    }

}
