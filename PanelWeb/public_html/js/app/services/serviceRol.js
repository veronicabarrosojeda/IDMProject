'use strict';

PanelWeb.factory('rolService', ['$http', '$q', '$window', function ($http, $q, $window) {
        return{
            getRoles: getRoles,
            agregarRol: agregarRol,
            modificarRol: modificarRol,
            borrarRol: borrarRol
        };
        
         function getRoles(token)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsRol/getRoles',token)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function agregarRol(rol)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsRol/altaRol', JSON.stringify(rol))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;

        }

        function modificarRol(rol)
        {

            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsRol/modificarRol', JSON.stringify(rol))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function borrarRol(rol)
        {

            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsRol/borrarRol', JSON.stringify(rol))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

       

    }]);