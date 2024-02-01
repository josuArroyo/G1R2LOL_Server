/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cipher.MailCifrado;
import cipher.AsimetricS;
import cipher.HashContra;
import entity.Voluntario;
import exception.CreateException;
import exception.ReadException;
import exception.UpdateException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.logging.Logger;

/**
 *
 * @author Egoitz
 */
@Stateless
public class VoluntarioEJB implements VoluntarioInterface {

    private static final Logger LOGGER = Logger.getLogger("/service/VoluntarioEJB");
    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;

    private AbstractFacade abs;

    @Override
    public void recuperarContra(Voluntario volun) throws UpdateException {
        String nuevaContra = null;
        MailCifrado email = new MailCifrado();
        try {
            nuevaContra = email.sendMail(volun.getEmail());
            nuevaContra = HashContra.hashContra(nuevaContra);
            volun.setPasswd(nuevaContra);
            if (!em.contains(volun)) {
                em.merge(volun);
            }
            em.flush();
        } catch (Exception e) {

            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void cambiarContra(Voluntario volun) throws UpdateException {
        String contra = null;
        String hash = null;
        String contra_desc = null;
        AsimetricS asimetricS = new AsimetricS();
        try {

            PrivateKey privateKey;

            // Cargar la clave privada desde el archivo después de haberse generado
            privateKey = asimetricS.loadPrivateKey();
          

            // Obtener la contraseña del cliente
            contra = volun.getPasswd();

            // Descifrar la contraseña utilizando la clave privada
            AsimetricS asi = new AsimetricS();
            contra_desc = asi.receiveAndDecryptMessage(contra, privateKey);

            // Aplicar el hash a la contraseña descifrada
            hash = HashContra.hashContra(contra_desc);
            volun.setPasswd(hash);

            if (!em.contains(volun)) {
                em.merge(volun);
            }
            em.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void createVoluntario(Voluntario volun) throws CreateException {
        String contra = null;
        String hash = null;
        String contra_desc = null;
        AsimetricS asimetricS = new AsimetricS();
        try {

            PrivateKey privateKey;

            // Cargar la clave privada desde el archivo después de haberse generado
            privateKey = asimetricS.loadPrivateKey();

            // Obtener la contraseña del cliente
            contra = volun.getPasswd();

            // Descifrar la contraseña utilizando la clave privada
            contra_desc = asimetricS.receiveAndDecryptMessage(contra, privateKey);

            // Aplicar el hash a la contraseña descifrada
            hash = HashContra.hashContra(contra_desc);
            volun.setPasswd(hash);
            volun.setConfirmPasswd(hash);

            // Persistir el cliente en la base de datos
            em.persist(volun);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    // Método para cargar la clave privada desde un archivo
    private PrivateKey loadPrivateKeyFromFile(String filePath) {
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(filePath));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Voluntario> viewAllVoluntarios() throws ReadException {
        List<Voluntario> voluntario = null;
        try {
            voluntario
                    = em.createNamedQuery("viewAllVoluntaries").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return voluntario;

    }

    @Override
    public Voluntario filtrarVoluntarioPorID(Integer id) throws ReadException {
        Voluntario voluntario;
        try {
            voluntario = em.find(Voluntario.class, id);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return voluntario;
    }

}
