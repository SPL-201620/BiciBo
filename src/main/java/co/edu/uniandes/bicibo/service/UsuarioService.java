package co.edu.uniandes.bicibo.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.json.simple.JSONObject;
import javax.persistence.PersistenceContext;

import co.edu.uniandes.bicibo.domain.User;
import co.edu.uniandes.bicibo.domain.Usuario;
import java.util.*;

public class UsuarioService {
	@PersistenceContext
	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
	@PersistenceContext
	EntityManager entitymanager = emfactory.createEntityManager();
	
	public JSONObject Registrar(String nombre, String email, String username, String clave, String fotoPerfil) {
       
        JSONObject obj = new JSONObject();
        try{
        	entitymanager.getTransaction( ).begin( );

            Usuario user = new Usuario( ); 
            user.setNombre(nombre);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(clave);
            user.setRutaFoto(fotoPerfil);
            
            entitymanager.persist( user );
            entitymanager.getTransaction( ).commit( );

            entitymanager.close( );
            emfactory.close( );
            obj.put("status", "OK");
            obj.put("message", "Usuario Creado");
        }catch (Exception e){
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar registrar el usuario. <br>"+e.getMessage());
        }
    	
        return obj;
    }
    
    public JSONObject Login(String username, String clave) {
        JSONObject obj = new JSONObject();
        try{
            Usuario usuario = new Usuario();
         // Creamos un query con JPQL y lo ejecutamos directamente.
            Query query = entitymanager.createQuery("SELECT a FROM Usuario a WHERE a.username = ?1 AND a.password = ?2");
            query.setParameter(1, username); 
            query.setParameter(2, clave); 
            
            // Espera en el resultado un objeto unico.
            System.out.println("---->>>Resultado login: "+query.getSingleResult().toString());
            
            Object result = query.getSingleResult();
            if(result == null){
            	obj.put("status", "ERROR");
                obj.put("message", "Usuario o Clave incorrecta.");
            }else{
                usuario = (Usuario) result;
	            obj.put("id", usuario.getId().toString());
	            obj.put("username", usuario.getUsername().toString());
	            obj.put("status", "OK");
	            obj.put("message", "El usuario ha iniciado sesión");
            }
        }catch (Exception e){
        	obj.put("status", "ERROR");
            obj.put("message", "Usuario o Clave incorrecta.");
        }
    	
        return obj;
    }
    
    public Usuario InfoUsuairo(int id) {
    	 Usuario usuario = new Usuario();
         // Creamos un query con JPQL y lo ejecutamos directamente.
    	 Query query = entitymanager.createQuery("SELECT a FROM Usuario a WHERE a.id = ?1");
         query.setParameter(1, id);
         Object result = query.getSingleResult();
         if(result == null){
        	 usuario = null;
         }else{
             usuario = (Usuario) result;
         }
         
         return usuario;
    }

}
