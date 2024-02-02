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
 * Clase que implementa la interfaz EventoInterface y proporciona lógica de negocio
 * para las operaciones CRUD relacionadas con la entidad Evento. Esta clase es un EJB
 * (Enterprise JavaBean) sin estado y se encarga de interactuar con la base de datos
 * a través de JPA (Java Persistence API). Contiene métodos para filtrar eventos por
 * su identificador, eliminar eventos, obtener todos los eventos, crear nuevos eventos,
 * modificar eventos existentes, encontrar eventos por su identificador, aforo máximo
 * y fecha, así como obtener la lista de patrocinadores asociados a un evento específico.
 *
 * @author Josu
 * @version 1.0
 * @since 2024-02-01
 */
@Stateless
public class EventoEJB implements EventoInterface {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;

    @Override
    public Evento filterEventById(Integer id_evento) throws ReadException {
        Evento event;
        try {
            event = em.find(Evento.class, id_evento);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return event;

    }

    /**
     * Elimina un evento existente de la base de datos.
     *
     * @param event Evento a ser eliminado.
     * @throws DeleteException Si ocurre un error al eliminar el evento.
     */
    @Override
    public void deleteEvent(Evento event) throws DeleteException {
        try {
            em.remove(em.merge(event));
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }

    }

     /**
     * Obtiene una lista de todos los eventos almacenados en la base de datos.
     *
     * @return Lista de eventos.
     * @throws ReadException Si ocurre un error al leer la información desde la base de datos.
     */
    @Override
    public List<Evento> viewEvents() throws ReadException {
        List<Evento> events = null;
        try {
            events = em.createNamedQuery("viewAllEvents").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return events;
    }

     /**
     * Crea un nuevo evento en la base de datos.
     *
     * @param event Evento a ser creado.
     * @throws CreateException Si ocurre un error al crear el evento.
     */
    @Override
    public void createEvent(Evento event) throws CreateException {
        try {
            em.persist(event);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Modifica un evento existente en la base de datos.
     *
     * @param event Evento con los datos actualizados.
     * @throws UpdateException Si ocurre un error al actualizar el evento.
     */
    @Override
    public void modifyEvent(Evento event) throws UpdateException {
        try {
            if (!em.contains(event)) {
                em.merge(event);
            }
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }
    
    /**
     * Obtiene una lista de eventos filtrada por su identificador único.
     *
     * @param id_evento Identificador único del evento.
     * @return Lista de eventos filtrada por identificador único.
     * @throws ReadException Si ocurre un error al leer la información desde la base de datos.
     */
    @Override
    public List<Evento> findEventByEventId(Integer id_evento) throws ReadException {
        List<Evento> events;
        try {
            events = em.createNamedQuery("findEventByEventId").setParameter("id_evento", id_evento).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return events;
    }

    @Override
    public List<Evento> viewEventoAforoMax(Integer aforo) throws ReadException {
        List<Evento> evento;
        try {
            evento = em.createNamedQuery("findEventByAforo").setParameter("aforo", aforo).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return evento;

    }

    @Override
    public List<Evento> viewEventoByDate(Date fechaEvento) throws ReadException {

        List<Evento> evento;

        try {
            evento = em.createNamedQuery("findEventByDate").setParameter("fechaEvento", fechaEvento).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());

        }
        return evento;
    }

    /*
    @Override
    public List<Evento> viewEventoByPatrocinador(Integer id_evento) throws ReadException {
         
        List <Evento> list;
        try{
            list = em.createNamedQuery("findPatrocinadoresByEvento").setParameter("id_evento", em.find(Evento.class, patrocinador)).getResultList();
        }catch (Exception e){
            throw new ReadException(e.getMessage());
        }
        return list;
    }
     */
    @Override
    public List<Patrocinador> viewEventoByPatrocinador(Integer id_evento) throws ReadException {
        List<Patrocinador> list;
        try {
            list = em.createNamedQuery("findPatrocinadoresByEvento", Patrocinador.class)
                    .setParameter("id_evento", id_evento)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return list;
    }

}
