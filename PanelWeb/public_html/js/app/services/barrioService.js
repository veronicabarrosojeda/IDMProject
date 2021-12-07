'use strict';
PanelWeb.factory('barrioService', ['$http', '$q', '$window', function ($http, $q, $window) {
        return  {
            altaBarrio: altaBarrio,
            bajaBarrio: bajaBarrio,
            modificarBarrio: modificarBarrio,
            listarBarrios: listarBarrios
        };
        function altaBarrio(nuevoBarrio)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsBarrio/altaBarrio', JSON.stringify(nuevoBarrio))
                    .success(function (data) {
                        defered.resolve(data);

                    })
                    .error(function (error) {
                        barrio = {};
                    });
            return promise;
        }

        function bajaBarrio(idBarrio)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsBarrio/bajaBarrio', idBarrio)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function modificarBarrio(barrio)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsBarrio/modificarBarrio', JSON.stringify(barrio))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function listarBarrios() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsBarrio/listarBarrios')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }
    }]);