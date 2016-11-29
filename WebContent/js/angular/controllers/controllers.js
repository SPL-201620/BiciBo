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

app.controller('AppCtrl', ['$scope', '$q', 'UserSesion','UserFactory','FriendFactory','RouteFactory', '$log', '$cookieStore', '$location', '$routeParams', 'factoryUsuarios', 'factoryRecorridos', 'factoryRecorrido', 
                           function ($scope, $q, UserSesion, UserFactory, FriendFactory, RouteFactory, $log, $cookieStore, $location, $routeParams, factoryUsuarios, factoryRecorridos, factoryRecorrido) {
    //AUTENTICACION
	$scope.usrConectado = {nombre: "", estaConectado: '', message: ''};
	//VERIFICA SESSION
	$scope.verificarSesion = function()
	{
		console.log("Verifica sesion")
    	var cookieUsr = $cookieStore.get('usuario');
		//alert(cookieUsr)
    	if(typeof cookieUsr == "undefined")
    	{
    		//alert('1'+$("#accesoRedesSociales"))
    		$("#formSalir").hide();
    		$("#contenedor").show();
    		$("#btnPerfil").hide();
    		
    	}
    	else
    	{
    		//alert('2'+$("#accesoRedesSociales"))
    		$("#formSalir").show();
    		$("#contenedor").hide();
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
		//alert($scope.usuario.username);
		//alert($scope.usuario.password);
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
        		if(response.status == "OK")
        		{
            		inicioSesion.resolve(response);
					//alert('llamado a servicio REST Login: '+response.status+'-'+response.message)
					//alert("antes " + getStoredValue("reload"))
					if(getStoredValue("reload") <=1)
					{
						window.location.assign('#/perfil');
						window.location.reload(true);
						var x = getStoredValue("reload");
						x++;
						storeValue("reload", x);
						//alert(getStoredValue("reload"))
					}
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
    		//alert('respuesta servcion /logout: '+response.status)
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
	  	UserFactory.usuarios.mostrar({id: cookieUsr.id}, function (response)
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
    	$scope.listaAmigos = {};
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
    
    $scope.listarRecorridos = function()
    {
    	var id_recorrido = $routeParams.id;
    	if(typeof id_recorrido != "undefined")
    	{
    		$('#tablaInfoRecorridos').hide();
    		$scope.listaRecorridos = {};
    		$scope.infoRecorrido = RouteFactory.ruta2.show({id: id_recorrido}, 
    		function (response) 
    		{
    			if(response.origen == null)
				{
    				$scope.msgError = "No se encontro info del recorrido."; 
				}
				else
				{
					$scope.infoRecorrido = response;
				}
			});

    		$('#formAgregarRecorrido').hide();
			$('#formEditarRecorrido').show();
    	}
    	else
    	{
    		var cookieUsr = $cookieStore.get('usuario');
        	$scope.listaRecorridos = {};
        	RouteFactory.rutas.show({id: cookieUsr.id},
        	function (response) 
        	{
				console.log(response[0]);
        		alert(response.length)
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
    	//alert('recorrido a actualizar: '+ idRecorrido)
    	$('#areaMapa').empty();
		$('#tablaInfoRecorridos').hide();
		$('#formAgregarRecorrido').hide();
		$('#formEditarRecorrido').show();		
		$location.path('/individual/id=' + idRecorrido);
	};
	
	$scope.editarRecorrido = function()
	{
		var getUrlParameter = 1;/* function getUrlParameter() {
		    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;

	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');

	        if (sParameterName[0] === 'id') {
	            return sParameterName[1] === undefined ? true : sParameterName[1];
	        }
	    }
	};*/
		var sPageURL = decodeURIComponent(window.location.search.substring(1))/*,
        sURLVariables = sPageURL.split('/'),
        lengthURL = sURLVariables.length,
        idRecorrido = sURLVariables[0];*/
		
    	//alert('Llego a editar recorrido2 = ' + sPageURL )
    	var cookieUsr = $cookieStore.get('usuario');
    	//alert('Ingresando info recorrido usuario: '+ $scope.infoRecorrido.origen)
    	RouteFactory.ruta3.update({id_recorrido: 1,
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
	    		//alert('recorrido actualizado: '+response.status)
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
    $scope.listarRecorridosGrupo = function(){
    	var id_recorrido = $routeParams.id;
    	//alert('listando recorridos: '+id_recorrido)
    	if(typeof id_recorrido != "undefined"){
    		//alert('entra a mostrar recorrido.');
    		$('#tablaInfoRecorridos').hide();
    		$scope.listaRecorridosGrupo = {};

    		$scope.infoRecorrido = RouteFactory.ruta7.show7({id: id_recorrido}, function (response) {
						        		if(response.origen == null){
						        			$scope.msgError = "No se encontro info del recorrido en grupo."; 
						        		}else{
						        			//alert(response);
						        			$scope.infoRecorrido = response;
						        		}
						        	});

    		$('#formAgregarRecorrido').hide();
			$('#formEditarRecorrido').show();
        	//$scope.infoRecorrido = factoryRecorrido;
    		//alert('revisar que cargue la info del recorrido en grupo..');
    		//$('#btnGuardarRecorrido').attr('ng-click', editarRecorridoGrupo());
    	}else{
    		
	    	var cookieUsr = $cookieStore.get('usuario');
	    	$scope.listaRecorridosGrupo = {};
	    	//alert('listando recorrridos en grupo')
	    	//$scope.listaRecorridosGrupo = factoryRecorridos;
	    	RouteFactory.ruta4.showRr({}, function (response) {
	    		
	    		//alert(response.length)
	    		if(response.length <= 0){
	    			$scope.msgError = "No tiene recorridos en grupo en el momento"; 
	    		}else{
	    			//alert(response);
	    			$scope.listaRecorridosGrupo = response;
	    	    	//$scope.listaRecorridosGrupo = factoryRecorridos;
	    		}
	    	})
    	}
    };
    $scope.noAgregarRecorridoGrupo = function(){
    	window.location.assign('#/grupo');
        window.location.reload(true);
    };
    $scope.ingresarRecorridoGrupo = function(){
		$('#formAgregarRecorrido').show();
		$('#formEditarRecorrido').hide();
    	var cookieUsr = $cookieStore.get('usuario');
    	//alert('Ingresando info recorrido grupo: '+ $scope.infoRecorrido.origen)
    	RouteFactory.ruta5.crearR({id: cookieUsr.id,
    		origen:$scope.infoRecorrido.origen, 
    		destino: $scope.infoRecorrido.destino, 
    		hora_salida:$scope.infoRecorrido.hora_salida,
			hora_llegada: $scope.infoRecorrido.hora_llegada, 
			fecha_recorrido:$scope.infoRecorrido.fecha_recorrido, 
			frecuencia:$scope.infoRecorrido.frecuencia}, function (response) {    	
    		//alert('recorrido ingresado: '+response.status)
    		if(response.status != "OK"){
    			$scope.msgError = response.message; 
    		}else{
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
		$('#formAgregarRecorrido').show();
		$location.path('/grupo/' + idRecorrido);
	};
    $scope.editarRecorridoGrupo = function(idRecorrido){
    	var cookieUsr = $cookieStore.get('usuario');
    	//alert('Ingresando info recorrido grupo: '+ $scope.infoRecorrido.origen)
    	RouteFactory.ruta8.actualizar({id_recorrido: idRecorrido,
			id: cookieUsr.id,
    		origen:$scope.infoRecorrido.origen, 
    		destino: $scope.infoRecorrido.destino, 
    		hora_salida:$scope.infoRecorrido.hora_salida,
			hora_llegada: $scope.infoRecorrido.hora_llegada, 
			fecha_recorrido:$scope.infoRecorrido.fecha_recorrido, 
			frecuencia:$scope.infoRecorrido.frecuencia}, function (response) {    	
    		//alert('recorrido ingresado: '+response.status)
    		if(response.status != "OK"){
    			$scope.msgError = response.message; 
    		}else{
    			window.location.assign('#/grupo');
                window.location.reload(true);
    		}
    	})
    };
  //UNIRSE A RECORRIDO EN GRUPO
    $scope.unirseRecorridoGrupo = function(id_recorrido_grupo){
    	var cookieUsr = $cookieStore.get('usuario');
    	//alert('uniendose a recorrido en grupo: '+id_recorrido_grupo);
    	RouteFactory.ruta6.insertar({id: cookieUsr.id, id_recorrido: id_recorrido_grupo}, function (response) {
    		//alert('ya se unio al recorrido en grupo'+response.status);
    		if(response.status != "OK"){
    			$scope.msgError = response.message; 
    		}else{
    			//alert(response);
    			window.location.assign('#/grupo');
                window.location.reload(true);
    		}
    	})
    };
    
    //PARA GMAPS
    $scope.mostrarRuta = function cargarMapa(origen, destino){
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
    $scope.enviarCorreo = function(mensajeEnviado, id_usuario_destino)
    {
    	var cookieUsr = $cookieStore.get('usuario');
    	//alert('uniendose a recorrido en grupo: 2' + mensajeEnviado + '   ' + id_usuario_destino + ' o ' +cookieUsr.id);
    	MessageFactory.mensaje.create({id_usuario_origen : cookieUsr.id, mensaje : mensajeEnviado, id_usuario_destino : id_usuario_destino}, 
    	function (response) 
    	{
    		//alert('uniendose a recorrido en grupo: ');
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

    $scope.registrarUsuarioTwitter = function()
	{
		storeValue("reload",0);
		UserSesion.registrarTwitter.twitter(
			function (response)
			{
				$("#myModal2 .modal-body").html(" ");
				var res = JSON.stringify(response);
				var values = res.split('"');
				//alert(values[3]);
				$(document).ready(function()
				{
					var html = "<p> Sigue este <a href='¿'> link </a> para continuar con el registro.</p>";
					var x = html.replace(/¿/g, values[3]);
					console.log(x);
					$("#myModal2 .modal-body").html(x);
				});

			}
		);
	}

	$scope.registrarUsuarioFacebook = function()
	{
		storeValue("reload",0);
        var link = "https://www.facebook.com/v2.8/dialog/oauth?client_id=197784370679496&redirect_uri=http://localhost:8080/bicibo/rest/continueFace&response_type=code&scope=email";
		$(document).ready(function ()
        {
            var html = "<p> Sigue este <a href='¿'> link </a> para continuar con el registro.</p>";
            var x = html.replace(/¿/g, link);
            console.log(x);
            $("#myModal .modal-body").html(x);
        });
	}

	$scope.iniciarSesionTwitter = function ()
	{
		storeValue("reload",0);
		UserSesion.registrarTwitter.twitter(

			function (response)
			{
				var res = JSON.stringify(response);
				var values = res.split('"');
				window.open(values[3],'_self');
		});
	}

	$scope.iniciarSesionFacebook = function()
	{
		storeValue("reload",0);
		var link = "https://www.facebook.com/v2.8/dialog/oauth?client_id=197784370679496&redirect_uri=http://localhost:8080/bicibo/rest/continueFace&response_type=code&scope=email";
		window.open(link, '_self');
		//se mira si ya se redirigio a la pagina de perfil
		//se pide el usuario y la contraseña del usuario loggeado con facebook
		//se actualiza el scope.usuario
		//se llama a la funcion que inicia sesion, a nivel de servidor ya no se hace el login directamente
	}

	$scope.infoLoginn = function () {
		UserSesion.infoLogin.darInfo(
			function(response)
			{
				var res = JSON.stringify(response);
				console.log(res);
				var arr = res.split('"');
				//for (var prop in arr)
				//{
				//	alert(arr[prop]);
				//}
				console.log(arr[7]);
				console.log(arr[3]);
				$scope.usuario.username = arr[7];
				$scope.usuario.password = arr[3];
				$scope.iniciarSesion();
			});
	}

	function storeValue(key, value)
    {
        if (localStorage) {
            localStorage.setItem(key, value);
        }
        else
        {
            $.cookies.set(key, value);
        }
    }

    function getStoredValue(key)
    {
        if (localStorage)
        {
            return localStorage.getItem(key);
        }
        else
        {
            return $.cookies.get(key);
        }
    }

}]);//Fin Controlador principal

