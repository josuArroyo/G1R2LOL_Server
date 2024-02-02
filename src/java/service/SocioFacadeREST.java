/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Socio;
import java.util.List;
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

/**
 * EJB que implementa un servicio REST para gestionar operaciones CRUD
 * relacionadas con la entidad Socio. Proporciona endpoints para la creación,
 * edición, eliminación y consulta de socios.
 *
 * Este EJB utiliza técnicas de JAX-RS para la exposición de servicios RESTful.
 *
 * @author Eneko
 * @version 1.0
 * @since 2024-02-01
 */
@Stateless
@Path("entity.socio")
public class SocioFacadeREST extends AbstractFacade<Socio> {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;

    public SocioFacadeREST() {
        super(Socio.class);
    }
    
    /**
     * Crea un nuevo socio.
     *
     * @param entity Objeto Socio a ser creado.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Socio entity) {
        super.create(entity);
    }
    
    /**
     * Edita un socio existente.
     *
     * @param id Identificador único del socio a ser editado.
     * @param entity Objeto Socio con los datos actualizados.
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Socio entity) {
        super.edit(entity);
    }
    
     /**
     * Elimina un socio existente.
     *
     * @param id Identificador único del socio a ser eliminado.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }
    
    /**
     * Busca y devuelve un socio por su identificador único.
     *
     * @param id Identificador único del socio.
     * @return Objeto Socio con los datos del socio buscado.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Socio find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    /**
     * Obtiene la lista de todos los socios.
     *
     * @return Lista de todos los socios.
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Socio> findAll() {
        return super.findAll();
    }
    
    /**
     * Obtiene una lista de socios en un rango específico.
     *
     * @param from Índice de inicio del rango.
     * @param to Índice de fin del rango.
     * @return Lista de socios dentro del rango especificado.
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Socio> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    /**
     * Obtiene la cantidad total de socios.
     *
     * @return Cantidad total de socios como cadena de texto.
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
