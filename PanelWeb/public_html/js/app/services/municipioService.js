'use strict';
PanelWeb.factory('municipioService', ['$http', '$q', '$location', '$window', function ($http, $q, $location, $window) {
        return {
            altaMunicipio: altaMunicipio,
            getMunicipios: getMunicipios,
            modificarMunicipio: modificarMunicipio,
            borrarMunicipio: borrarMunicipio
        };

        function altaMunicipio(nuevoMunicipio)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsMunicipio/altaMunicipio', JSON.stringify(nuevoMunicipio))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                        user = {};
                    });
            return promise;
        }
        ;

        function getMunicipios() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsMunicipio/getMunicipios')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function modificarMunicipio(municipio)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsMunicipio/modificarMunicipio', JSON.stringify(municipio))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function borrarMunicipio(idMunicipio)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsMunicipio/borrarMunicipio', idMunicipio)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }
    }]);