package co.edu.uniandes.bicibo.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.json.simple.JSONObject;

import co.edu.uniandes.bicibo.domain.Recorrido;
import co.edu.uniandes.bicibo.domain.Usuario;

public class RecorridoService {
	public JSONObject AgregarRecorrido (String id_usuario,  
    		String origen,
    		String destino,
    		String hora_salida,
    		String hora_llegada,
    		String fecha_recorrido,
    		String realizado,
    		String distancia,
    		String tiempoEstimado,
    		String caloriasQuemadas,
    		String infoClima)
    {
    	JSONObject obj = new JSONObject();
    	try
    	{
    		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
    		EntityManager entitymanager = emfactory.createEntityManager();
    		entitymanager.getTransaction( ).begin( );
    		
    		Recorrido recorrido = new Recorrido();
    		recorrido.setId_usuario(Integer.parseInt(id_usuario));
    		recorrido.setOrigen(origen);
    		recorrido.setDestino(destino);
    		recorrido.setHora_salida(hora_salida);
    		recorrido.setHora_llegada(hora_llegada);
    		recorrido.setFecha_recorrido(fecha_recorrido);
    		recorrido.setRealizado(Boolean.parseBoolean(realizado));
    		recorrido.setDistancia(distancia);
    		recorrido.setTiempoEstimado(tiempoEstimado);
    		recorrido.setCaloriasQuemadas(caloriasQuemadas);
    		recorrido.setInfoClima(infoClima);
    		
    		
    		Usuario usuario = entitymanager.find(Usuario.class, Integer.parseInt(id_usuario));

    		List<Recorrido> recorridos = usuario.getRecorridos();
    		recorridos.add(recorrido);
    		usuario.setRecorridos(recorridos);

    		entitymanager.persist(usuario);
    		entitymanager.persist(recorrido);
    		entitymanager.getTransaction( ).commit( );
    		entitymanager.close();
    		emfactory.close();
    		obj.put("status", "OK");
    		obj.put("message", "Recorrido agregado");
    	}
    	catch (Exception e)
    	{
    		obj.put("status", "ERROR");
    		obj.put("message", "Se produjo un error al intentar agregar el recorrido del usuario. <br>"+e.getMessage());
    	}    	
    	return obj;
    } 
    
    public JSONObject listarRecorridos (String id)
    {
    	JSONObject obj = new JSONObject();
    	try
        {
        	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
            EntityManager entitymanager = emfactory.createEntityManager();
            
            Usuario usuario = entitymanager.find(Usuario.class, Integer.parseInt(id));
                        
            List<Recorrido> recorridos = usuario.getRecorridos();
            
            if(recorridos.size() <= 0)
            {
                obj.put("status", "ERROR");
                obj.put("message", "No existen recorridos registrados en el momento.");
            	
            }
            else
            {
                obj.put("status", "OK");
                obj.put("recorridos", recorridos);
            }
            entitymanager.close();
            emfactory.close();
        }
    	catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar cargar los recorridos del usuario. <br>"+e.getMessage());
        }    	
    	return obj;
    }
    
    public JSONObject darRecorrido (String id)
    {
    	JSONObject obj = new JSONObject();
    	try
        {
        	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
            EntityManager entitymanager = emfactory.createEntityManager();
            
            Usuario usuario = entitymanager.find(Usuario.class, Integer.parseInt(id));
                        
            List<Recorrido> recorridos = usuario.getRecorridos();
            
            if(recorridos.size() <= 0)
            {
                obj.put("status", "ERROR");
                obj.put("message", "No existen recorridos registrados en el momento.");
            	
            }
            else
            {
                obj.put("status", "OK");
                obj.put("recorridos", recorridos);
            }
            entitymanager.close();
            emfactory.close();
        }
    	catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar cargar los recorridos del usuario. <br>"+e.getMessage());
        }    	
    	return obj;
    }
}
