package co.edu.uniandes.bicibo.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.json.simple.JSONObject;
import javax.persistence.PersistenceContext;

import co.edu.uniandes.bicibo.domain.Usuario;

public class UsuarioService {
	
    public JSONObject Registrar(String nombre, String email, String username, String clave, String fotoPerfil) {
       
        JSONObject obj = new JSONObject();
        try{
        	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );

            EntityManager entityManager;
        	entityManager = emfactory.createEntityManager( );
        	entityManager.getTransaction( ).begin( );

            Usuario user = new Usuario( ); 
            user.setNombre(nombre);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(clave);
            user.setRutaFoto(fotoPerfil);
            
            entityManager.persist( user );
            entityManager.getTransaction( ).commit( );

            entityManager.close( );
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
        	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
            EntityManager entitymanager = emfactory.createEntityManager();
           // Usuario usuario = entitymanager.find( Usuario.class 1651 );
            //System.out.println("Nombre ID = " + usuario.getNombre());
         // Creamos un query con JPQL y lo ejecutamos directamente.
            Query query = entitymanager.createQuery("SELECT a FROM Usuario a WHERE a.username = ?1 AND a.password = ?2");
                   query.setParameter(1, username); 
                   query.setParameter(2, clave); 
            
            // Espera en el resultado un objeto unico.
            System.out.println("---->>>Resultado login: "+query.getSingleResult().toString());
            obj.put("status", "OK");
            obj.put("message", "El usuario ha iniciado sesión");
        }catch (Exception e){
        	obj.put("status", "ERROR");
            obj.put("message", "Email o Clave incorrecta.\n"+e.getMessage());
        }
    	
        return obj;
    }

}
