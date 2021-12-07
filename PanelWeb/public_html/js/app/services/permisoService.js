'use strict';

PanelWeb.factory('permisoService', ['$http', '$q', '$window', function ($http, $q, $window) {
        return{
            getPermisos: getPermisos,
            getPermisosByRol: getPermisosByRol,
            actualizarRolPermiso: actualizarRolPermiso
        };

        function actualizarRolPermiso(rolpermiso)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsPermiso/actualizarRolPermiso', rolpermiso)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }
        function getPermisos(rol)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsPermiso/getPermisos',JSON.stringify(rol))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function getPermisosByRol(rol)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsPermiso/getPermisosByRol',JSON.stringify(rol))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

    }]);