package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by santi on 5/10/2016.
 */
@Entity
@DynamicUpdate(value=true)
public class Usuario
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String email;
    private String password;
    private Integer edad;
    private String nombre;
    private String rutaFoto;

    @OneToMany
    @JsonIgnore
    List<Usuario> amigos;

    @OneToMany
    List<Recorrido> recorridos;

    @OneToMany
    List<RecorridoGrupal> recorridosGrupalesAdmin;


    public Usuario()
    {
        amigos = new ArrayList<>();
        recorridos = new ArrayList<>();
        recorridosGrupalesAdmin = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public List<Usuario> getAmigos() {
        return amigos;
    }

    public void setAmigos(List<Usuario> amigos) {
        this.amigos = amigos;
    }

    public List<Recorrido> getRecorridos() {
        return recorridos;
    }

    public void setRecorridos(List<Recorrido> recorridos) {
        this.recorridos = recorridos;
    }


    public List<RecorridoGrupal> getRecorridosGrupalesAdmin() {
        return recorridosGrupalesAdmin;
    }

    public void setRecorridosGrupalesAdmin(List<RecorridoGrupal> recorridosGrupalesAdmin) {
        this.recorridosGrupalesAdmin = recorridosGrupalesAdmin;
    }



    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", edad=" + edad +
                ", nombre='" + nombre + '\'' +
                ", rutaFoto='" + rutaFoto + '\'' +
                '}';
    }
}
