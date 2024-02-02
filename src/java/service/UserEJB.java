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
 * EJB que implementa la interfaz UserInterface para gestionar operaciones
 * relacionadas con los usuarios. Proporciona métodos para la autenticación de
 * usuarios, consulta por tipo de usuario, visualización de todos los usuarios,
 * y búsqueda por correo electrónico.
 *
 * Este EJB utiliza técnicas de cifrado asimétrico y aplicación de hash para
 * gestionar la autenticación segura de usuarios.
 *
 * @author Eneko
 * @version 1.0
 * @since 2024-02-01
 */
@Stateless
public class UserEJB implements UserInterface {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;
    
    private static final String PRIVATE_KEY_PATH = "./src/cipher/privateKey.der";
    
     /**
     * Autentica a un usuario por correo electrónico y contraseña.
     *
     * @param email Correo electrónico del usuario.
     * @param passwd Contraseña del usuario cifrada.
     * @return Lista de usuarios autenticados.
     * @throws ReadException Si ocurre un error durante la autenticación.
     */
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
    
    /**
     * Busca usuarios por tipo de usuario.
     *
     * @param userType Tipo de usuario a ser filtrado.
     * @return Lista de usuarios del tipo especificado.
     * @throws ReadException Si ocurre un error durante la consulta.
     */
    @Override
    public List<User> findForUserType(UserType userType) throws ReadException {
        try {
            return em.createNamedQuery("findForUserType").setParameter("userType", userType).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }
    
    /**
     * Obtiene la lista de todos los usuarios.
     *
     * @return Lista de todos los usuarios.
     * @throws ReadException Si ocurre un error durante la lectura.
     */
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

    
    
    /**
     * Busca y devuelve usuarios por correo electrónico.
     *
     * @param email Correo electrónico del usuario a ser buscado.
     * @return Lista de usuarios con el correo electrónico especificado.
     * @throws ReadException Si ocurre un error durante la consulta.
     */
    @Override
    public List<User> viewByEmail(String email) throws ReadException {
        
        try {
            return em.createNamedQuery("findUserByMail").setParameter("email", email).getResultList();
            
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

}
