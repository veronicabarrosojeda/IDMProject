PanelWeb.controller("ctrTipoServicio", ['$scope', '$http', '$q', '$location', 'tipoServicioService', 'listItemService', 'msgServices', 'uiGridValidateService', 'sessionService', function ($scope, $http, $q, $location, tipoServicioService, listItemService, msgServices, uiGridValidateService, sessionService) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 20)
                        rest = true;
                }
                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }
        verificarPermiso();
        $scope.titulo = "Gestion de Tipo de Servicio";
        $scope.tipoServicios = [];
        $scope.myData = [];
        $scope.formTipoServicio = {};
        $scope.cancelarModificacion = cancelarModificacion;
        $scope.habilitarModificacion = habilitarModificacion;
        $scope.altaTipoServicio = altaTipoServicio;
        $scope.bajaTipoServicio = bajaTipoServicio;
        $scope.modificarTipoServicio = modificarTipoServicio;
        $scope.listarTipoServicio = listarTipoServicio;
        $scope.colEmpresas = [];
        init();
        getAreas();
        getEmpresas();

        function listarTipoServicio()
        {
            tipoServicioService.listarTipoServicio().then(function (data) {
                $scope.tipoServicios = data;
                $scope.myData = $scope.tipoServicios;
            });
        }
        ;

        function getEmpresas()
        {
            listItemService.getEmpresas().then(function (data) {
                $scope.colEmpresas = data;
            });
        }

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
                {field: 'Borrar', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-trash btn-danger" ng-click="grid.appScope.bajaTipoServicio(row)"></button></center>'},
                {field: 'Editar', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-pencil btn-success"  ng-click="grid.appScope.habilitarModificacion(row)"></button></center>'},
                {field: 'idTipoServicio', width: '20%', enableCellEdit: false, displayName: 'Id'},
                {field: 'nombre', width: '30%'},
                {field: 'descripcion', width: '40%'},
                {field: 'idArea', width: '20%'},
                {field: 'nombreArea', width: '20%'},
                {field: 'idEmpresa', width: '10%'},
                {field: 'nombreEmpresa', width: '20%'}
            ],
            enableGridMenu: true,
            enableSelectAll: true,
            exporterCsvFilename: 'TipoServicio.csv',
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

        function bajaTipoServicio(row)
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
                        tipoServicioService.bajaTipoServicio(row.entity.idTipoServicio).then(function (data) {
                            msgServices.darMensaje(data);
                            if (data.tipo == 'success')
                            {
                                $scope.listarTipoServicio();
                            }
                        });
                    });
        }
        ;


        $scope.reset = function () {
            $scope.formTipoServicio = {};
            $("#tipoServicioform div").each(function () {
                $(this).removeClass('has-error');
            });
        };

        function getAreas()
        {
            listItemService.getListItemAreas().then(function (data) {
                $scope.areas = data;
            });
        }
        ;

        function modificarTipoServicio()
        {
            tipoServicioService.modificarTipoServicio($scope.formTipoServicio).then(function (data) {
                msgServices.darMensaje(data);
                if (data.tipo == 'success')
                {
                    init();
                    $scope.reset();
                    $scope.listarTipoServicio();
                }
            });
        }
        ;

        function habilitarModificacion(row)
        {
            $('#alta').hide();
            $('#edit').show();
            $scope.formTipoServicio.idTipoServicio = row.entity.idTipoServicio;
            $scope.formTipoServicio.nombre = row.entity.nombre;
            $scope.formTipoServicio.descripcion = row.entity.descripcion;
            $scope.formTipoServicio.idArea = row.entity.idArea;
            $scope.formTipoServicio.idEmpresa = row.entity.idEmpresa;
        }
        ;

        function cancelarModificacion()
        {
            $scope.formTipoServicio = {};
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

        function altaTipoServicio()
        {
            var tipoServicio = angular.copy($scope.formTipoServicio);
            tipoServicioService.altaTipoServicio(tipoServicio).then(function (data) {
                msgServices.darMensaje(data);
                $scope.listarTipoServicio();
            });
            $scope.reset();
        }
        ;

    }]);






