package co.edu.uniandes.bicibo.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.json.simple.JSONObject;

import co.edu.uniandes.bicibo.domain.Recorrido;
import co.edu.uniandes.bicibo.domain.Usuario;

public class RecorridoService {
    
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
            
            Recorrido recorrido = entitymanager.find(Recorrido.class, Integer.parseInt(id));
                        
            obj.put("status", "OK");
            obj.put("message", recorrido); 
            entitymanager.close();
            emfactory.close();
        }
    	catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar cargar el recorrido. <br>" + e.getMessage());
        }    	
    	return obj;
    }
    
    public JSONObject agregarRecorrido(String idUsuario, String origen, String destino, String horaSalida, 
    		String horaLlegada, String fechaRecorrido, String realizado, String distancia, String tiempoEstimado, 
    		String caloriasQuemadas, String infoClima) 
    {       
        JSONObject obj = new JSONObject();
        try
        {
        	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );

            EntityManager entityManager;
        	entityManager = emfactory.createEntityManager( );
        	entityManager.getTransaction( ).begin( );

        	Usuario usuario = entityManager.find(Usuario.class, Integer.parseInt(idUsuario));

        	List<Recorrido> recorridos = usuario.getRecorridos();

            Recorrido route = new Recorrido( ); 
            route.setOrigen(origen);
            route.setDestino(destino);
            route.setHora_salida(horaSalida);
            route.setHora_llegada(horaLlegada);
            route.setFecha_recorrido(fechaRecorrido);
            route.setRealizado(Boolean.parseBoolean(realizado));
            route.setDistancia(distancia);
            route.setTiempoEstimado(tiempoEstimado);
            route.setCaloriasQuemadas(caloriasQuemadas);
            route.setInfoClima(infoClima);
                        
            entityManager.persist( route );
            
            recorridos.add(route);
            usuario.setRecorridos(recorridos);
            
            entityManager.persist( usuario );
            entityManager.getTransaction( ).commit( );

            entityManager.close( );
            emfactory.close( );
            obj.put("status", "OK");
            obj.put("message", "Recorrido Creado");
        }
        catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar registrar el recorrido. <br>"+e.getMessage());
        }    	
        return obj;
    }
    
    public JSONObject actualizarRecorrido(String idRecorrido, String origen, String destino, String horaSalida, 
    		String horaLlegada, String fechaRecorrido, String realizado, String distancia, String tiempoEstimado, 
    		String caloriasQuemadas, String infoClima) 
    {       
        JSONObject obj = new JSONObject();
        try
        {
        	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );

            EntityManager entityManager;
        	entityManager = emfactory.createEntityManager( );
        	entityManager.getTransaction( ).begin( );

        	Recorrido route = entityManager.find(Recorrido.class, Integer.parseInt(idRecorrido));
        	
        	if(origen != null || origen.equals(""))
            {
        		route.setOrigen(origen);
            }
        	if(destino != null || destino.equals(""))
            {
        		route.setDestino(destino);
            }
        	if(horaSalida != null || horaSalida.equals(""))
            {
        		route.setHora_salida(horaSalida);
            }
        	if(horaLlegada != null || horaLlegada.equals(""))
            {
        		route.setHora_llegada(horaLlegada);
            }
        	if(fechaRecorrido != null || fechaRecorrido.equals(""))
            {
        		route.setFecha_recorrido(fechaRecorrido);
            }
        	if(realizado != null || realizado.equals(""))
            {
        		route.setRealizado(Boolean.parseBoolean(realizado));
            }
        	if(distancia != null || distancia.equals(""))
            {
        		route.setDistancia(distancia);
            }
        	if(tiempoEstimado != null || tiempoEstimado.equals(""))
            {
        		route.setTiempoEstimado(tiempoEstimado);
            }
        	if(caloriasQuemadas != null || caloriasQuemadas.equals(""))
            {
        		route.setCaloriasQuemadas(caloriasQuemadas);
            }
        	if(infoClima != null || infoClima.equals(""))
            {
        		route.setInfoClima(infoClima);
            }
        	entityManager.persist( route );

            entityManager.getTransaction( ).commit( );
            entityManager.close( );
            emfactory.close( );
            obj.put("status", "OK");
            obj.put("message", "Recorrido Actualizado");
        }
        catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar actualizar el recorrido. <br>"+e.getMessage());
        }    	
        return obj;
    }
}
