AtuServicio.factory('usuarioService', ['$http', 'md5', '$q', 'sessionService', 'configConexServer', function ($http, md5, $q, sessionService, configConexServer) {

        return{
            modificarClave: modificarClave,
            modificarPerfil: modificarPerfil,
            getSolicitudesUsuario: getSolicitudesUsuario
        };


        function modificarClave(datos)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            if (datos.passwordActual !== null && datos.passwordActual !== "")
            {
                datos.passwordActual = md5.createHash(datos.passwordActual);
            }
            if (datos.passwordNueva !== null && datos.passwordNueva !== "")
            {
                datos.passwordNueva = md5.createHash(datos.passwordNueva);
            }

            if (datos.passwordNuevaReiterada !== null && datos.passwordNuevaReiterada !== "")
            {
                datos.passwordNuevaReiterada = md5.createHash(datos.passwordNuevaReiterada);
            }

            $http.post(configConexServer.getServidor() + 'wsUsuario/cambiarClave', JSON.stringify(datos))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;

        }
        ;
        function modificarPerfil(perfil)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsUsuario/modificarUsuarioMovil', JSON.stringify(perfil))
                    .success(function (data) {
                        sessionService.set('telefono', perfil.telefono);
                        sessionService.set('nickname', perfil.nickname);
                        sessionService.set('tipoIdentidad', perfil.tipoIdentidad);
                        sessionService.set('nroIdentidad', perfil.nroIdentidad);
                        sessionService.set('correo', perfil.correo);
                        sessionService.set('apellido', perfil.apellido);
                        sessionService.set('nombre', perfil.nombre);
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });

            return promise;
        }
        ;
        function getSolicitudesUsuario(id) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsServicio/getSolicitudesUser')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }
    }]);
