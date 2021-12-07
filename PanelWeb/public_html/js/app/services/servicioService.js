PanelWeb.factory('servicioService', ['$http', '$q', '$location', '$window', function ($http, $q, $location, $window) {

        function getSupervisorServicios(idSupervisor)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsServicio/getSupervisorServicios', idSupervisor)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }

        function getSupervisorEmpresa(idEmpresa)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsServicio/getSupervisorEmpresa', idEmpresa)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }


        function getServiciosForStatus(whereData)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsServicio/getSolicitudesForStatus', JSON.stringify(whereData))  //Igual al de ws
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }
        ;

        function actualizarSupervisoresServicio(dtoSupServ)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsServicio/actualizarSupervisoresServicio', JSON.stringify(dtoSupServ))  //Igual al de ws
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }

        function actualizarEmpresaServicio(dtoEmpresaServicio)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsServicio/actualizarEmpresaServicio', JSON.stringify(dtoEmpresaServicio))  //Igual al de ws
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }

        var servServicios = {
            getServiciosForStatus: getServiciosForStatus,
            actualizarEmpresaServicio: actualizarEmpresaServicio,
            actualizarSupervisoresServicio: actualizarSupervisoresServicio,
            getSupervisorServicios: getSupervisorServicios,
            getSupervisorEmpresa: getSupervisorEmpresa
        };
        return servServicios;
    }]);