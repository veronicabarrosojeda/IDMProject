'use strict';

PanelWeb.factory('empresaService', ['$http', '$q', '$window', function ($http, $q, $window) {
        return{
            getEmpresas: getEmpresas,
            getEmpresasByUser: getEmpresasByUser,
            altaEmpresa: altaEmpresa,
            cambiarEstado: cambiarEstado,
            modificarEmpresa: modificarEmpresa,
            getEmpresasByService: getEmpresasByService
        };

        function altaEmpresa(em) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsEmpresa/altaEmpresa', JSON.stringify(em))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function modificarEmpresa(emM) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsEmpresa/modificarEmpresa', JSON.stringify(emM))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function getEmpresas() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsEmpresa/getEmpresas')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function getEmpresasByUser(idUsuario) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsEmpresa/getEmpresasByUser', idUsuario)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function getEmpresasByService(idServicio)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsEmpresa/getEmpresasByService', idServicio)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function cambiarEstado(emC) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsEmpresa/bajaEmpresa', emC)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }
    }]);