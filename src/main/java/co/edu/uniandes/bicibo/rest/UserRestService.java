package co.edu.uniandes.bicibo.rest;

import co.edu.uniandes.bicibo.domain.User;
import co.edu.uniandes.bicibo.service.UserService;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
public class UserRestService {
	/*
	@GET
	@Produces("text/html")
	public Response getStartingPage()
	{
		String output = "<h1>Hello World!<h1>" +
				"<p>RESTful Service is running ... <br>Ping @ " + new Date().toString() + "</p<br>";
		return Response.status(200).entity(output).build();
	}
	*/
	@Path("/login")
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    public User Login() {
        UserService userService = new UserService();
        return userService.Login();
    }
	@Path("/logout")
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    public User Logout() {
        UserService userService = new UserService();
        return userService.Logout();
    }
	@Path("/user/add")
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    public User Registrar() {
        UserService userService = new UserService();
        return userService.Registrar();
    }
	@Path("/user/{id}")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public User InfoUsuairo(@PathParam("id") String id) {
        UserService userService = new UserService();
        return userService.InfoUsuairo(id);
    }
	@Path("/user/{id}")
	@PUT
    @Produces(MediaType.APPLICATION_JSON)
    public User UpdateUsuairo(@PathParam("id") String id) {
        UserService userService = new UserService();
        return userService.UpdateUsuairo(id);
    }
	@Path("/users")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public User ListarRegistrados(@PathParam("id") String id) {
        UserService userService = new UserService();
        return userService.ListarRegistrados(id);
    }
	@Path("/friends")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public User ListarAmigos(@PathParam("id") String id) {
        UserService userService = new UserService();
        return userService.ListarAmigos(id);
    }
	@Path("/friends/add")
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    public User AgregarAmigo(@PathParam("id") String id) {
        UserService userService = new UserService();
        return userService.AgregarAmigo(id);
    }
	@Path("/routes")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public User ListarRecorridos(@PathParam("id") String id) {
        UserService userService = new UserService();
        return userService.ListarRecorridos(id);
    }
	@Path("/route/add")
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    public User AgregarRecorrido(@PathParam("id") String id) {
        UserService userService = new UserService();
        return userService.AgregarRecorrido(id);
    }
	@Path("/routes2")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public User ListarRecorridosGrupo() {
        UserService userService = new UserService();
        return userService.ListarRecorridosGrupo();
    }
	@Path("/route/add2")
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    public User AgregarRecorridoGrupo(@PathParam("id") String id) {
        UserService userService = new UserService();
        return userService.AgregarRecorridoGrupo(id);
    }
	@Path("/route/insert")
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    public User UnirseRecorridoGrupo(@PathParam("id") String id) {
        UserService userService = new UserService();
        return userService.UnirseRecorridoGrupo(id);
    }
	
}
