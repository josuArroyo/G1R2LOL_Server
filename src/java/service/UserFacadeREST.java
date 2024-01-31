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
 *
 * @author 2dam
 */
@Stateless
@Path("entity.user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;

    @EJB
    private UserInterface inter;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(User entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, User entity) {
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
    public User find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

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

    @Override

    protected EntityManager getEntityManager() {
        return em;
    }

}
