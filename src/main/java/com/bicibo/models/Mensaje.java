package com.bicibo.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilson on 8/11/2016.
 */
@Entity
@Table(name = "Mensajes")
public class Mensaje
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    
    @OneToMany
    private Usuario usuarioOrigen;
    @OneToMany
    private Usuario usuarioDestino;
    
    private String mensaje;
    private String fechaHora;
    
    public Mensaje()
    {
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuarioOrigen() {
        return usuarioOrigen;
    }

    public void setUsuarioOrigen(Usuario usuarioOrigen) {
        this.usuarioOrigen = usuarioOrigen;
    }

    public Usuario getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(Usuario usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "id=" + id +
                ", usuarioOrigen='" + usuarioOrigen + '\'' +
                ", usuarioDestino='" + usuarioDestino + '\'' +
                ", mensaje=" + mensaje +
                ", fechaHora='" + fechaHora + '\'' +
                '}';
    }
}
