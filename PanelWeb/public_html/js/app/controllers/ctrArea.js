PanelWeb.controller("ctrArea", ['$scope', '$http', '$location', 'md5', '$q', 'areaService', 'msgServices', 'uiGridValidateService', 'sessionService', function ($scope, $http, $location, md5, $q, areaService, msgServices, uiGridValidateService, sessionService) {


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

        $scope.titulo = "Gestión de Área";
        $scope.area = {};
        $scope.areas = [];
        $scope.myData = [];
        $scope.altaArea = altaArea;
        $scope.formUsuario = {};
        $scope.listarAreas = listarAreas;
        $scope.subArea = subArea;

        $scope.cambiarEstado = cambiarEstado;
        $scope.habilitarModificacion = habilitarModificacion;
        $scope.cancelarModificacion = cancelarModificacion;
        $scope.modificarArea = modificarArea;

        init();

        function altaArea()
        {
            var ar = {};
            angular.copy($scope.area, ar);

            areaService.altaArea(ar).then(function (data) {
                msgServices.darMensaje(data);
                $scope.reset();
                $scope.listarAreas();
            });
        }

        function listarAreas()
        {
            areaService.findAreas().then(function (data) {
                $scope.areas = data;
                $scope.myData = $scope.areas;
                $scope.gridApi.saveState.restore($scope, $scope.myData);
            });
        }
        ;

        $scope.gridOptions = {
            enableSorting: true,
            enableCellEditOnFocus: true,
            enablePinning: true,
            showGridFooter: true,
            enableFiltering: true,
            paginationPageSizes: [25, 50, 75],
            paginationPageSize: 10,
            data: 'myData',
            columnDefs: [
                {field: 'Editar', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-pencil btn-success"  ng-click="grid.appScope.habilitarModificacion(row)"></button></center>'},
                {field: 'Borrar', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-trash btn-danger"  ng-click="grid.appScope.cambiarEstado(row.entity.idArea)"></button></center>'},
                {field: 'Sub Area', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-list-alt btn-info"  ng-click="grid.appScope.subArea(row)"></button></center>'},
                {field: 'idArea', width: '5%', enableCellEdit: false, displayName: 'id'},
                {field: 'nombre', width: '20%'},
                {field: 'descripcion', width: '40%'}
            ],
            enableGridMenu: true,
            enableSelectAll: true,
            exporterCsvFilename: 'myFile.csv',
            exporterPdfDefaultStyle: {fontSize: 9},
            exporterPdfTableStyle: {margin: [30, 30, 30, 30]},
            exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: true, color: 'red'},
            exporterPdfHeader: {text: "My Header", style: 'headerStyle'},
            exporterPdfFooter: function (currentPage, pageCount) {
                return {text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle'};
            },
            exporterPdfCustomFormatter: function (docDefinition) {
                docDefinition.styles.headerStyle = {fontSize: 22, bold: true};
                docDefinition.styles.footerStyle = {fontSize: 10, bold: true};
                return docDefinition;
            },
            exporterPdfOrientation: 'portrait',
            exporterPdfPageSize: 'LETTER',
            exporterPdfMaxGridWidth: 500,
            exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
            enableRowSelection: true
        };

        function subArea(row)
        {
            sessionService.set("idArea", row.entity.idArea);
            $location.path("/subArea");
        }

        function cambiarEstado(idArea)
        {
            swal({
                title: "Confirmar Operación",
                text: "¿Seguro que desea confirmar la operación?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                closeOnConfirm: true
            },
                    function () {
                        areaService.cambiarEstado(idArea).then(function (data3) {
                            msgServices.darMensaje(data3);
                            $scope.listarAreas();
                            $scope.gridApi.core.refresh();
                        });
                    });
            //$scope.listarAreas();
            cancelarModificacion();
        }

        $scope.reset = function () {
            $scope.area = {};
            $("#userform div").each(function () {
                $(this).removeClass('has-error');
            });
        };

        function habilitarModificacion(row)
        {
            $('#alta').hide();
            $('#edit').show();
            $scope.area.nombre = row.entity.nombre;
            $scope.area.descripcion = row.entity.descripcion;
            $scope.area.estado = row.entity.estado;
            $scope.area.idArea = row.entity.idArea;
            window.scrollTo(0, 0);
        }

        function modificarArea()
        {
            areaService.modificarArea($scope.area).then(function (data) {
                msgServices.darMensaje(data);
                if (data.tipo == 'success')
                {
                    $scope.listarAreas();
                    $scope.reset();
                    cancelarModificacion();
                }
            });

        }

        function init()
        {
            $('#edit').hide();
            $('#alta').show();
        }
        ;

        function cancelarModificacion()
        {
            $scope.area = {};
            $('#edit').hide();
            $('#alta').show();
        }
        ;

    }]);


