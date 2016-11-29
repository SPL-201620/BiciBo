package co.edu.uniandes.bicibo.rest;

import co.edu.uniandes.bicibo.domain.AppConfig;
import co.edu.uniandes.bicibo.domain.Usuario;
import co.edu.uniandes.bicibo.service.MensajeService;
import co.edu.uniandes.bicibo.service.RecorridoGrupalService;
import co.edu.uniandes.bicibo.service.RecorridoService;
import co.edu.uniandes.bicibo.service.SitioAlquilerService;
import co.edu.uniandes.bicibo.service.UsuarioService;
//import co.edu.uniandes.bicibo.service.RecorridoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import twitter4j.Twitter;
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
	@Path("/numMensajes")
    public JSONObject darNumMensajesNuevos(@QueryParam("id") String id) 
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
            exc.printStackTrace();
            try
            {
                Twitter twitter = TwitterFactory.getSingleton();
                RequestToken requestToken = twitter.getOAuthRequestToken();
                urlTwitter = requestToken.getAuthenticationURL();
            }
            catch(Exception exc1)
            {
                exc1.printStackTrace();
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
    public void redirectToApp(@QueryParam("oauth_token") String oauthToken,
                              @QueryParam("oauth_verifier") String oauthVerifier, @Context HttpServletRequest request, @Context HttpServletResponse httpServletResponse) throws IOException {
        System.out.println("llego a continue twitter");
        UsuarioService usuarioService = new UsuarioService();
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
                usuarioService.registrar(user.getName(), user.getScreenName(), user.getScreenName(), accessToken.getTokenSecret(), user.getOriginalProfileImageURL());
                javax.servlet.http.Cookie[] cookies = request.getCookies();
                if(cookies != null)
                {
                    for(javax.servlet.http.Cookie cookie : cookies)
                    {
                        if(cookie.getName().equals("username"))
                        {
                            cookie.setMaxAge(0);
                        }
                    }
                }
                javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("username", user.getScreenName());
                cookie.setMaxAge(30*60);
                cookie.setPath("/");
                System.out.println("value en cookie "+ cookie.getValue());
                httpServletResponse.addCookie(cookie);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(user != null)
        {
            //usuarioService.login(user.getScreenName(), accessToken.getTokenSecret());
            httpServletResponse.sendRedirect("http://localhost:8080/#/perfil");
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

    private String getResponse(String url)
    {
        String USER_AGENT = "Mozilla/5.0";
        try
        {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
            return response.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private void printJsonObject(JSONObject jsonObject, ArrayList<String> arrayList)
    {
        for (Object key : jsonObject.keySet())
        {
            String keyStr = (String)key;
            Object keyvalue = jsonObject.get(keyStr);

            //Print key and value
            System.out.println("key: "+ keyStr + " value: " + keyvalue);
            arrayList.add(keyStr + ":" + keyvalue);

            //for nested objects iteration if required
            if (keyvalue instanceof JSONObject)
                printJsonObject((JSONObject)keyvalue, arrayList );
        }
    }

    @GET
    @Path("/continueFace")
    public void redirect(@Context UriInfo uriInfo, @Context HttpServletRequest httpServletRequest, @Context HttpServletResponse httpServletResponse) throws IOException {
        System.out.println("llego a continue con face");
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        Map<String,String> parameters = new HashMap<String,String>();
        Iterator<String> it = queryParams.keySet().iterator();

        while(it.hasNext()){
            String theKey = (String)it.next();
            parameters.put(theKey,queryParams.getFirst(theKey));
        }

        String value = null;
        for (Map.Entry<String,String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            value = entry.getValue();
            System.out.println(key + " "+ value);
        }


        String url = "https://graph.facebook.com/v2.8/oauth/access_token?client_id=197784370679496&redirect_uri=http://localhost:8080/bicibo/rest/continueFace&client_secret=bcf749ca3c4955bbe63c9e7f856b47ac&code="+value;

        String response = getResponse(url);
        String[] values = response.toString().split(":");
        String[] values1 = values[1].split(",");
        String token = values1[0].replace("\"", "");
        String urlInfo = "https://graph.facebook.com/me?fields=name,email,picture&access_token="+token;
        String res = getResponse(urlInfo);

        try
        {
            JSONParser parser_obj = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser_obj.parse(res);
            ArrayList<String> listaRes = new ArrayList<String>();
            printJsonObject(jsonObject, listaRes);
            String name = "";
            String email = "";
            String urlFoto = "";
            for (String info: listaRes)
            {
                if(info.startsWith("name"))
                {
                    name = info.split(":")[1];
                }
                else if(info.startsWith("email"))
                {
                    email = info.split(":")[1];
                }
                else if(info.startsWith("url"))
                {
                    urlInfo = info.split(":")[1];
                }
            }
            UsuarioService usuarioService = new UsuarioService();
            usuarioService.registrar(name, email,name.toLowerCase().replaceAll("\\s+",""), token, urlFoto);
            javax.servlet.http.Cookie[] cookies = httpServletRequest.getCookies();
            if(cookies != null)
            {
                for(javax.servlet.http.Cookie cookie : cookies)
                {
                    if(cookie.getName().equals("username"))
                    {
                        cookie.setMaxAge(0);
                    }
                }
            }
            javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("username", name.toLowerCase().replaceAll("\\s+",""));
            cookie.setMaxAge(30*60);
            cookie.setPath("/");
            System.out.println("value en cookie "+ cookie.getValue());
            httpServletResponse.addCookie(cookie);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        httpServletResponse.sendRedirect("http://localhost:8080/#/perfil");
    }

    @GET
    @Path("/infoLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response darInfoUserLogin(@Context HttpServletRequest request)
    {
        System.out.println("llego a infoLogin");
        UsuarioService usuarioService = new UsuarioService();
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        String userName ="";
        if(cookies != null)
        {
            for(javax.servlet.http.Cookie cookie : cookies)
            {
                System.out.println("cookie con name "+ cookie.getName()+ " value "+userName +" domain "+cookie.getDomain());
                if(cookie.getName().equals("username"))
                {
                    userName = cookie.getValue();
                    break;
                }
            }
            System.out.println("value encontrado en la cookie "+ userName);
            Usuario user = usuarioService.darInfoUsuarioUsername(userName);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", user.getUsername());
            jsonObject.put("password", user.getPassword());
            System.out.println("llego a infoLogin con user "+ user.getUsername()+ " pass "+ user.getPassword());
            return Response.ok(jsonObject).build();
        }
        else
        {
            return  Response.serverError().build();
        }
    }
}
