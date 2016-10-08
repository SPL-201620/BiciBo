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
import java.util.Date;
import java.text.*;

import static play.libs.Json.toJson;

/**
 * Created by santi on 5/10/2016.
 */
public class RecorridoController extends Controller
{
    public static final String SALT = "my-salt-text";

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result createRecorrido() {
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();
        Recorrido recorrido = new Recorrido();
        try
        {
            int id = json.get("id").asInt();

            Boolean realizado = json.get("realizado").asBoolean();
            recorrido.setRealizado(realizado);

            Long origen = json.get("origen").asLong();
            recorrido.setOrigen(origen);

            Long destino = json.get("destino").asLong();
            recorrido.setDestino(destino);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            try
            {
                Date hora_salida = formatter.parse(json.get("hora_salida").asText().replaceAll("Z$", "+0000"));
                recorrido.setHora_salida(hora_salida);

                Date hora_llegada = formatter.parse(json.get("hora_llegada").asText().replaceAll("Z$", "+0000"));
                recorrido.setHora_llegada(hora_llegada);

                Date fecha_recorrido = formatter.parse(json.get("fecha_recorrido").asText().replaceAll("Z$", "+0000"));
                recorrido.setFecha_recorrido(fecha_recorrido);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            int distancia = json.get("distancia").asInt();
            recorrido.setDistancia(distancia);

            int tiempoEstimado = json.get("tiempoEstimado").asInt();
            recorrido.setTiempoEstimado(tiempoEstimado);

            int caloriasQuemadas = json.get("caloriasQuemadas").asInt();
            recorrido.setCaloriasQuemadas(caloriasQuemadas);

            String infoClima = json.get("infoClima").asText();
            recorrido.setInfoClima(infoClima);
        }
        catch(Exception e)
        {
            result.put("status","ERROR");
            result.put("message","Todos los campos deben ser completados con el tipo de dato correcto!");
            return badRequest(toJson(result));
        }
        JPA.em().persist(recorrido);
        try
        {
            JPA.em().flush();
            result.put("status","OK");
            result.put("message","Recorrido agregado");
            return ok(toJson(result));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.put("status","ERROR");
            result.put("message","Error agregando el recorrido");
            return badRequest(toJson(result));
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result updateRecorrido()
    {
        ObjectNode result = Json.newObject();
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = request().body().asJson();
            Recorrido recorrido = new Recorrido();
            try
            {
                int id = json.get("id").asInt();

                if(json.get("realizado")!=null)
                {
                    Boolean realizado = json.get("realizado").asBoolean();
                    recorrido.setRealizado(realizado);
                }

                if(json.get("origen")!=null)
                {
                    Long origen = json.get("origen").asLong();
                    recorrido.setOrigen(origen);
                }

                if(json.get("destino")!=null)
                {
                    Long destino = json.get("destino").asLong();
                    recorrido.setDestino(destino);
                }

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                try
                {
                    if(json.get("hora_salida")!=null)
                    {
                        Date hora_salida = formatter.parse(json.get("hora_salida").asText().replaceAll("Z$", "+0000"));
                        recorrido.setHora_salida(hora_salida);
                    }

                    if(json.get("hora_llegada")!=null)
                    {
                        Date hora_llegada = formatter.parse(json.get("hora_llegada").asText().replaceAll("Z$", "+0000"));
                        recorrido.setHora_llegada(hora_llegada);
                    }

                    if(json.get("fecha_recorrido")!=null)
                    {
                        Date fecha_recorrido = formatter.parse(json.get("fecha_recorrido").asText().replaceAll("Z$", "+0000"));
                        recorrido.setFecha_recorrido(fecha_recorrido);
                    }
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                if(json.get("distancia")!=null)
                {
                    int distancia = json.get("distancia").asInt();
                    recorrido.setDistancia(distancia);
                }

                if(json.get("tiempoEstimado")!=null)
                {
                    int tiempoEstimado = json.get("tiempoEstimado").asInt();
                    recorrido.setTiempoEstimado(tiempoEstimado);
                }

                if(json.get("caloriasQuemadas")!=null)
                {
                    int caloriasQuemadas = json.get("caloriasQuemadas").asInt();
                    recorrido.setCaloriasQuemadas(caloriasQuemadas);
                }

                if(json.get("infoClima")!=null)
                {
                    String infoClima = json.get("infoClima").asText();
                    recorrido.setInfoClima(infoClima);
                }
            }
            catch(Exception e)
            {
                result.put("status","ERROR");
                result.put("message","Todos los campos deben ser completados con el tipo de dato correcto!");
                return badRequest(toJson(result));
            }
            Recorrido old = JPA.em().find(Recorrido.class,Integer.valueOf(json.get("id").asText()));
            if(!recorrido.equals(old))
            {
                old.setRealizado(recorrido.getRealizado());
                System.out.println("ola");
                old.setOrigen(recorrido.getOrigen());
                old.setHora_salida(recorrido.getHora_salida());
                old.setHora_llegada(recorrido.getHora_llegada());
                old.setFecha_recorrido(recorrido.getFecha_recorrido());
                old.setDistancia(recorrido.getDistancia());
                old.setTiempoEstimado(recorrido.getTiempoEstimado());
                old.setCaloriasQuemadas(recorrido.getCaloriasQuemadas());
                old.setInfoClima(recorrido.getInfoClima());
                System.out.println("ola2");
                JPA.em().merge(recorrido);
                result.put("status","OK");
                result.put("message","Recorrido actualizado");
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
            result.put("message", "Ocurrio un error al actualizar el recorrido!");
            return badRequest(toJson(result));
        }
    }

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
