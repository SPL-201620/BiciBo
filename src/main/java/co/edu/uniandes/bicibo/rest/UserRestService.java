package co.edu.uniandes.bicibo.rest;

import co.edu.uniandes.bicibo.domain.Usuario;
import co.edu.uniandes.bicibo.service.MensajeService;
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
    public JSONObject Registrar(@QueryParam("nombre") String nombre,
    		@QueryParam("email") String email,
    		@QueryParam("username") String username,
    		@QueryParam("password") String password,
    		@QueryParam("rutaFoto") String rutaFoto) 
	{
		System.out.println("email: "+email);
        UsuarioService usuarioService = new UsuarioService();
        return usuarioService.Registrar(nombre, email, username, password, rutaFoto);
    }
	
	@POST
	@Path("/login")
    public JSONObject Login(@QueryParam("username") String username,
    		@QueryParam("password") String password) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.Login(username, password);
    }
	
	@Path("/logout")
	@POST
    public JSONObject Logout(@QueryParam("id") String id) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.Logout(id);
    }
	/*
	 * Servicio para consultar la informacion de un usuario en particular
	 * */
	@GET		
	@Path("/user/{id}")
    public Usuario InfoUsuario(@PathParam("id") String id) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.InfoUsuairo(Integer.parseInt(id));
    }
	
	@Path("/user")
	@PUT
    public JSONObject UpdateUsuario(@QueryParam("id") String id,
    		@QueryParam("nombre") String nombre,
    		@QueryParam("email") String email,
    		@QueryParam("password") String password,
    		@QueryParam("username") String username,    		
    		@QueryParam("edad") String edad,
    		@QueryParam("fotoPerfil") String fotoPerfil) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.UpdateUsuario(id, nombre, email, password, username, edad, fotoPerfil);
    }
    
	@Path("/users/{id}")
	@GET
    public JSONObject ListarRegistrados(@PathParam("id") String id) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.ListarRegistrados(id);
    }
	@Path("/friends/{id}")
	@GET
    public JSONObject ListarAmigos(@PathParam("id") String id) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.ListarAmigos(id);
    }
	@Path("/friend")
	@POST
    public JSONObject AgregarAmigo(@QueryParam("id") String id,@QueryParam("id_friend") String idAmigo) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.AgregarAmigo(id, idAmigo);
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
    public JSONObject ListarRecorridos(@PathParam("id") String id) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.ListarRecorridos(id);
    }
	/*
	
	@Path("/routes")
	@GET
    public User ListarRecorridos(@PathParam("id") String id) {
        UserService userService = new UserService();
        return userService.ListarRecorridos(id);
    }
	@Path("/route/add")
	@POST
    public User AgregarRecorrido(@PathParam("id") String id) {
        UserService userService = new UserService();
        return userService.AgregarRecorrido(id);
    }
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
    public JSONObject EnviarMensaje(@QueryParam("id_usuario_origen") String id_usuario_origen,
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
}
