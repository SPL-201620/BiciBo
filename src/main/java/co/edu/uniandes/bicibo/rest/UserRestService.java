package co.edu.uniandes.bicibo.rest;

import co.edu.uniandes.bicibo.domain.Usuario;
import co.edu.uniandes.bicibo.service.MensajeService;
import co.edu.uniandes.bicibo.service.RecorridoGrupalService;
import co.edu.uniandes.bicibo.service.RecorridoService;
import co.edu.uniandes.bicibo.service.SitioAlquilerService;
import co.edu.uniandes.bicibo.service.UsuarioService;
//import co.edu.uniandes.bicibo.service.RecorridoService;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class UserRestService 
{
	@POST
	@Path("/user")
    public JSONObject registrar(@QueryParam("nombre") String nombre,
    		@QueryParam("email") String email,
    		@QueryParam("username") String username,
    		@QueryParam("password") String password,
    		@QueryParam("rutaFoto") String rutaFoto) 
	{
		System.out.println("email: "+email);
        UsuarioService usuarioService = new UsuarioService();
        return usuarioService.registrar(nombre, email, username, password, rutaFoto);
    }
	
	@POST
	@Path("/login")
    public JSONObject login(@QueryParam("username") String username,
    		@QueryParam("password") String password) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.login(username, password);
    }
	
	@Path("/logout")
	@POST
    public JSONObject logout(@QueryParam("id") String id) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.logout(id);
    }
	/*
	 * Servicio para consultar la informacion de un usuario en particular
	 * */
	@GET		
	@Path("/user/{id}")
    public Usuario infoUsuario(@PathParam("id") String id) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.infoUsuario(Integer.parseInt(id));
    }
	
	@Path("/user")
	@PUT
    public JSONObject updateUsuario(@QueryParam("id") String id,
    		@QueryParam("nombre") String nombre,
    		@QueryParam("email") String email,
    		@QueryParam("password") String password,
    		@QueryParam("username") String username,    		
    		@QueryParam("edad") String edad,
    		@QueryParam("fotoPerfil") String fotoPerfil) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.updateUsuario(id, nombre, email, password, username, edad, fotoPerfil);
    }
    
	@Path("/users/{id}")
	@GET
    public JSONObject listarRegistrados(@PathParam("id") String id) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.listarRegistrados(id);
    }
	@Path("/friends/{id}")
	@GET
    public JSONObject listarAmigos(@PathParam("id") String id) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.listarAmigos(id);
    }
	@Path("/friend")
	@POST
    public JSONObject agregarAmigo(@QueryParam("id") String id,@QueryParam("id_friend") String idAmigo) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.agregarAmigo(id, idAmigo);
    }
	/*
	 * Listar las recorridos individuales que un usuario ha realizado
	 * */
/*EN PROCESO....ABIMELEC
	@POST
	@Path("/recorrido")
    public JSONObject AgregarRecorrido(@PathParam("id_usuario") String id_usuario,
    		@PathParam("origen") String origen,
    		@PathParam("destino") String destino,
    		@PathParam("hora_salida") String hora_salida,
    		@PathParam("hora_llegada") String hora_llegada,
    		@PathParam("fecha_recorrido") String fecha_recorrido,
    		@PathParam("realizado") String realizado,
    		@PathParam("distancia") String distancia,
    		@PathParam("tiempoEstimado") String tiempoEstimado,
    		@PathParam("caloriasQuemadas") String caloriasQuemadas,
    		@PathParam("infoClima") String infoClima) 
	{
		RecorridoService recorridoService = new RecorridoService();
        return recorridoService.AgregarRecorrido(id_usuario,  
        		origen,
        		destino,
        		hora_salida,
        		hora_llegada,
        		fecha_recorrido,
        		realizado,
        		distancia,
        		tiempoEstimado,
        		caloriasQuemadas,
        		infoClima);
    }
---FIN ABIMELEC*/    
	/*
	 * Listar las recorridos individuales que un usuario ha realizado
	 * */

	@GET
	@Path("/recorridosUser/{id}")
    public JSONObject listarRecorridos(@PathParam("id") String id) 
	{
		RecorridoService recorridoService = new RecorridoService();
        return recorridoService.listarRecorridos(id);
    }
	
	@Path("/recorrido/{id}")
	@GET
    public JSONObject darRecorrido(@PathParam("id") String id) 
	{
		RecorridoService recorridoService = new RecorridoService();
        return recorridoService.darRecorrido(id);
    }
	
	@Path("/recorrido")
	@POST
    public JSONObject agregarRecorrido(@QueryParam("id_usuario") String idUsuario,
    		@QueryParam("origen") String origen,
    		@QueryParam("destino") String destino,
    		@QueryParam("hora_salida") String horaSalida,
    		@QueryParam("hora_llegada") String horaLlegada,
    		@QueryParam("fecha_recorrido") String fechaRecorrido,
    		@QueryParam("realizado") String realizado,
    		@QueryParam("distancia") String distancia,
    		@QueryParam("tiempoEstimado") String tiempoEstimado,
    		@QueryParam("caloriasQuemadas") String caloriasQuemadas,
    		@QueryParam("infoClima") String infoClima) 
	{
		RecorridoService recorridoService = new RecorridoService();
        return recorridoService.agregarRecorrido(idUsuario, origen, destino, horaSalida, horaLlegada, fechaRecorrido,
        		realizado, distancia, tiempoEstimado, caloriasQuemadas, infoClima);
    }
	
	@Path("/recorrido")
	@PUT
    public JSONObject actualizarRecorrido(@QueryParam("id_recorrido") String id_recorrido,
    		@QueryParam("origen") String origen,
    		@QueryParam("destino") String destino,
    		@QueryParam("hora_salida") String horaSalida,
    		@QueryParam("hora_llegada") String horaLlegada,
    		@QueryParam("fecha_recorrido") String fechaRecorrido,
    		@QueryParam("realizado") String realizado,
    		@QueryParam("distancia") String distancia,
    		@QueryParam("tiempoEstimado") String tiempoEstimado,
    		@QueryParam("caloriasQuemadas") String caloriasQuemadas,
    		@QueryParam("infoClima") String infoClima) 
	{
		RecorridoService recorridoService = new RecorridoService();
        return recorridoService.actualizarRecorrido(id_recorrido, origen, destino, horaSalida, horaLlegada, fechaRecorrido,
        		realizado, distancia, tiempoEstimado, caloriasQuemadas, infoClima);
    }
	
	@Path("/recorridosGrupal/{id}")
	@GET
    public JSONObject listarRecorridosGrupo(@PathParam("id") String id) 
	{
		RecorridoGrupalService recorridoGrupalService = new RecorridoGrupalService();
        return recorridoGrupalService.listarRecorridosGrupo(id);
    }
	
	@Path("/recorridoGrupal/{id}/{id2}")
	@GET
    public JSONObject verRecorridoGrupo(@PathParam("id") String id, @PathParam("id2") String id2) 
	{
		RecorridoGrupalService recorridoGrupalService = new RecorridoGrupalService();
        return recorridoGrupalService.verRecorridoGrupo(id, id2);
    }
	
	@Path("/recorridoGrupal")
	@POST
    public JSONObject agregarRecorridoGrupo(@QueryParam("id_usuario") String idUsuario, 
    		@QueryParam("nombre_organizador") String nombreOrganizador, 
    		@QueryParam("origen") String origen, 
    		@QueryParam("destino") String destino, 
    		@QueryParam("horaSalida") String horaSalida, 
    		@QueryParam("horaLlegada") String horaLlegada, 
    		@QueryParam("fechaRecorrido") String fechaRecorrido, 
    		@QueryParam("distancia") String distancia, 
    		@QueryParam("tiempoEstimado") String tiempoEstimado, 
    		@QueryParam("caloriasQuemadas") String caloriasQuemadas, 
    		@QueryParam("infoClima") String infoClima, 
    		@QueryParam("frecuencia") String frecuencia) 
	{
		RecorridoGrupalService recorridoGrupalService = new RecorridoGrupalService();
        return recorridoGrupalService.agregarRecorridoGrupo(idUsuario, origen, destino, horaSalida, horaLlegada, fechaRecorrido,
        		distancia, tiempoEstimado, caloriasQuemadas, infoClima, frecuencia, nombreOrganizador);
    }
	
	@Path("/recorridoGrupal")
	@PUT
    public JSONObject actualizarRecorridoGrupo(@QueryParam("id") String id, 
    		@QueryParam("nombre_organizador") String nombreOrganizador, 
    		@QueryParam("origen") String origen, 
    		@QueryParam("destino") String destino,
    		@QueryParam("hora_salida") String horaSalida, 
    		@QueryParam("hora_llegada") String horaLlegada, 
    		@QueryParam("fecha_recorrido") String fechaRecorrido, 
    		@QueryParam("distancia") String distancia, 
    		@QueryParam("tiempoEstimado") String tiempoEstimado, 
    		@QueryParam("caloriasQuemadas") String caloriasQuemadas, 
    		@QueryParam("infoClima") String infoClima, 
    		@QueryParam("frecuencia") String frecuencia) 
	{
		RecorridoGrupalService recorridoGrupalService = new RecorridoGrupalService();
        return recorridoGrupalService.actualizarRecorridoGrupo(id, origen, destino, horaSalida, horaLlegada, fechaRecorrido,
        		distancia, tiempoEstimado, caloriasQuemadas, infoClima, frecuencia, nombreOrganizador);
    }
	
	@Path("/recorridoGrupal/insert")
	@POST
    public JSONObject UnirseRecorridoGrupo(@QueryParam("id") String id, @QueryParam("id2") String idRecorridoGrupal) 
	{
		RecorridoGrupalService recorridoGrupalService = new RecorridoGrupalService();
        return recorridoGrupalService.unirseRecorridoGrupo(id, idRecorridoGrupal);
    }
    
	//Mensajes
	@POST
	@Path("/mensaje")
    public JSONObject enviarMensaje(@QueryParam("id_usuario_origen") String id_usuario_origen,
    		@QueryParam("mensaje") String mensaje,
    		@QueryParam("id_usuario_destino") String id_usuario_destino) 
	{
		System.out.println("olaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa2 "+mensaje + id_usuario_origen + " + "+ id_usuario_destino);
        MensajeService mensajeService = new MensajeService();
        return mensajeService.enviarMensaje(id_usuario_origen, mensaje, id_usuario_destino);
    }
	
	@GET
	@Path("/numMensajes/{id}")
    public JSONObject darNumMensajesNuevos(@PathParam("id") String id) 
	{
        MensajeService mensajeService = new MensajeService();
        return mensajeService.darNumMensajesNuevos(id);
    }
	
	@GET
	@Path("/mensajes")
    public JSONObject verMensajesNuevos(@QueryParam("id") String id) 
	{
        MensajeService mensajeService = new MensajeService();
        return mensajeService.verMensajesNuevos(id);
    }
	
	@PUT
	@Path("/mensaje")
    public JSONObject marcarMensajeVisto(@QueryParam("id_mensaje") String id) 
	{
        MensajeService mensajeService = new MensajeService();
        return mensajeService.marcarMensajeVisto(id);
    }
	
	@GET
	@Path("/chat")
    public JSONObject verChat(@QueryParam("id") String id, @QueryParam("id2") String id2) 
	{
        MensajeService mensajeService = new MensajeService();
        return mensajeService.verChat(id, id2);
    }
	
	@PUT
	@Path("/mensajes")
    public JSONObject marcarMensajeSVisto(@QueryParam("id_usuario_origen") String id, @QueryParam("id_usuario_destino") String id2) 
	{
        MensajeService mensajeService = new MensajeService();
        return mensajeService.marcarMensajesVisto(id, id2);
    }
	
	@GET
	@Path("/sitiosAlquiler")
    public JSONObject darSitiosAlquiler() 
	{
        SitioAlquilerService sitioAlquilerService = new SitioAlquilerService();
        return sitioAlquilerService.darSitiosAlquiler();
    }
}
