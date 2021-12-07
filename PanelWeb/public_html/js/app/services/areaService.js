'use strict';
PanelWeb.factory('areaService', ['$http', '$q', '$window', function ($http, $q, $window) {
        return{
            altaArea: altaArea,
            findAreas: findAreas,
            cambiarEstado: cambiarEstado,
            modificarArea: modificarArea
        };

        function altaArea(ar) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsArea/altaArea', JSON.stringify(ar))
                    .success(function (data) {
                        //alert(data);
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });

            return promise;
        }

        function modificarArea(arM) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsArea/modificarArea', JSON.stringify(arM))
                    .success(function (data) {
                        //alert(data);
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });

            return promise;
        }

        function cambiarEstado(arC) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsArea/bajaArea', arC)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });

            return promise;
        }

        function findAreas() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsArea/listarAreas')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }
    }]);



