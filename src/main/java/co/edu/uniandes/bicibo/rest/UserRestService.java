package co.edu.uniandes.bicibo.rest;

import co.edu.uniandes.bicibo.domain.User;
import co.edu.uniandes.bicibo.domain.Usuario;
import co.edu.uniandes.bicibo.service.UserService;
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
    public JSONObject Registrar(@QueryParam("nombre") String nombre,
    		@QueryParam("email") String email,
    		@QueryParam("username") String username,
    		@QueryParam("clave") String clave) 
	{
		System.out.println("email: "+email);
        UsuarioService usuarioService = new UsuarioService();
        return usuarioService.Registrar(nombre, email, username, clave);
    }
	
	@POST
	@Path("/login")
    public JSONObject Login(@QueryParam("username") String username,
    		@QueryParam("clave") String clave) 
	{
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.Login(username, clave);
    }
	
	@Path("/logout")
	@POST
    public User Logout() 
	{
        UserService userService = new UserService();
        return userService.Logout();
    }
		
	@Path("/user/{id}")
	@GET
    public User InfoUsuario(@PathParam("id") String id) 
	{
        UserService userService = new UserService();
        return userService.InfoUsuairo(id);
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
    
	@Path("/users")
	@GET
    public JSONObject ListarRegistrados(@PathParam("id") String id) {
		System.out.println("ID: "+id);
		UsuarioService usuarioService = new UsuarioService();
        return usuarioService.ListarRegistrados(id);
    }
	@Path("/friends/{id}")
	@GET
    public User ListarAmigos(@PathParam("id") String id) {
        UserService userService = new UserService();
        return userService.ListarAmigos(id);
    }
	@Path("/friends/add")
	@POST
    public User AgregarAmigo(@PathParam("id") String id) {
        UserService userService = new UserService();
        return userService.AgregarAmigo(id);
    }
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
	
}
