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
	$scope.verificarSesion = function(){
		console.log("Verifica sesion")
    	var cookieUsr = $cookieStore.get('usuario');
		//alert(cookieUsr)
    	if(typeof cookieUsr == "undefined"){
    		//alert('1'+$("#accesoRedesSociales"))
    		$("#formSalir").hide();
    		$("#formLogin").show();
    		$("#btnPerfil").hide();
    		
    	}else{
    		//alert('2'+$("#accesoRedesSociales"))
    		$("#formSalir").show();
    		$("#formLogin").hide();
    		$("#btnPerfil").show();
    	}   	
    };
    
    var inicioSesion = $q.defer();//Se usa el servicio q, que me permite diferir la variable.
    
    inicioSesion.promise.then(usrASesion);
    
    //Aqui es que se inicia la sesion del usuario es decir se crean las cookies 
    function usrASesion(user){
    	//alert('Creando la session.')
    	//$scope.usrConectado.nombre = user.nombre;//Info que viene de BD
    	if(user.status == "OK"){
        	$scope.usrConectado.estaConectado = true;
        	$scope.usrConectado.message = user.message;
        	$log.info($scope.usrConectado);
        	
        	//Para poder garantizar que se pueda ver los contenidos que solo son para usuarios conectados
        	$cookieStore.put('estaConectado', true);
        	$cookieStore.put('usuario', user);
        	
        	$location.path('/perfil');

    	}else{
    		//alert(user.message);
    		$scope.salir();
    	}
    };
	
    //Session Usuario
      $scope.usuario = {
    		  nombre: '',
              email: '',
              username: '',
              clave: '',
              fotoPerfil: ''
      };
    
    //Para cuando se de clic en enviar y se hace el llamado al servicio REST
    //$scope.iniciarSesion = {};
    
    $scope.iniciarSesion = function() {
    	var username_usu = $scope.usuario.username;
    	var clave_usu = $scope.usuario.clave;
    	if(!username_usu || !clave_usu){
    		alert("Usuario y Clave son requeridos.");
    		return;
    	}else{
        	UserSesion.iniciar.sesion({username: username_usu, clave: clave_usu}, function (response) {
        		//alert('llamado a servicio REST Login: '+response.status+'-'+response.message)
        		if(response.status == "OK"){
            		inicioSesion.resolve(response);
    				window.location.assign('#/perfil');
    				window.location.reload(true);
        			
        		}else{
        			alert(response.message)
        		}
        	});
        	/*var usr = UserSesion.iniciar.sesion({email: $scope.usuario.email, clave: $scope.usuario.clave}).$promise.then(function(usr){
    			inicioSesion.resolve(usr); //Y es aqui cuando se llama la funcion que se crea las cookies.
    		});
			*/
    	}
    };
    
    //REGISTRO
   //Para cuando se de clic en registrar y se hace el llamado al servicio REST
     var registraUsuario = $q.defer();//Se usa el servicio q, que me permite diferir la variable.
     
     registraUsuario.promise.then(usrASesion);
     
     $scope.registrarUsuario = {};
     
     $scope.registrarUsuario = function() {
      	var nombre_usu = $scope.usuario.nombre;
     	var email_usu = $scope.usuario.email;
     	var username_usu = $scope.usuario.username;
     	var clave_usu = $scope.usuario.clave;
     	var fotoPerfil_usu = $scope.usuario.fotoPerfil;
     	alert(nombre_usu+'-'+email_usu+'-'+username_usu+'-'+clave_usu+'-'+fotoPerfil_usu)
     	if(!email_usu || !clave_usu){
     		alert("Email y Clave son requeridos.");
     		return;
     	}else{
         	
         	UserSesion.registrar.normal({nombre: nombre_usu, email: email_usu, username:username_usu, clave: clave_usu, fotoPerfil: fotoPerfil_usu}, function (response) {
         		//alert('registrando.. servicio REST: '+response.status)
         		if(response.status=="OK"){
             		//inicioSesion.resolve(response); Si quiero que quede autenticado despues de registro
         			mostraPanelExito();
         		}else{
         			$("#panelError").html(response.message)
         		}
         	});
         	/*var usr = UserSesion.iniciar.sesion({email: $scope.usuario.email, clave: $scope.usuario.clave}).$promise.then(function(usr){
     			inicioSesion.resolve(usr); //Y es aqui cuando se llama la funcion que se crea las cookies.
     		});
			*/
     		
     	}
     };
    
    // funcion para el boton de salir o cerrar sesion.
    $scope.salir = function(){
    	var cookieUsr = $cookieStore.get('usuario');
    	//alert('saliendo..')
    	UserSesion.logout.normal({id: cookieUsr.id}, function (response) {
    		if(response.status != "OK"){
    			$log.info(response.message);
    		}else{
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
              fotoPerfil: '',
              edad: ''
      };
    
    //INFO USUARIO ESPECIFICO
    $scope.getInfoUsuario = function(){
		console.log("infoUsuario")
    	var cookieUsr = $cookieStore.get('usuario');
		console.log(cookieUsr)
    	//alert('Consultando info usuario de id: ' + cookieUsr.id);
    	UserFactory.usuario.show({id: cookieUsr.id}, function (response) {
    		if(response.email == null){
    			$scope.msgError = "No se encontro info para el usuario."; 
    		}else{
    			//alert(response);
    			$scope.infoUsuario = response;    			
    		}
    	})
    	
    };

    $scope.listaRegistrados = {};

    //LISTA USUARIOS REGISTRADOS
	$scope.listarRegistrados = function(){
		console.log("listar registrados")
  		//$scope.listaRegistrados = factoryUsuarios;
  		//alert('listando usuarios registrados: ')
	  	UserFactory.usuarios.mostrar(function (response)
		{
			//alert('listaAmigos:'+response[0]);
	  		//alert('listado de usuarios registrados: '+response.length)
	  		if(response.length <= 0){
	  			$scope.msgError = "No hay usuarios registrados en el momento"; 
	  		}else{
	  			//alert(response);
	  			$scope.listaRegistrados = response;
				for(var i=0; i<$scope.listaRegistrados.length; ++i){
					console.log($scope.listaRegistrados[i]);
				}//PARA TOMAR DE REST
	  	    	//$scope.listaRegistrados = factoryUsuarios;//PARA TOMAR DE simulado.
	  		}
	  	})
	};
	
    $scope.actualizarUsuario = function(){
    	var cookieUsr = $cookieStore.get('usuario');
    	//alert('Actualizando info usuario: '+ $scope.infoUsuario.id)
    	UserFactory.user.update({id: $scope.infoUsuario.id, nombre: $scope.infoUsuario.nombre,
    		email: $scope.infoUsuario.email, edad: $scope.infoUsuario.edad, fotoPerfil: $scope.infoUsuario.fotoPerfil}, function (response) {
    		//alert('respuesta'+response.status)
    		if(response.status != "OK"){
    			$scope.msgError = response.message; 
    		}else{
				//alert('respuesta'+response.status)
    			window.location.assign('#/perfil');
                window.location.reload(true);
                //$location.path('/perfil');
    		}
    	})
    };
    $scope.noActualizar = function(){
    	window.location.assign('#/perfil');
        window.location.reload(true);
    };
    
    
    //LISTA AMIGOS
    $scope.listarAmigos = function(){
		console.log("listar amigos")
    	var cookieUsr = $cookieStore.get('usuario');
    	$scope.listaAmigos = {};
    	
    	//$scope.listaAmigos = factoryUsuarios;
    	FriendFactory.amigos.show({id: cookieUsr.id}, function (response) {

			//alert('listaAmigos:'+response[0].nombre);
    		if(response.length <= 0){
    			$scope.msgError = "No tiene amigos en el momento"; 
    		}else{
    			//alert(response);
    			$scope.listaAmigos = response;//PARA TOMAR DE REST
    	    	//$scope.listaAmigos = factoryUsuarios;//PARA TOMAR DE SIMULADO.
    		}
    	})
    };
    
    //AGREGAR AMIGO
    $scope.agregarAmigo = function(id_amigo){
    	var cookieUsr = $cookieStore.get('usuario');
    	//alert('agregando amigo: '+id_amigo);
    	FriendFactory.amigos.create({id: cookieUsr.id, id_friend: id_amigo}, function (response) {
    		//alert('agregado amigo'+response.status);
    		if(response.status != "OK"){
    			$scope.msgError = response.message; 
    		}else{
    			//alert(response);
    			window.location.assign('#/perfil');
                window.location.reload(true);
    		}
    	})
    };
   
  //RECORRIDOS INDIVIDUALES
    
  //LISTA RECORRIDOS
    $scope.infoRecorrido = {
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
    
    $scope.listarRecorridos = function(){
    	var id_recorrido = $routeParams.id;
    	//alert('listando recorridos: '+id_recorrido)
    	if(typeof id_recorrido != "undefined"){
    		//alert('entra a mostrar recorrido.');
    		$('#tablaInfoRecorridos').hide();
    		$scope.listaRecorridos = {};

    		$scope.infoRecorrido = RouteFactory.ruta2.show({id: id_recorrido}, function (response) {
						        		if(response.origen == null){
						        			$scope.msgError = "No se encontro info del recorrido."; 
						        		}else{
						        			//alert(response);
						        			$scope.infoRecorrido = response;
						        		}
						        	});

    		$('#formAgregarRecorrido').hide();
			$('#formEditarRecorrido').show();
        	//$scope.infoRecorrido = factoryRecorrido;
    		//alert('revisar que cargue la info del recorrido..');
    		//$('#btnGuardarRecorrido').attr('ng-click', $scope.editarRecorrido(id_recorrido));
    	}else{
    		var cookieUsr = $cookieStore.get('usuario');
        	$scope.listaRecorridos = {};
        	
        	//$scope.listaRecorridos = factoryRecorridos;//Temporal Comentariar
        	RouteFactory.rutas.show({id: cookieUsr.id},function (response) {
				console.log(response[0]);
        		//alert(response.length)
        		if(response.length <= 0){
        			$scope.msgError = "No tiene recorridos en el momento"; 
        		}else{
        			//alert(response);
        			$scope.listaRecorridos = response;
        	    	//$scope.listaRecorridos = factoryRecorridos;
        		}
        	})
    	}
    };
    $scope.noAgregarRecorrido = function(){
    	window.location.assign('#/individual');
        window.location.reload(true);
    };
    $scope.ingresarRecorrido = function(){
    	//Recordar a Santiago incluir los 5 ultimos campos en el servicio REST
		$('#formAgregarRecorrido').show();
		$('#formEditarRecorrido').hide();
    	var cookieUsr = $cookieStore.get('usuario');
    	//alert('Ingresando info recorrido usuario: '+ $scope.infoRecorrido.origen)
    	RouteFactory.ruta1.crear({id: cookieUsr.id,
    		origen:$scope.infoRecorrido.origen, 
    		destino: $scope.infoRecorrido.destino, 
    		hora_salida:$scope.infoRecorrido.hora_salida,
			hora_llegada: $scope.infoRecorrido.hora_llegada, 
			fecha_recorrido:$scope.infoRecorrido.fecha_recorrido, 
			frecuencia:$scope.infoRecorrido.frecuencia, 
			distancia:$scope.infoRecorrido.distancia, 
			tiempoEstimado:$scope.infoRecorrido.tiempoEstimado, 
			caloriasQuemadas:$scope.infoRecorrido.caloriasQuemadas, 
			infoClima:$scope.infoRecorrido.infoClima, 
			realizado:$scope.infoRecorrido.realizado}, function (response) {    	
    		//alert('recorrido ingresado: '+response.status)
    		if(response.status != "OK"){
    			$scope.msgError = response.message; 
    		}else{
    			window.location.assign('#/individual');
                window.location.reload(true);
    		}
    	})
    };
    
    $scope.mostrarEditarRecorrido = function (idRecorrido){
    	$('#areaMapa').empty();
		$('#tablaInfoRecorridos').hide();
		$('#formAgregarRecorrido').show();
		$location.path('/individual/' + idRecorrido);
	};
	
	$scope.editarRecorrido = function(idRecorrido){

    	//alert('Llego a editar usuario')
    	var cookieUsr = $cookieStore.get('usuario');
    	//alert('Ingresando info recorrido usuario: '+ $scope.infoRecorrido.origen)
    	RouteFactory.ruta3.update({id_recorrido: idRecorrido,
    		id: cookieUsr.id, 
    		origen:$scope.infoRecorrido.origen, 
    		destino: $scope.infoRecorrido.destino, 
    		hora_salida:$scope.infoRecorrido.hora_salida,
			hora_llegada: $scope.infoRecorrido.hora_llegada, 
			fecha_recorrido:$scope.infoRecorrido.fecha_recorrido, 
			frecuencia:$scope.infoRecorrido.frecuencia, 
			distancia:$scope.infoRecorrido.distancia, 
			tiempoEstimado:$scope.infoRecorrido.tiempoEstimado, 
			caloriasQuemadas:$scope.infoRecorrido.caloriasQuemadas, 
			infoClima:$scope.infoRecorrido.infoClima, 
			realizado:$scope.infoRecorrido.realizado}, function (response) {    	
    		//alert('recorrido actualizado: '+response.status)
    		if(response.status != "OK"){
    			$scope.msgError = response.message; 
    		}else{
    			window.location.assign('#/individual');
                window.location.reload(true);
    		}
    	})
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
    $scope.mostrarEditarRecorridoGrupo = function (idRecorrido){
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
    
    
    
    
}]);//Fin Controlador principal

