package co.edu.uniandes.bicibo.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Date;

/**
 * Entidad que representa un Recorrido en el sistema de BiciBo.
 * @author Abimelec
 */
@Entity
@Table(name = "Recorrido", schema = "bicibo")
public class Recorrido implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public Integer id_usuario;

    public boolean realizado = true;

    public String origen;

    public String destino;

    public String hora_salida;

    public String hora_llegada;

    public String fecha_recorrido;

    public String distancia;

    public String tiempoEstimado;
    
    public String caloriasQuemadas;

    public String infoClima;

    public String getCaloriasQuemadas() {
        return caloriasQuemadas;
    }

    public void setCaloriasQuemadas(String caloriasQuemadas) {
        this.caloriasQuemadas = caloriasQuemadas;
    }

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

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public String getHora_llegada() {
        return hora_llegada;
    }

    public void setHora_llegada(String hora_llegada) {
        this.hora_llegada = hora_llegada;
    }

    public String getFecha_recorrido() {
        return fecha_recorrido;
    }

    public void setFecha_recorrido(String fecha_recorrido) {
        this.fecha_recorrido = fecha_recorrido;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(String tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getInfoClima() {
        return infoClima;
    }

    public void setInfoClima(String infoClima) {
        this.infoClima = infoClima;
    }
}
