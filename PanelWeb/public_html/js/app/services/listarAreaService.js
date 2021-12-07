'use strict';

PanelWeb.factory('listarAreaService', ['$http', '$q', '$window', function ($http, $q, $window) {
        return{
            findAreas: findAreas,
            listarAreaFiltro: listarAreaFiltro
        };

        function findAreas() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsArea/findArea')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function listarAreaFiltro(filtro) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsArea/listarAreaFiltro', filtro)
                    .success(function (data2) {
                        defered.resolve(data2);
                    })
                    .error(function (error) {
                    });
            return promise;
        }
    }]);

