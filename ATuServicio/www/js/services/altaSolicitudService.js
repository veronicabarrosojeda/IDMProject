AtuServicio.factory('altaSolicitudService', ['$http', '$q', 'configConexServer', function ($http, $q, configConexServer, $ionicPopup) {
        return{
            altaServicio: altaServicio,
            getServiciosForStatus: getServiciosForStatus,
            unirseServicioMovil: unirseServicioMovil
        };

        function altaServicio(ser) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsServicio/altaServicioMovil', JSON.stringify(ser))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function unirseServicioMovil(serSol) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsServicio/unirseServicioMovil', JSON.stringify(serSol))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function getServiciosForStatus(whereData)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsServicio/getSolicitudesForStatusMovil', JSON.stringify(whereData))  //Igual al de ws
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }

       
        function showAlert(msgTitle, msgResult) {
            var alertPopup = $ionicPopup.alert({
                title: msgTitle,
                template: msgResult
            });
        }
        ;
    }]);
