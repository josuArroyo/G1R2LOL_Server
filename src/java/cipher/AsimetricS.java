package cipher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;

public class AsimetricS {

    private static final String ENCRYPTED_DATA_PATH = "c:\\Cifrado\\UserCredentialC.properties";
    private static final String PRIVATE_KEY_PATH = "C:\\Cifrado\\privateKey.der";  // Ruta de la clave privada generada por GenerarClaves

    private PrivateKey loadPrivateKey() {
        // Load Private Key from file
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(PRIVATE_KEY_PATH));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String decryptData(byte[] data, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData = cipher.doFinal(data);
            return new String(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] fileReader(String path) {
        byte[] ret = null;
        try {
            ret = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void main(String[] args) {
        AsimetricS asimetricS = new AsimetricS();

        // Load Private Key
        PrivateKey privateKey = asimetricS.loadPrivateKey(); // Asegúrate de tener la clave privada generada por GenerarClaves

        if (privateKey != null) {
            // Leer datos cifrados desde el cliente
            byte[] encryptedData = asimetricS.fileReader(ENCRYPTED_DATA_PATH);

            // Descifrar datos utilizando la clave privada
            String decryptedMessage = asimetricS.decryptData(encryptedData, privateKey);
            System.out.println("Descifrado en el servidor: " + decryptedMessage);
        } else {
            System.out.println("Error: Clave privada no encontrada.");
        }
    }
}
