PanelWeb.controller("ctrEmpresaUsuario", ['$scope', '$http', '$q', '$location', 'usuarioService', 'listItemService', 'msgServices', 'uiGridValidateService', 'sessionService', 'msgServices', 'empresaService', function ($scope, $http, $q, $location, usuarioService, listItemService, msgServices, uiGridValidateService, sessionService, msgServices, empresaService) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 26)
                        rest = true;
                }
                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }
        verificarPermiso();
        $scope.titulo = "Empresas del Usuario";
        $scope.mySelections = [];
        $scope.myData = [];
        $scope.colEmpresas = [];
        $scope.colEmpresasUser = [];
        $scope.listarEmpresas = listarEmpresas;
        $scope.modificarEmpresas = modificarEmpresas;

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
                {field: 'idEmpresa', width: '10%', enableCellEdit: false, displayName: 'id', visible: false},
                {field: 'nombre', width: '20%'},
                {field: 'descripcion', width: '50%'},
                {field: 'rut', width: '20%'},
                {field: 'estado', width: '10%', visible: false}
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
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.selection.on.rowSelectionChanged($scope, function (row) { });
            }
        };

        function modificarEmpresas()
        {
            var idUsuario = sessionService.get("idUsuario_empresas");
            var empresas = $scope.gridApi.selection.getSelectedRows();
            var dataEmpresasUsuario = {idUsuario: idUsuario, empresas: empresas};
            usuarioService.actualizarEmpresaUsuario(dataEmpresasUsuario).then(function (data) {
                msgServices.darMensaje(data);
            });
        }

        function listarEmpresas()
        {
            var idUsuario = sessionService.get("idUsuario_empresas");
            empresaService.getEmpresas().then(function (data) {
                $scope.colEmpresas = data;
                $scope.myData = $scope.colEmpresas;
                empresaService.getEmpresasByUser(idUsuario).then(function (data) {
                    if (data.colEmp == null) {
                        msgServices.darMensaje(data);
                    } else {
                        $scope.colEmpresasUser = data.colEmp;
                    }
                    var rows = $scope.gridApi.grid.rows;
                    for (var j = 0; j < rows.length; j++)
                    {
                        for (var i = 0; i < $scope.colEmpresasUser.length; i++)
                        {
                            if ($scope.colEmpresasUser[i].idEmpresa == rows[j].entity.idEmpresa) {

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
