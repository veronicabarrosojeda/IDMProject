PanelWeb.controller("ctrMapaSolicitudes", ['$scope', '$http', '$q', '$location', 'permisoService', 'listItemService', 'msgServices', 'uiGridValidateService', 'sessionService', 'msgServices', 'uiGmapGoogleMapApi', 'servicioService', function ($scope, $http, $q, $location, permisoService, listItemService, msgServices, uiGridValidateService, sessionService, msgServices, uiGmapGoogleMapApi, servicioService) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 27)
                        rest = true;
                }

                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }
        verificarPermiso();
        $scope.titulo = "Mapa de Solicitudes";
        $scope.colSolicitudesServicio = [];
        $scope.estados = [];
        $scope.settings = {enableSearch: true};
        $scope.colSolicitudes = [];
        $scope.tipoServicioSelect = [];
        $scope.tipoEmpresasSelect = [];
        $scope.tipoServicio = [];
        $scope.colEmpresasItems = [];
        $scope.getServicios = getServicios;
        $scope.getEstadosServicio = getEstadosServicio;
        $scope.actualizarMapa = actualizarMapa;
        $scope.setCheckBox = setCheckBox;
        $scope.qtSolicitantes;
        $scope.colIdEmpresa = [];
        $scope.diasPendiente;
        $scope.getRutaImagenByClave = getRutaImagenByClave;
        $scope.verServicio = verServicio;
        init();

        function verServicio(idServicio)
        {
            sessionService.set("idServicio_verServicio", idServicio);
            $location.path("/verServicio");

        }

        $scope.map = {
            center: {latitude: 45, longitude: -73},
            zoom: 12,
            markersEvents: {
                click: function (marker, eventName, model, arguments) {
                    console.log('Marker was clicked (' + marker + ', ' + eventName);//+', '+mydump(model, 0)+', '+mydump(arguments)+')');
                    $scope.map.window.model = model;
                    $scope.map.window.model = model;
                    $scope.map.window.title = model.title;
                    $scope.map.window.show = true;
                }
            },
            window: {
                marker: {},
                show: false,
                closeClick: function () {
                    this.show = false;
                },
                options: {}, // define when map is ready
                title: ''
            }
        };

        $scope.onMarkerClicked = function (m) {
            console.log('Marker was clicked');
            console.log(m);
        };

        $scope.closeClick = function () {
            this.window = false;
        };

        var onSuccess = function (position) {
            $scope.map.center = {
                latitude: position.coords.latitude,
                longitude: position.coords.longitude
            };
        };
        function onError(error) {
            console.log('code: ' + error.code + '\n' + 'message: ' + error.message + '\n');
        }
        navigator.geolocation.getCurrentPosition(onSuccess, onError);

        function setEmpresa()
        {
            var empresas = JSON.parse(sessionService.get('empresas'));
            for (var i = 0; i < empresas.length; i++)
            {
                $scope.colIdEmpresa.push(empresas[i].idEmpresa);
            }

            if (empresas == null || empresas.length == 0)
                $scope.colIdEmpresa.push(-1);
        }

        function init()
        {
            setEmpresa();
            $scope.qtSolicitantes = 0;
            $scope.diasPendiente = 0;
            var dataWhere = {};
            dataWhere.colEstados = ['1', '2', '3', '4', '5'];
            dataWhere.tipoDeServicios = [];
            dataWhere.colEmpresas = $scope.colIdEmpresa;
            var fecha = new Date();
            var desde = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
            var hasta = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
            dataWhere.fechaDesde = desde;
            dataWhere.fechaHasta = hasta;
            getServicios(dataWhere);
            getEstadosServicio();
            getTipoServicio();
            getEmpresas();
        }

        function getServicios(dataWhere)
        {
            var token = sessionService.get("token");
            dataWhere.token = token;
            $scope.control = {};
            servicioService.getServiciosForStatus(dataWhere).then(function (data) {
                $scope.colSolicitudesServicio = data;
                dataServicioToMarker($scope.colSolicitudesServicio);

            });
        }

        function getRutaImagenByClave(clave)
        {
            if (clave == 1)
            {
                return "images/imgApp/iconMap/pinRojo.png";
            } else if (clave == 2)
            {
                return "images/imgApp/iconMap/pinVerde.png";
            } else if (clave == 3)
            {
                return "images/imgApp/iconMap/pinCeleste.png";
            } else if (clave == 4)
            {
                return "images/imgApp/iconMap/pinAmarillo.png";
            } else if (clave == 5)
            {
                return "images/imgApp/iconMap/pinPurpura.png";
            } else if (clave == 6)
            {
                return "images/imgApp/iconMap/pinRosado.png";
            } else if (clave == 7)
            {
                return "images/imgApp/iconMap/pinBlanco.png";
            } else if (clave == 8)
            {
                return "images/imgApp/iconMap/pinNaranja.png";
            }
            return "images/imgApp/iconMap/pinRojo.png";
        }

        function setCheckBox(item)
        {
            var esatdosNoCkecked = ["4", "2"];

            for (var i = 0; i < esatdosNoCkecked.length; i++)
            {
                if (esatdosNoCkecked[i] == item.chk.clave)
                    return false
            }
            return true;
        }

        function getEstadosServicio()
        {
            var idTipo = "2";
            listItemService.getDetTipoById(idTipo).then(function (data) {
                $scope.estados = data;
            });
        }

        function dataServicioToMarker(colDataServicio)
        {
            $scope.colSolicitudes = [];
            for (var i = 0; i < colDataServicio.length; i++)
            {
                var coords = {longitude: '', latitude: ''};
                var marker = {};
                marker.id = colDataServicio[i].idServicio;
                marker.descripcionServicio = colDataServicio[i].descripcionServicio;
                coords.longitude = colDataServicio[i].longitud;
                coords.latitude = colDataServicio[i].latitud;
                marker.nombreTipoServicio = colDataServicio[i].nombreTipoServicio;
                marker.qtSolicitantes = colDataServicio[i].qtSolicitantes;
                marker.fechaIngreso = colDataServicio[i].fechaIngreso;
                marker.nombreArea = colDataServicio[i].nombreArea;
                marker.DiasIngreso = colDataServicio[i].DiasIngreso * -1;
                marker.coords = coords;
                marker.options = {icon: colDataServicio[i].imgEstado};
                $scope.colSolicitudes.push(marker);
            }
        }
        ;

        function ListItemSelectToListValue()
        {
            var arr = [];
            for (var i = 0; i < $scope.tipoServicioSelect.length; i++)
            {
                arr.push($scope.tipoServicioSelect[i].id);
            }
            return arr;
        }

        function ListItemEmpresaSelectToListValue()
        {
            var arr = [];
            for (var i = 0; i < $scope.tipoEmpresasSelect.length; i++)
            {
                arr.push($scope.tipoEmpresasSelect[i].id);
            }
            return arr;
        }

        function actualizarMapa()
        {
            var dataWhere = {};
            var desde = $scope.dtDesde.getFullYear() + "-" + ($scope.dtDesde.getMonth() + 1) + "-" + $scope.dtDesde.getDate();
            var hasta = $scope.dtHasta.getFullYear() + "-" + ($scope.dtHasta.getMonth() + 1) + "-" + $scope.dtHasta.getDate();
            dataWhere.colEstados = [];
            dataWhere.fechaDesde = desde;
            dataWhere.fechaHasta = hasta;
            dataWhere.qtSolicitantes = $scope.qtSolicitantes;
            dataWhere.diasPendiente = $scope.diasPendiente;
            dataWhere.tipoUbicacion = "-1";
            dataWhere.origen = "-1";
            dataWhere.tipoDeServicios = ListItemSelectToListValue();
            dataWhere.colEmpresas = ListItemEmpresaSelectToListValue();
            $('#filtros input:checked').each(function () {
                dataWhere.colEstados.push($(this).attr('id'));
            });
            getServicios(dataWhere);
        }

        function colTipoServicioToListMultiSelect(colTipoServicio)
        {
            for (var i = 0; i < colTipoServicio.length; i++)
            {
                var item = {};
                item.id = colTipoServicio[i].clave;
                item.label = colTipoServicio[i].descripcion;
                $scope.tipoServicio.push(item);

            }
        }

        function colEmpresaToListMultiSelect(colEmp)
        {
            for (var i = 0; i < colEmp.length; i++)
            {
                var item = {};
                item.id = colEmp[i].clave;
                item.label = colEmp[i].descripcion;
                $scope.colEmpresasItems.push(item);
            }
        }
        function getTipoServicio()
        {
            var colTipoServicio = [];
            listItemService.getTipoServicio().then(function (data) {
                colTipoServicio = data;
                colTipoServicioToListMultiSelect(colTipoServicio);
            });

        }

        function getEmpresas()
        {
            var colEmpresas = [];
            listItemService.getEmpresas().then(function (data) {
                colEmpresas = data;
                colEmpresaToListMultiSelect(colEmpresas);
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
