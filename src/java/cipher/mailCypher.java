/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cipher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Egoitz
 */
public class mailCypher {

    static String sSalt = "Mensaje super secreto";
    private static byte[] salt = sSalt.getBytes();
    String user = null;
    String emailKey = null;

    public String sendEmail(String emailUser) {
        final ResourceBundle bundle = ResourceBundle.getBundle("credential.properties");

        final String cyperuser = bundle.getString("EMAILCIFRADO");
        final String cyperemailKey = bundle.getString("CONTRACIFRADA");
        final String ZOHO_HOST = "smtp.zoho.eu";
        final String TLS_PORT = "897";
        final String SENDER_USERNAME = "pruebacorreog1@zohomail.eu";
        final String SENDER_PASSWORD = "MiPatataSagrada123";

        try {
            user = descifrarTexto("Clave", "user");
            emailKey = descifrarTexto("Clave", "key");
        } catch (IOException ex) {
            Logger.getLogger(mailCypher.class.getName()).log(Level.SEVERE, null, ex);
        }

        final String recibido = emailUser;
        final String nuevaContra = generateRandomPassword(Integer.parseInt(bundle.getString("MINIMOCONTRASEÑA")));

        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", ZOHO_HOST);
        props.setProperty("mail.smtp.port", TLS_PORT);
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtps.auth", "true");

        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, emailKey);
            }
        });

        try {

            final MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(SENDER_USERNAME));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recibido, false));
            msg.setSubject("Recuperación de contraseña");
            msg.setText("¡Hola!\n"
                    + "\n"
                    + "Esperamos que te encuentres bien. Nos dirigimos a ti para informarte que hemos recibido una solicitud de recuperación de contraseña para tu cuenta.\n"
                    + "\n"
                    + "Aquí está tu nueva contraseña temporal: [Nueva Contraseña]. Por motivos de seguridad, te recomendamos cambiar esta contraseña en cuanto inicies sesión en tu cuenta.\n"
                    + "\n"
                    + "Si no has realizado esta solicitud o necesitas ayuda, por favor, ponte en contacto con nosotros respondiendo a este correo. Estamos aquí para garantizar que recuperes el acceso a tu cuenta de manera segura.\n"
                    + "\n"
                    + "¡Gracias por confiar en nosotros!\n"
                    + "\n"
                    + "Saludos cordiales,\n"
                    + "[Nombre de tu Empresa o Servicio]\n"
                    + "[Información de Contacto]\n");
            msg.setSentDate(new Date());

            Transport transport = session.getTransport("smtps");

            transport.connect(ZOHO_HOST, SENDER_USERNAME, SENDER_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (MessagingException e) {
            throw new RuntimeException(e);

        }

        return nuevaContra;
    }

    public String descifrarTexto(String clave, String mensajeCyper) throws IOException {
        String ret = null;
        byte[] fileContent = null;

        if (mensajeCyper.equals("user")) {

            InputStream is = getClass().getResourceAsStream("email.dat");
            fileContent = new byte[is.available()];
            is.read(fileContent, 0, is.available());
        } else {
            InputStream is = getClass().getResourceAsStream("key.dat");
            fileContent = new byte[is.available()];
            is.read(fileContent, 0, is.available());
        }

        // Fichero leído
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;

        try {
            // Obtenemos el keySpec
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); // AES-128

            // Obtenemos una instancide de SecretKeyFactory con el algoritmo "PBKDF2WithHmacSHA1"
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            // Generamos la clave
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();

            // Creamos un SecretKey usando la clave + salt
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");// AES;

            // Obtenemos una instancide de Cipher con el algoritmos que vamos a usar "AES/CBC/PKCS5Padding"
            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave privada
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Leemos el fichero codificado 
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16));

            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave privada y el ivParam
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            // Le decimos que descifre
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));

            // Texto descifrado
            ret = new String(decodedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private static String generateRandomPassword(int len) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

    private static byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
