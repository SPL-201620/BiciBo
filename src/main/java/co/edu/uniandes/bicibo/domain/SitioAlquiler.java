package co.edu.uniandes.bicibo.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Entidad que representa un mensaje en el sistema de BiciBo.
 * @author Wilson
 */
@Entity
@Table(name = "sitiosAlquiler", schema = "bicibo")
public class SitioAlquiler implements Serializable 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
	private String nombre;
    private String direccionPrincipal;
    @OneToOne
    private PuntoUbicacion ubicacion;
    
    @OneToMany
    private List<PuntoUbicacion> estacionesEntrega;
    
    private boolean abreEntreSemana;
    private boolean abreSabado;
    private boolean abreDomingo;
    
    private String horaAbre;
    private String horaCierra;
    
    private int tarifa;
    private int limiteKm;
    
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
	public PuntoUbicacion getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(PuntoUbicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	public List<PuntoUbicacion> getEstacionesEntrega() {
		return estacionesEntrega;
	}
	public void setEstacionesEntrega(List<PuntoUbicacion> estacionesEntrega) {
		this.estacionesEntrega = estacionesEntrega;
	}
	public boolean isAbreEntreSemana() {
		return abreEntreSemana;
	}
	public void setAbreEntreSemana(boolean abreEntreSemana) {
		this.abreEntreSemana = abreEntreSemana;
	}
	public boolean isAbreSabado() {
		return abreSabado;
	}
	public void setAbreSabado(boolean abreSabado) {
		this.abreSabado = abreSabado;
	}
	public boolean isAbreDomingo() {
		return abreDomingo;
	}
	public void setAbreDomingo(boolean abreDomingo) {
		this.abreDomingo = abreDomingo;
	}
	public String getHoraAbre() {
		return horaAbre;
	}
	public void setHoraAbre(String horaAbre) {
		this.horaAbre = horaAbre;
	}
	public String getHoraCierra() {
		return horaCierra;
	}
	public void setHoraCierra(String horaCierra) {
		this.horaCierra = horaCierra;
	}
	public int getTarifa() {
		return tarifa;
	}
	public void setTarifa(int tarifa) {
		this.tarifa = tarifa;
	}
	public int getLimiteKm() {
		return limiteKm;
	}
	public void setLimiteKm(int limiteKm) {
		this.limiteKm = limiteKm;
	}
	@Override
	public String toString() {
		return "SitioAlquiler ["
				+ "id=" + id + 
				", nombre=" + nombre + 
				", direccionPrincipal=" + direccionPrincipal + 
				", ubicacion=" + ubicacion + 
				", estacionesEntrega=" + estacionesEntrega + 
				", abreEntreSemana=" + abreEntreSemana + 
				", abreSabado=" + abreSabado + 
				", abreDomingo=" + abreDomingo + 
				", horaAbre=" + horaAbre + 
				", horaCierra=" + horaCierra + 
				", tarifa=" + tarifa + 
				", limiteKm=" + limiteKm + 
				"]";
	}
}
