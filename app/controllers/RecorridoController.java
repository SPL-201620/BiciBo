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

    public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Transactional(readOnly = true)
    public Result getRecorridos()
    {
        ObjectNode result = Json.newObject();
        List<Recorrido> viajes = JPA.em().createQuery("select p from Recorrido p", Recorrido.class).getResultList();
        return ok(toJson(viajes));
    }

    @Transactional(readOnly = true)
    public Result getRecorridosUsuario(Integer id)
    {
        ObjectNode result = Json.newObject();
        List<Recorrido> viajes = JPA.em().createQuery("select p from Recorrido p where id_usuario = "+id, Recorrido.class).getResultList();
        return ok(toJson(viajes));
    }

    @Transactional(readOnly = true)
    public Result getRecorrido(Integer id)
    {
        ObjectNode result = Json.newObject();
        Recorrido recorrido = JPA.em().find(Recorrido.class,Integer.valueOf(id));
        return ok(toJson(recorrido));
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result createRecorrido()
    {
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();
        Recorrido recorrido = new Recorrido();
        try
        {
            int id = json.get("id").asInt();
            recorrido.setId_usuario(id);

            Boolean realizado = json.get("realizado").asBoolean();
            recorrido.setRealizado(realizado);

            Long origen = json.get("origen").asLong();
            recorrido.setOrigen(origen);

            Long destino = json.get("destino").asLong();
            recorrido.setDestino(destino);
            try
            {
                Date hora_salida = formatter.parse(json.get("hora_salida").asText());
                recorrido.setHora_salida(hora_salida);

                Date hora_llegada = formatter.parse(json.get("hora_llegada").asText());
                recorrido.setHora_llegada(hora_llegada);

                Date fecha_recorrido = formatter.parse(json.get("fecha_recorrido").asText());
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
            Recorrido old = JPA.em().find(Recorrido.class,Integer.valueOf(json.get("id").asText()));
            try
            {
                int id = json.get("id").asInt();

                if(json.get("id_usuario")!=null)
                {
                    int id_usuario = json.get("id_usuario").asInt();
                    recorrido.setId_usuario(id_usuario);
                    old.setId_usuario(recorrido.getId_usuario());
                }
                if(json.get("realizado")!=null)
                {
                    Boolean realizado = json.get("realizado").asBoolean();
                    recorrido.setRealizado(realizado);
                    old.setRealizado(recorrido.isRealizado());
                }
                if(json.get("origen")!=null)
                {
                    Long origen = json.get("origen").asLong();
                    recorrido.setOrigen(origen);
                    old.setOrigen(recorrido.getOrigen());
                }
                if(json.get("destino")!=null)
                {
                    Long destino = json.get("destino").asLong();
                    recorrido.setDestino(destino);
                    old.setDestino(recorrido.getDestino());
                }
                try
                {
                    if(json.get("hora_salida")!=null)
                    {
                        Date hora_salida = formatter.parse(json.get("hora_salida").asText());
                        recorrido.setHora_salida(hora_salida);
                        old.setHora_salida(recorrido.getHora_salida());
                    }
                    if(json.get("hora_llegada")!=null)
                    {
                        Date hora_llegada = formatter.parse(json.get("hora_llegada").asText());
                        recorrido.setHora_llegada(hora_llegada);
                        old.setHora_llegada(recorrido.getHora_llegada());
                    }
                    if(json.get("fecha_recorrido")!=null)
                    {
                        Date fecha_recorrido = formatter.parse(json.get("fecha_recorrido").asText());
                        recorrido.setFecha_recorrido(fecha_recorrido);
                        old.setFecha_recorrido(recorrido.getFecha_recorrido());
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
                    old.setDistancia(recorrido.getDistancia());
                }
                if(json.get("tiempoEstimado")!=null)
                {
                    int tiempoEstimado = json.get("tiempoEstimado").asInt();
                    recorrido.setTiempoEstimado(tiempoEstimado);
                    old.setTiempoEstimado(recorrido.getTiempoEstimado());
                }
                if(json.get("caloriasQuemadas")!=null)
                {
                    int caloriasQuemadas = json.get("caloriasQuemadas").asInt();
                    recorrido.setCaloriasQuemadas(caloriasQuemadas);
                    old.setCaloriasQuemadas(recorrido.getCaloriasQuemadas());
                }
                if(json.get("infoClima")!=null)
                {
                    String infoClima = json.get("infoClima").asText();
                    recorrido.setInfoClima(infoClima);
                    old.setInfoClima(recorrido.getInfoClima());
                }
            }
            catch(Exception e)
            {
                result.put("status","ERROR");
                result.put("message","Todos los campos deben ser completados con el tipo de dato correcto!");
                return badRequest(toJson(result));
            }
            if(!recorrido.equals(old))
            {
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
}
