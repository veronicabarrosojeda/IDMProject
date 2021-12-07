PanelWeb.controller("ctrSupervisor", ['$scope', '$http', '$q', '$location', 'supervisorService', 'listItemService', 'msgServices', 'uiGridValidateService', 'sessionService', function ($scope, $http, $q, $location, supervisorService, listItemService, msgServices, uiGridValidateService, sessionService) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 13)
                        rest = true;
                }

                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }
        verificarPermiso();
        $scope.titulo = "Gestión de Supervisor";
        $scope.mySelections = [];
        $scope.supervisores = [];
        $scope.myData = [];
        $scope.formSupervisor = {};
        $scope.cancelarSupervisor = cancelarSupervisor;
        $scope.habilitarModificacion = habilitarModificacion;
        $scope.modificarSupervisor = modificarSupervisor;
        $scope.agregarSupervisor = agregarSupervisor;
        $scope.listarSupervisores = listarSupervisores;
        $scope.borrarSupervisor = borrarSupervisor;
        init();

        function listarSupervisores()
        {
            var token = sessionService.get("token");
            supervisorService.getSupervisores(token).then(function (data) {
                if (data.colSup == null) {
                    msgServices.darMensaje(data);
                } else {
                    $scope.myData = data.colSup;
                }
                $scope.gridApi.saveState.restore($scope, $scope.myData);
            });
        }
        ;

        $scope.showMe = function (arr) {
            alert(arr);
        };

        $scope.reset = function () {
            $scope.formSupervisor = {};
            $("#supform div").each(function () {
                $(this).removeClass('has-error');
            });

        };

        function init()
        {
            $('#edit').hide();
            $('#alta').show();
        }
        ;
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
                {field: 'Borrar', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-trash btn-danger" ng-click="grid.appScope.borrarSupervisor(row)"></button></center>'},
                {field: 'Editar', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-pencil btn-success"  ng-click="grid.appScope.habilitarModificacion(row)"></button></center>'},
                {field: 'idSupervisor', width: '20%', enableCellEdit: false, displayName: 'id'},
                {field: 'nombre', width: '30%'},
                {field: 'apellido', width: '30%'}
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

        function borrarSupervisor(row)
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
                        var sup = {idSupervisor:""};
                        sup.idSupervisor = row.entity.idSupervisor;
                        sup.token = sessionService.get("token");
                        supervisorService.borrarSupervisor(sup).then(function (data) {
                            msgServices.darMensaje(data);
                            if (data.tipo == 'success')
                            {
                                $scope.reset();
                                $scope.listarSupervisores();
                            }
                        });
                    });
        }
        ;

        function modificarSupervisor()
        {
            var supervisor = $scope.formSupervisor;
            supervisor.token = sessionService.get("token");
            supervisorService.modificarSupervisor(supervisor).then(function (data) {
                msgServices.darMensaje(data);
                if (data.tipo == 'success')
                {
                    init();
                    $scope.reset();
                    $scope.listarSupervisores();
                }
            });
        }

        function habilitarModificacion(row)
        {
            $('#alta').hide();
            $('#edit').show();
            $scope.formSupervisor.nombre = row.entity.nombre;
            $scope.formSupervisor.apellido = row.entity.apellido;
            $scope.formSupervisor.idSupervisor = row.entity.idSupervisor;
            window.scrollTo(0, 0);
        }

        function cancelarSupervisor()
        {
            $scope.reset();
            init();
            $scope.formSupervisor = {};
        }
        ;

        function agregarSupervisor()
        {
            var sup = angular.copy($scope.formSupervisor);
            sup.token = sessionService.get("token");
            supervisorService.altaSupervisor(sup).then(function (data) {
                msgServices.darMensaje(data);
                if (data.tipo == "success")
                {
                    $scope.reset();
                    $scope.listarSupervisores();
                }
            });
        }
    }]);
