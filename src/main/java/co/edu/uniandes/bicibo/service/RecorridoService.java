package co.edu.uniandes.bicibo.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.json.simple.JSONObject;
import javax.persistence.PersistenceContext;

import co.edu.uniandes.bicibo.domain.Usuario;
import co.edu.uniandes.bicibo.domain.Recorrido;
import java.util.*;

public class RecorridoService 
{	
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
