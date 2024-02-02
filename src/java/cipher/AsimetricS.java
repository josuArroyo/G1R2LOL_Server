package cipher;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ResourceBundle;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;



public class AsimetricS {

    //private static final String ENCRYPTED_DATA_PATH = "c:\\Cifrado\\UserCredentialC.properties";
    //esta es la linea original
    private static final String PRIVATE_KEY_PATH = "src/cipher/privateKey.der";  
    /**
     * Carga la clave privada desde el archivo especificado.
     *
     * @return La clave privada cargada desde el archivo.
     */
    
    public PrivateKey loadPrivateKey() {
        // Load Private Key from file
        try {
            byte[] keyBytes = fileReader(getClass().getResource("privateKey.der").getPath());
           // byte[] privateKeyBytes;
           // FileInputStream fis = new FileInputStream(".//src//cipher//privateKey.der");
            //privateKeyBytes = new byte[fis.available()];
            //fis.read(privateKeyBytes);
          
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

     /**
     * Recibe y descifra un mensaje en formato hexadecimal utilizando la clave privada proporcionada.
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

    /*private byte[] fileReader(String path) {
        byte[] ret = null;
        try {
            //ret = Files.readAllBytes(getClass().getResourceAsStream("publicKey.der"));
           InputStream in= getClass().getResourceAsStream("privateKey.der");
           ret =  toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }*/
    /**
     * Lee los bytes de un archivo y los devuelve como un array de bytes.
     *
     * @param path La ruta del archivo.
     * @return Un array de bytes que representa el contenido del archivo.
     */
    private byte[] fileReader(String path) {
        byte[] ret = null;
        File file = new File (path);
        try {
           ret= Files.readAllBytes(file.toPath());
          // InputStream in= getClass().getResourceAsStream("publicKey.der");
           //ret = toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
