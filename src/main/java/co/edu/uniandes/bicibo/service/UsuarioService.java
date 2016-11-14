package co.edu.uniandes.bicibo.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.json.simple.JSONObject;

import co.edu.uniandes.bicibo.domain.Usuario;

public class UsuarioService {
	
    public JSONObject Registrar(String nombre, String email, String clave, String fotoPerfil) {
       
        JSONObject obj = new JSONObject();
        try{
        	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
            
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            Usuario user = new Usuario( ); 
            user.setNombre(nombre);
            user.setEmail(email);
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

}
