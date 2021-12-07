'use strict';

PanelWeb.factory('supervisorService', ['$http', '$q', '$window', function ($http, $q, $window) {
        return{
            getSupervisores: getSupervisores,
            altaSupervisor: altaSupervisor,
            borrarSupervisor: borrarSupervisor,
            modificarSupervisor: modificarSupervisor,
            getSupervisoresByServicio: getSupervisoresByServicio
        };

        function getSupervisoresByServicio(servicio)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsSupervisor/getSupervisoresByServicio', JSON.stringify(servicio))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;

        }

        function getSupervisores(token) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsSupervisor/getSupervisores', token)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function altaSupervisor(sup) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsSupervisor/altaSupervisor', JSON.stringify(sup))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function borrarSupervisor(sup)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsSupervisor/borrarSupervisor',JSON.stringify(sup))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function modificarSupervisor(sup)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsSupervisor/modificarSupervisor', JSON.stringify(sup))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

    }]);