/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.User;
import entity.UserType;
import exception.ReadException;
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

    @Override
    public List<User> findUserByEmailAndPasswd(String email, String passwd) throws ReadException {
        try {
            return em.createNamedQuery("findUserByEmailAndPasswd").setParameter("email", email).setParameter("passwd", passwd).getResultList();
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

}
