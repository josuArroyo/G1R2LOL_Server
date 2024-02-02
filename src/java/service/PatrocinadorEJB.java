/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Evento;
import entity.Patrocinador;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase EJB (Enterprise JavaBeans) que implementa la interfaz PatrocinadorInterface.
 * Proporciona métodos para realizar operaciones CRUD relacionadas con la entidad
 * Patrocinador. Esta clase interactúa con la base de datos a través de JPA (Java
 * Persistence API) y maneja excepciones específicas para cada operación.
 *
 * Los métodos de esta clase gestionan la creación, modificación, eliminación y consulta
 * de patrocinadores, así como la obtención de eventos asociados a un patrocinador.
 *
 * @author Egoitz
 * @version 1.0
 * @since 2024-02-01
 */
@Stateless
public class PatrocinadorEJB implements PatrocinadorInterface {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;
    
    /**
     * Crea un nuevo patrocinador en la base de datos.
     *
     * @param patrocinador Patrocinador a ser creado.
     * @throws CreateException Si hay un error al persistir el patrocinador en la base de datos.
     */
    @Override
    public void createPatrocinador(Patrocinador patrocinador) throws CreateException {

        try {
            em.persist(patrocinador);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }
    
     /**
     * Elimina un patrocinador existente de la base de datos.
     *
     * @param patrocinador Patrocinador a ser eliminado.
     * @throws DeleteException Si hay un error al eliminar el patrocinador de la base de datos.
     */
    @Override
    public void deletePatrocinador(Patrocinador patrocinador) throws DeleteException {

        try {
            em.remove(em.merge(patrocinador));

        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }
    
     /**
     * Modifica un patrocinador existente en la base de datos.
     *
     * @param patrocinador Patrocinador con los datos actualizados.
     * @throws UpdateException Si hay un error al actualizar el patrocinador en la base de datos.
     */
    @Override
    public void modifyPatrocinador(Patrocinador patrocinador) throws UpdateException {

        try {
            if (!em.contains(patrocinador)) {
                em.merge(patrocinador);
            }
            em.flush();

        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }
    
    /**
     * Obtiene una lista de todos los patrocinadores almacenados en la base de datos.
     *
     * @return Lista de patrocinadores.
     * @throws ReadException Si hay un error al recuperar los patrocinadores desde la base de datos.
     */
    @Override
    public List<Patrocinador> viewPatrocinadores() throws ReadException {
        List<Patrocinador> patrocinador;
        try {
            patrocinador = em.createNamedQuery("findAllPatrocinadores").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return patrocinador;
    }
    
    /**
     * Obtiene una lista de patrocinadores filtrada por nombre.
     *
     * @param nombre Nombre por el cual filtrar los patrocinadores.
     * @return Lista de patrocinadores filtrada por nombre.
     * @throws ReadException Si hay un error al recuperar los patrocinadores desde la base de datos.
     */
    @Override
    public List<Patrocinador> viewPatrocinadorByName(String nombre) throws ReadException {
        List<Patrocinador> patrocinador;
        try {
            patrocinador = em.createNamedQuery("findForName").setParameter("nombre", nombre).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }

        return patrocinador;

    }
    
    /**
     * Obtiene una lista de patrocinadores filtrada por duración de patrocinio.
     *
     * @param DuracionPatrocinio Duración por la cual filtrar los patrocinadores.
     * @return Lista de patrocinadores filtrada por duración de patrocinio.
     * @throws ReadException Si hay un error al recuperar los patrocinadores desde la base de datos.
     */
    @Override
    public List<Patrocinador> viewPatrocinadorByDuration(Date DuracionPatrocinio) throws ReadException {
        List<Patrocinador> patrocinador;
        try {
            patrocinador = em.createNamedQuery("findForDuration").setParameter("DuracionPatrocinio", DuracionPatrocinio).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }

        return patrocinador;
    }
    
    /**
     * Obtiene un patrocinador específico por su identificador único.
     *
     * @param id_patrocinador Identificador único del patrocinador.
     * @return Patrocinador con el identificador proporcionado.
     * @throws ReadException Si hay un error al recuperar el patrocinador desde la base de datos.
     */
    @Override
    public Patrocinador viewPatrocinadorById(Integer id_patrocinador) throws ReadException {
        Patrocinador patrocinador;
        try {
            patrocinador = em.find(Patrocinador.class, id_patrocinador);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }

        return patrocinador;
    }
    
    /**
     * Obtiene una lista de eventos asociados a un patrocinador específico.
     *
     * @param id_patrocinador Identificador único del patrocinador.
     * @return Lista de eventos asociados al patrocinador.
     * @throws ReadException Si hay un error al recuperar los eventos desde la base de datos.
     */
    @Override
    public List<Evento> viewEventosByPatrocinador(Integer id_patrocinador) throws ReadException {
        List<Evento> list;
        try {
            list = em.createNamedQuery("findEventosByPatrocinador", Evento.class)
                    .setParameter("id_patrocinador", id_patrocinador)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return list;
    }

}
