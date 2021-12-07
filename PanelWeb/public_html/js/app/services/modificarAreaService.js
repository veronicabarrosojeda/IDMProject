'use strict';

PanelWeb.factory('modificarAreaService', ['$http', '$q', 'sessionService', '$window', function ($http, $q, sessionService, $window) {
        return{
            cargarDatos: cargarDatos,
            modificarArea: modificarArea,
            cambiarEstado: cambiarEstado
        };

        function cargarDatos(idAr) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsArea/listarArea', idAr)
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
            $http.post($window.ip + '/ServerApp/ws/wsArea/cambiarEstado', arC)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

    }]);


