/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Trabaja;
import entity.TrabajaId;
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
import javax.ws.rs.core.PathSegment;

/**
 * EJB que implementa un servicio REST para gestionar operaciones CRUD
 * relacionadas con la entidad Trabaja. Proporciona endpoints para la creación,
 * edición, eliminación y consulta de asignaciones de trabajadores a sedes.
 *
 * La clave primaria de la entidad Trabaja es compuesta y se representa en la URI
 * en forma de matriz de parámetros, donde 'id_sede' y 'ud_User' son utilizados
 * como nombres de campo para construir la instancia de clave primaria.
 *
 * Este EJB utiliza técnicas de JAX-RS para la exposición de servicios RESTful.
 *
 * @author Eneko
 * @version 1.0
 * @since 2024-02-01
 */
@Stateless
@Path("entity.trabaja")
public class TrabajaFacadeREST extends AbstractFacade<Trabaja> {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;

    private TrabajaId getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;id_sede=id_sedeValue;ud_User=ud_UserValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entity.TrabajaId key = new entity.TrabajaId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> id_sede = map.get("id_sede");
        if (id_sede != null && !id_sede.isEmpty()) {
            key.setId_sede(new java.lang.Integer(id_sede.get(0)));
        }
        java.util.List<String> ud_User = map.get("ud_User");
        if (ud_User != null && !ud_User.isEmpty()) {
            key.setUd_User(new java.lang.Integer(ud_User.get(0)));
        }
        return key;
    }

    public TrabajaFacadeREST() {
        super(Trabaja.class);
    }
    
    /**
     * Crea una nueva asignación de trabajador a sede.
     *
     * @param entity Objeto Trabaja a ser creado.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Trabaja entity) {
        super.create(entity);
    }
    
    /**
     * Edita una asignación de trabajador existente.
     *
     * @param id Segmento de ruta URI que representa la clave primaria de la entidad Trabaja.
     * @param entity Objeto Trabaja con los datos actualizados.
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Trabaja entity) {
        super.edit(entity);
    }
    
    /**
     * Elimina una asignación de trabajador existente.
     *
     * @param id Segmento de ruta URI que representa la clave primaria de la entidad Trabaja.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entity.TrabajaId key = getPrimaryKey(id);
        super.remove(super.find(key));
    }
    
    /**
     * Busca y devuelve una asignación de trabajador por su clave primaria.
     *
     * @param id Segmento de ruta URI que representa la clave primaria de la entidad Trabaja.
     * @return Objeto Trabaja con los datos de la asignación de trabajador.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Trabaja find(@PathParam("id") PathSegment id) {
        entity.TrabajaId key = getPrimaryKey(id);
        return super.find(key);
    }
    
    /**
     * Obtiene la lista de todas las asignaciones de trabajadores a sedes.
     *
     * @return Lista de todas las asignaciones de trabajadores a sedes.
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Trabaja> findAll() {
        return super.findAll();
    }

    /**
     * Obtiene una lista de asignaciones de trabajadores a sedes en un rango específico.
     *
     * @param from Índice de inicio del rango.
     * @param to Índice de fin del rango.
     * @return Lista de asignaciones de trabajadores a sedes dentro del rango especificado.
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Trabaja> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
    /**
     * Obtiene la cantidad total de asignaciones de trabajadores a sedes.
     *
     * @return Cantidad total de asignaciones de trabajadores a sedes como cadena de texto.
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
