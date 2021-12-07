AtuServicio.factory('seguimientoService', ['$http', '$q', 'configConexServer', function ($http, $q, configConexServer) {
        return{
            getServicio: getServicio,
            getServiciosUser: getServiciosUser
        };
        function getServicio(idServicio) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsServicio/getServicioMovil', idServicio)
                    .success(function (data) {
                        //alert(data);
                        defered.resolve(data);
                    })
                    .error(function (error) {
                        

                    });

            return promise;
        };
        function getServiciosUser(data) {
            var defered = $q.defer();
//            var id = parseInt(id);
            var promise = defered.promise;
            $http.post(configConexServer.getServidor()+ 'wsServicio/getServiciosUser',JSON.stringify(data))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                        console.log(error.toString());
                    });

            return promise;
        }
    }]);
