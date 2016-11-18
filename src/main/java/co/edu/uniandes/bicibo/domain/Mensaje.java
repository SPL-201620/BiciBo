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
@Table(name = "mensajes", schema = "bicibo")
public class Mensaje implements Serializable 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Usuario usuarioOrigen;
    private String mensaje;
    @OneToOne
    private Usuario usuarioDestino;
    
    private boolean nuevo;
        
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

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Usuario getUsuarioDestino() {
		return usuarioDestino;
	}

	public void setUsuarioDestino(Usuario usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	@Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", usuarioOrigen='" + usuarioOrigen.getId() + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", usuarioDestino='" + usuarioDestino.getId() + '\'' +
                ", nuevo='" + nuevo + '\'' +
                '}';
    }
}
