/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author 2dam
 */
@Embeddable
public class TrabajaId implements Serializable {

    private static final long serialVersionUID = 1L;
 
    private Integer id_sede;
    private Integer ud_User;

    public Integer getId_sede() {
        return id_sede;
    }

    public void setId_sede(Integer id_sede) {
        this.id_sede = id_sede;
    }

    public Integer getUd_User() {
        return ud_User;
    }

    public void setUd_User(Integer ud_User) {
        this.ud_User = ud_User;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id_sede);
        hash = 67 * hash + Objects.hashCode(this.ud_User);
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
        final TrabajaId other = (TrabajaId) obj;
        if (!Objects.equals(this.id_sede, other.id_sede)) {
            return false;
        }
        if (!Objects.equals(this.ud_User, other.ud_User)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TrabajaId{" + "id_sede=" + id_sede + ", ud_User=" + ud_User + '}';
    }
    
    
    
}
