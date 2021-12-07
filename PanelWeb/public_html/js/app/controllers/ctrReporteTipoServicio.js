PanelWeb.controller("ctrReporteTipoServicio", ['$scope', '$http', '$q', '$location', 'usuarioService', 'listItemService', 'msgServices', 'uiGridValidateService', 'sessionService', 'servicioService', 'reporteService', function ($scope, $http, $q, $location, usuarioService, listItemService, msgServices, uiGridValidateService, sessionService, servicioService, reporteService) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 31)
                        rest = true;
                }

                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }
        verificarPermiso();
        $scope.titulo = "Reporte por Tipo de Servicio";
        $scope.generarReporte = generarReporte;
        generarReporte(null, null);

         $('#titulo1').hide();
         $('#titulo2').hide();


        $scope.pdf = function ()
        {
            html2canvas(document.getElementById('pdfZone'), {
                onrendered: function (canvas) {
                    var data = canvas.toDataURL();
                    var docDefinition = {
                        content: [{
                                image: data,
                                width: 500,
                            }]
                    };
                    pdfMake.createPdf(docDefinition).download("ReporteDeCobertura" + new Date() + ".pdf");
                }
            });
        }

        $scope.chartOption = {
            scales: {
                yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]

            }

        };

        function generarReporte(dtDesde, dtHasta)
        {          
            
            var hasta = dtHasta;
            var desde = dtDesde;

            if (dtHasta != null || dtDesde != null)
            {
                $('#titulo1').show();
                $('#titulo2').show();
                var desde = dtDesde.getFullYear() + "-" + (dtDesde.getMonth() + 1) + "-" + dtDesde.getDate();
                var hasta = dtHasta.getFullYear() + "-" + (dtHasta.getMonth() + 1) + "-" + dtHasta.getDate();
            }

            var dataReporte = [];
            var colItems = [];
            var rows = [];
            
            $scope.labelsPie = [];
            $scope.dataPie = [];

            var data = {tipo: "TipoServicio", fechaDesde: desde, fechaHasta: hasta};
            reporteService.reportePorFecha(data).then(function (data) {
                dataReporte = data;
                for (var i = 0; i < dataReporte.length; i++)
                {
                    rows.push(dataReporte[i].nombre);
                    colItems.push(dataReporte[i].qtSolicitudes);
                    
                    $scope.labelsPie.push(dataReporte[i].nombre);
                    $scope.dataPie.push(dataReporte[i].qtSolicitudes);
                }
                $scope.labels = rows;
                $scope.data = colItems;

            });
        }
        $scope.today = function () {
            $scope.dtHasta = new Date();
            $scope.dtDesde = new Date();
        };
        $scope.today();

        $scope.clear = function () {
            $scope.dtHasta = new Date();
            $scope.dtDesde = new Date();
        };

        $scope.inlineOptions = {
            customClass: getDayClass,
            minDate: new Date(),
            showWeeks: true
        };

        $scope.dateOptions = {
            dateDisabled: disabled,
            formatYear: 'yy',
            maxDate: new Date(2020, 5, 22),
            minDate: new Date(),
            startingDay: 1
        };

        // Disable weekend selection
        function disabled(data) {
            var date = data.date,
                    mode = data.mode;
            return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
        }

        $scope.toggleMin = function () {
            $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
            $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
        };

        $scope.toggleMin();

        $scope.open1 = function () {
            $scope.popup1.opened = true;
        };

        $scope.open2 = function () {
            $scope.popup2.opened = true;
        };

        $scope.setDate = function (year, month, day) {
            $scope.dtDesde = new Date(year, month, day);
        };

        $scope.setDate = function (year, month, day) {
            $scope.dtHasta = new Date(year, month, day);
        };

        $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
        $scope.format = $scope.formats[0];
        $scope.altInputFormats = ['M!/d!/yyyy'];

        $scope.popup1 = {
            opened: false
        };

        $scope.popup2 = {
            opened: false
        };

        var tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        var afterTomorrow = new Date();
        afterTomorrow.setDate(tomorrow.getDate() + 1);
        $scope.events = [
            {
                date: tomorrow,
                status: 'full'
            },
            {
                date: afterTomorrow,
                status: 'partially'
            }
        ];

        function getDayClass(data) {
            var date = data.date,
                    mode = data.mode;
            if (mode === 'day') {
                var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

                for (var i = 0; i < $scope.events.length; i++) {
                    var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                    if (dayToCheck === currentDay) {
                        return $scope.events[i].status;
                    }
                }
            }

            return '';
        }
    }]);




