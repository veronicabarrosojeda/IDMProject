'use strict';

PanelWeb.factory('usuarioService', ['$http', '$q', '$window', 'md5','sessionService', function ($http, $q,$window, md5,sessionService) {
        return{
            getUsuarios: getUsuarios,
            altaUsuario: altaUsuario,
            borrarUsuario: borrarUsuario,
            modificarUsuario: modificarUsuario,
            cambioClave: cambioClave,
            actualizarEmpresaUsuario:actualizarEmpresaUsuario,
            verificarPermiso:verificarPermiso
        };

        function getUsuarios(user) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip+'/ServerApp/ws/wsUsuario/getUsuarios',JSON.stringify(user))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function altaUsuario(user) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip+'/ServerApp/ws/wsUsuario/altaUsuario', JSON.stringify(user))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function borrarUsuario(user)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip+'/ServerApp/ws/wsUsuario/borrarUsuario', JSON.stringify(user))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function modificarUsuario(user)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip+'/ServerApp/ws/wsUsuario/modificarUsuario', JSON.stringify(user))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function cambioClave(datos)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            if (datos.passwordActual != null && datos.passwordActual != "")
            {
                datos.passwordActual = md5.createHash(datos.passwordActual);
            }
            if (datos.passwordNueva != null && datos.passwordNueva != "")
            {
                datos.passwordNueva = md5.createHash(datos.passwordNueva);
            }

            if (datos.passwordNuevaReiterada != null && datos.passwordNuevaReiterada != "")
            {
                datos.passwordNuevaReiterada = md5.createHash(datos.passwordNuevaReiterada);
            }
            $http.post($window.ip+'/ServerApp/ws/wsUsuario/cambiarClave', JSON.stringify(datos))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }
        
        function actualizarEmpresaUsuario(data)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip+'/ServerApp/ws/wsUsuario/actualizarEmpresaUsuario', data)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
           return promise;         
        }
        
        function verificarPermiso(idPermiso)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {               
                for (var i = 0; i < arr.length; i++)
                {
                    if(arr[i].idPermiso == idPermiso)
                       defered.resolve(true);
                }                
                defered.resolve(false);                
            }
            return promise;
        }    
    }]);