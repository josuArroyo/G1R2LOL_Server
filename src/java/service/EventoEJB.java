/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Evento;
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
 * Este es el EJB de Evento
 *
 * @author Josu
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

    @Override
    public void deleteEvent(Evento event) throws DeleteException {
        try {
            em.remove(em.merge(event));
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }

    }

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

    @Override
    public void createEvent(Evento event) throws CreateException {
        try {
            em.persist(event);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

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

}
