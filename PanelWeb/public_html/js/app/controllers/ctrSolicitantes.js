PanelWeb.controller("ctrSolicitantes", ['$scope', '$http', '$q', '$location', 'solicitanteService', 'listItemService', 'msgServices', 'uiGridValidateService', 'sessionService', 'servicioService', function ($scope, $http, $q, $location, solicitanteService, listItemService, msgServices, uiGridValidateService, sessionService, servicioService) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 24)
                        rest = true;
                }

                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }
        verificarPermiso();
        $scope.titulo = "Solicitantes";
        $scope.listarSolicitantes = listarSolicitantes;
        $scope.myData = [];
        init();
        function init()
        {
            listarSolicitantes();
        }

        function listarSolicitantes()
        {
            var idServicio = sessionService.get("idServicio_solicitantes");
            solicitanteService.getSolicitantesByService(idServicio).then(function (data) {
                $scope.myData = data;
            });
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
                {field: 'idServicioSolicitante', width: '20%', enableCellEdit: false, visible: false},
                {field: 'idServicio', width: '30%', enableCellEdit: false},
                {field: 'nroIdentidad', width: '20%', enableCellEdit: false},
                {field: 'tipoIdentidad', width: '20%', enableCellEdit: false},
                {field: 'telefono', width: '20%', enableCellEdit: false},
                {field: 'nombreSolicitante', width: '35%', enableCellEdit: false},
                {field: 'correoSolicitante', width: '20%', enableCellEdit: false}
            ],
            enableGridMenu: true,
            exporterCsvFilename: 'solicitantes.csv',
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
    }]);
