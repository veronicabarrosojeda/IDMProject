'use strict';
PanelWeb.factory('subAreaService', ['$http', '$q', '$window', function ($http, $q, $window) {

        return {
            altaSubArea: altaSubArea,
            listarSubAreas: listarSubAreas,
            listarSubAreasDArea: listarSubAreasDArea,
            borrarSubArea: borrarSubArea,
            modificarSubArea: modificarSubArea,
        };
        function listarSubAreas() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsSubArea/getSubAreas')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }
        function listarSubAreasDArea(idArea) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsSubArea/getSubAreasDeArea', idArea)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }
        function altaSubArea(nuevaSubArea)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsSubArea/altaSubArea', JSON.stringify(nuevaSubArea))  //Igual al de ws
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }
        ;
        function borrarSubArea(idSubArea)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsSubArea/borrarSubArea', idSubArea)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }
        function modificarSubArea(subA)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsSubArea/modificarSubArea', JSON.stringify(subA))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }
    }]);