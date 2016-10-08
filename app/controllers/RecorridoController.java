package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Recorrido;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by santi on 5/10/2016.
 */
public class RecorridoController extends Controller
{
    public static final String SALT = "my-salt-text";

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result createRecorrido()
    {
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();
        Boolean realizado = json.get("realizado").asBoolean();
        Long origen = json.get("origen").asLong();
        /*String mail = json.get("email").asText();
        String pass = json.get("clave").asText();
        String mail = json.get("email").asText();
        String pass = json.get("clave").asText();*/
        /*if(mail.isEmpty() || pass.isEmpty())
        {
            result.put("status","ERROR");
            result.put("message","Todos los campos deben ser completados!");
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
                result.put("status","OK");
                result.put("message","Usuario creado");
                return ok(toJson(result));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("LLEGO AL CATCH");
                result.put("status","ERROR");
                result.put("message","Usuario ya existe con ese email!");
                return badRequest(toJson(result));
            }

        }*/
        return ok(toJson(result));
    }
    /*
    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result loginUser()
    {
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();
        String mail = json.get("email").asText();
        String pass = json.get("clave").asText();
        if(mail.isEmpty() || pass.isEmpty())
        {
            result.put("status","ERROR");
            result.put("message","Todos los campos deben ser completados!");
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
                    return ok(toJson(result));
                }
                else
                {
                    result.put("nombre", user.getNombre());
                    result.put("status","ERROR");
                    result.put("message","Email o Clave incorrecta");
                    return badRequest(toJson(result));
                }
            }
            catch (Exception e)
            {
                result.put("status","ERROR");
                result.put("message","Email o Clave incorrecta");
                return badRequest(toJson(result));
            }
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result updateUser()
    {
        ObjectNode result = Json.newObject();
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = request().body().asJson();
            Usuario user = mapper.readValue(json.toString(), Usuario.class);
            Usuario old = JPA.em().find(Usuario.class,Integer.valueOf(json.get("id").asText()));
            if(!user.equals(old))
            {
                user.setPassword(old.getPassword());
                user.setAmigos(old.getAmigos());
                JPA.em().merge(user);
                result.put("status","OK");
                result.put("message","Usuario actualizado!");
                return ok(toJson(result));
            }
            else
            {
                result.put("status","ERROR");
                result.put("message", "No se registran cambios!");
                return badRequest(toJson(result));
            }
        }
        catch(Exception e)
        {
            result.put("status","ERROR");
            result.put("message", "Ocurrio un error al actualizar el usuario!");
            return badRequest(toJson(result));
        }
    }*/

    @Transactional(readOnly = true)
    public Result getRecorrido(Integer id)
    {
        ObjectNode result = Json.newObject();
        Recorrido recorrido = JPA.em().find(Recorrido.class,Integer.valueOf(id));
        return ok(toJson(recorrido));
    }

    @Transactional(readOnly = true)
    public Result getRecorridos()
    {
        ObjectNode result = Json.newObject();
        List<Recorrido> viajes = JPA.em().createQuery("select p from Recorrido p", Recorrido.class).getResultList();
        return ok(toJson(viajes));
    }

   /* @Transactional(readOnly = true)
    public Result getFriends(Integer id)
    {
        ObjectNode result = Json.newObject();
        Usuario user = JPA.em().find(Usuario.class, id);
        List<Usuario> amigos = user.getAmigos();
        return ok(toJson(amigos));
    }

    @Transactional
    public Result addFriend()
    {
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();
        Integer id = Integer.valueOf(json.get("id").asText());
        Integer id_friend = Integer.valueOf(json.get("id_friend").asText());
        Usuario user = JPA.em().find(Usuario.class, id);
        Usuario userFriend = JPA.em().find(Usuario.class, id_friend);
        //user.setParent(user);
        user.getAmigos().add(userFriend);
        JPA.em().merge(user);
        JPA.em().merge(userFriend);
        result.put("status","OK");
        result.put("message","Amigo agregado");
        return ok(toJson(result));
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
    }*/
}
