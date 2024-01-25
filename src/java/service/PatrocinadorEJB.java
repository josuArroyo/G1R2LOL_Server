package service;

import entity.Evento;
import entity.Patrocinador;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Este es el EJB de Patrocinador
 *
 * @author 2dam
 */
@Stateless
public class PatrocinadorEJB implements PatrocinadorInterface {

    @PersistenceContext(unitName = "G1R2LOL_ServerPU")
    private EntityManager em;

    @Override
    public void createPatrocinador(Patrocinador patrocinador) throws CreateException {
        try {
            em.persist(patrocinador);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void deletePatrocinador(Patrocinador patrocinador) throws DeleteException {
        try {
            em.remove(em.merge(patrocinador));
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

    @Override
    public void modifyPatrocinador(Patrocinador patrocinador) throws UpdateException {
        try {
            if (!em.contains(patrocinador)) {
                em.merge(patrocinador);
            }
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public List<Patrocinador> viewPatrocinadores() throws ReadException {
        List<Patrocinador> patrocinadores;
        try {
            patrocinadores = em.createNamedQuery("findAllPatrocinadores", Patrocinador.class).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return patrocinadores;
    }

    @Override
    public List<Patrocinador> viewPatrocinadorByName(String nombre) throws ReadException {
        List<Patrocinador> patrocinadores;
        try {
            patrocinadores = em.createNamedQuery("findForName", Patrocinador.class)
                    .setParameter("nombre", nombre)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return patrocinadores;
    }

    @Override
    public List<Patrocinador> viewPatrocinadorByDuration(Date duracion) throws ReadException {
        List<Patrocinador> patrocinadores;
        try {
            patrocinadores = em.createNamedQuery("findForDuration", Patrocinador.class)
                    .setParameter("DuracionPatrocinio", duracion)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return patrocinadores;
    }

    @Override
    public Patrocinador viewPatrocinadorById(Integer id_patrocinador) throws ReadException {
        Patrocinador patrocinador;
        try {
            patrocinador = em.find(Patrocinador.class, id_patrocinador);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return patrocinador;
    }

    @Override
    public List<Evento> viewEventosByPatrocinador(Integer id_patrocinador) throws ReadException {
        List<Evento> eventos;
        try {
            eventos = em.createNamedQuery("findEventosByPatrocinador", Evento.class)
                    .setParameter("id_patrocinador", id_patrocinador)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return eventos;
    }
}
