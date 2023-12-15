/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Josu
 */
@Entity
@Table(name = "Evento", schema = "LOLdb")
@NamedQueries({
    @NamedQuery(name = "viewAllEvents", query = "SELECT e FROM Evento e ORDER BY e.id_evento")
    ,
    @NamedQuery(name = "findEventByEventId", query = "SELECT e FROM Evento e WHERE e.id_evento = :idEvento")
    ,
    @NamedQuery(name = "findEventByAforo", query = "SELECT e FROM Evento e WHERE e.aforo = :aforo")
    ,
    @NamedQuery(name = "findEventByDate", query = "SELECT e FROM Evento e WHERE e.fechaEvento = :fechaEvento")
    ,    
    @NamedQuery(name = "findEventBySede", query = "SELECT e FROM Evento e WHERE e.sede = :sede")
    ,    
    @NamedQuery(name = "findEventByPatrocinador", query = "SELECT e FROM Evento e WHERE e.patrocinador = :patrocinador")
})

@XmlRootElement
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_evento;

    private String nombre;

    @Temporal(TemporalType.DATE)
    private Date fechaEvento;

    private String descripcion;

    private Integer aforo;

    private boolean catering;

    @ManyToOne
    private Sede sede;

    @ManyToOne
    private Socio socio;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "evento_patrocinador", schema = "LOLdb")
    private Set<Patrocinador> patrocinador;

    public Integer getId_evento() {
        return id_evento;
    }

    public void setId_evento(Integer id_evento) {
        this.id_evento = id_evento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getAforo() {
        return aforo;
    }

    public void setAforo(Integer aforo) {
        this.aforo = aforo;
    }

    public boolean isCatering() {
        return catering;
    }

    public void setCatering(boolean catering) {
        this.catering = catering;
    }

    @XmlTransient
    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {                                                                         
        this.socio = socio;
    }

    @XmlTransient
    public Set<Patrocinador> getPatrocinador() {
        return patrocinador;
    }

    public void setPatrocinador(Set<Patrocinador> patrocinador) {
        this.patrocinador = patrocinador;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id_evento;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evento other = (Evento) obj;
        if (this.id_evento != other.id_evento) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Evento{" + "id_evento=" + id_evento + '}';
    }

    

}
