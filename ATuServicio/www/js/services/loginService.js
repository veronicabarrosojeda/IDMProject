AtuServicio.factory('loginService', ['$http', 'md5', '$q', '$location', 'sessionService', 'configConexServer', function ($http, md5, $q, $location, sessionService, configConexServer) {
        var ServidorApp = configConexServer.getServidor();
        var logIn = function (user, scope) {

            var usuario = user;
            var defered = $q.defer();
            var promise = defered.promise;
            usuario.password = md5.createHash(usuario.password);
            $http.post(ServidorApp + 'wsUsuario/logIn', JSON.stringify(usuario))
                    .success(function (data) {
                        if (data) {
                            if (data.isLogged)
                            {

                                scope.usuario = {idUsuario: '', nombre: '', apellido: '', nickname: '', password: '', token: '', correo: '', telefono: '', nroIdentidad: '', tipoIdentidad: '', isLogged: '', rename: '', notificar: ''};
                                sessionService.set('isLogged', data.isLogged);
                                sessionService.set('token', data.token);
                                sessionService.set('telefono', data.telefono);
                                sessionService.set('nickname', data.nickname);
                                sessionService.set('tipoIdentidad', data.tipoIdentidad);
                                sessionService.set('nroIdentidad', data.nroIdentidad);
                                sessionService.set('correo', data.correo);
                                sessionService.set('apellido', data.apellido);
                                sessionService.set('nombre', data.nombre);
                                sessionService.set('notificar', data.notificar);
                                sessionService.set('rename', data.rename);
                                sessionService.set('idUsuario', data.idUsuario);
                                $location.path('/menu');
                                user = {};

                            }
                            defered.resolve(data);
                        }
                    })
                    .error(function (error) {

                    });

            return promise;
        };
        var isLogged = function () {
            var defered = $q.defer();
            var promise = defered.promise;
            var userToken = sessionService.get('token');
            if (userToken !== null) {
                $http.post(ServidorApp + 'wsUsuario/isLogged', userToken)
                        .success(function (data) {
                            if (data)
                            {
                                defered.resolve(true);
                            } else
                            {
                                defered.resolve(false);
                            }
                        })
                        .error(function (error) {
                            return false;
                        });
            } else {
                defered.resolve(false);
            }
            return promise;

        };
        var logOut = function () {
            var user = {};

            user.token = sessionService.get('token');
            user.idUsuario = sessionService.get('idUsuario');
            sessionService.destroy('isLogged');
            sessionService.destroy('rename');
            sessionService.destroy('token');
            sessionService.destroy('telefono');
            sessionService.destroy('nickname');
            sessionService.destroy('tipoIdentidad');
            sessionService.destroy('nroIdentidad');
            sessionService.destroy('correo');
            sessionService.destroy('apellido');
            sessionService.destroy('nombre');
            sessionService.destroy('idUsuario');
            $location.path('/tabs');
            $http.post(ServidorApp + 'wsUsuario/logOut', user.token)
                    .success(function (data) {
                        if (data.isLogged === false)
                        {
                            return false;
                        }
                        return true;
                    })
                    .error(function (error) {
                        return false;
                    });


        };
        function recuperarPass(userP) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsUsuario/recuperarPass', JSON.stringify(userP))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });

            return promise;
        }
        var service = {
            logIn: logIn,
            isLogged: isLogged,
            logOut: logOut,
            recuperarPass: recuperarPass
        };

        return service;
    }]);

