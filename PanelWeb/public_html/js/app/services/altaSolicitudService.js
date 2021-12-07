PanelWeb.factory('altaSolicitudService', ['$http', '$q', '$window', function ($http, $q, $window) {

        return{
            altaServicio: altaServicio,
        };

        function altaServicio(ser) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsServicio/altaServicio', JSON.stringify(ser))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }
    }]);

