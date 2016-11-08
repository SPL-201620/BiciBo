package com.bicibo.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilson on 8/11/2016.
 */
@Entity
@Table(name = "SitiosAlquiler")
public class SitioAlquiler
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    
    private String nombre;
    
    private String direccionPrincipal;
    
    private int precio;
    
    private int distanciaMaxima;
    
    private List<String> direccionesSucursales;
    
    public SitioAlquiler()
    {
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccionPrincipal() {
        return direccionPrincipal;
    }

    public void setDireccionPrincipal(String direccionPrincipal) {
        this.direccionPrincipal = direccionPrincipal;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getDistanciaMaxima() {
        return distanciaMaxima;
    }

    public void setDistanciaMaxima(int distanciaMaxima) {
        this.distanciaMaxima = distanciaMaxima;
    }
    
    public List<String> getDireccionesSucursales() {
        return direccionesSucursales;
    }

    public void setDireccionesSucursales(List<String> direccionesSucursales) {
        this.direccionesSucursales = direccionesSucursales;
    }

    @Override
    public String toString() {
        return "SitioAlquiler{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccionPrincipal='" + direccionPrincipal + '\'' +
                ", precio=" + precio +
                ", distanciaMaxima='" + distanciaMaxima + '\'' +
                '}';
    }
}
