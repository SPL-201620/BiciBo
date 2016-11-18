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
@Table(name = "puntosUbicaciones", schema = "bicibo")
public class PuntoUbicacion implements Serializable 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
	private String nombre;
	private String ubicacionX;
    private String ubicacionY;
            
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

	public String getUbicacionX() {
		return ubicacionX;
	}

	public void setUbicacionX(String ubicacionX) {
		this.ubicacionX = ubicacionX;
	}

	public String getUbicacionY() {
		return ubicacionY;
	}

	public void setUbicacionY(String ubicacionY) {
		this.ubicacionY = ubicacionY;
	}

	@Override
	public String toString() {
		return "PuntoUbicacion ["
				+ "id=" + id + 
				", nombre=" + nombre + 
				", ubicacionX=" + ubicacionX + 
				", ubicacionY=" + ubicacionY + 
				"]";
	}
}
