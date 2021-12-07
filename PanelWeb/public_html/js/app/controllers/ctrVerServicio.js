PanelWeb.controller("ctrVerServicio", ['$scope', '$http', '$q', '$window', '$location', 'verServicioService', 'listItemService', 'msgServices', 'uiGridValidateService', 'uiGmapGoogleMapApi', 'uiGmapGmapUtil', 'sessionService', 'uiGridConstants', function ($scope, $http, $q, $window, $location, verServicioService, listItemService, msgServices, uiGridValidateService, uiGmapGoogleMapApi, uiGmapGmapUtil, sessionService, uiGridConstants) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 25)
                        rest = true;
                }
                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }

        verificarPermiso();
        $scope.titulo = "Ver Solicitud de Servicio";
        $scope.getServicio = getServicio();
        $scope.idServicio;
        $scope.categoriaSelect;
        $scope.estadoServicio;
        $scope.servicio = {};
        $scope.myData = [];
        $scope.historial = [];
        $scope.nuevoServicio = {};
        $scope.modificarServicio = modificarServicio;
        var userLng;
        var userLat;
        var idMun;

        function getServicio() {
            $scope.idServicio = sessionService.get("idServicio_verServicio");
            var token = sessionService.get("token");
            verServicioService.getServicio($scope.idServicio, token).then(function (data) {
                $scope.servicio = data;
                if ($scope.servicio.rutaImagen == null || $scope.servicio.rutaImagen == "")
                {
                    $scope.imagen = $window.ip + "/ServerApp/STORAGE/nodis.jpg";
                } else {
                    $scope.imagen = $window.ip + $scope.servicio.rutaImagen;
                }
                $scope.historial = $scope.servicio.historial;
                $scope.categoriaSelect = $scope.servicio.idTipoServicio;
                $scope.estadoServicio = $scope.servicio.estado;
                $scope.nuevoServicio.descripcion = $scope.servicio.descripcionServicio;
                $scope.nuevoServicio.observaciones = $scope.servicio.observaciones;
                if ($scope.servicio.latitud !== 0 && $scope.servicio.longitud !== 0) {
                    userLat = $scope.servicio.latitud;
                    userLng = $scope.servicio.longitud;
                }
                if ($scope.servicio.ubicacionManual === true) {
                    if ($scope.servicio.idMunicipio !== 0) {
                        idMun = $scope.servicio.idMunicipio;
                    }
                }
                getCategorias();
                getEstadosServicio();
            });
        }
        ;

        var getMunicipios = function ()
        {
            listItemService.getListItemMunicipios().then(function (data) {
                $scope.municipios = data;
            });
        }
        ;
        function getCategorias()
        {
            listItemService.getListItemCategorias().then(function (data) {
                $scope.categorias = data;
            });
        }
        ;
        function getEstadosServicio()
        {
            var idTipo = "2";
            listItemService.getDetTipoById(idTipo).then(function (data) {
                $scope.estados = data;
            });
        }
        ;
        var getBarrios = function (idMun)
        {
            listItemService.getListItemBarrios(idMun).then(function (data) {
                $scope.barrios = data;
            });
        }
        ;
        function modificarServicio()
        {
            $scope.nuevoServicio.idServicio = $scope.idServicio;
            if ($scope.nuevoServicio.idCategoria == null) {
                $scope.nuevoServicio.idCategoria = $scope.categoriaSelect;
            }
            if ($scope.nuevoServicio.estado == null) {
                $scope.nuevoServicio.estado = $scope.estadoServicio;
            }
            var token = sessionService.get("token");
            $scope.nuevoServicio.token = token;
            verServicioService.modificarServicio($scope.nuevoServicio).then(function (data) {
                msgServices.darMensaje(data);
                if (data.tipo == 'success')
                {
                    $scope.categoriaSelect = $scope.nuevoServicio.idCategoria;
                    $scope.estadoServicio = $scope.nuevoServicio.estado;
                    getServicio();
                }
            });
        }

        $scope.verUbicacion = function ()
        {
            if ($scope.servicio.coordenadas === true) {
                if ($scope.servicio.latitud !== 0 && $scope.servicio.longitud !== 0) {
                    $scope.habilitarMapa = true;
                    $scope.habilitarUbicacionManual = false;
                    $scope.map.center = {
                        latitude: userLat,
                        longitude: userLng
                    };
                    $scope.pin = {};
                    $scope.pin.coords = {};
                    $scope.pin.id = 2;
                    $scope.pin.key = 2;
                    $scope.pin.coords.latitude = userLat;
                    $scope.pin.coords.longitude = userLng;
                    $scope.$apply();
                }
            }
            if ($scope.servicio.ubicacionManual === true)
            {
                $scope.habilitarMapa = false;
                $scope.habilitarUbicacionManual = true;
                getMunicipios();
                getBarrios(idMun);
                if ($scope.servicio.calle !== "")
                    $scope.calle = $scope.servicio.calle;
                if ($scope.servicio.entreCalles !== null && $scope.servicio.entreCalles !== "")
                    $scope.entreCalles = $scope.servicio.entreCalles;
                if ($scope.servicio.nroPuerta !== null)
                    $scope.nroPuerta = $scope.servicio.nroPuerta;
                if ($scope.servicio.apto !== null && $scope.servicio.apto !== "")
                    $scope.apto = $scope.servicio.apto;
                if ($scope.servicio.nroPuerta !== null && $scope.servicio.nroPuerta !== "")
                    $scope.nroPuerta = $scope.servicio.nroPuerta;
                if ($scope.servicio.nroSolar !== null && $scope.servicio.nroSolar !== "")
                    $scope.nroSolar = $scope.servicio.nroSolar;
                if ($scope.servicio.nroManzana !== null && $scope.servicio.nroManzana !== "")
                    $scope.nroManzana = $scope.servicio.nroManzana;
                if ($scope.servicio.nroPadron !== null && $scope.servicio.nroPadron !== "")
                    $scope.nroPadron = $scope.servicio.nroPadron;
            }
        };

        $scope.map = {
            zoom: 12,
            markersEvents: {
                click: function (marker, eventName, model, arguments) {
                    console.log('Marker was clicked (' + marker + ', ' + eventName); //+', '+mydump(model, 0)+', '+mydump(arguments)+')');
                    $scope.map.window.model = model;
                    $scope.map.window.model = model;
                    $scope.map.window.title = model.title;
                    $scope.map.window.show = true;
                }
            }
        };
        $scope.polygons = [
            {
                id: 1,
                path: [
                    {
                        latitude: -34.611345,
                        longitude: -55.450871
                    },
                    {
                        latitude: -34.594056,
                        longitude: -55.349145
                    },
                    {
                        latitude: -34.555720,
                        longitude: -55.327829
                    },
                    {
                        latitude: -34.475802,
                        longitude: -55.181059
                    },
                    {
                        latitude: -34.357981,
                        longitude: -54.867948
                    },
                    {
                        latitude: -34.038130,
                        longitude: -54.725110
                    },
                    {
                        latitude: -34.013307,
                        longitude: -54.594584
                    },
                    //norte

                    {
                        latitude: -33.890545,
                        longitude: -54.510057
                    },
                    //

                    {
                        latitude: -34.008967,
                        longitude: -54.466028
                    },
                    {
                        latitude: -34.803462,
                        longitude: -54.532865
                    },
                    {
                        latitude: -34.852526,
                        longitude: -54.630319
                    },
                    //sur
                    {
                        latitude: -34.977426,
                        longitude: -54.953992
                    },
                    //puntaBallena

                    {
                        latitude: -34.918299,
                        longitude: -55.048001
                    },
                    //

                    {
                        latitude: -34.907468,
                        longitude: -55.263573
                    },
                    {
                        latitude: -34.798692,
                        longitude: -55.393695
                    },
                    {
                        latitude: -34.726899,
                        longitude: -55.457069
                    },
                    {
                        latitude: -34.617944,
                        longitude: -55.488490
                    },
                    {
                        latitude: -34.596882,
                        longitude: -55.465612
                    },
                    {
                        latitude: -34.611345,
                        longitude: -55.450871
                    }
                ],
                stroke: {
                    color: '#b9e8a2',
                    weight: 1

                },
                fill: {
                    color: '#ddedbd',
                    opacity: 0.2
                },
                editable: false,
                draggable: false,
                geodesic: true,
                visible: true,
                clickable: false,
            }
        ];
        $scope.gridOptions = {
            enableSorting: true,
            enableCellEditOnFocus: true,
            enablePinning: true,
            showGridFooter: true,
            enableFiltering: true,
            paginationPageSizes: [25, 50, 75],
            paginationPageSize: 25,
            data: 'historial',
            columnDefs: [
                {field: 'idHistorialServicio', width: '10%', enableCellEdit: false, displayName: 'Nro. Historial', visible: false, sort: {direction: uiGridConstants.DESC, priority: 1}},
                {field: 'idServicio', width: '10%', enableCellEdit: false, displayName: 'Nro. Servicio', visible: false},
                {field: 'observaciones', width: '40%', enableCellEdit: false},
                {field: 'nombreEstado', width: '20%', enableCellEdit: false},
                {field: 'nombreTipo', width: '30%', enableCellEdit: false, displayName: 'Tipo Servicio'},
                {field: 'fechaModificacion', width: '30%', enableCellEdit: false}
            ],
            enableGridMenu: true,
            enableSelectAll: true,
            exporterCsvFilename: 'historialServicio.csv',
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
        uiGridValidateService.setValidator('validarNombre',
                function (argument) {
                    return function (newValue, oldValue, rowEntity, colDef) {
                        if (!newValue) {
                            return true;
                        } else {
                            return true;
                        }
                    };
                },
                function (argument) {
                    return false;
                }
        );
    }]);
