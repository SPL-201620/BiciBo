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
var url_servicio_rest = 'http://localhost\\:9000';
//Servicios AUTENTICACION
services.factory('UserSesion', function ($resource) {
    var metodo = {
		iniciar: $resource('http://localhost\\:9000/login', {}, {
			sesion:{method:'POST', params:{email: '@email', clave: '@clave'}}
		}),
		registrar: $resource('http://localhost\\:9000/user/add', {}, {
			normal:{method:'POST', params:{email: '@email', clave: '@clave'}}
		}),
		logout: $resource('http://localhost\\:9000/logout', {}, {
			normal:{method:'POST', params:{id: '@id'}}
		})
    };
    return metodo;
});


//Servicios USUARIO
services.factory('UserFactory', function ($resource) {
    var metodo = {
		usuario: $resource('http://localhost\\:9000/user', {}, {
			show:{method:'GET'},
		}),
		user: $resource(url_servicio_rest+'/user/update',{},{
			update: { method: 'PUT', params: {id: '@id', nombre: '@nombre', email: '@email', edad: '@edad', fotoPerfil: '@fotoPerfil'} }
		}),
		usuarios: $resource('http://localhost\\:9000/users', {}, {
			mostrar:{method:'GET', params:{}, isArray: true}
		})
    };
    return metodo;
});

//Servicio AMIGOS
services.factory('FriendFactory', function ($resource) {
    var metodo = {
		amigos: $resource('http://localhost\\:9000/friends', {}, {
			show:{method:'GET', params: {id: '@id'}, isArray: true },
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
