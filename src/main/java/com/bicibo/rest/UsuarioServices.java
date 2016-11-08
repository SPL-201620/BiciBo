package com.bicibo.rest;

import com.bicibo.models.Usuario;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import persistence.PersistenceManager;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by santi on 8/11/2016.
 */
@Path("/")
public class UsuarioServices
{
    public static final String SALT = "my-salt-text";

    @POST
    @Path("/user")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response crearUsuario(String json)
    {
        Response response = null;
        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("clave").getAsString();
        if(email.length() == 0 || password.length() == 0)
        {
            Map<String, String> entity = Maps.newHashMap();
            entity.put("status", "ERROR");
            entity.put("message", "Todos los campos deben ser llenados");
            response = Response.status(Response.Status.BAD_REQUEST)
                     .entity(entity)
                     .type(MediaType.APPLICATION_JSON)
                     .build();
            return response;
        }
        else
        {
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            String hPass = generateHash(SALT+password);
            usuario.setPassword(hPass);
            EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
            try
            {
                em.getTransaction().begin();
                em.persist(usuario);
                em.getTransaction().commit();
                em.close();
                Map<String, String> entity = Maps.newHashMap();
                entity.put("status", "OK");
                entity.put("message", "Usuario creado");
                response = Response.status(Response.Status.OK)
                        .entity(entity)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
                return response;
            }
            catch(Exception e)
            {
                Map<String, String> entity = Maps.newHashMap();
                entity.put("status", "ERROR");
                entity.put("message", "El email ingresado ya existe");
                response = Response.status(Response.Status.BAD_REQUEST)
                        .entity(entity)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
                return  response;
            }
        }
    }
    
    @POST
    @Path("/login")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response loginUser(String json, @Context HttpServletRequest request)
    {
        Response response = null;
        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("clave").getAsString();
        if(email.length() == 0 || password.length() == 0)
        {
            Map<String, String> entity = Maps.newHashMap();
            entity.put("status", "ERROR");
            entity.put("message", "Todos los campos deben ser llenados");
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(entity)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            return response;
        }
        else
        {
            try
            {
                EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
                Usuario user = (Usuario) em.createQuery("SELECT u FROM Usuario u where u.email = :value1")
                        .setParameter("value1", email).getSingleResult();
                String p = user.getPassword();
                String hPass = generateHash(SALT+password);
                if(p.equals(hPass))
                {
                    request.setAttribute("username", user.getEmail());
                    Map<String, String> entity = Maps.newHashMap();
                    entity.put("id", user.getId().toString());
                    entity.put("nombre", user.getNombre().toString());
                    entity.put("status", "OK");
                    entity.put("message", "Usuario ha iniciado sesion");
                    response = Response.status(Response.Status.OK)
                            .entity(entity)
                            .type(MediaType.APPLICATION_JSON)
                            .build();
                    return  response;
                }
                else
                {
                    Map<String, String> entity = Maps.newHashMap();
                    entity.put("nombre", user.getNombre().toString());
                    entity.put("status", "ERROR");
                    entity.put("message", "Email o Clave Incorrecta");
                    response = Response.status(Response.Status.BAD_REQUEST)
                            .entity(entity)
                            .type(MediaType.APPLICATION_JSON)
                            .build();
                    return response;
                }
            }
            catch(Exception e)
            {
                Map<String, String> entity = Maps.newHashMap();
                entity.put("status", "ERROR");
                entity.put("message", "Email o Clave Incorrecta");
                response = Response.status(Response.Status.BAD_REQUEST)
                        .entity(entity)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
                return response;
            }

        }
    }

    @POST
    @Path("/logout")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response logoutUser(String json, @Context HttpServletRequest req)
    {
        Response response = null;
        HttpSession session= req.getSession(true);
        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String id = jsonObject.get("id").getAsString();
        Object user = session.getAttribute("username");

        if(user != null)
        {
            session.removeAttribute("username");
            Map<String, String> entity = Maps.newHashMap();
            entity.put("status", "OK");
            entity.put("message", "Usuario desautenticado");
            response = Response.status(Response.Status.OK)
                    .entity(entity)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            return  response;
        }
        else
        {
            Map<String, String> entity = Maps.newHashMap();
            entity.put("status", "ERROR");
            entity.put("message", "Usuario no esta loggeado");
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(entity)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            return response;
        }
    }

    @GET
    @Path("/user/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response infoUsuario(@PathParam("id") String id, @Context HttpServletRequest req)
    {
        HttpSession session= req.getSession(true);
        Response response = null;
        Object user = session.getAttribute("username");
        if(user != null)
        {
            String email = user.toString();
            EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
            Usuario usuario = (Usuario) em.createQuery("SELECT u FROM Usuario u where u.email = :value1")
                    .setParameter("value1", email).getSingleResult();
            response = Response.status(Response.Status.OK)
                    .entity(usuario)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            return response;
        }
        else
        {
            response = Response.status(Response.Status.BAD_REQUEST).build();
            return response;
        }
    }

    @PUT
    @Path("/user/{id}")//porque le llega id si ese va en el request?
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response updateUser(@PathParam("id") String iD, String json, @Context HttpServletRequest req)
    {
        Response response = null;
        HttpSession session= req.getSession(true);
        Object user = session.getAttribute("username");

        if(user != null)
        {
            JsonElement jsonElement = new JsonParser().parse(json);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Integer id = jsonObject.get("id").getAsInt();
            String email = jsonObject.get("email").getAsString();
            String nombre = jsonObject.get("nombre").getAsString();
            String fotoPerfil = jsonObject.get("fotoPerfil").getAsString();
            Integer edad = jsonObject.get("edad").getAsInt();
            EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
            Usuario old = (Usuario) em.createQuery("SELECT u FROM Usuario u where u.email = :value1")
                    .setParameter("value1", user.toString()).getSingleResult();
            if(old != null)
            {
                if (old.getEmail() != email)
                {
                    Usuario test = (Usuario) em.createQuery("SELECT u FROM Usuario u where u.email = :value1")
                            .setParameter("value1", user.toString()).getSingleResult();
                    if(test == null)
                    {
                        old.setId(id);
                        old.setEmail(email);
                        old.setNombre(nombre);
                        old.setRutaFoto(fotoPerfil);
                        old.setEdad(edad);
                        em.merge(old);
                        Map<String, String> entity = Maps.newHashMap();
                        entity.put("status", "OK");
                        entity.put("message", "Usuario actualizado");
                        response = Response.status(Response.Status.OK)
                                .entity(entity)
                                .type(MediaType.APPLICATION_JSON)
                                .build();
                        return  response;
                    }
                    else
                    {
                        Map<String, String> entity = Maps.newHashMap();
                        entity.put("status", "ERROR");
                        entity.put("message", "No se puede actualizar. Email ya en uso");
                        response = Response.status(Response.Status.BAD_REQUEST)
                                .entity(entity)
                                .type(MediaType.APPLICATION_JSON)
                                .build();
                        return response;
                    }
                }
                else
                {
                    old.setId(id);
                    old.setEmail(email);
                    old.setNombre(nombre);
                    old.setRutaFoto(fotoPerfil);
                    old.setEdad(edad);
                    em.merge(old);
                    Map<String, String> entity = Maps.newHashMap();
                    entity.put("status", "OK");
                    entity.put("message", "Usuario actualizado");
                    response = Response.status(Response.Status.OK)
                            .entity(entity)
                            .type(MediaType.APPLICATION_JSON)
                            .build();
                    return  response;
                }
            }
            else
            {
                Map<String, String> entity = Maps.newHashMap();
                entity.put("status", "ERROR");
                entity.put("message", "Ocurrio un error al actualizar el usuario");
                response = Response.status(Response.Status.BAD_REQUEST)
                        .entity(entity)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
                return response;
            }
        }
        else
        {
            Map<String, String> entity = Maps.newHashMap();
            entity.put("status", "ERROR");
            entity.put("message", "Usuario no esta loggeado");
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(entity)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            return response;
        }
    }

    @GET
    @Path("/users")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response listRegisterdUsers(String json, @Context HttpServletRequest req)
    {
        Response response = null;
        HttpSession session= req.getSession(true);
        Object user = session.getAttribute("username");

        if(user != null)
        {
            EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
            List<Usuario> persons = (List<Usuario>) em.createQuery("select p from Usuario p").getResultList();
            Usuario usuario = (Usuario) em.createQuery("SELECT u FROM Usuario u where u.email = :value1")
                    .setParameter("value1", user.toString()).getSingleResult();
            ArrayList<Usuario> usuarios = new ArrayList();
            for(int i = 0; i< persons.size();i++)
            {
                if(!usuario.getAmigos().contains(persons.get(i)) && !persons.get(i).equals(usuario))
                {
                    usuarios.add(persons.get(i));
                }
            }
            response = Response.status(Response.Status.OK)
                    .entity(usuarios)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            return  response;
        }
        else
        {
            Map<String, String> entity = Maps.newHashMap();
            entity.put("status", "ERROR");
            entity.put("message", "Usuario no esta loggeado");
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(entity)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            return response;
        }
    }

    @GET
    @Path("/friends")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response friends(String json, @Context HttpServletRequest req)
    {
        Response response = null;
        HttpSession session= req.getSession(true);
        Object user = session.getAttribute("username");

        if(user != null)
        {
            EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
            Usuario usuario = (Usuario) em.createQuery("SELECT u FROM Usuario u where u.email = :value1")
                    .setParameter("value1", user.toString()).getSingleResult();
            List<Usuario> amigos = usuario.getAmigos();
            response = Response.status(Response.Status.OK)
                    .entity(amigos)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            return  response;
        }
        else
        {
            Map<String, String> entity = Maps.newHashMap();
            entity.put("status", "ERROR");
            entity.put("message", "Usuario no esta loggeado");
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(entity)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            return response;
        }
    }

    @POST
    @Path("/friend")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response addFriend(String json, @Context HttpServletRequest req)
    {
        Response response = null;
        HttpSession session= req.getSession(true);
        Object user = session.getAttribute("username");

        if(user != null)
        {
            JsonElement jsonElement = new JsonParser().parse(json);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
            Integer id = jsonObject.get("id").getAsInt();
            Integer id_friend = jsonObject.get("id_friend").getAsInt();
            Usuario usuario = (Usuario) em.createQuery("SELECT u FROM Usuario u where u.email = :value1")
                    .setParameter("value1", user.toString()).getSingleResult();
            Usuario userFriend = em.find(Usuario.class, id_friend);
            //user.setParent(user);
            usuario.getAmigos().add(userFriend);
            em.merge(user);
            em.merge(userFriend);
            Map<String, String> entity = Maps.newHashMap();
            entity.put("status", "OK");
            entity.put("message", "Amigo agregado");
            response = Response.status(Response.Status.OK)
                    .entity(entity)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            return  response;
        }
        else
        {
            Map<String, String> entity = Maps.newHashMap();
            entity.put("status", "ERROR");
            entity.put("message", "Usuario no esta loggeado");
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(entity)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            return response;
        }
    }
    
    @POST
    @Path("/mensajes")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response enviarMensaje(String json, @Context HttpServletRequest req)
    {
        Response response = null;
        HttpSession session= req.getSession(true);
        Object user = session.getAttribute("username");

        if(user != null)
        {
            JsonElement jsonElement = new JsonParser().parse(json);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
            Integer id_usuario_origen = jsonObject.get("id_usuario_origen").getAsInt();
            Usuario usuarioOrigen = (Usuario) em.createQuery("SELECT u FROM Usuario u where u.id = :id1")
                    .setParameter("id1", id_usuario_origen).getSingleResult();
            String mensaje = (String) jsonObject.get("mensaje");
            Integer id_usuario_destino = jsonObject.get("id_usuario_destino").getAsInt();
            Usuario usuarioDestino = (Usuario) em.createQuery("SELECT u FROM Usuario u where u.id = :id2")
                    .setParameter("id2", id_usuario_destino).getSingleResult();
            Usuario usuario = (Usuario) em.createQuery("SELECT u FROM Usuario u where u.email = :value1")
                    .setParameter("value1", user.toString()).getSingleResult();
            if (usuario.getId() != id_usuario_origen && usuario.getId() != id_usuario_destino)
            {
            	Map<String, String> entity = Maps.newHashMap();
                entity.put("status", "ERROR");
                entity.put("message", "Mensaje no permitido para el usuario");
                response = Response.status(Response.Status.BAD_REQUEST)
                        .entity(entity)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
                return response;
            }
            else
            {
            	Mensaje mens = new Mensaje();
            	mens.setUsuarioOrigen(usuarioOrigen);
            	mens.setUsuarioDestino(usuarioDestino);
            	mens.setMensaje(mensaje);
            	
            }
            Usuario userFriend = em.find(Usuario.class, id_friend);
            //user.setParent(user);
            usuario.getAmigos().add(userFriend);
            em.merge(user);
            em.merge(userFriend);
            Map<String, String> entity = Maps.newHashMap();
            entity.put("status", "OK");
            entity.put("message", "Amigo agregado");
            response = Response.status(Response.Status.OK)
                    .entity(entity)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            return  response;
        }
        else
        {
            Map<String, String> entity = Maps.newHashMap();
            entity.put("status", "ERROR");
            entity.put("message", "Usuario no esta loggeado");
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(entity)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            return response;
        }
    }

    public static String generateHash(String input)
    {
        StringBuilder hash = new StringBuilder();

        try
        {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; idx++)
            {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            // handle error here.
        }

        return hash.toString();
    }
}
