PanelWeb.controller("ctrAltaServicio", ['$scope', '$http', '$q', '$location', 'altaSolicitudService', 'listItemService', 'msgServices', 'uiGridValidateService', 'uiGmapGoogleMapApi', 'sessionService', 'uiGmapGmapUtil', 'usuarioService', function ($scope, $http, $q, $location, altaSolicitudService, listItemService, msgServices, uiGridValidateService, uiGmapGoogleMapApi, sessionService, uiGmapGmapUtil, usuarioService) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 17)
                        rest = true;
                }

                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }

        verificarPermiso();
        $scope.titulo = "Solicitar servicio";
        $scope.formServicio = {};
        $scope.nuevoServicio = {};
        getMunicipios();
        getCategorias();
        getTiposId();
        cargarPath();
        $scope.change = {};
        $scope.latitud = 0;
        $scope.longitud = 0;
        var coordsLat = [];
        var coordsLng = [];
        $scope.latitudGPS = {};
        $scope.longitudGPS = {};
        $scope.radio = {};

        function cargarPath()
        {
            coordsLat = [-34.611345, -34.594056, -34.555720, -34.475802, -34.357981, -34.038130,
                -34.013307, -33.890545, -34.008967, -34.803462, -34.852526, -34.977426, -34.918299,
                -34.907468, -34.798692, -34.726899, -34.617944, -34.596882, -34.611345];
            coordsLng = [-55.450871, -55.349145, -55.327829, -55.181059, -54.867948, -54.725110,
                -54.594584, -54.510057, -54.466028, -54.532865, -54.630319, -54.953992, -55.048001,
                -55.263573, -55.393695, -55.457069, -55.488490, -55.465612, -55.450871];
        }
        function getCategorias()
        {
            listItemService.getListItemCategorias().then(function (data) {
                $scope.categorias = data;
            });
        }

        function getTiposId() {
            var i = 1;
            listItemService.getDetTipoById(i).then(function (data) {
                $scope.tiposIdentidad = data;
            });
        }
        ;
        function getMunicipios()
        {
            listItemService.getListItemMunicipios().then(function (data) {
                $scope.municipios = data;
            });
        }

        $scope.getBarrios = function (id)
        {
            listItemService.getListItemBarrios(id).then(function (data) {
                $scope.barrios = data;
            });
        };
        $scope.altaServicio = function () {
            var ser = {};
            angular.copy($scope.nuevoServicio, ser);
            ser.tipoOrigen = 1;
            ser.idUsuario = sessionService.get('idUsuario');
            if ($scope.environment != 1 && $scope.environment != 2) {
                ser.idBarrio = -1;
                ser.idMunicipio = -1;
            }

            if ($scope.change == true) {
                ser.ubicacionservicio.coordenadas = true;
                ser.ubicacionservicio.latitud = $scope.latitud;
                ser.ubicacionservicio.longitud = $scope.longitud;
                ser.ubicacionservicio.ubicacionManual = false;
            }
            if ($scope.change == false) {
                ser.ubicacionservicio.coordenadas = false;
                ser.ubicacionservicio.ubicacionManual = true;
            }

            var token = sessionService.get("token");
            ser.token = token;

            altaSolicitudService.altaServicio(ser).then(function (data) {
                msgServices.darMensaje(data);
                if (data.tipo == "success")
                {
                    $scope.reset();
                }
            });
        };

        $scope.map = {
            zoom: 17,
            markersEvents: {
                click: function (marker, eventName, model, arguments) {
                    console.log('Marker was clicked (' + marker + ', ' + eventName);
                    $scope.map.window.model = model;
                    $scope.map.window.title = model.title;
                    $scope.map.window.show = true;
                }
            },
            events: {
                click: function (mapModel, eventName, originalEventArgs) {
                    cargarPath();
                    var c = 0;
                    var e = originalEventArgs[0];
                    var nvert = 19;
                    var vertx = coordsLng; //longitud
                    var verty = coordsLat;
                    var testx = e.latLng.lng();
                    var testy = e.latLng.lat();
                    var i, j;
                    for (i = 0, j = nvert - 1; i < nvert; j = i++) {
                        if (((verty[i] > testy) !== (verty[j] > testy)) &&
                                (testx < (vertx[j] - vertx[i]) * (testy - verty[i]) / (verty[j] - verty[i]) + vertx[i]))
                            c = !c;
                    }

                    if (c == true || c == 1) {
                        $scope.latitud = e.latLng.lat();
                        $scope.longitud = e.latLng.lng();
                        $scope.pin = {};
                        $scope.pin.coords = {};
                        $scope.pin.id = 1;
                        $scope.pin.key = 1;
                        $scope.pin.coords.latitude = $scope.latitud;
                        $scope.pin.coords.longitude = $scope.longitud;
                        $scope.$apply();
                    } else {
                        sweetAlert("Ubicacion fuera de rango", "error");
                    }
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

        $scope.onMarkerClicked = function (m) {
            //this.windowOptions = !this.windowOptions;
            console.log('Marker was clicked');
            console.log(m);
        };
        $scope.closeClick = function () {
            this.window = false;
        };
        var onSuccess = function (position) {
            $scope.latitudGPS = position.coords.latitude;
            $scope.longitudGPS = position.coords.longitude;
            $scope.map.center = {
                latitude: position.coords.latitude,
                longitude: position.coords.longitude
            };
            $scope.$apply();
        };
        function onError(error) {
            console.log('code: ' + error.code + '\n' + 'message: ' + error.message + '\n');
        }
        navigator.geolocation.getCurrentPosition(onSuccess, onError);
        $scope.getVal = function () {
            $scope.nuevoServicio.ubicacionservicio = {};
            $scope.nuevoServicio.idBarrio = 0;
            $scope.nuevoServicio.idMunicipio = 0;
            $scope.pin = {};
            if ($scope.environment == 1) {
                $scope.manual = false;
                $scope.verMapa = true;
                $scope.change = true;
            }

            if ($scope.environment == 2) {
                $scope.verMapa = false;
                $scope.manual = true;
                $scope.change = false;
            }
        };

        $scope.reset = function () {
            $scope.nuevoServicio.ubicacionservicio = {};
            $scope.nuevoServicio = {};
            $scope.latitud = {};
            $scope.longitud = {};
            $scope.pin = {};
            $scope.$apply();
            $("#formServicio div").each(function () {
                $(this).removeClass('has-error');
            });
        };

    }]);

