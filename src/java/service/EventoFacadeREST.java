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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa un servicio REST para la entidad Evento. Proporciona
 * métodos para realizar operaciones CRUD relacionadas con eventos, como la
 * creación, modificación, eliminación y consulta de eventos. Esta clase interactúa
 * con la base de datos a través de JPA (Java Persistence API) y maneja excepciones
 * específicas para cada operación.
 *
 * Los métodos de esta clase permiten gestionar eventos, incluyendo la obtención
 * de eventos por su identificador, por aforo máximo, por fecha y la obtención de
 * patrocinadores asociados a un evento.
 *
 * @author Eneko
 * @version 1.0
 * @since 2024-02-01
 */
@Path("entity.evento")
public class EventoFacadeREST {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;

    @EJB
    private EventoInterface inter;

    /**
     * Crea un nuevo evento en la base de datos.
     *
     * @param id_evento Identificador único del evento.
     * @param event Evento a ser creado.
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createEvent(@PathParam("id_evento") Integer id_evento, Evento event) {

        try {
            inter.createEvent(event);
        } catch (CreateException e) {
            throw new InternalServerErrorException(e.getMessage());
        }

    }

    /**
     * Modifica un evento existente en la base de datos.
     *
     * @param id_evento Identificador único del evento.
     * @param event Evento con los datos actualizados.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void modifyEvent(@PathParam("id_evento") Integer id_evento, Evento event) {
        try {
            inter.modifyEvent(event);
        } catch (UpdateException e) {
            System.out.println(e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Elimina un evento existente de la base de datos.
     *
     * @param id_evento Identificador único del evento a ser eliminado.
     */
    @DELETE
    @Path("DELETE-Evento/{id_evento}")
    public void deleteEvent(@PathParam("id_evento") Integer id_evento) {
        try {
            inter.deleteEvent(inter.filterEventById(id_evento));
        } catch (DeleteException | ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Obtiene una lista de todos los eventos almacenados en la base de datos.
     *
     * @return Lista de eventos.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Evento> viewEvents() {
        try {
            return inter.viewEvents();
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Obtiene una lista de eventos filtrada por su identificador único.
     *
     * @param id_evento Identificador único del evento.
     * @return Lista de eventos filtrada por identificador único.
     */
    @GET
    @Path("FindEventBy/{id_evento}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Evento> findEventByEventId(@PathParam("id_evento") Integer id_evento) {
        try {
            return inter.findEventByEventId(id_evento);
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Obtiene una lista de eventos filtrada por aforo máximo.
     *
     * @param aforo Aforo máximo por el cual filtrar los eventos.
     * @return Lista de eventos filtrada por aforo máximo.
     */
    @GET
    @Path("ViewEventByAforo/{aforo}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Evento> viewSedeByAforoMax(@PathParam("aforo") Integer aforo) {
        try {
            return inter.viewEventoAforoMax(aforo);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Obtiene una lista de eventos filtrada por fecha.
     *
     * @param fechaEvento Fecha por la cual filtrar los eventos.
     * @return Lista de eventos filtrada por fecha.
     */
    @GET
    @Path("viewEventByDate/{fechaEvento}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Evento> viewEventoByDate(@PathParam("fechaEvento") String fechaEvento) {

        try {
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formateador.parse(fechaEvento);
            return inter.viewEventoByDate(date);

        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }

    }

    /**
     * Obtiene una lista de patrocinadores asociados a un evento específico.
     *
     * @param id_evento Identificador único del evento.
     * @return Lista de patrocinadores asociados al evento.
     */
    @GET
    @Path("getPatrocinadorByEvento/{id_evento}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Patrocinador> findPatrocinadoresByEvento(@PathParam("id_evento") Integer id_evento) {
        List<Patrocinador> lista;
        try {
            lista = inter.viewEventoByPatrocinador(id_evento);
            return lista;
        } catch (ReadException ex) {
            System.out.println(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    protected EntityManager getEntityManager() {
        return em;
    }

}

