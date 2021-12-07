PanelWeb.controller("ctrModificarArea", ['$scope', '$http', '$location', 'md5', '$q', 'modificarAreaService', 'sessionService', function ($scope, $http, $location, md5, $q, modificarAreaService, sessionService) {

        $scope.areaM = {};
        $scope.areaori = {};

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
        $scope.idAreaS = sessionService.get('idArea');
        var idAr = sessionService.get('idArea');

        $scope.modificarArea = modificarArea;
        $scope.cambiarEstado = cambiarEstado;

        modificarAreaService.cargarDatos(idAr).then(function (data) {
            $scope.areaori = data;
            $scope.descripcion = $scope.areaori.descripcion;
            $scope.nombre = $scope.areaori.nombre;
        });

        function modificarArea()
        {
            var arM = {};
            $scope.areaM = $scope.areaori;
            $scope.areaM.nombre = $scope.nombre;
            $scope.areaM.descripcion = $scope.descripcion;
            angular.copy($scope.areaM, arM);

            modificarAreaService.modificarArea(arM).then(function (data) {
                $scope.prueba = data;
            });

        }

        function cambiarEstado()
        {
            modificarAreaService.cambiarEstado(idAr).then(function (data3) {
                $scope.prueba = data3;
            });

        }

    }]);

