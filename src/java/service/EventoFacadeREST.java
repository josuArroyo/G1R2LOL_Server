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
 *
 * @author 2dam
 */
@Path("entity.evento")
public class EventoFacadeREST {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;

    @EJB
    private EventoInterface inter;

    public EventoFacadeREST() {

    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createEvent(@PathParam("id_evento") Integer id_evento, Evento event) {

        try {
            inter.createEvent(event);
        } catch (CreateException e) {
            throw new InternalServerErrorException(e.getMessage());
        }

    }

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

    @DELETE
    @Path("DELETE-Evento/{id_evento}")
    public void deleteEvent(@PathParam("id_evento") Integer id_evento) {
        try {
            inter.deleteEvent(inter.filterEventById(id_evento));
        } catch (DeleteException | ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Evento> viewEvents() {
        try {
            return inter.viewEvents();
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

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
