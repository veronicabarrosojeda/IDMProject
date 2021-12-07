AtuServicio.factory('registroService', ['$http', '$q', 'configConexServer', function ($http, $q, configConexServer) {
        return{
            altaUsuario: altaUsuario
        };

        function altaUsuario(us) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsUsuario/altaUsuarioMovil', JSON.stringify(us))
                    .success(function (data) {
                        //alert(data);
                        defered.resolve(data);
                    })
                    .error(function (error) {


                    });

            return promise;
        }

    }]);