/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Egoitz Fernandez
 */
@Entity
@Table(name = "patrocinador", schema = "LOLdb")
@NamedQueries({
    @NamedQuery(name = "findAllPatrocinador",
            query = "SELECT p FROM Patrocinador p"
    )
    ,
@NamedQuery(name = "findForDuration",
            query = "SELECT p FROM Patrocinador p where duracion = :duracion"
    )
    ,
@NamedQuery(name = "findForName",
            query = "SELECT p FROM Patrocinador p where nombre = :nombre"
    
    )
           ,
@NamedQuery(name = "findForEvento",
            query = "SELECT p FROM Patrocinador p where evento = :evento"
)
})
@XmlRootElement
public class Patrocinador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_Patrocinador;
    private String Nombre;
    private String Descripcion;
    private String email;
    private Integer telefono;
    @Temporal(TemporalType.DATE)
    private Date DuracionPatrocinio;

    @ManyToMany(mappedBy = "patrocinador")
    private List<Evento> evento;

    public void setId_Patrocinador(Integer id_Patrocinador) {
        this.id_Patrocinador = id_Patrocinador;
    }

    public Integer getId_Patrocinador() {
        return id_Patrocinador;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setDuracionPatrocinio(Date DuracionPatrocinio) {
        this.DuracionPatrocinio = DuracionPatrocinio;
    }

    public Date getDuracionPatrocinio() {
        return DuracionPatrocinio;
    }

    public void setEvento(List<Evento> evento) {
        this.evento = evento;
    }

    @XmlTransient
    public List<Evento> getEvento() {
        return evento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id_Patrocinador;
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
        final Patrocinador other = (Patrocinador) obj;
        if (this.id_Patrocinador != other.id_Patrocinador) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Patrocinador{" + "id_Patrocinador=" + id_Patrocinador + '}';
    }

}
