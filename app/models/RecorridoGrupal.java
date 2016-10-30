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
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public Integer id_usuario;

    public String nombre_organizador;

    public String origen;

    public String destino;

    public String hora_salida;

    public String hora_llegada;

    public String fecha_recorrido;

    //public int distancia;

    //public int tiempoEstimado;

    //public int caloriasQuemadas;

    //public String infoClima;

    public String esFrecuente;

    public Boolean getRegistrado() {
        return registrado;
    }

    public void setRegistrado(Boolean registrado) {
        this.registrado = registrado;
    }

    private Boolean registrado;

    @OneToMany
    List<Usuario> suscritos;

    public RecorridoGrupal()
    {
        suscritos = new ArrayList<>();
    }

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

    //public int getDistancia() {
      //  return distancia;
    //}

    //public void setDistancia(int distancia) {
      //  this.distancia = distancia;
    //}

    //public int getTiempoEstimado() {
 //       return tiempoEstimado;
   // }

    //public void setTiempoEstimado(int tiempoEstimado) {
        //this.tiempoEstimado = tiempoEstimado;
    //}

    //public int getCaloriasQuemadas() {
     //   return caloriasQuemadas;
    //}

    //public void setCaloriasQuemadas(int caloriasQuemadas) {
        //this.caloriasQuemadas = caloriasQuemadas;
    //}

    //public String getInfoClima() {
        //return infoClima;
   // }

    //public void setInfoClima(String infoClima) {
      //  this.infoClima = infoClima;
    //}

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