package co.edu.uniandes.bicibo.rest;

import co.edu.uniandes.bicibo.domain.Usuario;
import co.edu.uniandes.bicibo.service.MensajeService;
import co.edu.uniandes.bicibo.service.RecorridoService;
import co.edu.uniandes.bicibo.service.SitioAlquilerService;
import co.edu.uniandes.bicibo.service.UsuarioService;

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

	@GET
	@Path("/recorridosUser/{id}")
    public JSONObject listarRecorridos(@PathParam("id") String id) 
	{
		RecorridoService recorridoService = new RecorridoService();
        return recorridoService.listarRecorridos(id);
    }
	
	@Path("/recorrido")
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
	/*
	@Path("/routes2")
	@GET
    public User ListarRecorridosGrupo() {
        UserService userService = new UserService();
        return userService.ListarRecorridosGrupo();
    }
	@Path("/route/add2")
	@POST
    public User AgregarRecorridoGrupo(@PathParam("id") String id) {
        UserService userService = new UserService();
        return userService.AgregarRecorridoGrupo(id);
    }
	@Path("/route/insert")
	@POST
    public User UnirseRecorridoGrupo(@PathParam("id") String id) {
        UserService userService = new UserService();
        return userService.UnirseRecorridoGrupo(id);
    }
    */
	
	//Mensajes
	
	@POST
	@Path("/mensaje")
    public JSONObject enviarMensaje(@QueryParam("id_usuario_origen") String id_usuario_origen,
    		@QueryParam("mensaje") String mensaje,
    		@QueryParam("id_usuario_destino") String id_usuario_destino) 
	{
		System.out.println("olaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa2 "+mensaje + id_usuario_origen + " + "+ id_usuario_destino);
        MensajeService mensajeService = new MensajeService();
        return mensajeService.enviarMensaje("1", "hello", "2");
    }
	
	@GET
	@Path("/numMensajes/{id}")
    public JSONObject darNumMensajesNuevos(@PathParam("id") String id) 
	{
        MensajeService mensajeService = new MensajeService();
        return mensajeService.darNumMensajesNuevos(id);
    }
	
	@GET
	@Path("/mensajes/{id}")
    public JSONObject verMensajesNuevos(@PathParam("id") String id) 
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
	@Path("/chat/{id}/{id2}")
    public JSONObject verChat(@PathParam("id") String id, @PathParam("id2") String id2) 
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
