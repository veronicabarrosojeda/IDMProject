PanelWeb.controller("ctrPermiso", ['$scope', '$http', '$q', '$location', 'permisoService', 'listItemService', 'msgServices', 'uiGridValidateService', 'sessionService', 'msgServices', function ($scope, $http, $q, $location, permisoService, listItemService, msgServices, uiGridValidateService, sessionService, msgServices) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 9)
                        rest = true;
                }
                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }
        verificarPermiso();
        $scope.titulo = "Permisos del Rol";
        $scope.mySelections = [];
        $scope.myData = [];
        $scope.colPermisos = [];
        $scope.colPermisosUser = [];
        $scope.listarPermisos = listarPermisos;
        $scope.modificarPermisos = modificarPermisos;

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
                {field: 'idPermiso', width: '5%', enableCellEdit: false, displayName: 'Id', visible: false},
                {field: 'nombre', width: '30%'},
                {field: 'descripcion', width: '50%'},
                {field: 'nombreUnico', width: '50%', visible: false},
                {field: 'estado', width: '50%', visible: false}
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
                gridApi.selection.on.rowSelectionChanged($scope, function (row) {
                });
            }
        };

        function modificarPermisos()
        {
            var idRol = sessionService.get("idRol_to_permiso");
            var permisos = $scope.gridApi.selection.getSelectedRows();
            var rolpermiso = {idRol: idRol, permisos: permisos ,token:sessionService.get("token")};
            permisoService.actualizarRolPermiso(rolpermiso).then(function (data) {
                msgServices.darMensaje(data);
            });
        }

        function listarPermisos()
        {
            var rol = {token: ""};
            rol.token = sessionService.get("token");
            permisoService.getPermisos(rol).then(function (data) {
                $scope.colPermisos = data.colGridPer;
                $scope.myData = $scope.colPermisos;
                rol.token = sessionService.get("token");
                rol.idRol = sessionService.get("idRol_to_permiso");
                permisoService.getPermisosByRol(rol).then(function (data) {
                    $scope.colPermisosUser = data.colGridPer;
                    var rows = $scope.gridApi.grid.rows;
                    for (var j = 0; j < rows.length; j++)
                    {
                        for (var i = 0; i < $scope.colPermisosUser.length; i++)
                        {
                            if ($scope.colPermisosUser[i].idPermiso == rows[j].entity.idPermiso) {

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
