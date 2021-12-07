PanelWeb.controller("ctrSubArea", ['$scope', '$http', '$location', 'subAreaService', 'listItemService', 'msgServices', 'uiGridValidateService', 'sessionService', function ($scope, $http, $location, subAreaService, listItemService, msgServices, uiGridValidateService, sessionService) {

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

        $scope.titulo = "Gestion de SubArea";
        $scope.subArea = {};
        $scope.subAreas = [];
        $scope.myData = [];
        $scope.formSubArea = {};
        $scope.altaSubArea = altaSubArea;
        $scope.listarSubAreas = listarSubAreas;
        $scope.listarSubAreasDArea = listarSubAreasDArea;
        $scope.borrarSubArea = borrarSubArea;
        $scope.modificarSubArea = modificarSubArea;
        $scope.cancelarModificacion = cancelarModificacion;
        $scope.habilitarModificacion = habilitarModificacion;
        init();

        // LISTAR
        function listarSubAreas()
        {
            subAreaService.listarSubAreas().then(function (data) {
                $scope.subAreas = data;
                $scope.myData = $scope.subAreas;
                $scope.gridApi.saveState.restore($scope, $scope.myData);
            });
        }
        ;
        function listarSubAreasDArea()
        {
            var idArea = sessionService.get('idArea');
            subAreaService.listarSubAreasDArea(idArea).then(function (data) {
                $scope.subAreas = data;
                $scope.myData = $scope.subAreas;
                $scope.gridApi.saveState.restore($scope, $scope.myData);
            });
        }
        ;

        $scope.showMe = function (arr) {
            alert(arr);
        };

        //Configuración de la grilla
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
                {field: 'Borrar', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-trash btn-danger"  ng-click="grid.appScope.borrarSubArea(row.entity.idSubArea)"></button></center>'},
                {field: 'Editar', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-pencil btn-success"  ng-click="grid.appScope.habilitarModificacion(row)"></button></center>'},
                {field: 'idArea', width: '20%', enableCellEdit: false, displayName: 'IdArea'},
                {field: 'idSubArea', width: '20%', enableCellEdit: false, displayName: 'IdSubArea'},
                {field: 'nombre', width: '20%'},
                {field: 'descripcion', width: '20%'},
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

        function borrarSubArea(idSubA)
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
                        subAreaService.borrarSubArea(idSubA).then(function (data) {
                            msgServices.darMensaje(data);
                            if (data.tipo == 'success')
                            {
                                $scope.reset();
                                $scope.listarSubAreasDArea();
                            }
                        });
                    });
        }
        ;
        $scope.reset = function () {
            $scope.formSubArea = {};
            $("#subAreaform div").each(function () {
                $(this).removeClass('has-error');
            });
        };

        function modificarSubArea()
        {
            subAreaService.modificarSubArea($scope.formSubArea).then(function (data) {
                msgServices.darMensaje(data);
                if (data.tipo == 'success')
                {
                    $scope.listarSubAreasDArea();
                    $scope.reset();
                    cancelarModificacion();
                }
            });
        }
        function habilitarModificacion(row)
        {
            $('#alta').hide();
            $('#edit').show();
            $scope.formSubArea.idSubArea = row.entity.idSubArea;
            $scope.formSubArea.idArea = sessionService.get('idArea');
            $scope.formSubArea.nombre = row.entity.nombre;
            $scope.formSubArea.descripcion = row.entity.descripcion;
            window.scrollTo(0, 0);
        }
        function cancelarModificacion()
        {
            $scope.formSubArea = {};
            $('#edit').hide();
            $('#alta').show();
        }
        ;
        function init()
        {
            $('#edit').hide();
            $('#alta').show();
        }
        ;
        function altaSubArea()
        {
            $scope.formSubArea.idArea = sessionService.get('idArea');
            var subArea = angular.copy($scope.formSubArea);
            subAreaService.altaSubArea(subArea).then(function (data) {
                msgServices.darMensaje(data);
                if (data.tipo == "success")
                {
                    $scope.reset();
                    $scope.listarSubAreasDArea();

                }
            });
        }
    }]);

