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
 * Created by santi on 3/10/2016.
 */
@Entity
@DynamicUpdate(value=true)
public class Recorrido
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public Integer id_usuario;

    public boolean realizado = true;

    public long origen;

    public long destino;

    public Date hora_salida;

    public Date hora_llegada;

    public Date fecha_recorrido;

    public int distancia;

    public int tiempoEstimado;

    public int getCaloriasQuemadas() {
        return caloriasQuemadas;
    }

    public void setCaloriasQuemadas(int caloriasQuemadas) {
        this.caloriasQuemadas = caloriasQuemadas;
    }

    public int caloriasQuemadas;

    public String infoClima;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
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

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
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

    public String getInfoClima() {
        return infoClima;
    }

    public void setInfoClima(String infoClima) {
        this.infoClima = infoClima;
    }
}