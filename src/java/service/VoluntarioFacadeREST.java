/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cipher.HashContra;

import entity.Voluntario;
import exception.CreateException;
import exception.UpdateException;
import java.util.List;
import java.util.logging.Level;
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
import java.util.logging.Logger;


/**
 *
 * @author 2dam
 */
@Path("entity.voluntario")
public class VoluntarioFacadeREST {

    private static final Logger LOGGER = Logger.getLogger("/service/VoluntarioFacadeREST");
    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;

    @EJB
    private VoluntarioInterface inter;

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Voluntario volun) {

        try {
            inter.createVoluntario(volun);
        } catch (CreateException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /*
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Voluntario entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }
*/
    
    @GET
    @Path("buscarVoluntarioPorId/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Voluntario find(@PathParam("id") Integer id) {
        try {
            return inter.filtrarVoluntarioPorID(id);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        
        
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Voluntario> findAll() {
        LOGGER.info("Mostrando todos los voluntarios");
        try {
            return inter.viewAllVoluntarios();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
/*
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Voluntario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
/*
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
     */
    @PUT
    @Path("cambiarContra")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void cambiarContra(Voluntario volun) {
        try {
            inter.cambiarContra(volun);
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Path("recuperarContra")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void RecuperarContra(Voluntario volun) {
        try {
            inter.recuperarContra(volun);
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    protected EntityManager getEntityManager() {
        return em;
    }

}
