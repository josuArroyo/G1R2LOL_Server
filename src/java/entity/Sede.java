/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Eneko.
 */
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "sede", schema = "LOLdb")
@NamedQueries({
    @NamedQuery(name = "findAllSede",
            query = "SELECT s FROM Sede s ORDER BY s.id_sede")
    ,
@NamedQuery(name = "findSeded",
            query = "SELECT s FROM sede s where s.idSede= :idSede")
    ,

@NamedQuery(name = "findSedeByCountry",
            query = "SELECT s FROM sede s where s.pais= :pais")
    ,
@NamedQuery(name = "findSedeAforoMax",
            query = "SELECT s FROM sede s where s.aforoMax= :aforoMax")
})
@XmlRootElement
public class Sede implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_sede;
    
    @Temporal(TemporalType.DATE)
    private Date finDeContrato;
    
    private String pais;
    private Integer aforoMax;
    private Integer numVolMax;
    private String ubicacion;

    @OneToMany(mappedBy = "sede")
    private List<Trabaja> trabaja;
    
    @OneToMany(mappedBy = "sede")
    private List<Evento> evento;

    @OneToMany(mappedBy = "sede")
    private Voluntario voluntario;

    public void setId_sede(Integer id_sede) {
        this.id_sede = id_sede;
    }

    public Integer getId_sede() {
        return id_sede;
    }

    public void setFinDeContrato(Date finDeContrato) {
        this.finDeContrato = finDeContrato;
    }

    public Date getFinDeContrato() {
        return finDeContrato;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPais() {
        return pais;
    }

    public void setNumVolMax(Integer numVolMax) {
        this.numVolMax = numVolMax;
    }

    public Integer getNumVolMax() {
        return numVolMax;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public Integer getAforoMax() {
        return aforoMax;
    }

    public void setAforoMax(Integer aforoMax) {
        this.aforoMax = aforoMax;
    }

    public List<Trabaja> getTrabaja() {
        return trabaja;
    }

    public void setTrabaja(List<Trabaja> trabaja) {
        this.trabaja = trabaja;
    }

    public void setEvento(List<Evento> evento) {
        this.evento = evento;
    }

    public List<Evento> getEvento() {
        return evento;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id_sede;
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
        final Sede other = (Sede) obj;
        if (this.id_sede != other.id_sede) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sede{" + "id_sede=" + id_sede + ", FinDeContrato=" + finDeContrato + ", Pais=" + pais + ", NumVolMax=" + numVolMax + ", Ubicacion=" + ubicacion + ", Trabaja=" + trabaja + ", evento=" + evento + ", voluntario=" + voluntario + '}';
    }

}
