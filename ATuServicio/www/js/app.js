// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
var AtuServicio = angular.module('ATuServicio', ['ionic','base64', 'angular-md5', 'session.service', 'ngCordova', 'uiGmapgoogle-maps'])

        .run(function ($ionicPlatform) {
            $ionicPlatform.ready(function () {
                // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
                // for form inputs)
                if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
                    cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
                    cordova.plugins.Keyboard.disableScroll(true);

                }
                if (window.StatusBar) {
                    // org.apache.cordova.statusbar required
                    StatusBar.styleDefault();
                }
            });
        })

        .config(function ($stateProvider, $urlRouterProvider, $ionicConfigProvider) {

            // Ionic uses AngularUI Router which uses the concept of states
            // Learn more here: https://github.com/angular-ui/ui-router
            // Set up the various states which the app can be in.
            // Each state's controller can be found in controllers.js
            $ionicConfigProvider.tabs.position("bottom");
            $stateProvider


                    // setup an abstract state for the tabs directive
                    .state('tab', {
                        url: '/tab',
                        abstract: true,
                        templateUrl: 'templates/tabs.html',
                        controller: 'ctrTabs'
                    })
                    .state('menu', {
                        url: '/menu',
                        templateUrl: 'templates/menus/menu.html',
                        controller: 'ctrLogin'
                    })
                    .state('menu.solicitud', {
                        url: '/solicitud',
                        views: {
                            'menu-solicitud': {
                                templateUrl: 'templates/menus/solicitud.html'
                            }
                        }
                    })
                    .state('menu.perfil', {
                        url: '/perfil',
                        views: {
                            'menu-perfil': {
                                templateUrl: 'templates/menus/perfil.html',
                                controller: 'ctrUsuario'
                            }
                        }
                    })
                    .state('menu.misSolicitudes', {
                        url: '/misSolicitudes',
                        views: {
                            'menu-misSolicitudes': {
                                templateUrl: 'templates/menus/misSolicitudes.html',
                                controller: 'ctrMisSolicitudes'
                            }
                        }
                    })
                    // Each tab has its own nav history stack:
                    .state('tab.inicio', {
                        url: '/inicio',
                        views: {
                            'tab-inicio': {
                                templateUrl: 'templates/inicio.html',
                                controller: 'ctrInicio'
                            }
                        }
                    })
                    .state('tab.login', {
                        url: '/login',
                        views: {
                            'tab-login': {
                                templateUrl: 'templates/login.html',
                                controller: 'ctrLogin'
                            }
                        }
                    })
                    .state('tab.registro', {
                        url: '/registro',
                        views: {
                            'tab-registro': {
                                templateUrl: 'templates/registro.html',
                                controller: 'ctrRegistro'
                            }
                        }
                    })
                    .state('tab.altaSolicitud', {
                        url: '/altaSolicitud',
                        views: {
                            'tab-altaSolicitud': {
                                templateUrl: 'templates/altaSolicitud.html',
                                controller: 'ctrAltaSolicitud'
                            }
                        }
                    })
                    .state('tab.seguimientoSolicitud', {
                        url: '/seguimientoSolicitud',
                        views: {
                            'tab-seguimientoSolicitud': {
                                templateUrl: 'templates/seguimientoSolicitud.html',
                                controller: 'ctrSeguimiento'
                            }
                        }
                    });

            // if none of the above states are matched, use this as the fallback
            $urlRouterProvider.otherwise('/tab/inicio');

        });
