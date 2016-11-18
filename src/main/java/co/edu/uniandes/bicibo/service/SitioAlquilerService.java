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
import co.edu.uniandes.bicibo.domain.SitioAlquiler;

import java.util.*;

public class SitioAlquilerService 
{
	public JSONObject darSitiosAlquiler()
	{
		JSONObject obj = new JSONObject();
        try
        {
        	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
            EntityManager entityManager = emfactory.createEntityManager( );
            
        	Query query = entityManager.createQuery("SELECT s FROM SitioAlquiler s");
            
            // Espera en el resultado un objeto unico.
            List<SitioAlquiler> result = query.getResultList();
            
            entityManager.close( );
            emfactory.close( );
            obj.put("status", "OK");
            obj.put("message", result);
        }
        catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar dar los sitios de alquiler. <br>"+e.getMessage());
        }    	
        return obj;
	}
}
