package co.edu.uniandes.bicibo.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.json.simple.JSONObject;
import javax.persistence.PersistenceContext;

import co.edu.uniandes.bicibo.domain.Usuario;
import co.edu.uniandes.bicibo.domain.Recorrido;
import co.edu.uniandes.bicibo.domain.RecorridoGrupal;

import java.util.*;

public class RecorridoGrupalService 
{	
    public JSONObject listarRecorridosGrupo (String id)
    {
    	JSONObject obj = new JSONObject();
    	try
        {
        	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
            EntityManager entitymanager = emfactory.createEntityManager();
            
            Query query = entitymanager.createQuery("SELECT a FROM RecorridoGrupal a");
            
            List<RecorridoGrupal> recorridos = query.getResultList();
            for(int i = 0; i< recorridos.size();i++)
            {
            	recorridos.get(i).setInscritos(null);
            }
            StoredProcedureQuery query2 = entitymanager.createStoredProcedureQuery("getRegistrosGrupales");
            
            /*PARA QUE CREEEN ESTE PROCEDIMIENTO EN SUS BASES DE DATOS, ES ASI:
            CREATE DEFINER=`root`@`localhost` PROCEDURE `getRegistrosGrupales`(IN idUsuario int)
            		BEGIN
            			SELECT  CASE WHEN ID IS NULL THEN 0 ELSE 1 END  
            			FROM bicibo.recorridosgrupales_usuarios ru
            			LEFT JOIN bicibo.usuarios u
            			ON 	ru.inscritos_ID = u.ID AND
            				ID = idUsuario;
            		END*/
            
            query2.registerStoredProcedureParameter("id", Integer.class, ParameterMode.IN);
            query2.setParameter("id", Integer.parseInt(id));
            List registrados = query2.getResultList();
            
            
            if(registrados.size() <= 0)
            {
                obj.put("status", "ERROR");
                obj.put("message", "No existen recorridos grupales registrados en el momento.");
            }
            else
            {
                obj.put("status", "OK");
                obj.put("recorridos", recorridos);
                obj.put("registrados", registrados);
            }
            entitymanager.close();
            emfactory.close();
        }
    	catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar cargar los recorridos grupales. <br>"+e.getMessage());
        }    	
    	return obj;
    }
    
    public JSONObject verRecorridoGrupo (String id, String id2)
    {
    	JSONObject obj = new JSONObject();
    	try
        {
        	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
            EntityManager entitymanager = emfactory.createEntityManager();
            
            RecorridoGrupal recorrido = entitymanager.find(RecorridoGrupal.class, Integer.parseInt(id2));
            recorrido.setInscritos(null);
            
                        
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
    
    public JSONObject agregarRecorridoGrupo(String idUsuario, String origen, String destino, String horaSalida, 
    		String horaLlegada, String fechaRecorrido, String distancia, String tiempoEstimado, 
    		String caloriasQuemadas, String infoClima, String frecuencia, String nombreOrganizador) 
    {       
        JSONObject obj = new JSONObject();
        try
        {	
        	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );

            EntityManager entityManager;
        	entityManager = emfactory.createEntityManager( );
        	entityManager.getTransaction( ).begin( );
        	
        	Usuario usuario = entityManager.find(Usuario.class, Integer.parseInt(idUsuario));
        	
        	List<RecorridoGrupal> recorridos = usuario.getRecorridosGrupalesAdmin();

            RecorridoGrupal route = new RecorridoGrupal( ); 
            route.setId_usuario(Integer.parseInt(idUsuario));
            route.setNombre_organizador(nombreOrganizador);
            route.setOrigen(origen);
            route.setDestino(destino);
            route.setHora_salida(horaSalida);
            route.setHora_llegada(horaLlegada);
            route.setFecha_recorrido(fechaRecorrido);
            route.setDistancia(Integer.parseInt(distancia));
            route.setTiempoEstimado(Integer.parseInt(tiempoEstimado));
            route.setCaloriasQuemadas(Integer.parseInt(caloriasQuemadas));
            route.setInfoClima(infoClima);
            route.setFrecuencia(frecuencia);
            ArrayList<Usuario> inscritos = new ArrayList<Usuario>();
            inscritos.add(usuario);
            route.setInscritos(inscritos);  
            
            entityManager.persist( route );
            recorridos.add(route);
            
            usuario.setRecorridosGrupalesAdmin(recorridos);
            
            entityManager.persist( usuario );
            
            entityManager.getTransaction( ).commit( );
            entityManager.close( );
            emfactory.close( );
            obj.put("status", "OK");
            obj.put("message", "Recorrido Grupal agregado.");
        }
        catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar registrar el recorrido grupal. <br>"+e.getMessage());
        }    	
        return obj;
    }
    /*
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

        	System.out.println("heloooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo2 "+idRecorrido);
        	Recorrido route = entityManager.find(Recorrido.class, Integer.parseInt(idRecorrido));

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
    */
}
