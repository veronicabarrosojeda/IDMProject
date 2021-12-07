'use strict';
PanelWeb.factory('tipoServicioService', ['$http', '$q', '$window', function ($http, $q, $window) {
        return  {
            altaTipoServicio: altaTipoServicio,
            bajaTipoServicio: bajaTipoServicio,
            modificarTipoServicio: modificarTipoServicio,
            listarTipoServicio: listarTipoServicio
        };


        function altaTipoServicio(nuevoTipoServicio)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsTipoServicio/altaTipoServicio', JSON.stringify(nuevoTipoServicio))
                    .success(function (data) {
                        defered.resolve(data);

                    })
                    .error(function (error) {
                        tipoServicio = {};
                    });
            return promise;
        }

        function bajaTipoServicio(idTipoServicio)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsTipoServicio/bajaTipoServicio', idTipoServicio)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function modificarTipoServicio(tipoServicio)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsTipoServicio/modificarTipoServicio', JSON.stringify(tipoServicio))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function listarTipoServicio() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsTipoServicio/listarTipoServicio')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }
    }]);
