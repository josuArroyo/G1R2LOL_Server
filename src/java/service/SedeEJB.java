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
 * EJB que implementa la interfaz SedeInterface para gestionar operaciones CRUD
 * relacionadas con la entidad Sede. Proporciona métodos para la creación,
 * modificación, eliminación y consulta de sedes.
 *
 * Este EJB interactúa con la base de datos a través de JPA (Java Persistence API).
 *
 * @author Eneko
 * @version 1.0
 * @since 2024-02-01
 */
@Stateless
public class SedeEJB implements SedeInterface {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;
    
     /**
     * Obtiene la lista de todas las sedes.
     *
     * @return Lista de todas las sedes.
     * @throws ReadException Si hay un error al recuperar las sedes desde la base de datos.
     */
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
    
    /**
     * Elimina una sede existente.
     *
     * @param sede Sede a ser eliminada.
     * @throws DeleteException Si hay un error al eliminar la sede de la base de datos.
     */
    @Override
    public void deleteSede(Sede sede) throws DeleteException {
        try {
            em.remove(em.merge(sede));
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }

    }
    
    /**
     * Crea una nueva sede.
     *
     * @param sede Sede a ser creada.
     * @throws CreateException Si hay un error al persistir la sede en la base de datos.
     */
    @Override
    public void createSede(Sede sede) throws CreateException {
        try {
            em.persist(sede);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }
    
    /**
     * Modifica una sede existente.
     *
     * @param sede Sede con los datos actualizados.
     * @throws UpdateException Si hay un error al actualizar la sede en la base de datos.
     */
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
    
    /**
     * Obtiene una sede por su identificador único.
     *
     * @param id_sede Identificador único de la sede.
     * @return Objeto Sede con los datos de la sede buscada.
     * @throws ReadException Si hay un error al recuperar la sede desde la base de datos.
     */
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
    
    /**
     * Obtiene la lista de sedes filtrada por país.
     *
     * @param pais País por el cual filtrar las sedes.
     * @return Lista de sedes filtrada por país.
     * @throws ReadException Si hay un error al recuperar las sedes desde la base de datos.
     */
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
    
    /**
     * Obtiene la lista de sedes filtrada por aforo máximo.
     *
     * @param aforoMax Aforo máximo por el cual filtrar las sedes.
     * @return Lista de sedes filtrada por aforo máximo.
     * @throws ReadException Si hay un error al recuperar las sedes desde la base de datos.
     */
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
    
    /**
     * Obtiene la lista de eventos asociados a una sede.
     *
     * @param id_sede Identificador único de la sede.
     * @return Lista de eventos asociados a la sede.
     * @throws ReadException Si hay un error al recuperar los eventos desde la base de datos.
     */
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
