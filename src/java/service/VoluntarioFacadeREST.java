/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cipher.HashContra;
import cipher.mailCypher;
import entity.Voluntario;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author 2dam
 */
@Stateless
@Path("entity.voluntario")
public class VoluntarioFacadeREST extends AbstractFacade<Voluntario> {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;
    
    
    
    @EJB
    private VoluntarioInterface inter;
    
    
    public VoluntarioFacadeREST() {
        super(Voluntario.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Voluntario entity) {
        super.create(entity);
    }

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

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Voluntario find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Voluntario> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Voluntario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    
    private AbstractFacade abs;
    
    @POST
    @Path("/recuperarContra")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response recuperarContra(Voluntario volun) {
        try {
            inter.recuperarContra(volun);
            return Response.status(Response.Status.OK).entity("Contraseña recuperada exitosamente.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al recuperar la contraseña.").build();
        }
    }

    private String generarNuevaContrasena(String email) {
        // Lógica para generar y enviar una nueva contraseña por correo
        mailCypher emailCypher = new mailCypher();
        return emailCypher.sendEmail(email);
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
