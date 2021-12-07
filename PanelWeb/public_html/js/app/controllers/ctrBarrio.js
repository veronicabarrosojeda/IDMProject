PanelWeb.controller("ctrBarrio", ['$scope', '$http', '$q', '$location', 'barrioService', 'listItemService', 'msgServices', 'uiGridValidateService', 'sessionService', function ($scope, $http, $q, $location, barrioService, listItemService, msgServices, uiGridValidateService, sessionService) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 21)
                        rest = true;
                }

                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }
        verificarPermiso();
        $scope.titulo = "Gestion de Barrio";
        $scope.barrios = [];
        $scope.myData = [];
        $scope.formBarrio = {};
        $scope.cancelarModificacion = cancelarModificacion;
        $scope.habilitarModificacion = habilitarModificacion;
        $scope.altaBarrio = altaBarrio;
        $scope.bajaBarrio = bajaBarrio;
        $scope.modificarBarrio = modificarBarrio;
        $scope.listarBarrios = listarBarrios;
        init();
        getMunicipios();

        function listarBarrios()
        {
            barrioService.listarBarrios().then(function (data) {
                $scope.barrios = data;
                $scope.myData = $scope.barrios;

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
                {field: 'Borrar', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-trash btn-danger" ng-click="grid.appScope.bajaBarrio(row)"></button></center>'},
                {field: 'Editar', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-pencil btn-success"  ng-click="grid.appScope.habilitarModificacion(row)"></button></center>'},
                {field: 'nombre', width: '20%'},
                {field: 'descripcion', width: '30%'},
                {field: 'nombreMunicipio', width: '20%'},
            ],
            enableGridMenu: true,
            enableSelectAll: true,
            exporterCsvFilename: 'Barrios.csv',
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

        function bajaBarrio(row)
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
                        barrioService.bajaBarrio(row.entity.idBarrio).then(function (data) {
                            msgServices.darMensaje(data);
                            if (data.tipo == 'success')
                            {
                                $scope.listarBarrios();
                            }
                        });
                    });
        }
        ;

        $scope.reset = function () {
            $scope.formBarrio = {};
            $("#barrioform div").each(function () {
                $(this).removeClass('has-error');
            });
        };

        function getMunicipios()
        {
            listItemService.getListItemMunicipios().then(function (data) {
                $scope.municipios = data;
            });
        }
        ;

        function modificarBarrio()
        {
            barrioService.modificarBarrio($scope.formBarrio).then(function (data) {
                msgServices.darMensaje(data);
                if (data.tipo == 'success')
                {
                    init();
                    $scope.reset();
                    $scope.listarBarrios();

                }
            });
        }
        ;

        function habilitarModificacion(row)
        {
            $('#alta').hide();
            $('#edit').show();
            $scope.formBarrio.idBarrio = row.entity.idBarrio;
            $scope.formBarrio.nombre = row.entity.nombre;
            $scope.formBarrio.descripcion = row.entity.descripcion;
            $scope.formBarrio.idMunicipio = row.entity.idMunicipio;
            window.scrollTo(0, 0);
        }
        ;

        function cancelarModificacion()
        {
            $scope.formBarrio = {};
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

        function altaBarrio()
        {
            var barrio = angular.copy($scope.formBarrio);
            barrioService.altaBarrio(barrio).then(function (data) {
                msgServices.darMensaje(data);
                $scope.reset();
                $scope.listarBarrios();
            });

        }
        ;

    }]);



