'use strict';

// Declare app level module which depends on filters, and services
angular.module('ngbicibo', ['ngbicibo.filters', 'ngbicibo.services', 'ngbicibo.directives', 'ngbicibo.controllers', 
                            'ngResource', 'ngCookies'])
    .run(function($rootScope, $location, $cookieStore){
    	$rootScope.$on('$routeChangeStart', function (event, next, current){
        	if($cookieStore.get('estaConectado') == false || $cookieStore.get('estaConectado') == null){
        		//$('#formLogin').show()
        		//$('#formSalir').hide()
        		//$location.path('/')
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
    	$routeProvider.when('/registrar', {templateUrl: 'templates/registrar.html'});
    	$routeProvider.otherwise({redirectTo: '/'});
    }]);
function avatar1(){
	$("#avatar2").hide();
	$("#rutaFoto").val("img/avatars/avatar1.jpg");
	$("#btnCambiarAvatar").show();
}
function avatar2(){
	$("#avatar1").hide();
	$("#rutaFoto").val("img/avatars/avatar2.jpg");
	$("#btnCambiarAvatar").show();
}
function btnCambiarAvatar(){
	$("#rutaFoto").val(" ");
	$("#btnCambiarAvatar").hide();
	$("#avatar1").show();
	$("#avatar2").show();
}
function mostraPanelExito(){
	$("#formRegistro").hide();
	$("#panelExito").show();
	
}