/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Evento;
import entity.Sede;
import entity.User;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eneko.
 */
@Stateless
public class SedeEJB implements SedeInterface {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;

    @Override
    public List<Sede> viewSedes() throws ReadException {
        List<Sede> sedes = null;
        try {
            sedes = em.createNamedQuery("findAllSede").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return sedes;
    }

    @Override
    public void deleteSede(Sede sede) throws DeleteException {
        try {
            em.remove(em.merge(sede));
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }

    }

    @Override
    public void createSede(Sede sede) throws CreateException {
        try {
            em.persist(sede);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void modifySede(Sede sede) throws UpdateException {
        try {
            if (!em.contains(sede)) {
                em.merge(sede);
            }
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public Sede viewSedeById(Integer id_sede) throws ReadException {
        Sede sede;
        try {
            sede = em.find(Sede.class, id_sede);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return sede;

    }

    @Override
    public List<Sede> viewSedeByCountry(String pais) throws ReadException {
        List<Sede> sede = null;
        try {
            sede = em.createNamedQuery("findSedeByCountry").setParameter("pais", pais).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return sede;
    }

    @Override
    public List<Sede> viewSedeAforoMax(Integer aforoMax) throws ReadException {
        List<Sede> sede = null;
        try {
            sede = em.createNamedQuery("findSedeAforoMax").setParameter("aforoMax", aforoMax).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return sede;
    }

    @Override
    public List<Evento> viewEventoBySede(Integer id_sede) throws ReadException {
        List<Evento> list;
        try {
            list = em.createNamedQuery("findEventBySede", Evento.class)
                    .setParameter("id_sede", id_sede)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return list;
    }

}
