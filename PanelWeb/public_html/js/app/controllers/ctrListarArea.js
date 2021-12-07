PanelWeb.controller("ctrListarArea", ['$scope', '$http', '$location', 'md5', '$q', 'listarAreaService', 'sessionService', function ($scope, $http, $location, md5, $q, listarAreaService, sessionService) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 4)
                        rest = true;
                }
                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }

        verificarPermiso();
        $scope.colAreas = [];
        $scope.filtro;
        $scope.listarAreaFiltro = listarAreaFiltro;
        $scope.cargarId = cargarId;

        listarAreaService.findAreas().then(function (data) {
            $scope.colAreas = data;
        });

        function listarAreaFiltro()
        {
            listarAreaService.listarAreaFiltro($scope.filtro).then(function (data2) {
                $scope.colAreas = data2;
            });

        }

        function cargarId(idArea)
        {
            sessionService.set('idArea', idArea);
        }

    }]);

