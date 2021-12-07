PanelWeb.factory('verServicioService', ['$http', '$q','$window', function ($http, $q, $window) {

        return{
            getServicio: getServicio,
            modificarServicio: modificarServicio
        };

        function getServicio(idServicio, token) {
            var defered = $q.defer();
            var promise = defered.promise;
            var data = {idServicio:idServicio, token:token};
            $http.post($window.ip+'/ServerApp/ws/wsServicio/getServicio', JSON.stringify(data))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function modificarServicio(servicio) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip+'/ServerApp/ws/wsServicio/modificarServicio', JSON.stringify(servicio))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }
    }]);

