PanelWeb.factory('solicitanteService', ['$http', '$q', '$location', '$window', function ($http, $q, $location, $window) {

        function getSolicitantesByService(id)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsServicio/getSolicitantes', id)  //Igual al de ws
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }
        ;
        var getSolicitantesByService = {
            getSolicitantesByService: getSolicitantesByService
        };
        return getSolicitantesByService;
    }]);