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

            messageDigest = MessageDigest.getInstance(encriptacion);

            messageDigest.update(texto);

            byte[] digest = messageDigest.digest();

        } catch (NoSuchAlgorithmException e) {

        }
        return mensaje;
    }

}
