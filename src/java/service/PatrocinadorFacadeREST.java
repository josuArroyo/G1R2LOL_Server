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
import java.text.ParseException;
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
import javax.ws.rs.core.Response;

/**
 * Clase RESTful que expone servicios web para realizar operaciones CRUD
 * relacionadas con la entidad Patrocinador. Proporciona métodos para la creación,
 * modificación, eliminación y consulta de patrocinadores.
 *
 * Este servicio interactúa con la base de datos a través de JPA (Java Persistence API)
 * y utiliza la interfaz PatrocinadorInterface para realizar operaciones sobre
 * la entidad Patrocinador.
 *
 * Los servicios RESTful siguen el protocolo HTTP y utilizan las anotaciones JAX-RS
 * para definir las operaciones.
 *
 * @author Eneko
 * @version 1.0
 * @since 2024-02-01
 */
@Path("entity.patrocinador")
@Stateless
public class PatrocinadorFacadeREST {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;

    @EJB
    private PatrocinadorInterface pat;

    Patrocinador patrocinador = new Patrocinador();

    /**
     * Crea un nuevo patrocinador.
     *
     * @param patrocinador Patrocinador a ser creado.
     * @throws CreateException Si hay un error al persistir el patrocinador en la base de datos.
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Patrocinador patrocinador) throws CreateException {
        try {
            pat.createPatrocinador(patrocinador);
        } catch (CreateException ex) {
            Logger.getLogger(PatrocinadorFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Modifica un patrocinador existente.
     *
     * @param id_patrocinador Identificador único del patrocinador a ser modificado.
     * @param patrocinador Patrocinador con los datos actualizados.
     * @throws UpdateException Si hay un error al actualizar el patrocinador en la base de datos.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id_patrocinador") Integer id_patrocinador, Patrocinador patrocinador) {
        try {
            pat.modifyPatrocinador(patrocinador);
        } catch (UpdateException e) {
            System.out.println(e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Elimina un patrocinador existente.
     *
     * @param id_patrocinador Identificador único del patrocinador a ser eliminado.
     */
    @DELETE
    @Path("deletePatrocinador/{id_patrocinador}")
    public void remove(@PathParam("id_patrocinador") int id_patrocinador) {
        try {
            pat.deletePatrocinador(pat.viewPatrocinadorById(id_patrocinador));

        } catch (ReadException | DeleteException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Obtiene una lista de patrocinadores filtrada por nombre.
     *
     * @param nombre Nombre por el cual filtrar los patrocinadores.
     * @return Lista de patrocinadores filtrada por nombre.
     * @throws ReadException Si hay un error al recuperar los patrocinadores desde la base de datos.
     */
    @GET
    @Path("ViewBy/String/{nombre}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Patrocinador> viewPatrocinadorByName(@PathParam("nombre") String nombre) throws ReadException {
        try {
            return pat.viewPatrocinadorByName(nombre);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Obtiene una lista de todos los patrocinadores.
     *
     * @return Lista de todos los patrocinadores.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Patrocinador> findAll() {
        try {
            return pat.viewPatrocinadores();
        } catch (ReadException ex) {
            System.out.println(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Obtiene una lista de patrocinadores filtrada por duración de patrocinio.
     *
     * @param DuracionPatrocinio Duración por la cual filtrar los patrocinadores.
     * @return Lista de patrocinadores filtrada por duración de patrocinio.
     */
    @GET
    @Path("findByDuracion/{DuracionPatrocinio}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Patrocinador> viewPatrocinadorByDuration(@PathParam("DuracionPatrocinio") String DuracionPatrocinio) {

        try {
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formateador.parse(DuracionPatrocinio);
            return pat.viewPatrocinadorByDuration(date);
        } catch (ReadException | ParseException ex) {
            Logger.getLogger(PatrocinadorFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Obtiene una lista de eventos asociados a un patrocinador.
     *
     * @param id_patrocinador Identificador único del patrocinador.
     * @return Lista de eventos asociados al patrocinador.
     */
    @GET
    @Path("getEventosByPatrocinador/{id_patrocinador}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Evento> findEventosByPatrocinador(@PathParam("id_patrocinador") Integer id_patrocinador) {
        try {
            return pat.viewEventosByPatrocinador(id_patrocinador);
        } catch (ReadException ex) {
            System.out.println(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    protected EntityManager getEntityManager() {
        return em;
    }
}

