'use strict';

/* Services */
/*
http://docs.angularjs.org/api/ngResource.$resource

Default ngResources are defined as

'show':    {method:'GET'},
'create':   {method:'POST'},
'query':  {method:'GET', isArray:true},
'remove': {method:'DELETE'},
'delete': {method:'DELETE'},
'update': {method:'PUT'}

*/

var services = angular.module('ngbicibo.services', ['ngResource']);
var url_servicio_rest = '/bicibo/rest';
//Servicios AUTENTICACION
services.factory('UserSesion', function ($resource) 
{
    var metodo = 
    {
		iniciar: $resource('/bicibo/rest/login', {}, 
		{
			sesion:{method:'POST', params:{username: '@username', password: '@password'}}
		}),
		registrar: $resource(url_servicio_rest + '/user', {}, 
		{
			normal:{method:'POST', params:{nombre: '@nombre', email: '@email', username: '@username', password: '@password', rutaFoto: '@rutaFoto'}}
		}),
		logout: $resource(url_servicio_rest + '/logout', {}, 
		{
			normal:{method:'POST', params:{id: '@id'}}
		})
    };
    return metodo;
});


//Servicios USUARIO
services.factory('UserFactory', function ($resource) 
{
    var metodo = 
    {
		usuario: $resource(url_servicio_rest + '/user/:id', {id: '@id'}, 
		{
			show:{method:'GET'},
		}),
		user: $resource(url_servicio_rest+'/user',{},
		{
			update: { method: 'PUT', params: {id: '@id', username: '@username',  email: '@email', password: '@password',  edad: '@edad', nombre: '@nombre', rutaFoto: '@rutaFoto'} }
		}),
		usuarios: $resource(url_servicio_rest + '/users/:id', {id: '@id'}, 
		{
			mostrar:{method:'GET'}
		})
    };
    return metodo;
});

//Servicio AMIGOS
services.factory('FriendFactory', function ($resource) 
{
    var metodo = 
    {
		amigos: $resource(url_servicio_rest + '/friends/:id', {id: '@id'}, 
		{
			show:{method:'GET'}
		}),
		amigo: $resource(url_servicio_rest + '/friend', {id: '@id'}, 
		{				
			create:{ method: 'POST', params: {id: '@id', id_friend: '@id_friend'} }       
		})
    };
    return metodo;
});

//Servicio RUTAS

services.factory('RouteFactory', function ($resource) 
{
    var metodo = 
    {
    	rutas: $resource(url_servicio_rest + '/recorridosUser/:id', {id: '@id'}, 
    	{
			show:{method:'GET'}
		}),
		ruta1: $resource(url_servicio_rest + '/recorrido', {}, 
		{
			crear:{method:'POST', params: 
				{
					id_usuario: '@id_usuario',
					origen:'@origen', 
					destino: '@destino', 
					hora_salida:'@hora_salida',
					hora_llegada: '@hora_llegada', 
					fecha_recorrido:'@fecha_recorrido', 
					//frecuencia:'@frecuencia', 
					distancia:'@distancia', 
					tiempoEstimado:'@tiempoEstimado', 
					caloriasQuemadas:'@caloriasQuemadas', 
					infoClima:'@infoClima', 
					realizado:'@realizado'
				} 
			}
		}),
    	ruta2: $resource(url_servicio_rest + '/recorrido/:id', {}, 
    	{
			show:{method:'GET', params:{id: '@id'}}
		}),
		ruta3: $resource(url_servicio_rest + '/recorrido', {}, 
		{
			update:{method:'PUT', params: 
				{
					id_recorrido: '@id_recorrido', 
					origen:'@origen', 
					destino: '@destino', 
					hora_salida:'@hora_salida',
					hora_llegada: '@hora_llegada', 
					fecha_recorrido:'@fecha_recorrido', 
					//frecuencia:'@frecuencia', 
					distancia:'@distancia', 
					tiempoEstimado:'@tiempoEstimado', 
					caloriasQuemadas:'@caloriasQuemadas', 
					infoClima:'@infoClima', 
					realizado:'@realizado'
				} 
			}
		}),
    	ruta4: $resource(url_servicio_rest + '/recorridosGrupal/:id', {}, {
			showRr:{method:'GET', params: {id: '@id'}}
		}),
		ruta5: $resource(url_servicio_rest + '/recorridoGrupal', {}, {
			crearR:{method:'POST', params: {id_usuario: '@id_usuario',
				nombre_organizador:'@nombre_organizador',
				origen:'@origen', 
				destino: '@destino', 
				horaSalida:'@horaSalida',
				horaLlegada: '@horaLlegada', 
				fechaRecorrido:'@fechaRecorrido', 
				distancia:'@distancia', 
				tiempoEstimado:'@tiempoEstimado', 
				caloriasQuemadas:'@caloriasQuemadas', 
				infoClima:'@infoClima',
				frecuencia:'@frecuencia'} 
			}
		}),
		ruta6: $resource(url_servicio_rest + '/recorridoGrupal/insert', {}, {
			insertar:{method:'POST', params: {id: '@id',
				id2:'@id2'} 
			}
		}),
    	ruta7: $resource(url_servicio_rest + '/recorridoGrupal/:id/:id2', {}, {
			show7:{method:'GET', params: {id: '@id', id2: '@id2'}}
		}),
		ruta8: $resource(url_servicio_rest + '/recorridoGrupal', {}, 
			{
				actualizar:{method:'PUT', params: 
				{
					id: '@id', 
					nombre_organizador: '@nombre_organizador',
					origen:'@origen', 
					destino:'@destino', 
					hora_salida:'@hora_salida',
					hora_llegada:'@hora_llegada', 
					fecha_recorrido:'@fecha_recorrido', 
					distancia:'@distancia',
					tiempoEstimado:'@tiempoEstimado',
					caloriasQuemadas:'@caloriasQuemadas',
					infoClima:'@infoClima',
					frecuencia:'@frecuencia'
				} 
			}
		})
    };
    return metodo;
});

//Servicio Mensajes
services.factory('MessageFactory', function ($resource) {
    var metodo = {
		mensaje: $resource(url_servicio_rest + '/mensaje', {}, 
		{				
			create:{ method: 'POST', params: {id_usuario_origen: '@id_usuario_origen', mensaje: '@mensaje', 
				id_usuario_destino: '@id_usuario_destino'} },
			leido:{ method: 'PUT', params: {id_mensaje: '@id_mensaje'} }, 
				
		}),
		chat: $resource(url_servicio_rest + '/chat', {}, 
		{				
			listar:{ method: 'GET', params: {id: '@id', id2: '@id2'} },  
		}),
		mensajes: $resource(url_servicio_rest + '/mensajes', {}, 
		{				
			listar:{ method: 'GET', params: {id: '@id'} },      
		}),
		nuevosmensajes: $resource(url_servicio_rest + '/numMensajes', {}, 
		{				
			show:{ method: 'GET', params: {id: '@id'} },      
		}),
		
    };
    return metodo;
});

//Servicio SITIOS ALQUILER
services.factory('AlquilerFactory', function ($resource) {
    var metodo = {
		sitios: $resource(url_servicio_rest + '/sitiosAlquiler', {}, 
		{				
			listar:{ method: 'GET'},      
		})
    };
    return metodo;
});
