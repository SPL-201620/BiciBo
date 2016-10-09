package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.RecorridoGrupal;
import models.Usuario;
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
public class RecorridoGrupalController extends Controller
{
    public static final String SALT = "my-salt-text";

    @Transactional(readOnly = true)
    public Result getRecorridosGrupales()
    {
        ObjectNode result = Json.newObject();
        List<RecorridoGrupal> viajes = JPA.em().createQuery("select p from RecorridoGrupal as p", RecorridoGrupal.class).getResultList();
        return ok(toJson(viajes));
    }

    @Transactional(readOnly = true)
    public Result getRecorridosGrupalesUsuario(Integer id)
    {
        ObjectNode result = Json.newObject();
        List viajes = JPA.em().createQuery("select p, case when s.id is null then 0 else 1 end as registrado from RecorridoGrupal as p right join p.suscritos as s where s.id = "+id).getResultList();
        return ok(toJson(viajes));
    }

    @Transactional(readOnly = true)
    public Result getRecorridoGrupal(Integer id, Integer id2)
    {
        ObjectNode result = Json.newObject();
        List viajes = JPA.em().createQuery("select p, case when s.id is null then 0 else 1 end as registrado from RecorridoGrupal as p left join p.suscritos as s where p.id = "+id+" and s.id = "+id2).getResultList();
        return ok(toJson(viajes));
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result createRecorridoGrupal()
    {
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();
        RecorridoGrupal recorrido = new RecorridoGrupal();
        try
        {
            int id = json.get("id").asInt();
            recorrido.setId_usuario(id);

            String nombre_organizador = json.get("nombre_organizador").asText();
            recorrido.setNombre_organizador(nombre_organizador);

            Long origen = json.get("origen").asLong();
            recorrido.setOrigen(origen);

            Long destino = json.get("destino").asLong();
            recorrido.setDestino(destino);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            try
            {
                System.out.println(json.get("hora_salida").asText());
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

            String esFrecuente = json.get("esFrecuente").asText();
            recorrido.setEsFrecuente(esFrecuente);
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
            result.put("message","Recorrido Grupal agregado");
            return ok(toJson(result));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.put("status","ERROR");
            result.put("message","Error agregando el recorrido grupal");
            return badRequest(toJson(result));
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result updateRecorridoGrupal()
    {
        ObjectNode result = Json.newObject();
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = request().body().asJson();
            RecorridoGrupal recorrido = new RecorridoGrupal();
            RecorridoGrupal old = JPA.em().find(RecorridoGrupal.class,Integer.valueOf(json.get("id").asText()));
            try
            {
                int id = json.get("id").asInt();

                if(json.get("id_usuario")!=null)
                {
                    int id_usuario = json.get("id_usuario").asInt();
                    recorrido.setId_usuario(id_usuario);
                    old.setId_usuario(recorrido.getId_usuario());
                }
                if(json.get("nombre_organizador")!=null)
                {
                    String nombre_organizador = json.get("nombre_organizador").asText();
                    recorrido.setNombre_organizador(nombre_organizador);
                    old.setNombre_organizador(recorrido.getNombre_organizador());
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
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                try
                {
                    if(json.get("hora_salida")!=null)
                    {
                        Date hora_salida = formatter.parse(json.get("hora_salida").asText().replaceAll("Z$", "+0000"));
                        recorrido.setHora_salida(hora_salida);
                        old.setHora_salida(recorrido.getHora_salida());
                    }
                    if(json.get("hora_llegada")!=null)
                    {
                        Date hora_llegada = formatter.parse(json.get("hora_llegada").asText().replaceAll("Z$", "+0000"));
                        recorrido.setHora_llegada(hora_llegada);
                        old.setHora_llegada(recorrido.getHora_llegada());
                    }
                    if(json.get("fecha_recorrido")!=null)
                    {
                        Date fecha_recorrido = formatter.parse(json.get("fecha_recorrido").asText().replaceAll("Z$", "+0000"));
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
                if(json.get("esFrecuente")!=null)
                {
                    String esFrecuente = json.get("esFrecuente").asText();
                    recorrido.setEsFrecuente(esFrecuente);
                    old.setEsFrecuente(recorrido.getEsFrecuente());
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
