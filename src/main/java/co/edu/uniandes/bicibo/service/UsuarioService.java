package co.edu.uniandes.bicibo.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import co.edu.uniandes.bicibo.domain.Usuario;

public class UsuarioService {
	
    public Usuario Registrar() {
    	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
        
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );

        Usuario user = new Usuario( ); 
        user.setNombre("Abimelec");
        user.setPassword("admin123");
        user.setEdad(35);
        user.setRutaFoto("foto1");
        
        entitymanager.persist( user );
        entitymanager.getTransaction( ).commit( );

        entitymanager.close( );
        emfactory.close( );
        return user;
    }

}
