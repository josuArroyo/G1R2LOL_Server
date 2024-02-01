/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.User;
import entity.UserType;
import exception.ReadException;
import java.util.List;
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
 * relacionadas con la entidad User. Proporciona endpoints para la creación,
 * edición, eliminación y consulta de usuarios, así como consultas específicas
 * relacionadas con el tipo de usuario y la autenticación.
 *
 * @author 2dam
 * @version 1.0
 * @since 2024-02-01
 */
@Stateless
@Path("entity.user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;

    @EJB
    private UserInterface inter;

    /**
     * Constructor por defecto que llama al constructor de la superclase
     * AbstractFacade inicializando la clase base con la entidad User.
     */
    public UserFacadeREST() {
        super(User.class);
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param entity Objeto User a ser creado.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(User entity) {
        super.create(entity);
    }

    /**
     * Edita un usuario existente.
     *
     * @param id ID del usuario a ser editado.
     * @param entity Objeto User con los datos actualizados.
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, User entity) {
        super.edit(entity);
    }

    /**
     * Elimina un usuario existente.
     *
     * @param id ID del usuario a ser eliminado.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    /**
     * Busca y devuelve un usuario por su ID.
     *
     * @param id ID del usuario a ser buscado.
     * @return Objeto User con los datos del usuario encontrado.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    /**
     * Obtiene la lista de todos los usuarios.
     *
     * @return Lista de todos los usuarios.
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findAll() {
        return super.findAll();
    }

    /**
     * Obtiene una lista de usuarios en un rango específico.
     *
     * @param from Índice de inicio del rango.
     * @param to Índice de fin del rango.
     * @return Lista de usuarios dentro del rango especificado.
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     * Obtiene la cantidad total de usuarios.
     *
     * @return Cantidad total de usuarios como cadena de texto.
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    /**
     * Busca y devuelve usuarios autenticados por correo electrónico y contraseña.
     *
     * @param email Correo electrónico del usuario.
     * @param passwd Contraseña del usuario.
     * @return Lista de usuarios autenticados.
     * @throws ReadException Si ocurre un error durante la autenticación.
     */
    @GET
    @Path("findUserByEmailAndPasswd/{email}/{passwd}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findUserByEmailAndPasswd(@PathParam("email") String email, @PathParam("passwd") String passwd) throws ReadException {
        try {
            return inter.findUserByEmailAndPasswd(email, passwd);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Busca y devuelve usuarios por tipo de usuario.
     *
     * @param userType Tipo de usuario a ser filtrado.
     * @return Lista de usuarios del tipo especificado.
     * @throws ReadException Si ocurre un error durante la consulta.
     */
    @GET
    @Path("findForUserType/{userType}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findForUserType(@PathParam("userType") UserType userType) throws ReadException {
        try {
            return inter.findForUserType(userType);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }
    
    /**
     * Busca y devuelve usuarios por correo electrónico.
     *
     * @param email Correo electrónico del usuario a ser buscado.
     * @return Lista de usuarios con el correo electrónico especificado.
     */
    @GET
    @Path("findUserforEmail/{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findUserforEmail(@PathParam("email") String email)  {
        try {
            return inter.viewByEmail(email);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Obtiene el EntityManager asociado a la clase.
     *
     * @return EntityManager asociado a la clase.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}

