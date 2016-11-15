package co.edu.uniandes.bicibo.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.json.simple.JSONObject;
import javax.persistence.PersistenceContext;

import co.edu.uniandes.bicibo.domain.Usuario;
import co.edu.uniandes.bicibo.domain.Mensaje;
import co.edu.uniandes.bicibo.domain.Recorrido;
import java.util.*;

public class MensajeService 
{
	public JSONObject enviarMensaje(String id_usuario_origen, String mensaje, String id_usuario_destino)
	{
		JSONObject obj = new JSONObject();
		System.out.println("///////////////////////////////1");
        try
        {
        	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
        	System.out.println("////////////////////2");
            EntityManager entityManager = emfactory.createEntityManager( );
        	entityManager.getTransaction( ).begin( );
        	
        	Mensaje mensajeNuevo = new Mensaje();
        	System.out.println("//////////////////////3" + id_usuario_origen + " " + id_usuario_destino);
        	Usuario usuarioOrigen = entityManager.find(Usuario.class, Integer.parseInt(id_usuario_origen));
        	Usuario usuarioDestino = entityManager.find(Usuario.class, Integer.parseInt(id_usuario_destino));
        	System.out.println("//////////////////////////4");
        	mensajeNuevo.setUsuarioOrigen(usuarioOrigen);
        	mensajeNuevo.setMensaje(mensaje);
        	mensajeNuevo.setUsuarioDestino(usuarioDestino);
        	mensajeNuevo.setNuevo(true);
        	System.out.println("///////////////////////////5");
        	entityManager.persist(mensajeNuevo);
        	entityManager.getTransaction( ).commit( );
        	System.out.println("////////////////////////////6");
            entityManager.close( );
            emfactory.close( );
            obj.put("status", "OK");
            obj.put("message", "Ha sido enviado el mensaje.");
        }
        catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar enviar el mensaje. <br>"+e.getMessage());
        }    	
        return obj;
	}

	public JSONObject numMensajes(String id)
	{
		JSONObject obj = new JSONObject();
        try
        {
        	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
            EntityManager entityManager = emfactory.createEntityManager( );
            
        	Usuario usuario = entityManager.find(Usuario.class, Integer.parseInt(id));
        	Query query = entityManager.createQuery("SELECT m FROM Mensaje m WHERE m.nuevo=true AND (m.usuarioOrigen = ?1 or m.usuarioDestino = ?1)");
            query.setParameter(1, usuario); 
            
            // Espera en el resultado un objeto unico.
            List<Mensaje> result = query.getResultList();
            int filas = result.size();
            
            entityManager.close( );
            emfactory.close( );
            obj.put("NumMensajesNuevos", filas);
        }
        catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar enumerar los mensajes. <br>"+e.getMessage());
        }    	
        return obj;
	}
	
	public JSONObject verMensajes(String id)
	{
		JSONObject obj = new JSONObject();
        try
        {
        	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
            EntityManager entityManager = emfactory.createEntityManager( );
            
        	Usuario usuario = entityManager.find(Usuario.class, Integer.parseInt(id));
            Query query = entityManager.createQuery("SELECT m FROM Mensaje m WHERE m.nuevo=true AND (m.usuarioOrigen = ?1 or m.usuarioDestino = ?1)");
            query.setParameter(1, usuario); 
            
            // Espera en el resultado un objeto unico.
            List<Mensaje> result = query.getResultList();
            for(Mensaje m : result)
            {
            	m.getUsuarioOrigen().setAmigos(null);
            	m.getUsuarioDestino().setAmigos(null);
            }
            
            entityManager.close( );
            emfactory.close( );
            obj.put("status", "OK");
            obj.put("message", result);
        }
        catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar traer los mensajes. <br>"+e.getMessage());
        }    	
        return obj;
	}
}
