package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Recorrido;
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
 * Created by Junior on 5/10/2016.
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
    public Result getRecorridosUsuario()
    {
        ObjectNode result = Json.newObject();
        Usuario user = (Usuario) JPA.em().createQuery("SELECT u FROM Usuario u where u.email = :value1")
                .setParameter("value1", session().get("username")).getSingleResult();
        List<Recorrido> viajes = JPA.em().createQuery("select p from Recorrido p where p.id_usuario = "+user.getId(), Recorrido.class).getResultList();
        System.out.println("RECORRIDOS "+toJson(viajes).toString());
        return ok(toJson(viajes));
    }

    @Transactional(readOnly = true)
    public Result getRecorrido(Integer id)
    {
        ObjectNode result = Json.newObject();
        System.out.println("Llego a mostrar recorrido con id "+id);
        Recorrido recorrido = JPA.em().find(Recorrido.class,Integer.valueOf(id));
        return ok(toJson(recorrido));
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result createRecorrido(String caloriasQuemada , String  destin,String distanci ,String fecha_recorrid ,String frecuenci ,String hora_llegad ,String hora_salid,  String iD, String infoClim, String orige, String real, String tiempoEstimad)
    {
        System.out.println("Llego a crear recorrido");
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();
        System.out.println(json.toString());
        Recorrido recorrido = new Recorrido();
        Usuario user = (Usuario) JPA.em().createQuery("SELECT u FROM Usuario u where u.email = :value1")
                .setParameter("value1", session().get("username")).getSingleResult();
        try
        {

            recorrido.setId_usuario(user.getId());

            String realizado = json.get("realizado").asText();
            if(realizado.equalsIgnoreCase("si") || realizado.equalsIgnoreCase("true"))
            {
                recorrido.setRealizado(true);
            }
            else
            {
                recorrido.setRealizado(false);
            }


            String origen = json.get("origen").asText();
            recorrido.setOrigen(origen);

            String destino = json.get("destino").asText();
            recorrido.setDestino(destino);
            //try
            //{
                //SimpleDateFormat formatter = new SimpleDateFormat("HH:MM");
                String hora_salida = json.get("hora_salida").asText();
                recorrido.setHora_salida(hora_salida);

                String hora_llegada = json.get("hora_llegada").asText();
                recorrido.setHora_llegada(hora_llegada);

                //SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
                String fecha_recorrido = json.get("fecha_recorrido").asText();
                recorrido.setFecha_recorrido(fecha_recorrido);
            //}
            //catch (ParseException e)
            //{
            //    e.printStackTrace();
            //}
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
            e.printStackTrace();
            result.put("status","ERROR");
            result.put("message","Todos los campos deben ser completados con el tipo de dato correcto!");
            return badRequest(toJson(result));
        }
        JPA.em().persist(recorrido);
        user.getRecorridos().add(recorrido);
        JPA.em().merge(user);
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
    public Result updateRecorrido(String caloriasQuemada , String  destin,String distanci ,String fecha_recorrid ,String frecuenci ,String hora_llegad ,String hora_salid,  String iD, String id_recorrido, String infoClim, String orige, String real, String tiempoEstimad)
    {
        ObjectNode result = Json.newObject();
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = request().body().asJson();
            System.out.println("Llego a actualizar recorrido con JSON "+ json.toString());
            Recorrido recorrido = new Recorrido();
            Recorrido old = JPA.em().find(Recorrido.class,Integer.valueOf(json.get("id_recorrido").asText()));
            int id = old.getId();
            try
            {
                //int id = json.get("id").asInt();
                Usuario user = (Usuario) JPA.em().createQuery("SELECT u FROM Usuario u where u.email = :value1")
                        .setParameter("value1", session().get("username")).getSingleResult();
                //int id_usuario = json.get("id_usuario").asInt();
                recorrido.setId_usuario(user.getId());
                if(json.get("realizado")!=null)
                {
                    String realizado = json.get("realizado").asText();
                    if(realizado.equalsIgnoreCase("si") || realizado.equalsIgnoreCase("true"))
                    {
                        recorrido.setRealizado(true);
                    }
                    else
                    {
                        recorrido.setRealizado(false);
                    }
                }
                if(json.get("origen")!=null)
                {
                    String origen = json.get("origen").asText();
                    recorrido.setOrigen(origen);
                }
                if(json.get("destino")!=null)
                {
                    String destino = json.get("destino").asText();
                    recorrido.setDestino(destino);
                }
                //try
                //{
                    if(json.get("hora_salida")!=null)
                    {
                        String hora_salida = json.get("hora_salida").asText();
                        recorrido.setHora_salida(hora_salida);
                    }
                    if(json.get("hora_llegada")!=null)
                    {
                        String hora_llegada = json.get("hora_llegada").asText();
                        recorrido.setHora_llegada(hora_llegada);
                    }
                    if(json.get("fecha_recorrido")!=null)
                    {
                        String fecha_recorrido = json.get("fecha_recorrido").asText();
                        recorrido.setFecha_recorrido(fecha_recorrido);
                    }
                //}
                //catch (ParseException e)
                //{
                //    e.printStackTrace();
                //}
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
                e.printStackTrace();
                result.put("status","ERROR");
                result.put("message","Todos los campos deben ser completados con el tipo de dato correcto!");
                return badRequest(toJson(result));
            }
            if(!recorrido.equals(old))
            {
                //JPA.em().remove(old);
                //JPA.em().clear();
                recorrido.setId(id);
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
            e.printStackTrace();
            result.put("status","ERROR");
            result.put("message", "Ocurrio un error al actualizar el recorrido!");
            return badRequest(toJson(result));
        }
    }

    public Result mostrarMapa()
    {
        return ok().sendFile(new java.io.File("public/templates/mostrarMapa.html"));
    }
}