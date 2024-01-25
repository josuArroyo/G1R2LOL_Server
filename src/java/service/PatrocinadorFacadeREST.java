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

/**
 *
 * @author 2dam
 */
@Path("entity.patrocinador")
public class PatrocinadorFacadeREST {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;

    @EJB
    private PatrocinadorInterface patrocinadorInterface;

    public PatrocinadorFacadeREST() {

    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createPatrocinador(@PathParam("id_patrocinador") Integer id_patrocinador, Patrocinador patrocinador) {
        try {
            patrocinadorInterface.createPatrocinador(patrocinador);
        } catch (CreateException ex) {
            Logger.getLogger(PatrocinadorFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void modifyPatrocinador(@PathParam("id_patrocinador") Integer id_patrocinador, Patrocinador patrocinador) {
        try {
            patrocinadorInterface.modifyPatrocinador(patrocinador);
        } catch (UpdateException e) {
            System.out.println(e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DELETE
    @Path("deletePatrocinador/{id_patrocinador}")
    public void deletePatrocinador(@PathParam("id_patrocinador") Integer id_patrocinador) {
        try {
            patrocinadorInterface.deletePatrocinador(patrocinadorInterface.viewPatrocinadorById(id_patrocinador));
        } catch (ReadException | DeleteException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Patrocinador> viewPatrocinadores() {
        try {
            return patrocinadorInterface.viewPatrocinadores();
        } catch (ReadException ex) {
            System.out.println(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Path("ViewBy/String/{nombre}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Patrocinador> viewPatrocinadorByName(@PathParam("nombre") String nombre) {
        try {
            return patrocinadorInterface.viewPatrocinadorByName(nombre);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Path("findByDuracion/{DuracionPatrocinio}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Patrocinador> viewPatrocinadorByDuration(@PathParam("DuracionPatrocinio") String DuracionPatrocinio) {
        try {
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formateador.parse(DuracionPatrocinio);
            return patrocinadorInterface.viewPatrocinadorByDuration(date);
        } catch (ReadException | ParseException ex) {
            Logger.getLogger(PatrocinadorFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Path("getEventosByPatrocinador/{id_patrocinador}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Evento> findEventosByPatrocinador(@PathParam("id_patrocinador") Integer id_patrocinador) {
        try {
            return patrocinadorInterface.viewEventosByPatrocinador(id_patrocinador);
        } catch (ReadException ex) {
            System.out.println(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    protected EntityManager getEntityManager() {
        return em;
    }
}
