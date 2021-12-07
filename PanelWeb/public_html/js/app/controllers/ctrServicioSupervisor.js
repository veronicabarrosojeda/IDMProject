PanelWeb.controller("ctrServicioSupervisor", ['$scope', '$http', '$q', '$location', 'listItemService', 'msgServices', 'uiGridValidateService', 'sessionService', 'msgServices', 'supervisorService', 'servicioService', function ($scope, $http, $q, $location, listItemService, msgServices, uiGridValidateService, sessionService, msgServices, supervisorService, servicioService) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 23)
                        rest = true;
                }

                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }
        verificarPermiso();
        $scope.titulo = "Supervisores del Servicio";
        $scope.mySelections = [];
        $scope.myData = [];
        $scope.colSupervisores = [];
        $scope.colSupervisoresServicio = [];
        $scope.listarSupervisores = listarSupervisores;
        $scope.modificarSupervisores = modificarSupervisores;

        //Configuración de la grilla
        $scope.gridOptions = {
            enableSorting: true,
            multiSelect: true,
            enableCellEditOnFocus: true,
            enablePinning: true,
            showGridFooter: true,
            enableFiltering: true,
            paginationPageSizes: [25, 50, 75],
            paginationPageSize: 10,
            data: 'myData',
            columnDefs: [
                {field: 'idSupervisor', width: '20%', enableCellEdit: false, displayName: 'Nro. Supervisor'},
                {field: 'nombre', width: '20%'},
                {field: 'apellido', width: '20%'},
                {field: 'estado', width: '30%'}
            ],
            enableGridMenu: true,
            enableSelectAll: true,
            exporterCsvFilename: 'EmpresaServicio.csv',
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
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.selection.on.rowSelectionChanged($scope, function (row) {
                });
            }
        };

        function modificarSupervisores()
        {
            var dtoSupServ = {};
            dtoSupServ.idServicio = sessionService.get("idServicio_supervisor");
            dtoSupServ.supervisores = $scope.gridApi.selection.getSelectedRows();
            dtoSupServ.token = sessionService.get("token");
            servicioService.actualizarSupervisoresServicio(dtoSupServ).then(function (data) {
                msgServices.darMensaje(data);
            });
        }

        function listarSupervisores()
        {
            var servicio = {};
            servicio.idServicio = sessionService.get("idServicio_supervisor");
            servicio.token = sessionService.get("token");
            supervisorService.getSupervisores(servicio.token).then(function (data) {
                $scope.myData = data.colSup;// todos los supervisores
                supervisorService.getSupervisoresByServicio(servicio).then(function (data) {
                    $scope.colSupervisoresServicio = data.colSup;//supervisores del servicio
                    var rows = $scope.gridApi.grid.rows;
                    for (var j = 0; j < rows.length; j++)
                    {
                        for (var i = 0; i < $scope.colSupervisoresServicio.length; i++)
                        {
                            if ($scope.colSupervisoresServicio[i].idSupervisor == rows[j].entity.idSupervisor) {

                                $scope.gridApi.selection.selectRow(rows[j].entity);
                            }
                        }
                    }
                    ;
                    $scope.gridOptions.isRowSelectable();

                });
            });
        }
        ;
    }]);
