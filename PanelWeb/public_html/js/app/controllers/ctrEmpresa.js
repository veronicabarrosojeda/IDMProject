PanelWeb.controller("ctrEmpresa", ['$scope', '$http', '$location', 'md5', '$q', 'empresaService', 'msgServices', 'uiGridValidateService', 'sessionService', function ($scope, $http, $location, md5, $q, empresaService, msgServices, uiGridValidateService, sessionService) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 18)
                        rest = true;
                }
                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }
        verificarPermiso();
        $scope.titulo = "Gestión de Empresa";
        $scope.empresa = {};
        $scope.empresas = [];
        $scope.myData = [];
        $scope.altaEmpresa = altaEmpresa;
        $scope.formUsuario = {};
        $scope.listarEmpresas = listarEmpresas;

        $scope.cambiarEstado = cambiarEstado;
        $scope.habilitarModificacion = habilitarModificacion;
        $scope.cancelarModificacion = cancelarModificacion;
        $scope.modificarEmpresa = modificarEmpresa;
        init();

        function altaEmpresa()
        {
            var em = {};
            angular.copy($scope.empresa, em);
            empresaService.altaEmpresa(em).then(function (data) {
                msgServices.darMensaje(data);
                $scope.reset();
                $scope.listarEmpresas();
            });
        }

        function listarEmpresas()
        {
            empresaService.getEmpresas().then(function (data) {
                $scope.empresas = data;
                $scope.myData = $scope.empresas;
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
                {field: 'Borrar', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-trash btn-danger" ng-click="grid.appScope.cambiarEstado(row.entity.idEmpresa); listarEmpresas()"></button></center>'},
                {field: 'idEmpresa', width: '5%', enableCellEdit: false, displayName: 'id'},
                {field: 'nombre', width: '20%'},
                {field: 'descripcion', width: '40%'},
                {field: 'rut', width: '12%'}
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

        function cambiarEstado(idEmpresa)
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
                        empresaService.cambiarEstado(idEmpresa).then(function (data3) {
                            msgServices.darMensaje(data3);
                            $scope.listarEmpresas();
                            $scope.gridApi.core.refresh();
                        });

                    });
            cancelarModificacion();
        }

        $scope.reset = function () {
            $scope.empresa = {};
            $("#userform div").each(function () {
                $(this).removeClass('has-error');
            });
        };

        function habilitarModificacion(row)
        {
            $('#alta').hide();
            $('#edit').show();
            $scope.empresa.nombre = row.entity.nombre;
            $scope.empresa.rut = row.entity.rut;
            $scope.empresa.descripcion = row.entity.descripcion;
            $scope.empresa.estado = row.entity.estado;
            $scope.empresa.idEmpresa = row.entity.idEmpresa;
            window.scrollTo(0, 0);
        }

        function modificarEmpresa()
        {
            empresaService.modificarEmpresa($scope.empresa).then(function (data) {
                msgServices.darMensaje(data);
                if (data.tipo == 'success')
                {
                    $scope.listarEmpresas();
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
            $scope.empresa = {};
            $('#edit').hide();
            $('#alta').show();
        }
        ;

    }]);


