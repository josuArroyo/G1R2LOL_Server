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
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "socio", schema = "LOLdb")
public class Socio extends User {

    private int Nivel_de_Socio;
    
    @OneToMany(mappedBy = "socio")
    private List<Sede> sede;
    @OneToMany(mappedBy = "socio")
    private List<Evento> evento;
   

    public void setEvento(List<Evento> evento) {
        this.evento = evento;
    }

    public List<Evento> getEvento() {
        return evento;
    }

    public int getNivel_de_Socio() {
        return Nivel_de_Socio;
    }

    public void setNivel_de_Socio(int Nivel_de_Socio) {
        this.Nivel_de_Socio = Nivel_de_Socio;
    }

    public void setSede(List<Sede> sede) {
        this.sede = sede;
    }

    public List<Sede> getSede() {
        return sede;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.Nivel_de_Socio;
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
        final Socio other = (Socio) obj;
        if (this.Nivel_de_Socio != other.Nivel_de_Socio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Socio{" + "Nivel_de_Socio=" + Nivel_de_Socio + '}';
    }

  

  

}
