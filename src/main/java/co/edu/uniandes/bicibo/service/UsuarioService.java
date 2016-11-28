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

public class UsuarioService 
{
    public boolean face = false;

    public JSONObject registrar(String nombre, String email, String username, String password, String rutaFoto) 
    {       
        JSONObject obj = new JSONObject();
        try
        {
        	EntityManager entityManager = PersistenceManager.INSTANCE.getEntityManager();//Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );

            //EntityManager entityManager;
        	//entityManager = emfactory.createEntityManager( );
        	entityManager.getTransaction( ).begin( );

            Usuario user = new Usuario( );
            user.setNombre(nombre);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            user.setRutaFoto(rutaFoto);

            entityManager.persist( user );
            entityManager.getTransaction( ).commit( );

            entityManager.close( );
            //PersistenceManager.INSTANCE.close();
            //emfactory.close( );
            obj.put("status", "OK");
            obj.put("message", "Usuario Creado");
        }
        catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar registrar el usuario. <br>"+e.getMessage());
        }    	
        return obj;
    }
    public JSONObject login(String username, String clave) 
    {
        System.out.println("USERNAME------> "+username+ " CLAVE ----> "+ clave);
        JSONObject obj = new JSONObject();
        try
        {
            EntityManager entityManager = PersistenceManager.INSTANCE.getEntityManager();
            //EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
            //EntityManager entitymanager = emfactory.createEntityManager();
            Usuario usuario = new Usuario();
            // Creamos un query con JPQL y lo ejecutamos directamente.
            if(face == true)
            {
                Query query = entityManager.createQuery("SELECT a FROM Usuario a WHERE a.username = ?1");
                query.setParameter(1, username);
                // Espera en el resultado un objeto unico.
                System.out.println("---->>>Resultado login: "+query.getSingleResult().toString());

                Object result = query.getSingleResult();
                usuario = (Usuario) result;
                if(result == null)
                {
                    obj.put("status", "ERROR");
                    obj.put("message", "Usuario o Clave incorrecta.");
                }
                else
                {
                    usuario.setPassword(clave);
                    obj.put("id", usuario.getId().toString());
                    obj.put("username", usuario.getUsername().toString());
                    obj.put("status", "OK");
                    obj.put("message", "El usuario ha iniciado sesión");
                }
            }
            else
            {
                Query query = entityManager.createQuery("SELECT a FROM Usuario a WHERE a.username = ?1 AND a.password = ?2");
                query.setParameter(1, username);
                query.setParameter(2, clave);

                // Espera en el resultado un objeto unico.
                System.out.println("---->>>Resultado login: "+query.getSingleResult().toString());

                Object result = query.getSingleResult();
                usuario = (Usuario) result;
                if(result == null)
                {
                    obj.put("status", "ERROR");
                    obj.put("message", "Usuario o Clave incorrecta.");
                }
                else
                {
                    obj.put("id", usuario.getId().toString());
                    obj.put("username", usuario.getUsername().toString());
                    obj.put("status", "OK");
                    obj.put("message", "El usuario ha iniciado sesión");
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        	obj.put("status", "ERROR");
            obj.put("message", "Usuario o Clave incorrecta.");
        }
        return obj;
    }

    public JSONObject logout(String id) {
    	JSONObject obj = new JSONObject();
        try
        {
        	obj.put("status", "OK");
	        obj.put("message", "El usuario se ha desautenticado");

        }
        catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Problemas al intentar desautenticar");
        }
        return obj;
    }
    
    public Usuario infoUsuario(int id)
    {
        Usuario usuario = new Usuario();
    	try
        {
            EntityManager entityManager = PersistenceManager.INSTANCE.getEntityManager();
        	//EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
            //EntityManager entitymanager = emfactory.createEntityManager();
            
            usuario = entityManager.find(Usuario.class, id);
            usuario.setAmigos(null);
        	usuario.setRecorridos(null);
        	usuario.setRecorridosGrupalesAdmin(null);
        }
        catch (Exception e)
        {
            usuario = null;
        }
        return usuario;
    	
    }
    
    public JSONObject updateUsuario(String id, String nombre, String email, String password, String username, 
    		String edad, String fotoPerfil) 
    {        
    	JSONObject obj = new JSONObject();
        try
        {
            EntityManager entityManager = PersistenceManager.INSTANCE.getEntityManager();
        	//EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
            //EntityManager entitymanager = emfactory.createEntityManager();
            entityManager.getTransaction( ).begin( );
            
            Usuario usuario = entityManager.find(Usuario.class, Integer.parseInt(id));
            if( usuario != null)
            {
            	if(!nombre.equals("") && !nombre.equals(usuario.getNombre()))
                {
                	usuario.setNombre(nombre);
                }
                if(!email.equals("") && !email.equals(usuario.getEmail()))
                {
                	usuario.setEmail(email);
                }
                if(!password.equals("") && !password.equals(usuario.getPassword()))
                {
                	usuario.setPassword(password);
                }
                if(!username.equals("") && !username.equals(usuario.getUsername()))
                {
                	usuario.setUsername(username);
                }
                if(!edad.equals("") && usuario.getEdad() == null || 
                		!edad.equals("") && Integer.parseInt(edad) != usuario.getEdad())
                {
                	usuario.setEdad(Integer.parseInt(edad));
                }
                if(fotoPerfil != null && !fotoPerfil.equals("") && usuario.getRutaFoto() == null || 
                		fotoPerfil != null && !fotoPerfil.equals("") && !fotoPerfil.equals(usuario.getRutaFoto()))
                {
                	usuario.setRutaFoto((fotoPerfil));
                }  
                entityManager.persist(usuario);
                entityManager.getTransaction( ).commit( );
                entityManager.close();
                //emfactory.close();
                //PersistenceManager.INSTANCE.close();
                obj.put("status", "OK");
                obj.put("message", "Usuario actualizado");
            }
            else
            {
            	obj.put("status", "ERROR");
                obj.put("message", "Usuario no existe");
            }
        }
        catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar actualizar el usuario. <br>" + e.getMessage());
        }    	
        return obj;
    }
    
    public JSONObject listarRegistrados (String id)
    {
    	JSONObject obj = new JSONObject();
    	try
        {
            EntityManager entityManager = PersistenceManager.INSTANCE.getEntityManager();
        	//EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
            //EntityManager entitymanager = emfactory.createEntityManager();
            
            Query query = entityManager.createQuery("SELECT a FROM Usuario a");
            List<Usuario> persons = query.getResultList();
            
            Usuario usuario = entityManager.find(Usuario.class, Integer.parseInt(id));
            ArrayList<Usuario> usuariosNoAmigos = new ArrayList();
            
            for(int i = 0; i< persons.size();i++)
            {
                if(!usuario.getAmigos().contains(persons.get(i)) && !persons.get(i).equals(usuario))
                {
                	persons.get(i).setAmigos(null);
                	persons.get(i).setRecorridos(null);
                	persons.get(i).setRecorridosGrupalesAdmin(null);
                	usuariosNoAmigos.add(persons.get(i));
                }
            }
            entityManager.close();
            //PersistenceManager.INSTANCE.close();
            obj.put("status", "OK");
            obj.put("message", usuariosNoAmigos);      
        }
    	catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar cargar los usuarios registrados. <br>"+e.getMessage());
        }    	
        return obj;
    }
    
    public JSONObject listarAmigos (String id)
    {
    	JSONObject obj = new JSONObject();
    	try
        {
            EntityManager entityManager = PersistenceManager.INSTANCE.getEntityManager();
        	//EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
            //EntityManager entitymanager = emfactory.createEntityManager();
            
            Usuario usuario = entityManager.find(Usuario.class, Integer.parseInt(id));
            
            List<Usuario> amigos = usuario.getAmigos();
            for(Usuario u : amigos)
            {
            	u.setAmigos(null);
            	u.setRecorridos(null);
            	u.setRecorridosGrupalesAdmin(null);
            }
            entityManager.close();
            //PersistenceManager.INSTANCE.close();
            obj.put("status", "OK");
            obj.put("message", amigos);
        }
    	catch (Exception e)
        {
        	obj.put("status", "ERROR");
            obj.put("message", "Se produjo un error al intentar cargar los amigos del usuario. <br>"+e.getMessage());
        }    	
    	return obj;
    }

    public JSONObject agregarAmigo (String id, String idAmigo)
    {
    	JSONObject obj = new JSONObject();
    	try
    	{
            EntityManager entityManager = PersistenceManager.INSTANCE.getEntityManager();
    		//EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
    		//EntityManager entitymanager = emfactory.createEntityManager();
    		entityManager.getTransaction( ).begin( );

    		Usuario usuario = entityManager.find(Usuario.class, Integer.parseInt(id));
    		Usuario amigo = entityManager.find(Usuario.class, Integer.parseInt(idAmigo));

    		List<Usuario> amigos = usuario.getAmigos();
    		amigos.add(amigo);
    		usuario.setAmigos(amigos);
    		
    		/*amigos = amigo.getAmigos();
    		amigos.add(usuario);
    		amigo.setAmigos(amigos);

    		entitymanager.persist(amigo);*/
    		entityManager.persist(usuario);
    		entityManager.getTransaction( ).commit( );
    		entityManager.close();
            //PersistenceManager.INSTANCE.close();
    		obj.put("status", "OK");
    		obj.put("message", "Amigo agregado");
    	}
    	catch (Exception e)
    	{
    		obj.put("status", "ERROR");
    		obj.put("message", "Se produjo un error al intentar agregar el amigo del usuario. <br>"+e.getMessage());
    	}    	
    	return obj;
    }

    public Usuario darInfoUsuarioUsername(String username)
    {
        Usuario user  = null;
        EntityManager entityManager = PersistenceManager.INSTANCE.getEntityManager();
        Query query = entityManager.createQuery("SELECT a FROM Usuario a WHERE a.username = ?1");
        query.setParameter(1, username);
        Object result = query.getSingleResult();
        user = (Usuario) result;
        return user;
    }
}
