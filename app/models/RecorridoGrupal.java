package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Date;


/**
 * Created by Junior on 3/10/2016.
 */
@Entity
@DynamicUpdate(value=true)
public class RecorridoGrupal
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public Integer id_usuario;

    public String nombre_organizador;

    public long origen;

    public long destino;

    public Date hora_salida;

    public Date hora_llegada;

    public Date fecha_recorrido;

    public int distancia;

    public int tiempoEstimado;

    public int caloriasQuemadas;

    public String infoClima;

    public String esFrecuente;

    @ManyToMany
    List<Usuario> suscritos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_organizador() {
        return nombre_organizador;
    }

    public void setNombre_organizador(String nombre_organizador) {
        this.nombre_organizador = nombre_organizador;
    }

    public long getOrigen() {
        return origen;
    }

    public void setOrigen(long origen) {
        this.origen = origen;
    }

    public long getDestino() {
        return destino;
    }

    public void setDestino(long destino) {
        this.destino = destino;
    }

    public Date getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(Date hora_salida) {
        this.hora_salida = hora_salida;
    }

    public Date getHora_llegada() {
        return hora_llegada;
    }

    public void setHora_llegada(Date hora_llegada) {
        this.hora_llegada = hora_llegada;
    }

    public Date getFecha_recorrido() {
        return fecha_recorrido;
    }

    public void setFecha_recorrido(Date fecha_recorrido) {
        this.fecha_recorrido = fecha_recorrido;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(int tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public int getCaloriasQuemadas() {
        return caloriasQuemadas;
    }

    public void setCaloriasQuemadas(int caloriasQuemadas) {
        this.caloriasQuemadas = caloriasQuemadas;
    }

    public String getInfoClima() {
        return infoClima;
    }

    public void setInfoClima(String infoClima) {
        this.infoClima = infoClima;
    }

    public String getEsFrecuente() {
        return esFrecuente;
    }

    public void setEsFrecuente(String esFrecuente) {
        this.esFrecuente = esFrecuente;
    }

    public List<Usuario> getSuscritos() {
        return suscritos;
    }

    public void setSuscritos(List<Usuario> suscritos) {
        this.suscritos = suscritos;
    }
}