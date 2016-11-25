'use strict';

/* Controllers */


//var app = angular.module('ngbicibo.controllers', ['google-maps']);
var app = angular.module('ngbicibo.controllers', []);


// Clear browser cache (in development mode)
//
// http://stackoverflow.com/questions/14718826/angularjs-disable-partial-caching-on-dev-machine
app.run(function ($rootScope, $templateCache) {
    $rootScope.$on('$viewContentLoaded', function () {
        $templateCache.removeAll();
    });
});

/*
app.controller('iniCtrl', ['$scope', 'UserFactory', function ($scope, UserFactory) {
    $scope.bla = 'bla from controller';
    UserFactory.get({}, function (userFactory) {
        $scope.firstname = userFactory.firstName;
    })
}]);
*/
var rutaFotos = 'http://i393.photobucket.com/albums/pp17/acuestap/U_Andes_Fabricas/Foto_Abimelec_zpspb0uhnlm.jpg';

app.factory('factoryUsuarios', function() {
	return [
	        {
	        	id: '1' , nombre: 'Alexander 1', email: 'acuestap@gmail.com', fotoPerfil: rutaFotos
	        },
	        {
	        	id: '2' , nombre: 'Abimelec 2', email: 'acuestap2@gmail.com', fotoPerfil: rutaFotos
	        }
	       ];
});

app.factory('factoryRecorrido', function() {
	return [
	        {
	        	id: '1' , 
	        	origen: 'carrera 15 124 30, Bogota, Colombia', 
	        	destino: 'Carrera 1 18a 12, Bogota, Colombia', 
	        	horaSalida: new Date(), 
	        	horaLlegada: new Date(), 
	        	fechaRecorrido: new Date(), 
	        	realizado: true,
	        	distancia: '3 kilometros', 
	        	tiempoEstimado: '3 horas', 
	        	caloriasQuemadas: '3000 calorias', 
	        	infoClima:'Estuvo muy lluvioso',
	        	esFrecuente: null, 
	        	nombre_organizador: null, 
	        	registrado: null
	        }
	       ];
});

app.factory('factoryRecorridos', function() {
	return [
	        {
	        	id: '1' , origen: 'carrera 15 124 30, Bogota, Colombia', destino: 'Carrera 1 18a 12, Bogota, Colombia', horaSalida: new Date(), horaLlegada: new Date(), fechaRecorrido: new Date(), realizado: true,
	        	distancia: '3 kilometros', tiempoEstimado: '3 horas', caloriasQuemadas: '3000 calorias', infoClima:'Estuvo muy lluvioso',
	        	esFrecuente: null, nombre_organizador: null, registrado: null
	        },
	        {
	        	id: '2' , origen: 'calle 187 55b 90, Bogota, Colombia', destino: 'Carrera 1 18a 12, Bogota, Colombia', horaSalida: new Date(), horaLlegada: new Date(), fechaRecorrido: new Date(), realizado: false,
	        	distancia: '4 kilometros', tiempoEstimado: '4 horas', caloriasQuemadas: '4000 calorias', infoClima:'Estuvo muy caluroso',
	        	esFrecuente: null, nombre_organizador: null, registrado: null
	        },
	        {
	        	id: '3' , origen: 'calle 187 55b 90, Bogota, Colombia', destino: 'Carrera 1 18a 12, Bogota, Colombia', horaSalida: new Date(), horaLlegada: new Date(), fechaRecorrido: new Date(), realizado: false,
	        	distancia: '5 kilometros', tiempoEstimado: '5 horas', caloriasQuemadas: '5000 calorias', infoClima:'Estuvo muy humedo',
	        	esFrecuente: true, nombre_organizador: "admin recorrido grupo 1", registrado: true
	        },
	        {
	        	id: '4' , origen: 'calle 187 55b 90, Bogota, Colombia', destino: 'Carrera 1 18a 12, Bogota, Colombia', horaSalida: new Date(), horaLlegada: new Date(), fechaRecorrido: new Date(), realizado: false,
	        	distancia: '6 kilometros', tiempoEstimado: '5 horas', caloriasQuemadas: '6000 calorias', infoClima:'Estuvo muy nublado',
	        	esFrecuente: true, nombre_organizador: "admin recorrido grupo 2", registrado: false
	        }
	       ];
})
/*
app.factory('factoryUsuarios', function() {
	return [];
})
*/

//Controlador principal o padre.
//Para la version final quitar los siguientes servicios: factoryUsuarios, factoryRecorridos , puesto que se usaron para simular la info de la BD.

app.controller('AppCtrl', ['$scope', '$q', 'UserSesion','UserFactory','FriendFactory','RouteFactory', '$log', '$cookieStore', '$location', '$routeParams', 'factoryUsuarios', 'factoryRecorridos', 'factoryRecorrido', 'MessageFactory', 
                           function ($scope, $q, UserSesion, UserFactory, FriendFactory, RouteFactory, $log, $cookieStore, $location, $routeParams, factoryUsuarios, factoryRecorridos, factoryRecorrido, MessageFactory) {
    //AUTENTICACION
	$scope.usrConectado = {nombre: "", estaConectado: '', message: ''};
    
	//VERIFICA SESSION
	$scope.verificarSesion = function()
	{
    	var cookieUsr = $cookieStore.get('usuario');
    	if(typeof cookieUsr == "undefined")
    	{
    		$("#formSalir").hide();
    		$("#formLogin").show();
    		$("#btnPerfil").hide();
    	}
    	else
    	{
    		$("#formSalir").show();
    		$("#formLogin").hide();
    		$("#btnPerfil").show();
    	}   	
    };
    
    var inicioSesion = $q.defer();//Se usa el servicio q, que me permite diferir la variable.
    
    inicioSesion.promise.then(usrASesion);
    
    //Aqui es que se inicia la sesion del usuario es decir se crean las cookies 
    function usrASesion(user)
    {
    	if(user.status == "OK")
    	{
        	$scope.usrConectado.estaConectado = true;
        	$scope.usrConectado.message = user.message;
        	$log.info($scope.usrConectado);
        	
        	//Para poder garantizar que se pueda ver los contenidos que solo son para usuarios conectados
        	$cookieStore.put('estaConectado', true);
        	$cookieStore.put('usuario', user);
        	
        	$location.path('/perfil');
    	}
    	else
    	{
    		$scope.salir();
    	}
    };
	
    //Session Usuario
    $scope.usuario = 
    {
    		nombre: '',
    		email: '',
    		username: '',
    		password: '',
    		rutaFoto: ''
    };
    
    //Para cuando se de clic en enviar y se hace el llamado al servicio REST
    $scope.iniciarSesion = function() 
    {
    	var username_usu = $scope.usuario.username;
    	var password_usu = $scope.usuario.password;
    	if(!username_usu || !password_usu)
    	{
    		alert("Usuario y Clave son requeridos.");
    		return;
    	}
    	else
    	{
        	UserSesion.iniciar.sesion({username: username_usu, password: password_usu}, 
        	function (response) 
        	{
        		//alert('llamado a servicio REST Login: '+response.status+'-'+response.message)
        		if(response.status == "OK")
        		{
            		inicioSesion.resolve(response);
    				window.location.assign('#/perfil');
    				window.location.reload(true);
        		}
        		else
        		{
        			alert(response.message)
        		}
        	});
    	}
    };
    
    //REGISTRO
    //Para cuando se de clic en registrar y se hace el llamado al servicio REST
     var registraUsuario = $q.defer();//Se usa el servicio q, que me permite diferir la variable.
     
     registraUsuario.promise.then(usrASesion);
     
     $scope.registrarUsuario = {};
     
     $scope.registrarUsuario = function() 
     {
      	var nombre_usu = $scope.usuario.nombre;
     	var email_usu = $scope.usuario.email;
     	var username_usu = $scope.usuario.username;
     	var password_usu = $scope.usuario.password;
     	var rutaFoto_usu = $scope.usuario.rutaFoto;
     	//alert(nombre_usu+'-'+email_usu+'-'+username_usu+'-'+password_usu+'-'+rutaFoto_usu)
     	if(!username_usu || !password_usu)
     	{
     		alert("Usuario y Clave son requeridos.");
     		return;
     	}
     	else
     	{         	
         	UserSesion.registrar.normal({nombre: nombre_usu, email: email_usu, username:username_usu, password: password_usu, rutaFoto: rutaFoto_usu}, 
         	function (response) 
         	{
         		if(response.status=="OK")
         		{
         			mostraPanelExito();
         		}
         		else
         		{
         			$("#panelError").html(response.message)
         		}
         	});
     	}
     };
    
    // funcion para el boton de salir o cerrar sesion.
    $scope.salir = function()
    {
    	var cookieUsr = $cookieStore.get('usuario');
    	//alert('saliendo..:'+cookieUsr.id)
    	
    	UserSesion.logout.normal({id: cookieUsr.id}, 
    	function (response) 
    	{
    		if(response.status != "OK")
    		{
    			$log.info(response.message);
    		}
    		else
    		{
        		$scope.usrConectado = {nombre: "", estaConectado: '', message: ''};
            	$cookieStore.remove('estaConectado');
            	$cookieStore.remove('usuario');            	
            	$location.path('/');
            	window.location.reload(true);
    		}
    	})
    };
	
    //USUARIO
      $scope.infoUsuario = {
              nombre: '',
              email: '',
              password: '',
              username: '',
              edad: '',
              rutaFoto: ''              
      };
    
    //INFO USUARIO ESPECIFICO
    $scope.getInfoUsuario = function()
    {
    	var cookieUsr = $cookieStore.get('usuario');
    	UserFactory.usuario.show({id: cookieUsr.id}, 
    	function (response) 
    	{
    		if(response.email == null)
    		{
    			$scope.msgError = "No se encontro info para el usuario."; 
    		}
    		else
    		{
    			$scope.infoUsuario = response;    			
    		}
    	})
    };

    $scope.listaRegistrados = {};

    //LISTA USUARIOS REGISTRADOS
	$scope.listarRegistrados = function()
	{
		var cookieUsr = $cookieStore.get('usuario');
	  	UserFactory.usuarios.mostrar({id: cookieUsr.id}, 
	  	function (response)
		{
	  		if(response.length <= 0)
	  		{
	  			$scope.msgError = "No hay usuarios registrados en el momento"; 
	  		}
	  		else
	  		{
	  			$scope.listaRegistrados = response.message;
	  		}
	  	})
	};
	
    $scope.actualizarUsuario = function()
    {
    	var cookieUsr = $cookieStore.get('usuario');
    	UserFactory.user.update({id: $scope.infoUsuario.id, username : "", email: $scope.infoUsuario.email, 
    		password : "", edad: $scope.infoUsuario.edad, nombre: $scope.infoUsuario.nombre,
    		fotoPerfil: $scope.infoUsuario.fotoPerfil}, 
    		function (response) 
    		{
	    		if(response.status != "OK")
	    		{
	    			$scope.msgError = response.message; 
	    		}
	    		else
	    		{
	    			window.location.assign('#/perfil');
	                window.location.reload(true);
	    		}
    		})
    };
    
    $scope.noActualizar = function(){
    	window.location.assign('#/perfil');
        window.location.reload(true);
    };    
    
    //LISTA AMIGOS
    $scope.listarAmigos = function()
    {
    	var cookieUsr = $cookieStore.get('usuario');
    	FriendFactory.amigos.show({id: cookieUsr.id}, 
    	function (response) 
    	{
    		if(response.length == 0)
    		{
    			$scope.msgError = "No tiene amigos en el momento"; 
    		}
    		else
    		{
    			$scope.listaAmigos = response.message;
    		}
    	})
    };
    
    //AGREGAR AMIGO
    $scope.agregarAmigo = function(id_amigo)
    {
    	var cookieUsr = $cookieStore.get('usuario');
    	FriendFactory.amigo.create({id: cookieUsr.id, id_friend: id_amigo}, 
    	function (response) 
    	{
    		if(response.status != "OK")
    		{
    			$scope.msgError = response.message; 
    		}
    		else
    		{
    			window.location.assign('#/perfil');
                window.location.reload(true);
    		}
    	})
    };
   
    //RECORRIDOS INDIVIDUALES
    
    //LISTA RECORRIDOS
    $scope.infoRecorrido = 
    {
    	id:'',
    	origen:'',
    	destino:'',
    	hora_salida:'',
    	hora_llegda:'',
    	fecha_recorrido: '',
    	frecuencia: '',
    	esFrecuente:'',
    	distancia:'',
    	tiempoEstimado:'',
    	caloriasQuemadas:'',
    	infoClima:'',
    	realizado:'',
		registrado:''
    };
    
    $scope.listarRecorridos = function(idRecorrido)
    {
    	if(typeof idRecorrido != "undefined")
    	{
    		$('#tablaInfoRecorridos').hide();
    		$scope.infoRecorrido = RouteFactory.ruta2.show({id: idRecorrido}, 
    		function (response) 
    		{
    			if(response.message.id == null)
				{
    				$scope.msgError = "No se encontro info del recorrido."; 
				}
				else
				{
					$scope.infoRecorrido = response.message;
				}
			});

    		$('#formAgregarRecorrido').hide();
			$('#formEditarRecorrido').show();
    	}
    	else
    	{
    		var cookieUsr = $cookieStore.get('usuario');
        	RouteFactory.rutas.show({id: cookieUsr.id},
        	function (response) 
        	{
        		if(response.status =="ERROR")
        		{
        			$scope.msgError = response.message; 
        		}
        		else
        		{
        			$scope.listaRecorridos = response.recorridos;
        		}
        	})
    	}
    };
    
    $scope.noAgregarRecorrido = function()
    {
    	window.location.assign('#/individual');
        window.location.reload(true);
    };
    
    $scope.ingresarRecorrido = function()
    {
		$('#formAgregarRecorrido').show();
		$('#formEditarRecorrido').hide();
    	var cookieUsr = $cookieStore.get('usuario');
    	RouteFactory.ruta1.crear(
	    	{
	    		id_usuario: cookieUsr.id,
	    		origen:$scope.infoRecorrido.origen, 
	    		destino: $scope.infoRecorrido.destino, 
	    		hora_salida:$scope.infoRecorrido.hora_salida,
				hora_llegada: $scope.infoRecorrido.hora_llegada, 
				fecha_recorrido:$scope.infoRecorrido.fecha_recorrido, 
				//frecuencia:$scope.infoRecorrido.frecuencia, 
				distancia:$scope.infoRecorrido.distancia, 
				tiempoEstimado:$scope.infoRecorrido.tiempoEstimado, 
				caloriasQuemadas:$scope.infoRecorrido.caloriasQuemadas, 
				infoClima:$scope.infoRecorrido.infoClima, 
				realizado:$scope.infoRecorrido.realizado
			}, 
			function (response) 
			{    	
	    		if(response.status != "OK")
	    		{
	    			$scope.msgError = response.message; 
	    		}
	    		else
	    		{
	    			window.location.assign('#/individual');
	                window.location.reload(true);
	    		}
			}
		)
    };
    
    $scope.mostrarEditarRecorrido = function (idRecorrido)
    {
    	$('#areaMapa').empty();
		$('#tablaInfoRecorridos').hide();
		$('#formAgregarRecorrido').hide();
		$('#formEditarRecorrido').show();
	};
	
	$scope.editarRecorrido = function(idRecorrido)
	{	
    	var cookieUsr = $cookieStore.get('usuario');
    	RouteFactory.ruta3.update({id_recorrido: idRecorrido,
    		origen:$scope.infoRecorrido.origen, 
    		destino: $scope.infoRecorrido.destino, 
    		hora_salida:$scope.infoRecorrido.hora_salida,
			hora_llegada: $scope.infoRecorrido.hora_llegada, 
			fecha_recorrido:$scope.infoRecorrido.fecha_recorrido, 
			//frecuencia:$scope.infoRecorrido.frecuencia, 
			distancia:$scope.infoRecorrido.distancia, 
			tiempoEstimado:$scope.infoRecorrido.tiempoEstimado, 
			caloriasQuemadas:$scope.infoRecorrido.caloriasQuemadas, 
			infoClima:$scope.infoRecorrido.infoClima, 
			realizado:$scope.infoRecorrido.realizado}, 
			function (response) 
			{    	
	    		if(response.status != "OK")
	    		{
	    			$scope.msgError = response.message; 
	    		}
	    		else
	    		{
	    			window.location.assign('#/individual');
	                window.location.reload(true);
	    		}
			}
		)
    };
    
    //LISTA RECORRIDOS GRUPO
    $scope.listarRecorridosGrupo = function(idRecorrido)
    {
    	var cookieUsr = $cookieStore.get('usuario');
    	if(typeof idRecorrido != "undefined")
    	{
    		$('#tablaInfoRecorridos').hide();
    		$scope.infoRecorrido = RouteFactory.ruta7.show7({id: cookieUsr.id, id2: idRecorrido}, 
    		function (response) 
    		{
    			if(response.message.id == null)
    			{
    				$scope.msgError = "No se encontro info del recorrido en grupo."; 
				}
    			else
    			{
    				$scope.infoRecorrido = response.message;
				}
			});

    		$('#formAgregarRecorrido').hide();
			$('#formEditarRecorrido').show();
    	}
    	else
    	{
	    	RouteFactory.ruta4.showRr({id: cookieUsr.id}, 
	    	function (response) 
	    	{
	    		if(response.length <= 0)
	    		{
	    			$scope.msgError = "No tiene recorridos en grupo en el momento"; 
	    		}
	    		else
	    		{
	    			$scope.listaRecorridosGrupo = response.recorridos.map(
	    			function(value, index)
	    			{
	    				return {
	    					recorrido: value,
	    					registrado: (response.registrados[index] == "1") ? true : false,
	    	    			administrador: (response.recorridos[index].id_usuario == cookieUsr.id) ? true : false
	    				}
	    			});
	    		}
	    	})
    	}
    };
    
    $scope.noAgregarRecorridoGrupo = function(){
    	window.location.assign('#/grupo');
        window.location.reload(true);
    };
    
    $scope.ingresarRecorridoGrupo = function()
    {
		$('#formAgregarRecorrido').show();
		$('#formEditarRecorrido').hide();
    	var cookieUsr = $cookieStore.get('usuario');
    	RouteFactory.ruta5.crearR({id_usuario: cookieUsr.id,
    		nombre_organizador:$scope.infoRecorrido.nombre_organizador,
    		origen:$scope.infoRecorrido.origen, 
    		destino: $scope.infoRecorrido.destino, 
    		horaSalida:$scope.infoRecorrido.horaSalida,
    		horaLlegada: $scope.infoRecorrido.horaLlegada, 
			fechaRecorrido:$scope.infoRecorrido.fechaRecorrido, 
			distancia:$scope.infoRecorrido.distancia, 
			tiempoEstimado:$scope.infoRecorrido.tiempoEstimado, 
			caloriasQuemadas:$scope.infoRecorrido.caloriasQuemadas, 
			infoClima:$scope.infoRecorrido.infoClima,
			frecuencia:$scope.infoRecorrido.frecuencia}, 
			function (response) 
			{    	
	    		if(response.status != "OK"){
	    			$scope.msgError = response.message; 
	    		}
	    		else
	    		{
	    			window.location.assign('#/grupo');
	                window.location.reload(true);
	    		}
			})
    };
    
    //Evento de Editar recorrido en grupo
    $scope.mostrarEditarRecorridoGrupo = function (idRecorrido)
    {
    	$('#areaMapa').empty();
		$('#tablaInfoRecorridos').hide();
		$('#formAgregarRecorrido').hide();
		$('#formEditarRecorrido').show();
		//$location.path('/grupo/' + idRecorrido);
	};
	
    $scope.editarRecorridoGrupo = function(idRecorrido)
    {
    	var cookieUsr = $cookieStore.get('usuario');
    	RouteFactory.ruta8.actualizar({id: idRecorrido,
    		nombre_organizador:$scope.infoRecorrido.nombre_organizador,
    		origen:$scope.infoRecorrido.origen, 
    		destino: $scope.infoRecorrido.destino, 
    		hora_salida:$scope.infoRecorrido.hora_salida,
    		hora_llegada: $scope.infoRecorrido.hora_llegada, 
    		fecha_recorrido:$scope.infoRecorrido.fecha_recorrido, 
    		distancia:$scope.infoRecorrido.distancia,
    		tiempoEstimado:$scope.infoRecorrido.tiempoEstimado,
    		caloriasQuemadas:$scope.infoRecorrido.caloriasQuemadas,
    		infoClima:$scope.infoRecorrido.infoClima,
			frecuencia:$scope.infoRecorrido.frecuencia}, 
			function (response) 
			{    	
	    		if(response.status != "OK")
	    		{
	    			$scope.msgError = response.message; 
	    		}
	    		else
	    		{
	    			window.location.assign('#/grupo');
	                window.location.reload(true);
	    		}
			})
    };
    
    //UNIRSE A RECORRIDO EN GRUPO
    $scope.unirseRecorridoGrupo = function(idRecorridoGrupo)
    {
    	var cookieUsr = $cookieStore.get('usuario');
    	RouteFactory.ruta6.insertar({id:cookieUsr.id, id2:idRecorridoGrupo}, 
    	function (response) 
    	{
    		if(response.status != "OK")
    		{
    			$scope.msgError = response.message; 
    		}
    		else
    		{
    			window.location.assign('#/grupo');
                window.location.reload(true);
    		}
    	})
    };
    
    //PARA GMAPS
    $scope.mostrarRuta = function cargarMapa(origen, destino)
    {
		/*var pruebaConsumirWS = $.post("https://maps.googleapis.com/maps/api/distancematrix/json?origins=carrera 15 124 30, Bogota, Colombia&destinations=Carrera 1 18a 12, Bogota, Colombia");
		pruebaConsumirWS.done(function( data ) {	    	
			$( "#areaMapa" ).empty().append( data );
	  	});*/
    	$( "#areaMapa" ).empty();
		var posting = $.post( "templates/mostrarMapa.html", { ori: origen, des:  destino} );
		// Put the results in a div
	  	posting.done(function( data ) {	    	
	    	$( "#areaMapa" ).empty().append( data );
	    	$("#origenInMapa").val(origen)
	    	$("#destinoInMapa").val(destino)
	  	});
	}
    
    //Mensajes
    $scope.mensaje = {contenido:''};
    $scope.template = {};
    $scope.usuarioDestino = {
    		id: '',
    		nombre:''
    };
 // callback for ng-click 'enviarMensaje':
    $scope.enviarMensaje = function (userId, userNombre) {
    	//alert('Enviar mensaje a:'+userId +'-nombre:'+userNombre);
    	
    	$("#focusChat").focus();
    	$scope.usuarioDestino.id = userId;
    	$scope.usuarioDestino.nombre= userNombre;
    	$scope.template = 'templates/nuevoMensaje.html';
    };
 // callback for ng-click 'guardarMensaje':
    $scope.guardarMensaje = function () {
    	var cookieUsr = $cookieStore.get('usuario');
    	var idUsuarioOrigen = cookieUsr.id;
    	var idUsuarioDestino = $scope.usuarioDestino.id;
    	var contenidoMensaje = $scope.mensaje.contenido;
    	
    	//alert('guardar mensaje:'+contenidoMensaje+' a:'+idUsuarioDestino + '-de:'+idUsuarioOrigen)
    	
    	MessageFactory.mensaje.create({id_usuario_origen: idUsuarioOrigen, 
    			mensaje: contenidoMensaje, 
    			id_usuario_destino: idUsuarioDestino})
    			
		//Recargar area de mensajes
		$scope.template = '';
    	setTimeout(recargarChat, 2500);
    };
    
    function recargarChat(){
    	$("#btnEnvMsq").click();	
    }
    $scope.cerrarMensajes = function () {
    	$scope.template = '';
    };
    $scope.ListadoChat = {};
    $scope.listarChat = function () {
    	var cookieUsr = $cookieStore.get('usuario');
    	var idUsuarioOrigen = cookieUsr.id;
    	var idUsuarioDestino = $scope.usuarioDestino.id;
    	
    	$scope.mensaje.contenido = '';
    	
    	//alert('chat entre  destino:'+idUsuarioDestino + '-origen:'+idUsuarioOrigen)
    	
    	MessageFactory.chat.listar({id: idUsuarioOrigen, id2: idUsuarioDestino}, function (response)
		{
	  		if(response.status != "OK")
	  		{
	  			$scope.msgError = response.message;
	  		}
	  		else
	  		{
	  			$scope.ListadoChat = response.message;
	  		}
	  	})
    	
    };
    $scope.ListadoMensajes = {};
    $scope.listarMensajes = function () {
    	var cookieUsr = $cookieStore.get('usuario');
    	var idUsuarioActual = cookieUsr.id;
    	//alert('chat entre  destino:'+idUsuarioDestino + '-origen:'+idUsuarioOrigen)
    	
    	MessageFactory.mensajes.listar({id: idUsuarioActual}, function (response)
		{
	  		if(response.status != "OK")
	  		{
	  			$scope.msgError = response.message;
	  		}
	  		else
	  		{
	  			$scope.ListadoMensajes = response.message;
	  		}
	  	})
    	
    };
    
    
}]);//Fin Controlador principal

