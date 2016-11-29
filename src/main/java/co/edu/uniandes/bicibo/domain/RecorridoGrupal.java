package co.edu.uniandes.bicibo.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Date;

/**
 * Entidad que representa un Recorrido Grupal en el sistema de BiciBo.
 * @author Abimelec
 */
@Entity
@Table(name = "recorridosGrupales", schema = "bicibo")
public class RecorridoGrupal implements Serializable  
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public Integer id_usuario;

    public String nombre_organizador;

    public String origen;

    public String destino;

    public String hora_salida;

    public String hora_llegada;

    public String fecha_recorrido;

    public int distancia;

    public int tiempoEstimado;

    public int caloriasQuemadas;

    public String infoClima;

    public String frecuencia;
    
    public List<Usuario> inscritos;

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

	public String getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}
	
	public List<Usuario> getInscritos() {
		return inscritos;
	}

	public void setInscritos(List<Usuario> inscritos) {
		this.inscritos = inscritos;
	}

	@Override
	public String toString() 
	{
		return "RecorridoGrupal [id=" + id + 
				", id_usuario=" + id_usuario + 
				", nombre_organizador=" + nombre_organizador
				+ ", origen=" + origen + 
				", destino=" + destino + 
				", hora_salida=" + hora_salida + 
				", hora_llegada=" + hora_llegada + 
				", fecha_recorrido=" + fecha_recorrido + 
				", distancia=" + distancia + 
				", tiempoEstimado=" + tiempoEstimado + 
				", caloriasQuemadas=" + caloriasQuemadas + 
				", infoClima=" + infoClima + 
				", frecuencia=" + frecuencia + 
				", inscritos=" + inscritos + 
				"]";
	}

    
}
