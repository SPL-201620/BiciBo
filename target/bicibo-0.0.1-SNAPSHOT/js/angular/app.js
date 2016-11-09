'use strict';

// Declare app level module which depends on filters, and services
angular.module('ngbicibo', ['ngbicibo.filters', 'ngbicibo.services', 'ngbicibo.directives', 'ngbicibo.controllers', 
                            'ngResource', 'ngCookies'])
    .run(function($rootScope, $location, $cookieStore){
    	$rootScope.$on('$routeChangeStart', function (event, next, current){
        	if($cookieStore.get('estaConectado') == false || $cookieStore.get('estaConectado') == null){
        		//$('#formLogin').show()
        		//$('#formSalir').hide()
        		$location.path('/')
        	}
        	else{
        		//$('#formLogin').hide()
        		//$('#formSalir').show()
        	}
    	})
	})
    
    .config(['$routeProvider', function ($routeProvider) {
    	$routeProvider.when('/', {templateUrl: 'templates/cover.html'});
    	$routeProvider.when('/perfil', {templateUrl: 'templates/perfil.html'});
    	$routeProvider.when('/individual', {templateUrl: 'templates/individuales.html'});
    	$routeProvider.when('/individual/:id', {templateUrl: 'templates/individuales.html'});
    	$routeProvider.when('/grupo', {templateUrl: 'templates/grupo.html'});
    	$routeProvider.when('/grupo/:id', {templateUrl: 'templates/grupo.html'});
    	$routeProvider.otherwise({redirectTo: '/'});
    }]);
