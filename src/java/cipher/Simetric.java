package cipher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Clase que proporciona funcionalidades para cifrar y descifrar texto utilizando un algoritmo de cifrado simétrico AES.
 * La clase utiliza el algoritmo PBKDF2 para derivar una clave de una contraseña y agrega una operación de cifrado y descifrado AES/CBC/PKCS5Padding.
 * 
 * <p>
 * La clave derivada se utiliza para cifrar el texto y también se almacena junto con el texto cifrado para permitir el descifrado posterior.
 * </p>
 * 
 * <p>
 * La clase utiliza una sal generada aleatoriamente para mejorar la seguridad de la derivación de la clave.
 * </p>
 * 
 * <p>
 * Los archivos generados, como la clave y el contenido cifrado, se almacenan en archivos en el sistema de archivos.
 * </p>
 * 
 * <p>
 * La clase proporciona métodos para cifrar y descifrar texto, así como un método principal de prueba.
 * </p>
 * 
 * @author Josu
 * @version 1.0
 * @since 2024-02-01
 */
public class Simetric {

    private static final byte[] salt = generateSalt();

    private static byte[] generateSalt() {
        // Genera la sal de manera segura
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Cifra el texto proporcionado utilizando una clave derivada y un algoritmo de cifrado simétrico AES.
     * 
     * @param clave La contraseña utilizada para derivar la clave.
     * @param email El email a cifrar.
     * @param contrasena La contraseña a cifrar.
     * @param nombreArchivo El nombre del archivo donde se almacenarán los datos cifrados.
     * @return El texto cifrado.
     */
    public String cifrarTexto(String clave, String email, String contrasena, String nombreArchivo) {
        String ret = null;
        KeySpec derivedKey = null;
        SecretKeyFactory secretKeyFactory = null;

        try {
            // Combinar email y contraseña
            String textoAEncriptar = email + " , " + contrasena;

            derivedKey = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128);
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] derivedKeyPBK = secretKeyFactory.generateSecret(derivedKey).getEncoded();
            SecretKey derivedKeyPBK_AES = new SecretKeySpec(derivedKeyPBK, 0, derivedKeyPBK.length, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, derivedKeyPBK_AES);

            byte[] encodedMessage = cipher.doFinal(textoAEncriptar.getBytes());
            byte[] iv = cipher.getIV();
            byte[] combined = concatArrays(iv, encodedMessage);
            //Estas 2 lineas comentadas son las líneas originales
            //fileWriter("c:\\Cifrado\\privateKeySimetric.der", iv);
            //fileWriter("c:\\Cifrado\\credential.properties", combined);
            fileWriter(getClass().getResource("privateKeySimetric.der").getPath(), iv);
            fileWriter(getClass().getResource("credential.properties").getPath(), combined);
            ret = new String(encodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Descifra el texto cifrado almacenado utilizando una clave derivada y un algoritmo de cifrado simétrico AES.
     * 
     * @param clave La contraseña utilizada para derivar la clave.
     * @param nombreArchivo El nombre del archivo donde se almacenaron los datos cifrados.
     * @return El texto descifrado.
     */
    public String descifrarTexto(String clave, String nombreArchivo) {
        String ret = null;
        //estas 2 líneas son las líneas originales
        //byte[] fileKey = fileReader("c:\\Cifrado\\privateKeySimetric.der");
        //byte[] fileContent = fileReader("c:\\Cifrado\\credential.properties");
        byte[] fileKey = fileReader(getClass().getResource("privateKeySimetric.der").getPath());
        byte[] fileContent = fileReader(getClass().getResource("credential.properties").getPath());
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;

        try {
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128);
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileKey, 0, 16));

            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));

            // Separar email y contraseña después de descifrar
            String[] partes = new String(decodedMessage).split(" , ");
            String email = partes[0];
            String contrasena = partes[1];

            ret = "Email: " + email + ", Contraseña: " + contrasena;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
        return ret;
    }

    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Método principal de prueba.
     * 
     * @param args Los argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {

        Simetric sim = new Simetric();
        
        //Este método es para crear la carpeta
        //String rutaCarpeta = "C:\\Cifrado";

        // Crear objeto File
        //File carpeta = new File(rutaCarpeta);

        // Utilizar el método mkdir para crear la carpeta
        //if (carpeta.mkdir()) {
           // System.out.println("Carpeta creada exitosamente en C:");
        //} else {
          //  System.err.println("Esa Carpeta ya existen");
        //}

        String mensajeCifrado = sim.cifrarTexto("clave", "pruebacorreogl@zohomail.eu", "MiPatataSagrada123", "email");
        System.out.println("Cifrado -> " + mensajeCifrado);

        String mensajeDescifrado = sim.descifrarTexto("clave", "email");
        System.out.println("Descifrado -> " + mensajeDescifrado);
    }
}
