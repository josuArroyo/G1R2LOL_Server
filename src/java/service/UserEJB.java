/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cipher.AsimetricS;
import cipher.HashContra;
import entity.User;
import entity.UserType;
import exception.ReadException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eneko.
 */
@Stateless
public class UserEJB implements UserInterface {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;
    
    private static final String PRIVATE_KEY_PATH = "./src/cipher/privateKey.der";

    @Override
    public List<User> findUserByEmailAndPasswd(String email, String passwd) throws ReadException {
        try {
            String contra = null;
            String contra_desc = null;
            String hash = null;
            AsimetricS asimetricS = new AsimetricS();
            PrivateKey privateKey;
            privateKey = asimetricS.loadPrivateKey();
            
            

            // Descifrar la contraseña utilizando la clave privada
            AsimetricS asi = new AsimetricS();
            contra_desc = asi.receiveAndDecryptMessage(passwd, privateKey);

            // Aplicar el hash a la contraseña descifrada
            hash = HashContra.hashContra(contra_desc);
            return em.createNamedQuery("findUserByEmailAndPasswd").setParameter("email", email).setParameter("passwd", hash).getResultList();

        } catch (Exception e) {
            throw new ReadException(e.getMessage());

        }
    }

    @Override
    public List<User> findForUserType(UserType userType) throws ReadException {
        try {
            return em.createNamedQuery("findForUserType").setParameter("userType", userType).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    @Override
    public List<User> viewAllUsers() throws ReadException {
        List<User> user = null;

        try {
            user = em.createNamedQuery("findAllUser").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return user;
    }

    
    

    @Override
    public List<User> viewByEmail(String email) throws ReadException {
        
        try {
            return em.createNamedQuery("findUserByMail").setParameter("email", email).getResultList();
            
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

}
