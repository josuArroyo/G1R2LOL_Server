package entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "sede", schema = "LOLdb")

@XmlRootElement
public class Voluntario extends User {

    @Id
    private String DNI;
    @ManyToOne
    private List<Sede> sede;
    private int numero_Voluntariados;

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getDNI() {
        return DNI;
    }

    public void setNumero_Voluntariados(int numero_Voluntariados) {
        this.numero_Voluntariados = numero_Voluntariados;
    }

    public void setSede(List<Sede> sede) {
        this.sede = sede;
    }

    public List<Sede> getSede() {
        return sede;
    }

}
