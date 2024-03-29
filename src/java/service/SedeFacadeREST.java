/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Evento;
import entity.Sede;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
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
 * EJB que implementa un servicio REST para gestionar operaciones CRUD
 * relacionadas con la entidad Sede. Proporciona endpoints para la creación,
 * modificación, eliminación y consulta de sedes.
 *
 * Este EJB utiliza técnicas de JAX-RS para la exposición de servicios RESTful.
 *
 * @author Eneko
 * @version 1.0
 * @since 2024-02-01
 */
@Path("entity.sede")
public class SedeFacadeREST {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;

    @EJB
    private SedeInterface inter;

    public SedeFacadeREST() {

    }
    
    /**
     * Crea una nueva sede.
     *
     * @param id_sede Identificador único de la sede.
     * @param sede Objeto Sede a ser creado.
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createSede(@PathParam("id_sede") Integer id_sede, Sede sede) {
        try {
            inter.createSede(sede);
        } catch (CreateException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    
    /**
     * Modifica una sede existente.
     *
     * @param id_sede Identificador único de la sede a ser modificada.
     * @param sede Objeto Sede con los datos actualizados.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void modifySede(@PathParam("id_sede") Integer id_sede, Sede sede) {
        try {
            inter.modifySede(sede);
        } catch (UpdateException e) {
            System.out.println(e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    
    /**
     * Elimina una sede existente.
     *
     * @param id_sede Identificador único de la sede a ser eliminada.
     */
    @DELETE
    @Path("DELETE-Sede/{id_sede}")
    public void deleteSede(@PathParam("id_sede") Integer id_sede) {
        try {
            inter.deleteSede(inter.viewSedeById(id_sede));
        } catch (DeleteException e) {

        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    /**
     * Obtiene la lista de todas las sedes.
     *
     * @return Lista de todas las sedes.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Sede> viewSedes() {
        try {
            return inter.viewSedes();
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    
    /**
     * Obtiene la lista de sedes filtrada por país.
     *
     * @param pais País por el cual filtrar las sedes.
     * @return Lista de sedes filtrada por país.
     */
    @GET
    @Path("ViewSedeBy/String/{pais}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Sede> viewSedeByCountry(@PathParam("pais") String pais) {
        try {
            return inter.viewSedeByCountry(pais);
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    
    /**
     * Obtiene la lista de sedes filtrada por aforo máximo.
     *
     * @param aforoMax Aforo máximo por el cual filtrar las sedes.
     * @return Lista de sedes filtrada por aforo máximo.
     */
    @GET
    @Path("ViewSedeByAforoMax/{aforoMax}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Sede> viewSedeByAforoMax(@PathParam("aforoMax") Integer aforoMax) {
        try {
            return inter.viewSedeAforoMax(aforoMax);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    /**
     * Obtiene una sede por su identificador único.
     *
     * @param id_sede Identificador único de la sede.
     * @return Objeto Sede con los datos de la sede buscada.
     */
    @GET
    @Path("ViewSedeById/{id_sede}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Sede viewSedeById(@PathParam("id_sede") Integer id_sede) {
        try {
            return inter.viewSedeById(id_sede);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    /**
     * Obtiene la lista de eventos asociados a una sede.
     *
     * @param id_sede Identificador único de la sede.
     * @return Lista de eventos asociados a la sede.
     */
    @GET
    @Path("viewEventoBySede/{id_sede}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Evento> findEventoBySede(@PathParam("id_sede") Integer id_sede) {
        List<Evento> lista;
        try {
            lista = inter.viewEventoBySede(id_sede);
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
