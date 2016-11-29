package co.edu.uniandes.bicibo.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Entidad que representa un usuario en el sistema de BiciBo.
 * @author Abimelec
 */
@Entity
@Table(name = "usuarios", schema = "bicibo")
public class Usuario implements Serializable 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private Integer edad;
    private String nombre;
    private String rutaFoto;

    @OneToMany
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", edad=" + edad +
                ", nombre='" + nombre + '\'' +
                ", rutaFoto='" + rutaFoto + '\'' +
                '}';
    }
}
