/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Egoitz..
 */
@Entity
        @NamedQueries({
    @NamedQuery(name = "findUserByEmailAndPasswd",
            query = "SELECT u FROM User u where email = :email and passwd = :passwd"
    )
    ,
@NamedQuery(name = "findForUserType",
            query = "SELECT u FROM User u where userType = :userType"
    )
    ,
@NamedQuery(name = "findForUserName",
            query = "SELECT u FROM User u where nombre = :nombre"
    )
    ,

@NamedQuery(name = "findAllUser",
            query = "SELECT u FROM User u ORDER BY u.id_user"
    ),
 @NamedQuery(name = "findUserByMail",
             query = "SELECT u FROM User u WHERE u.email= :email"
     )


})
@DiscriminatorColumn(name = "UserType", discriminatorType = DiscriminatorType.STRING)
@Table(name = "user", schema = "LOLdb")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@XmlRootElement
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_user;

    @Column(unique = true)
    private String DNI;

    private String nombre;
    private String apellido;
    private Integer telefono;
    private String email;
    private String passwd;
    private String ConfirmPasswd;

    @Enumerated(EnumType.STRING)
    @Column(name = "UserType", insertable = false, updatable = false)
    private UserType userType;

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getDNI() {
        return DNI;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApellido() {
        return apellido;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public String getConfirmPasswd() {
        return ConfirmPasswd;
    }

    public void setConfirmPasswd(String ConfirmPasswd) {
        this.ConfirmPasswd = ConfirmPasswd;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id_user);
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
        final User other = (User) obj;
        if (!Objects.equals(this.id_user, other.id_user)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id_user=" + id_user + '}';
    }

}
