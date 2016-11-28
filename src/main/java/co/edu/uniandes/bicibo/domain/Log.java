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
@Table(name = "logs", schema = "bicibo")
public class Log implements Serializable 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nombreMetodo;
    private String parametros;
    private String objetivo;
    private String retorno;
    private String excepcion;
    private String fecha;
    
    public Log()
    {

    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombreMetodo() {
		return nombreMetodo;
	}

	public void setNombreMetodo(String nombreMetodo) {
		this.nombreMetodo = nombreMetodo;
	}

	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}

	public String getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(String excepcion) {
		this.excepcion = excepcion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", nombreMetodo=" + nombreMetodo + ", parametros=" + parametros + ", objetivo="
				+ objetivo + ", retorno=" + retorno + ", excepcion=" + excepcion + "]";
	}
}
