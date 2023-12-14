package entity;

//Imports.
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@DiscriminatorValue("VOLUNTARIO")
@XmlRootElement
public class Voluntario extends User {

    private static final long serialVersionUID = 1L;
    

     private int numero_Voluntariados;
    
    @ManyToOne
    private List<Sede> sede;
    

    public void setNumero_Voluntariados(int numero_Voluntariados) {
        this.numero_Voluntariados = numero_Voluntariados;
    }

    public void setSede(List<Sede> sede) {
        this.sede = sede;
    }

    public List<Sede> getSede() {
        return sede;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.numero_Voluntariados;
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
        final Voluntario other = (Voluntario) obj;
        if (this.numero_Voluntariados != other.numero_Voluntariados) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Voluntario{" + "numero_Voluntariados=" + numero_Voluntariados + '}';
    }

    
    
}
