package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Usuario;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by santi on 5/10/2016.
 */
public class UsuarioController extends Controller
{
    public static final String SALT = "my-salt-text";

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result createUser()
    {
        System.out.println("Lllego a crear usuario");
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();
        System.out.println(json.toString());
        String mail = json.get("email").asText();
        String pass = json.get("clave").asText();
        if(mail.isEmpty() || pass.isEmpty())
        {
            result.put("status","ERROR");
            result.put("message","Todos los campos deben ser completados!");
            System.out.println(result.toString());
            return badRequest(toJson(result));
        }
        else
        {
            Usuario usuario = new Usuario();
            usuario.setEmail(mail);
            String hPass = generateHash(SALT+pass);
            usuario.setPassword(hPass);
            JPA.em().persist(usuario);
            try
            {
                JPA.em().flush();
                session().clear();
                session("username", usuario.getEmail());
                result.put("status","OK");
                result.put("message","Usuario creado");
                System.out.println(result.toString());
                return ok(toJson(result));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("LLEGO AL CATCH");
                result.put("status","ERROR");
                result.put("message","Usuario ya existe con ese email!");
                System.out.println(result.toString());
                return badRequest(toJson(result));
            }

        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result loginUser()
    {
        System.out.println("Llego a login");
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();
        System.out.println(json.toString());
        String mail = json.get("email").asText();
        String pass = json.get("clave").asText();
        if(mail.isEmpty() || pass.isEmpty())
        {
            result.put("status","ERROR");
            result.put("message","Todos los campos deben ser completados!");
            System.out.println(result.toString());
            return badRequest(toJson(result));
        }
        else
        {
            try
            {
                Usuario user = (Usuario) JPA.em().createQuery("SELECT u FROM Usuario u where u.email = :value1")
                        .setParameter("value1", mail).getSingleResult();
                String p = user.getPassword();
                String hPass = generateHash(SALT+pass);
                if(p.equals(hPass))
                {
                    result.put("id",user.getId());
                    result.put("nombre", user.getNombre());
                    result.put("status","OK");
                    result.put("message","Usuario ha iniciado sesión");
                    session().clear();
                    session("username", user.getEmail());
                    System.out.println(result.toString());
                    return ok(toJson(result));
                }
                else
                {
                    result.put("nombre", user.getNombre());
                    result.put("status","ERROR");
                    result.put("message","Email o Clave incorrecta");
                    System.out.println(result.toString());
                    return badRequest(toJson(result));
                }
            }
            catch (Exception e)
            {
                result.put("status","ERROR");
                result.put("message","Email o Clave incorrecta");
                System.out.println(result.toString());
                return badRequest(toJson(result));
            }
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result updateUser(String edad, String email, String fotoPerfil, String nombre)
    {
        System.out.println("Llego a actualizar");
        ObjectNode result = Json.newObject();
        if(isAuthenticated())
        {
            try
            {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode json = request().body().asJson();
                Usuario user = mapper.readValue(json.toString(), Usuario.class);
                System.out.println("JSON ENTRANTE "+json.toString());
                System.out.println(user.toString());
                Usuario old = (Usuario) JPA.em().createQuery("SELECT u FROM Usuario u where u.email = :value1")
                        .setParameter("value1", session().get("username").toString()).getSingleResult();
                if(!user.equals(old))
                {
                    user.setPassword(old.getPassword());
                    user.setAmigos(old.getAmigos());
                    JPA.em().merge(user);
                    System.out.println("Persitio el usuario");
                    result.put("status","OK");
                    result.put("message","Usuario actualizado!");
                    System.out.println(toJson(result));
                    System.out.println(result.toString());
                    return ok(toJson(result));
                }
                else
                {
                    System.out.println("ERRORRR");
                    result.put("status","ERROR");
                    result.put("message", "No se registran cambios!");
                    System.out.println(result.toString());
                    return badRequest(toJson(result));
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                result.put("status","ERROR");
                result.put("message", "Ocurrio un error al actualizar el usuario!");
                System.out.println(result.toString());
                return badRequest(toJson(result));
            }
        }
        else
        {
            result.put("status","ERROR");
            result.put("message", "Usuario no autenticado!");
            System.out.println(result.toString());
            return badRequest(toJson(result));
        }

    }

    @Transactional(readOnly = true)
    public Result getUser()
    {
        System.out.println("Llego a obtener usuario");
        if(isAuthenticated())
        {
            ObjectNode result = Json.newObject();
            String mail = session().get("username");
            Usuario user = (Usuario) JPA.em().createQuery("SELECT u FROM Usuario u where u.email = :value1")
                    .setParameter("value1", mail).getSingleResult();
            System.out.println("OBTENER USUARIO "+toJson(user));
            return ok(toJson(user));
        }
        else
        {
            System.out.println("No esta autenticado");
            return badRequest();
        }
    }

    @Transactional(readOnly = true)
    public Result getUsers()
    {
        System.out.println("Llego a obtener usuarios");
        if(isAuthenticated())
        {
            ObjectNode result = Json.newObject();
            List<Usuario> persons = JPA.em().createQuery("select p from Usuario p", Usuario.class).getResultList();
            String mail = session().get("username");
            Usuario user = (Usuario) JPA.em().createQuery("SELECT u FROM Usuario u where u.email = :value1")
                    .setParameter("value1", mail).getSingleResult();
            ArrayList<Usuario> usuarios = new ArrayList();
            for(int i = 0; i< persons.size();i++)
            {
                if(!user.getAmigos().contains(persons.get(i)) && !persons.get(i).equals(user))
                {
                    usuarios.add(persons.get(i));
                }
            }
            System.out.println("OBTENER USUARIOS "+toJson(usuarios).toString());
            return ok(toJson(usuarios));
        }
        else
        {
            System.out.println("No esta autenticado");
            return badRequest();
        }

    }

    @Transactional(readOnly = true)
    public Result getFriends()
    {
        System.out.println("Llego a obtener amigos");
        if(isAuthenticated())
        {
            ObjectNode result = Json.newObject();
            String mail = session().get("username");
            Usuario user = (Usuario) JPA.em().createQuery("SELECT u FROM Usuario u where u.email = :value1")
                    .setParameter("value1", mail).getSingleResult();
            List<Usuario> amigos = user.getAmigos();
            System.out.println("OBTENER AMIGOS "+toJson(amigos).toString());
            return ok(toJson(amigos));
        }
        else
        {
            System.out.println("No esta autenticado");
            return badRequest();
        }

    }

    @Transactional
    public Result addFriend()
    {
        System.out.println("Llego a agreagar amigo");
        if(isAuthenticated())
        {
            ObjectNode result = Json.newObject();
            JsonNode json = request().body().asJson();
            System.out.println(json.toString());
            Integer id_friend = Integer.valueOf(json.get("id_friend").asText());
            Usuario user = (Usuario) JPA.em().createQuery("SELECT u FROM Usuario u where u.email = :value1")
                    .setParameter("value1", session().get("username")).getSingleResult();
            Usuario userFriend = JPA.em().find(Usuario.class, id_friend);
            //user.setParent(user);
            user.getAmigos().add(userFriend);
            JPA.em().merge(user);
            JPA.em().merge(userFriend);
            result.put("status","OK");
            result.put("message","Amigo agregado");
            System.out.println(result.toString());
            return ok(toJson(result));
        }
        else
        {
            System.out.println("No esta autenticado");
            return badRequest();
        }
    }

    public Result index()
    {
        return ok().sendFile(new java.io.File("public/index.html"));
    }

    public Result logout()
    {
        ObjectNode result = Json.newObject();
        if(isAuthenticated())
        {
            session().clear();
            result.put("status","OK");
            result.put("message","Usuario desautenticado");
            return ok(toJson(result));
        }
        else
        {
            result.put("status","ERROR");
            result.put("message", "Usuario no autenticado!");
            return badRequest(toJson(result));
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

    public static boolean isAuthenticated() {
        if(session().get("username") == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
