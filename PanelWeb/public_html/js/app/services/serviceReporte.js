'use strict';

PanelWeb.factory('reporteService', ['$http', '$q', '$window', function ($http, $q, $window) {
        return{
            reportePorFecha: reportePorFecha
        };

        function reportePorFecha(data)
        {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsReporte/getReporteByTipo', JSON.stringify(data))
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }
    }]);