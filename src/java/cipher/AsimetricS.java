package cipher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

/**
 * Clase que implementa operaciones asimétricas de cifrado y descifrado utilizando RSA.
 * Se encarga de cargar la clave privada desde un archivo y de realizar la operación de descifrado.
 * Esta clase utiliza el algoritmo de cifrado RSA con modo ECB y relleno PKCS1.
 * La clase es utilizada en el contexto de un servidor para recibir y descifrar mensajes cifrados.
 * 
 * <p>
 * La ruta del archivo que contiene la clave privada se especifica en la constante
 * {@code PRIVATE_KEY_PATH}.
 * </p>
 * 
 * <p>
 * La clase utiliza el método {@code receiveAndDecryptMessage} para descifrar un mensaje
 * cifrado proporcionado como una cadena hexadecimal. El método {@code loadPrivateKey}
 * carga la clave privada desde el archivo especificado.
 * </p>
 * 
 * @author YourName
 * @version 1.0
 * @since 2024-02-01
 */
public class AsimetricS {

    //private static final String ENCRYPTED_DATA_PATH = "c:\\Cifrado\\UserCredentialC.properties";
    //esta es la línea original
    private static final String PRIVATE_KEY_PATH = "src/cipher/privateKey.der";  
    
    /**
     * Carga la clave privada desde el archivo especificado.
     * 
     * @return La clave privada cargada.
     */
    public PrivateKey loadPrivateKey() {
        // Load Private Key from file
        try {
            byte[] keyBytes = fileReader(PRIVATE_KEY_PATH);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Recibe y descifra un mensaje cifrado proporcionado como una cadena hexadecimal.
     * 
     * @param encryptedHexData El mensaje cifrado en formato hexadecimal.
     * @param privateKey La clave privada utilizada para descifrar el mensaje.
     * @return El mensaje descifrado.
     */
    public String receiveAndDecryptMessage(String encryptedHexData, PrivateKey privateKey) {
        String decryptedMessage = null;

        try {
            // Convertir la cadena hexadecimal a un array de bytes
            byte[] encryptedData = DatatypeConverter.parseHexBinary(encryptedHexData);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // Realizar la operación de descifrado
            byte[] decryptedData = cipher.doFinal(encryptedData);

            decryptedMessage = new String(decryptedData);
            System.out.println("Mensaje descifrado en el servidor: " + decryptedMessage);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return decryptedMessage;
    }

    /**
     * Lee el contenido de un archivo y lo devuelve como un array de bytes.
     * 
     * @param path La ruta del archivo.
     * @return El contenido del archivo como un array de bytes.
     */
    private byte[] fileReader(String path) {
        byte[] ret = null;
        File file = new File(path);
        try {
           ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
