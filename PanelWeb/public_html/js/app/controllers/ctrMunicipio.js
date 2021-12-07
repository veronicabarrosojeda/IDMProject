PanelWeb.controller("ctrMunicipio", ['$scope', '$http', '$q', '$location', 'municipioService', 'listItemService', 'msgServices', 'uiGridValidateService', 'sessionService', function ($scope, $http, $q, $location, municipioService, listItemService, msgServices, uiGridValidateService, sessionService) {
        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 3)
                        rest = true;
                }

                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }
        verificarPermiso();
        $scope.titulo = "Gestion de Municipio";
        $scope.colMunicipios = [];
        $scope.myData = [];
        $scope.municipioForm = {};
        $scope.altaMunicipio = altaMunicipio;
        $scope.listarMunicipios = listarMunicipios;
        $scope.borrarMunicipio = borrarMunicipio;
        $scope.modificarMunicipio = modificarMunicipio;
        $scope.habilitarModificacion = habilitarModificacion;
        $scope.cancelarModificacion = cancelarModificacion;
        init();

        function altaMunicipio()
        {
            var mu = angular.copy($scope.municipioForm);
            municipioService.altaMunicipio(mu).then(function (data) {
                msgServices.darMensaje(data);
                $scope.reset();
                $scope.listarMunicipios();
            });
        }
        ;

        function modificarMunicipio()
        {
            municipioService.modificarMunicipio($scope.municipioForm).then(function (data) {
                msgServices.darMensaje(data);
                if (data.tipo == 'success')
                {
                    init();
                    $scope.reset();
                    $scope.listarMunicipios();

                }
            });
        }

        function borrarMunicipio(row)
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
                        municipioService.borrarMunicipio(row.entity.idMunicipio).then(function (data) {
                            msgServices.darMensaje(data);
                            if (data.tipo == 'success')
                            {
                                $scope.listarMunicipios();
                            }
                        });
                    });
        }
        ;

        function listarMunicipios()
        {
            municipioService.getMunicipios().then(function (data) {
                $scope.colMunicipios = data;
                $scope.myData = $scope.colMunicipios;

            });
        }
        ;


        $scope.showMe = function (arr) {
            alert(arr);
        };

        $scope.gridOptions = {
            enableSorting: true,
            enableCellEditOnFocus: true,
            enablePinning: true,
            showGridFooter: true,
            enableFiltering: true,
            paginationPageSizes: [25, 50, 75],
            paginationPageSize: 25,
            data: 'myData',
            columnDefs: [
                {field: 'Borrar', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-trash btn-danger" ng-click="grid.appScope.borrarMunicipio(row)"></button></center>'},
                {field: 'Editar', width: '10%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-pencil btn-success"  ng-click="grid.appScope.habilitarModificacion(row)"></button></center>'},
                {field: 'nombre', width: '20%'},
                {field: 'descripcion', width: '50%'}
            ],
            enableGridMenu: true,
            enableSelectAll: true,
            exporterCsvFilename: 'municipios.csv',
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
            exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location"))
        };

        function habilitarModificacion(row)
        {
            $('#alta').hide();
            $('#edit').show();
            $scope.municipioForm.nombre = row.entity.nombre;
            $scope.municipioForm.descripcion = row.entity.descripcion;
            $scope.municipioForm.idMunicipio = row.entity.idMunicipio;
            window.scrollTo(0, 0);
        }

        function cancelarModificacion()
        {
            $scope.municipioForm = {};
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

        $scope.reset = function () {
            $scope.municipioForm = {};
            $("#formMunicipio div").each(function () {
                $(this).removeClass('has-error');
            });
        };

    }]);
