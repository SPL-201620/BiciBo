package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.RecorridoGrupal;
import models.Usuario;
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
import java.util.Date;
import javax.persistence.*;
import java.text.*;

import static play.libs.Json.toJson;

/**
 * Created by Junior on 5/10/2016.
 */
public class RecorridoGrupalController extends Controller
{
    public static final String SALT = "my-salt-text";

    public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Transactional(readOnly = true)
    public Result getRecorridosGrupales()
    {
        ObjectNode result = Json.newObject();
        List<RecorridoGrupal> viajes = JPA.em().createQuery("select p from RecorridoGrupal as p", RecorridoGrupal.class).getResultList();
        Usuario user = (Usuario) JPA.em().createQuery("SELECT u FROM Usuario u where u.email = :value1")
                .setParameter("value1", session().get("username")).getSingleResult();
        for(int i= 0; i< viajes.size();i++)
        {
            if(!viajes.get(i).getId_usuario().equals(user.getId())&& !viajes.get(i).getSuscritos().contains(user))
            {
                viajes.get(i).setRegistrado(false);
            }
        }
        System.out.println("RECORRIDOS GRUPALES "+ toJson(viajes));
        return ok(toJson(viajes));
    }

    @Transactional(readOnly = true)
    public Result getRecorridosGrupalesUsuario()
    {
        ObjectNode result = Json.newObject();
        Usuario user = (Usuario) JPA.em().createQuery("SELECT u FROM Usuario u where u.email = :value1")
                .setParameter("value1", session().get("username")).getSingleResult();
        //List viajes = JPA.em().createQuery("select p, case when s.id ="+user.getId()+" then 1 else 0 end as registrado " +
         //       "from RecorridoGrupal as p left join p.suscritos as s ").getResultList();
        ArrayList viajes =  new ArrayList();
        viajes.addAll(user.getRecorridosGrupalesAdmin());
        System.out.println("Llego a dar recorridos grupal "+ toJson(viajes));
        return ok(toJson(viajes));
    }

    @Transactional(readOnly = true)
    public Result getRecorridoGrupal(Integer id)
    {
        ObjectNode result = Json.newObject();
        //List viajes = JPA.em().createQuery("select p, case when s.id ="+id+" then 1 else 0 end as registrado " +
                //"from RecorridoGrupal as p left join p.suscritos as s where p.id = "+id2).getResultList();
        RecorridoGrupal rg = JPA.em().find(RecorridoGrupal.class, id);
        return ok(toJson(rg));
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result createRecorridoGrupal()
    {
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();
        Usuario user = (Usuario) JPA.em().createQuery("SELECT u FROM Usuario u where u.email = :value1")
                .setParameter("value1", session().get("username")).getSingleResult();
        System.out.println("Llego a crear recorrido en grupo con JSON "+ json.toString());
        RecorridoGrupal recorrido = new RecorridoGrupal();
        try
        {
            //int id = json.get("id").asInt();
            recorrido.setId_usuario(user.getId());

            //String nombre_organizador = json.get("nombre_organizador").asText();
            recorrido.setNombre_organizador(user.getNombre());

            String origen = json.get("origen").asText();
            recorrido.setOrigen(origen);

            String destino = json.get("destino").asText();
            recorrido.setDestino(destino);

            String hora_salida = json.get("hora_salida").asText();
            recorrido.setHora_salida(hora_salida);

            String hora_llegada = json.get("hora_llegada").asText();
            recorrido.setHora_llegada(hora_llegada);

            String fecha_recorrido = json.get("fecha_recorrido").asText();
            recorrido.setFecha_recorrido(fecha_recorrido);
            //int distancia = json.get("distancia").asInt();
            //recorrido.setDistancia(distancia);

            //int tiempoEstimado = json.get("tiempoEstimado").asInt();
            //recorrido.setTiempoEstimado(tiempoEstimado);

            //int caloriasQuemadas = json.get("caloriasQuemadas").asInt();
            //recorrido.setCaloriasQuemadas(caloriasQuemadas);

            //String infoClima = json.get("infoClima").asText();
            //recorrido.setInfoClima(infoClima);

            String esFrecuente = json.get("frecuencia").asText();
            recorrido.setEsFrecuente(esFrecuente);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            result.put("status","ERROR");
            result.put("message","Todos los campos deben ser completados con el tipo de dato correcto!");
            return badRequest(toJson(result));
        }
        recorrido.setRegistrado(true);
        JPA.em().persist(recorrido);
        user.getRecorridosGrupalesAdmin().add(recorrido);
        JPA.em().merge(user);
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
    public Result updateRecorridoGrupal(String destin , String fecha_recorrid ,String frecuenci ,String hora_llegad , String hora_salid ,String i , String id_recorrid, String orige)
    {
        ObjectNode result = Json.newObject();
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = request().body().asJson();
            System.out.println("ACTUALIZAR RECORRIDO GRUPAL "+ json.toString());
            RecorridoGrupal recorrido = new RecorridoGrupal();
            RecorridoGrupal old = JPA.em().find(RecorridoGrupal.class,Integer.valueOf(json.get("id_recorrido").asText()));
            Usuario user = (Usuario) JPA.em().createQuery("SELECT u FROM Usuario u where u.email = :value1")
                    .setParameter("value1", session().get("username")).getSingleResult();
            System.out.println("Llego a crear recorrido en grupo con JSON "+ json.toString());
            try
            {
                //int id = json.get("id").asInt();

                //if(json.get("id_usuario")!=null)
                //{
                    //int id_usuario = json.get("id_usuario").asInt();
                    //recorrido.setId_usuario(user.getId());
                //}
                //if(json.get("nombre_organizador")!=null)
                //{
                    //String nombre_organizador = json.get("nombre_organizador").asText();
                    //recorrido.setNombre_organizador(nombre_organizador);
                //}
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
                //if(json.get("distancia")!=null)
                //{
                //    int distancia = json.get("distancia").asInt();
                //    recorrido.setDistancia(distancia);
                //    old.setDistancia(recorrido.getDistancia());
                //}
                //if(json.get("tiempoEstimado")!=null)
                //{
                //    int tiempoEstimado = json.get("tiempoEstimado").asInt();
                //    recorrido.setTiempoEstimado(tiempoEstimado);
                //    old.setTiempoEstimado(recorrido.getTiempoEstimado());
                //}
                //if(json.get("caloriasQuemadas")!=null)
                //{
                //    int caloriasQuemadas = json.get("caloriasQuemadas").asInt();
                //    recorrido.setCaloriasQuemadas(caloriasQuemadas);
                //    old.setCaloriasQuemadas(recorrido.getCaloriasQuemadas());
                //}
                //if(json.get("infoClima")!=null)
                //{
                //    String infoClima = json.get("infoClima").asText();
                //    recorrido.setInfoClima(infoClima);
                //    old.setInfoClima(recorrido.getInfoClima());
                //}
                if(json.get("frecuencia")!=null)
                {
                    String esFrecuente = json.get("frecuencia").asText();
                    recorrido.setEsFrecuente(esFrecuente);
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
                recorrido.setId(old.getId());
                recorrido.setId_usuario(user.getId());
                recorrido.setSuscritos(old.getSuscritos());
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

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result insertToRecorridoGrupal(String iD, String id_recorrid)
    {
        ObjectNode result = Json.newObject();
        JsonNode json = request().body().asJson();

        //int id = json.get("id").asInt();
        Usuario user = (Usuario) JPA.em().createQuery("SELECT u FROM Usuario u where u.email = :value1")
                .setParameter("value1", session().get("username")).getSingleResult();
        int id_recorrido = json.get("id_recorrido").asInt();
        RecorridoGrupal recorrido = JPA.em().find(RecorridoGrupal.class,id_recorrido);
        try
        {
            List suscritos = JPA.em().createQuery("select p.suscritos from RecorridoGrupal as p where p.id = "+id_recorrido).getResultList();
            suscritos.add(user);
            recorrido.setSuscritos((List<Usuario>)suscritos);

            JPA.em().persist(recorrido);

            JPA.em().flush();
            result.put("status","OK");
            result.put("message","Recorrido Grupal agregado");
            return ok(toJson(result));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.put("status","ERROR");
            result.put("message","Ha ocurrido un error inscribiendo al usuario");
            return ok(toJson(result));
        }
    }
}
