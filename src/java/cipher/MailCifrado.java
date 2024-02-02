package cipher;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.security.SecureRandom;

import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * @author josu
 */
/**
 * La clase MailCifrado proporciona métodos para enviar correos electrónicos cifrados
 * con una contraseña temporal generada aleatoriamente para la recuperación de contraseñas.
 *
 * <p>La clase utiliza el algoritmo de cifrado simétrico para descifrar las credenciales del cliente
 * necesarias para autenticarse en el servidor de correo.</p>
 *
 * <p>Los correos electrónicos se envían utilizando el protocolo SMTPS (SMTP seguro) a través del servidor
 * Zoho con la información de autenticación proporcionada.</p>
 *
 * <p>Se incluye un mensaje estándar de recuperación de contraseña en el correo electrónico enviado.</p>
 *
 * <p>Esta clase forma parte del paquete cipher.</p>
 */
public class MailCifrado {

    static String sSalt = "Mensaje super secreto";
    private static byte[] salt = sSalt.getBytes();
    String client = null;
    String contraMail = null;
    Integer caracteresMinimos = 8;
    final String ZOHO_HOST = "smtp.zoho.eu";
    final String TLS_PORT = "465";
    //465

    final String SENDER_USERNAME = "pruebacorreog1@zohomail.eu";
    final String SENDER_PASSWORD = "MiPatataSagrada123";
    private static final Logger LOGGER = java.util.logging.Logger.getLogger("/cipher/MailCifrado");

    /**
     * Envía un correo electrónico cifrado con una contraseña temporal generada aleatoriamente
     * para la recuperación de contraseñas.
     *
     * @param mailUser La dirección de correo electrónico del destinatario.
     * @return La nueva contraseña temporal generada y enviada por correo.
     */
    public String sendMail(String mailUser) {
        Simetric simi = new Simetric();

        client = simi.descifrarTexto("Clave", "client");
        contraMail = simi.descifrarTexto("Clave", "contra");

        final String recibido = mailUser;
        final String nuevaContra = randomPasswordGenerator(caracteresMinimos);

        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", ZOHO_HOST);
        props.setProperty("mail.smtp.port", TLS_PORT);
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.starttls.required", "true");
        props.setProperty("mail.smtp.ssl.enable", "true");
        props.setProperty("mail.smtp.ssl.checkserveridentity", "true");
        props.setProperty("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");
        props.setProperty("mail.smtp.ssl.trust", "smtp.zoho.eu");
        props.setProperty("mail.debug", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(client, contraMail);
            }
        });

        try {

            final MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(SENDER_USERNAME));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recibido, false));
            msg.setSubject("Recuperación de contraseña");
            msg.setText("Nos ponemos en contacto contigo para informarte que hemos recibido una solicitud de recuperación de contraseña para tu cuenta en nuestro sistema. Entendemos lo importante que es el acceso seguro a tus datos y estamos aquí para ayudarte en este proceso.\n"
                    + "A continuación, encontrarás tu nueva contraseña temporal: " + nuevaContra + ".\n"
                    + "Por razones de seguridad, te recomendamos cambiar esta contraseña tan pronto como ingreses a tu cuenta. Si tienes alguna pregunta o necesitas asistencia adicional, no dudes en responder a este correo electrónico.\n"
                    + "Si no solicitaste esto o necesitas ayuda, respóndenos.\n"
                    + "Saludos, 2Dam G3 CIFP Tartanga LHII");
            msg.setSentDate(new Date());

            Transport transport = session.getTransport("smtp");

            transport.connect(ZOHO_HOST, SENDER_USERNAME, SENDER_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (MessagingException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return nuevaContra;

    }

    /**
     * Genera una contraseña aleatoria de la longitud especificada.
     *
     * @param len La longitud de la contraseña generada.
     * @return Una cadena que representa la contraseña aleatoria generada.
     */
    public String randomPasswordGenerator(Integer len) {
        final String caracter = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz0123456789";

        SecureRandom rng = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        int i;

        for (i = 0; i < len; i++) {
            int random = rng.nextInt(caracter.length());
            sb.append(caracter.charAt(random));
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
