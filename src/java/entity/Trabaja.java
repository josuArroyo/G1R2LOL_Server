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
import java.util.List;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "trabaja", schema = "LOLdb")
public class Trabaja implements Serializable {

    private String DNI;
    private Integer id_sede;
    @EmbeddedId
    private String puesto;
    @MapsId("socio")
    @ManyToOne
    private Socio socio;
    @MapsId("sede")
    @ManyToOne
    private Sede sede;

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public Integer getId_sede() {
        return id_sede;
    }

    public void setId_sede(Integer id_sede) {
        this.id_sede = id_sede;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.DNI);
        hash = 67 * hash + Objects.hashCode(this.id_sede);
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
        final Trabaja other = (Trabaja) obj;
        if (!Objects.equals(this.DNI, other.DNI)) {
            return false;
        }
        if (!Objects.equals(this.id_sede, other.id_sede)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Trabaja{" + "DNI=" + DNI + ", id_sede=" + id_sede + ", puesto=" + puesto + ", socio=" + socio + ", sede=" + sede + '}';
    }

}
