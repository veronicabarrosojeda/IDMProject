PanelWeb.controller("ctrRol", ['$scope', '$http', '$q', 'sessionService', '$location', 'rolService', 'listItemService', 'msgServices', 'uiGridValidateService', 'sessionService', function ($scope, $http, $q, sessionService, $location, rolService, listItemService, msgServices, uiGridValidateService, sessionService) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 10)
                        rest = true;
                }
                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }
        verificarPermiso();
        $scope.titulo = "Gestion de Roles";
        $scope.mySelections = [];
        $scope.myData = [];
        $scope.colRoles = [];
        $scope.formRol = {};
        $scope.listarRoles = listarRoles;
        $scope.asignarPermisos = asignarPermisos;
        $scope.modificarRol = modificarRol;
        $scope.habilitarModificacion = habilitarModificacion;
        $scope.cancelarModificacion = cancelarModificacion;
        $scope.borrarRol = borrarRol;
        $scope.agregarRol = agregarRol;
        init();

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
                {field: 'Borrar', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-trash btn-danger" ng-click="grid.appScope.borrarRol(row.entity)"></button></center>'},
                {field: 'Editar', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-pencil btn-success"  ng-click="grid.appScope.habilitarModificacion(row)"></button></center>'},
                {field: 'Permisos', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon glyphicon-list-alt"  ng-click="grid.appScope.asignarPermisos(row.entity)"></button></center>'},
                {field: 'idRol', width: '5%', enableCellEdit: false, displayName: 'id', visible: false},
                {field: 'nombre', width: '30%'},
                {field: 'descripcion', width: '50%', displayName: 'Descripción'}
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

        $scope.reset = function () {
            $scope.formUsuario = {};
            $("#rolForm div").each(function () {
                $(this).removeClass('has-error');
                $scope.formRol.nombre ="";
                $scope.formRol.descripcion="";
            });
        };

        function agregarRol()
        {
            var rol = angular.copy($scope.formRol);
            rol.token = sessionService.get("token");
            rolService.agregarRol(rol).then(function (data) {
                msgServices.darMensaje(data);
                if (data.tipo == 'success')
                {
                    $scope.reset();
                    $scope.listarRoles();
                }
            });
        }

        function modificarRol()
        {
            var rol = angular.copy($scope.formRol);
            rol.token = sessionService.get("token");
            rolService.modificarRol(rol).then(function (data) {
                msgServices.darMensaje(data);
                if (data.tipo == 'success')
                {
                    $scope.listarRoles();
                }
            });
        }

        function borrarRol(row)
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
                        var rol={idRol:'',token:''};
                        rol.idRol = row.idRol;
                        rol.token =sessionService.get("token")
                      
                        rolService.borrarRol(rol).then(function (data) {
                            msgServices.darMensaje(data);
                            if (data.tipo == 'success')
                            {
                                $scope.listarRoles();
                            }
                        });
                    });
        }

        function habilitarModificacion(row)
        {
            $('#alta').hide();
            $('#edit').show();
            $scope.formRol.idRol = row.entity.idRol;
            $scope.formRol.nombre = row.entity.nombre;
            $scope.formRol.descripcion = row.entity.descripcion;
            window.scrollTo(0, 0);
        }

        function cancelarModificacion()
        {
            $scope.formRol = {};
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

        function asignarPermisos(row)
        {
            sessionService.set("idRol_to_permiso", row.idRol);
            $location.path("/permiso");
        }

        function listarRoles()
        {
            var token = sessionService.get("token");
              
            rolService.getRoles(token).then(function (data) {
              if (data.colRol == null) {
                    msgServices.darMensaje(data);
                } else {
                    $scope.myData = data.colRol;
                }
            });
        };

    }]);
