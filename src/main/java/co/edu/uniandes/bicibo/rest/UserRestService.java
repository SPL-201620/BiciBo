package co.edu.uniandes.bicibo.rest;

import co.edu.uniandes.bicibo.domain.AppConfig;
import co.edu.uniandes.bicibo.domain.Usuario;
import co.edu.uniandes.bicibo.service.MensajeService;
import co.edu.uniandes.bicibo.service.RecorridoService;
import co.edu.uniandes.bicibo.service.SitioAlquilerService;
import co.edu.uniandes.bicibo.service.UsuarioService;

import java.net.URI;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuthService;
import org.eclipse.jetty.server.Authentication;
import org.json.simple.JSONObject;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class UserRestService 
{
	private static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";
	private AppConfig config;


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

    @POST
    @Path("/twitter")
    @Produces(MediaType.APPLICATION_JSON)
    public Response redirectToAuthorization()
    {
        String urlTwitter = null;
        JSONObject jsonObject = new JSONObject();
        try
        {
            Twitter twitter = TwitterFactory.getSingleton();
            twitter.setOAuthConsumer("ci5bruo7M6uqpp1pBAbUTw0oI", "Mklld5GPi1IsHIusQXABme2MuSX1NOiAd1nFOyHw1qvflKXMdP");
            RequestToken requestToken = twitter.getOAuthRequestToken();
            urlTwitter = requestToken.getAuthenticationURL();
        }
        catch(Exception exc)
        {
            try
            {
                Twitter twitter = TwitterFactory.getSingleton();
                RequestToken requestToken = twitter.getOAuthRequestToken();
                urlTwitter = requestToken.getAuthenticationURL();
            }
            catch(Exception exc1)
            {

            }
        }
        System.out.println(urlTwitter);
        jsonObject.put("url", urlTwitter);
        return Response.ok(jsonObject).build();
    }


    /**
     *
     * @param oauthToken
     * @param oauthVerifier
     * @return
     */
    @GET
    @Path("/continue")
    @Produces(MediaType.APPLICATION_JSON)
    public Response redirectToApp(@QueryParam("oauth_token") String oauthToken,
                                  @QueryParam("oauth_verifier") String oauthVerifier)
    {
        Twitter twitter = TwitterFactory.getSingleton();
        JSONObject jsonObject = new JSONObject();
        AccessToken accessToken = null;
        User user = null;
        try
        {
            accessToken = twitter.getOAuthAccessToken(oauthVerifier);
            user = twitter.showUser(accessToken.getScreenName());
            if(user != null)
            {
                UsuarioService usuarioService = new UsuarioService();
                usuarioService.registrar(user.getName(), user.getScreenName(), user.getScreenName(), accessToken.getTokenSecret(), user.getOriginalProfileImageURL());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(user != null)
        {
            jsonObject.put("username", user.getScreenName());
            jsonObject.put("password", accessToken.getTokenSecret());
            String url = "http://localhost:8080/bicibo/rest/login?"+"username="+user.getScreenName()+"&password="+accessToken.getTokenSecret();
            return Response.seeOther(URI.create(url))
                    .build();
        }
        else
        {
            jsonObject.put("status", "ERROR");
            jsonObject.put("message", "El usuario no se podido crear");
            return Response.serverError().entity(jsonObject).build();
        }


    }

    private ServiceBuilder createService()
    {
        config = new AppConfig();
        return new ServiceBuilder()
                .provider(TwitterApi.class)
                .apiKey(config.getConsumerKey())
                .apiSecret(config.getConsumerSecret());
    }

}
