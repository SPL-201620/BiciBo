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
services.factory('UserSesion', function ($resource) {
    var metodo = {
		iniciar: $resource('/bicibo/rest/login', {}, {
			sesion:{method:'POST', params:{username: '@username', password: '@password'}}
		}),
		registrar: $resource(url_servicio_rest + '/user', {}, {
			normal:{method:'POST', params:{nombre: '@nombre', email: '@email', username: '@username', password: '@password', rutaFoto: '@rutaFoto'}}
		}),
		logout: $resource(url_servicio_rest + '/logout', {}, {
			normal:{method:'POST', params:{id: '@id'}}
		})
    };
    return metodo;
});


//Servicios USUARIO
services.factory('UserFactory', function ($resource) {
    var metodo = {
		usuario: $resource(url_servicio_rest + '/user/:id', {id: '@id'}, {
			show:{method:'GET'},
		}),
		user: $resource(url_servicio_rest+'/user',{},{
			update: { method: 'PUT', params: {id: '@id', nombre: '@nombre',  email: '@email', password: '@password', username: '@username', edad: '@edad', rutaFoto: '@rutaFoto'} }
		}),
		usuarios: $resource(url_servicio_rest + '/users', {}, {
			mostrar:{method:'GET', params:{}, isArray: true}
		})
    };
    return metodo;
});

//Servicio AMIGOS
services.factory('FriendFactory', function ($resource) {
    var metodo = {
		amigos: $resource(url_servicio_rest + '/friends/@id', {}, {
			show:{method:'GET', params: {}, isArray: true },
	        create: { method: 'POST', params: {id: '@id', id_friend: '@id_friend'} }
		})
    };
    return metodo;
});

//Servicio RUTAS

services.factory('RouteFactory', function ($resource) {
    var metodo = {
    	rutas: $resource(url_servicio_rest + '/recorridosUser', {}, {
			show:{method:'GET', params:{}, isArray: true}
		}),
		ruta1: $resource(url_servicio_rest + '/recorrido/add', {}, {
			crear:{method:'POST', params: {id: '@id',
				origen:'@origen', destino: '@destino', 
				hora_salida:'@hora_salida',
				hora_llegada: '@hora_llegada', 
				fecha_recorrido:'@fecha_recorrido', 
				frecuencia:'@frecuencia', 
				distancia:'@distancia', 
				tiempoEstimado:'@tiempoEstimado', 
				caloriasQuemadas:'@caloriasQuemadas', 
				infoClima:'@infoClima', 
				realizado:'@realizado'} 
			}
		}),
    	ruta2: $resource(url_servicio_rest + '/recorrido/:id', {}, {
			show:{method:'GET', params:{id: '@id'}}
		}),
		ruta3: $resource(url_servicio_rest + '/recorrido/update', {}, {
			update:{method:'PUT', params: {id_recorrido: '@id_recorrido',
				id: '@id', 
				origen:'@origen', destino: '@destino', 
				hora_salida:'@hora_salida',
				hora_llegada: '@hora_llegada', 
				fecha_recorrido:'@fecha_recorrido', 
				frecuencia:'@frecuencia', 
				distancia:'@distancia', 
				tiempoEstimado:'@tiempoEstimado', 
				caloriasQuemadas:'@caloriasQuemadas', 
				infoClima:'@infoClima', 
				realizado:'@realizado'} 
			}
		}),
    	ruta4: $resource(url_servicio_rest + '/recorridosGrupal', {}, {
			showRr:{method:'GET', params: {} , isArray: true}
		}),
		ruta5: $resource(url_servicio_rest + '/recorridoGrupal/add', {}, {
			crearR:{method:'POST', params: {id: '@id',
				origen:'@origen', 
				destino: '@destino', 
				hora_salida:'@hora_salida',
				hora_llegada: '@hora_llegada', 
				fecha_recorrido:'@fecha_recorrido', 
				frecuencia:'@frecuencia'} 
			}
		}),
		ruta6: $resource(url_servicio_rest + '/recorridoGrupal/insert', {}, {
			insertar:{method:'POST', params: {id: '@id',
				id_recorrido:'@id_recorrido'} 
			}
		}),
    	ruta7: $resource(url_servicio_rest + '/recorridoGrupal/:id', {}, {
			show7:{method:'GET', params: {id: '@id'}}
		}),
		ruta8: $resource(url_servicio_rest + '/recorridoGrupal/update', {}, {
			actualizar:{method:'PUT', params: {id_recorrido: '@id_recorrido',
				id: '@id', 
				origen:'@origen', 
				destino: '@destino', 
				hora_salida:'@hora_salida',
				hora_llegada: '@hora_llegada', 
				fecha_recorrido:'@fecha_recorrido', 
				frecuencia:'@frecuencia'} 
			}
		})
    };
    return metodo;
});
