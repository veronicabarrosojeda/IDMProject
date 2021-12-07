PanelWeb.factory('loginService', ['$http', '$location', '$window', 'sessionService', 'md5', '$q', function ($http, $location, $window, sessionService, md5, $q) {

        var logIn = function (user, scope) {
            var usuario = user;
            var defered = $q.defer();
            var promise = defered.promise;
            usuario.password = md5.createHash(usuario.password);
            $http.post($window.ip + '/ServerApp/ws/wsUsuario/logIn', JSON.stringify(usuario))
                    .success(function (data) {
                        if (data) {
                            if (data.isLogged == true)
                            {
                                scope.usuario = {idUsuario: '', nickname: '', password: '', token: '', isLogged: '', rename: ''};
                                sessionService.set('token', data.token);
                                sessionService.set('nombre', data.nombre);
                                sessionService.set('apellido', data.apellido);
                                sessionService.set('correo', data.correo);
                                sessionService.set('nickname', data.nickname);
                                sessionService.set('rol', data.dtoRol.idRol);
                                sessionService.set('idUsuario', data.idUsuario);
                                sessionService.set('empresas', JSON.stringify(data.colEmpresas));
                                sessionService.set('permisos', JSON.stringify(data.colPermiso));
                                $window.location.href = "/PanelWeb/menu.html";
                                user = {};
                            }
                            defered.resolve(data);
                        }
                    })
                    .error(function (error) {
                        user = {};
                    });
            return promise;
        };

        var logOut = function () {
            var user = {};
            user.token = sessionService.get('token');
            user.idUsuario = sessionService.get('idUsuario');
            sessionService.destroy('token');
            sessionService.destroy('nickname');
            sessionService.destroy('rol');
            sessionService.destroy('idUsuario');
            sessionService.destroy('permisos');
            sessionService.destroy('empresas');
            $window.location.href = "/PanelWeb/login.html";

            $http.post($window.ip + '/ServerApp/ws/wsUsuario/logOut', user.token)
                    .success(function (data) {
                        if (data.isLogged == false)
                        {
                            return false;
                        }
                        return true;
                    })
                    .error(function (error) {
                        return false;
                    });
        };
        var isLogged = function () {
            var defered = $q.defer();
            var promise = defered.promise;
            var userToken = sessionService.get('token');
            if (userToken !== null) {
                $http.post($window.ip + '/ServerApp/ws/wsUsuario/isLogged', userToken)
                        .success(function (data) {
                            if (data == true)
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



        var service = {
            logIn: logIn,
            logOut: logOut,
            isLogged: isLogged
        };

        return service;

    }]);
