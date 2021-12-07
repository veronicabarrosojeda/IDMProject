AtuServicio.controller('ctrAltaSolicitud', function ($scope, $ionicPopup, $http, $q, $location, altaSolicitudService, listItemService, uiGmapGoogleMapApi, sessionService, uiGmapGmapUtil, msgService, $cordovaGeolocation) {

    $scope.titulo = "Solicitar servicio";
    cargarPath();
    getCategorias();
    getMunicipios();
    getTiposId();
    $scope.formServicio = {};
    $scope.nuevoServicio = {};
    $scope.nuevoServicio.ubicacionservicio = {};
    $scope.change = {};
    $scope.showAlert = showAlert;
    $scope.unirseReclamo = unirseReclamo;
    $scope.latitudGPS = {};
    $scope.longitudGPS = {};
    $scope.radio = {};

    $scope.inputSelector = 0;

    $scope.latitud;
    $scope.longitud;
    var coordsLat = [];
    var coordsLng = [];

    $scope.ocultarMapa = false;
    $scope.ubicacionManual = false;
    $scope.selectUbicacion = false;


    $scope.ocultarForm = true;
    $scope.cardText = "Seleccione la ubicación";
    $scope.botonText = "Siguiente";

    $scope.tipoUbicacion = 1;

    $scope.selectorUbicacion = selectorUbicacion;
    $scope.showForm = showForm;
    $scope.hideForm = hideForm;
    //////////////////////////////
    //CARGAR SERVICIOS EN EL MAPA//

    $scope.colSolicitudesServicio = [];
    $scope.getServicios = getServicios;
    $scope.colSolicitudes = [];


    init();

    function init()
    {
        var dataWhere = {};
        dataWhere.colEstados = ['1'];
        dataWhere.tipoDeServicios = [];
        dataWhere.colEmpresas = [];
        var fecha = new Date();
        var desde = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + ((fecha.getDate()) - 3);
        var hasta = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
        dataWhere.fechaDesde = desde;
        dataWhere.fechaHasta = hasta;

        getServicios(dataWhere);

    }



    function getServicios(dataWhere)
    {
        var options = {timeout: 10000, enableHighAccuracy: true};



        $scope.control = {};
        altaSolicitudService.getServiciosForStatus(dataWhere).then(function (data) {
            $scope.colSolicitudesServicio = data;

            var options = {timeout: 10000, enableHighAccuracy: true};

            $cordovaGeolocation.getCurrentPosition(options).then(function (position) {

                dataServicioToMarker($scope.colSolicitudesServicio, position.coords.latitude, position.coords.longitude);

            }, function (error) {

            });



        });


    }

    function dataServicioToMarker(colDataServicio, latitude, longitude)
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

            //$scope.latitudGPS = -34.915025;
            //$scope.longitudGPS = -54.942214;


            var lat = latitude - coords.latitude;
            var lng = longitude - coords.longitude;
            //var rad = $scope.radio / 100000000;
            var rad = 100 / 100000000;



            if ((Math.pow(lat, 2) + Math.pow(lng, 2)) < rad) {

                //$scope.showAlert("A Tu Servicio!", "DENTRO");

                $scope.colSolicitudes.push(marker);
            }


        }
    }
/////////////////////////////////



    function selectorUbicacion()
    {
        if ($scope.ubicacionManual == false) {
            $scope.ubicacionManual = true;
            $scope.ocultarMapa = true;
            $scope.tipoUbicacion = 2;
        } else {
            $scope.ubicacionManual = false;
            $scope.ocultarMapa = false;
            $scope.tipoUbicacion = 1;
        }

    }

    function unirseReclamo(idSolicitud)
    {
        //var idUsua = sessionService.get("idUsuario");
        if ((sessionService.get("idUsuario") == null) || (sessionService.get("idUsuario") == "") || (sessionService.get("idUsuario") == "undefined")) {
            var mensaje = "Para poder unirse a una solicitud debe iniciar sesión.";
            $scope.showAlert("A Tu Servicio!", mensaje);
        } else {

            var serSol = {};
            serSol.idServicio = idSolicitud;

            if (sessionService.get("nroIdentidad") != "undefined") {
                serSol.nroIdentidad = sessionService.get("nroIdentidad");
            }
            if (sessionService.get("tipoIdentidad") != "undefined") {
                serSol.tipoIdentidad = parseInt(sessionService.get("tipoIdentidad"));
            }

            serSol.telefono = sessionService.get("telefono");
            serSol.idUsuario = parseInt(sessionService.get("idUsuario"));
            serSol.nombreSolicitante = sessionService.get("nombre");

            if (sessionService.get("correo") != "undefined") {
                serSol.correoSolicitante = sessionService.get("correo");
            }

            serSol.token = sessionService.get("token");

            altaSolicitudService.unirseServicioMovil(serSol).then(function (data) {
                if (data.msg == "OK")
                {
                    var mensaje = "Se ha unido correctamente a la solicitud, puede seguir el estado de la misma desde la opción MIS SOLICITUDES en el menú. <br><br> Número de solicitud: " + idSolicitud;
                    $scope.showAlert("A Tu Servicio!", mensaje);
                } else if (data.msg == "EXISTE")
                {
                    var mensaje = "Ya se ha unido a esta solicitud, puede seguir el estado de la misma desde la opción MIS SOLICITUDES en el menú.";
                    $scope.showAlert("A Tu Servicio!", mensaje);
                } else {
                    var mensaje = "Ha ocurrido un error al procesar la solicitud, por favor inténtelo nuevamente.";
                    $scope.showAlert("A Tu Servicio!", mensaje);
                }
            });

            //$location.path("/login");
        }


    }


    function showForm()
    {

        if (($scope.latitud != null && $scope.longitud != null) && ($scope.ubicacionManual == false)) {
            $scope.ocultarMapa = true;
            $scope.ubicacionManual = false;
            $scope.ocultarForm = false;
            $scope.selectUbicacion = true;

            $scope.BotonSiguiente = true;
            $scope.BotonConfirmar = true;

            $scope.cardText = "Complete el formulario";
            $scope.botonText = "No se debe mostrar";
            $scope.cssbanner = "banner";
            $scope.ocultarBotonAtras = true;



        } else if (($scope.latitud == null && $scope.longitud == null) && ($scope.ubicacionManual == false)) {
            $scope.msgTitle = "Alerta";
            $scope.msgResult = "No ha seleccionado la ubicación.";
            $scope.showAlert($scope.msgTitle, $scope.msgResult);
        }

        if ((($scope.nuevoServicio.ubicacionservicio.nroManzana != null) && ($scope.nuevoServicio.idMunicipio != null) && ($scope.nuevoServicio.idBarrio != null) && ($scope.nuevoServicio.ubicacionservicio.calle != null) && ($scope.nuevoServicio.ubicacionservicio.entreCalles != null) && ($scope.nuevoServicio.ubicacionservicio.nroSolar != null) && ($scope.nuevoServicio.ubicacionservicio.nroPadron != null) && ($scope.nuevoServicio.ubicacionservicio.nroPuerta != null) && ($scope.nuevoServicio.ubicacionservicio.apto != null)) && ($scope.ubicacionManual == true)) {
            $scope.ocultarMapa = true;
            $scope.ubicacionManual = false;
            $scope.ocultarForm = false;
            $scope.selectUbicacion = true;

            $scope.BotonSiguiente = true;
            $scope.BotonConfirmar = true;

            $scope.cardText = "Complete el formulario";
            $scope.botonText = "No se debe mostrar";
            $scope.ocultarBotonAtras = true;
        } else if ((($scope.nuevoServicio.ubicacionservicio.nroManzana == null) || ($scope.nuevoServicio.idMunicipio == null) || ($scope.nuevoServicio.idBarrio == null) || ($scope.nuevoServicio.ubicacionservicio.calle == null) || ($scope.nuevoServicio.ubicacionservicio.entreCalles == null) || ($scope.nuevoServicio.ubicacionservicio.nroSolar == null) || ($scope.nuevoServicio.ubicacionservicio.nroPadron == null) || ($scope.nuevoServicio.ubicacionservicio.nroPuerta == null) || ($scope.nuevoServicio.ubicacionservicio.apto == null)) && ($scope.ubicacionManual == true)) {
            $scope.msgTitle = "Alerta";
            $scope.msgResult = "No ha completado la ubicación correctamete.";
            $scope.showAlert($scope.msgTitle, $scope.msgResult);
        }

    }


    function hideForm()
    {
        $scope.cssbanner = "bannerSig";
        $scope.ocultarMapa = false;
        $scope.ocultarForm = true;
        $scope.selectUbicacion = false;
        $scope.cardText = "Seleccione la ubicación";
        $scope.ocultarBotonAtras = false;
        $scope.BotonSiguiente = false;
        $scope.BotonConfirmar = false;
        $scope.botonText = "Siguiente";
    }

    $scope.mostrarU = function () {
        $scope.prueba = $scope.latitud + " " + $scope.longitud;
    };

    $scope.altaServicio = function () {

        var ser = {};
        angular.copy($scope.nuevoServicio, ser);

        ser.tipoOrigen = 2;
        ser.idUsuario = sessionService.get('idUsuario');


        if ($scope.tipoUbicacion == 1) {
            ser.ubicacionservicio.latitud = $scope.latitud;
            ser.ubicacionservicio.longitud = $scope.longitud;
            ser.ubicacionservicio.coordenadas = true;
            ser.ubicacionservicio.ubicacionManual = false;
        }
        if ($scope.tipoUbicacion == 2) {
            ser.ubicacionservicio.coordenadas = false;
            ser.ubicacionservicio.ubicacionManual = true;
        }

        ser.nroIdentidad = $scope.nuevoServicio.nroIdentidad;
        ser.tipoIdentidad = parseInt($scope.nuevoServicio.tipoIdentidad);

        altaSolicitudService.altaServicio(ser).then(function (data) {

            $scope.msgResult = data.msg;

            if ($scope.msgResult.match(/Solicitud de servicio.*/)) {
                showAlert("A Tu Servicio", $scope.msgResult);

                //LIMPIAR CAMPOS
                $scope.latitud = null;
                $scope.longitud = null;
                $scope.formServicio = {};
                $scope.nuevoServicio = {};
                $scope.nuevoServicio.ubicacionservicio = {};
                $scope.pin.coords.latitude = null;
                $scope.pin.coords.longitude = null;

                //RECARGAR RECLAMOS EN MAPA
                var dataWhere = {};
                dataWhere.colEstados = ['1', '3', '5'];
                dataWhere.tipoDeServicios = [];
                dataWhere.colEmpresas = [];
                var fecha = new Date();
                var desde = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + ((fecha.getDate()) - 3);
                var hasta = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
                dataWhere.fechaDesde = desde;
                dataWhere.fechaHasta = hasta;
                getServicios(dataWhere);

                //VOLER A PAGINA INICIAL
                hideForm();

            } else {
                $scope.msgResult = msgService.darMensaje(data.msg);
                showAlert("A Tu Servicio", $scope.msgResult);
            }
        });

    };

    $scope.altaServicioLogueado = function () {

        var ser = {};
        angular.copy($scope.nuevoServicio, ser);

        ser.tipoOrigen = 2;
        ser.idUsuario = sessionService.get('idUsuario');


        if ($scope.tipoUbicacion == 1) {
            ser.ubicacionservicio.latitud = $scope.latitud;
            ser.ubicacionservicio.longitud = $scope.longitud;
            ser.ubicacionservicio.coordenadas = true;
            ser.ubicacionservicio.ubicacionManual = false;
        }
        if ($scope.tipoUbicacion == 2) {
            ser.ubicacionservicio.coordenadas = false;
            ser.ubicacionservicio.ubicacionManual = true;
        }

        ser.idUsuario = parseInt(sessionService.get("idUsuario"));
        ser.nombre = sessionService.get("nombre");

        if (sessionService.get("correo") != "undefined") {
            ser.correo = sessionService.get("correo");
        }
        ser.telefono = sessionService.get("telefono");
        if (sessionService.get("nroIdentidad") == "undefined" || sessionService.get("tipoIdentidad") == "undefined") {
            showAlert("Debe ingresar su número y tipo de identidad en Perfil");
        } else
        {
            ser.nroIdentidad = sessionService.get("nroIdentidad");
            ser.tipoIdentidad = parseInt(sessionService.get("tipoIdentidad"));
            altaSolicitudService.altaServicio(ser).then(function (data) {

                $scope.msgResult = data.msg;

                if ($scope.msgResult.match(/Solicitud de servicio.*/)) {
                    showAlert("A Tu Servicio", $scope.msgResult);

                    //LIMPIAR CAMPOS
                    $scope.latitud = null;
                    $scope.longitud = null;
                    $scope.formServicio = {};
                    $scope.nuevoServicio = {};
                    $scope.nuevoServicio.ubicacionservicio = {};
                    $scope.pin.coords.latitude = null;
                    $scope.pin.coords.longitude = null;

                    //RECARGAR RECLAMOS EN MAPA
                    var dataWhere = {};
                    dataWhere.colEstados = ['1'];
                    dataWhere.tipoDeServicios = [];
                    dataWhere.colEmpresas = [];
                    var fecha = new Date();
                    var desde = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + ((fecha.getDate()) - 3);
                    var hasta = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
                    dataWhere.fechaDesde = desde;
                    dataWhere.fechaHasta = hasta;
                    getServicios(dataWhere);

                    //VOLER A PAGINA INICIAL
                    hideForm();

                } else {
                    $scope.msgResult = msgService.darMensaje(data.msg);
                    showAlert("A Tu Servicio", $scope.msgResult);
                }
            });
        }
    };



    function getCategorias()
    {
        listItemService.getListItemCategorias().then(function (data) {
            $scope.categorias = data;
        });
    }

    function getMunicipios()
    {
        listItemService.getListItemMunicipios().then(function (data) {
            $scope.municipios = data;
        });
    }

    function getTiposId() {
        var i = 1;
        listItemService.getTiposIdentidad(i).then(function (data) {
            $scope.tiposIdentidad = data;
        });
    }

    $scope.getBarrios = function (id)
    {
        listItemService.getListItemBarrios(id).then(function (data) {
            $scope.barrios = data;
        });
    };


    function cargarPath()
    {


        coordsLat = [-34.611345, -34.594056, -34.555720, -34.475802, -34.357981, -34.038130,
            -34.013307, -33.890545, -34.008967, -34.803462, -34.852526, -34.977426, -34.918299,
            -34.907468, -34.798692, -34.726899, -34.617944, -34.596882, -34.611345];
        coordsLng = [-55.450871, -55.349145, -55.327829, -55.181059, -54.867948, -54.725110,
            -54.594584, -54.510057, -54.466028, -54.532865, -54.630319, -54.953992, -55.048001,
            -55.263573, -55.393695, -55.457069, -55.488490, -55.465612, -55.450871];

    }

    $scope.map = {
        center: {latitude: -34, longitude: -53},
        zoom: 17,
        markersEvents: {
            click: function (marker, eventName, model, arguments) {
                console.log('Marker was clicked (' + marker + ', ' + eventName); //+', '+mydump(model, 0)+', '+mydump(arguments)+')');
                $scope.map.window.model = model;
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
                    //alert("Ubicacion fuera de rango");
                    $scope.msgTitle = "Alerta";
                    $scope.msgResult = "Debe seleccionar un punto terrestre dentro de Maldonado.";
                    $scope.showAlert($scope.msgTitle, $scope.msgResult);
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
                weight: 0.1

            },
            fill: {
                color: '#ddedbd',
                opacity: 0.1
            },
            editable: false,
            draggable: false,
            geodesic: true,
            visible: true,
            clickable: false
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

        $scope.map.center = {
            latitude: position.coords.latitude,
            longitude: position.coords.longitude
        };

        $scope.latitudGPS = position.coords.latitude;
        $scope.longitudGPS = position.coords.longitude;
        $scope.radio = 100;
        $scope.map.circle = {
            id: 3,
            center: {
                latitude: $scope.latitudGPS,
                longitude: $scope.longitudGPS
            },
            radius: $scope.radio,
            stroke: {
                color: '#55b742',
                weight: 1,
                opacity: 1
            },
            fill: {
                color: '#55b742',
                opacity: 0.5
            },
            geodesic: false, // optional: defaults to false
            draggable: false, // optional: defaults to false
            clickable: false, // optional: defaults to true
            editable: true, // optional: defaults to false
            visible: true // optional: defaults to true
        };


        $scope.$apply();
    };
    function onError(error) {
        console.log('code: ' + error.code + '\n' + 'message: ' + error.message + '\n');
    }
    ;
//PRUEBA GEOLOCALIZACION
    var options = {timeout: 10000, enableHighAccuracy: true};
    $cordovaGeolocation.getCurrentPosition(options).then(function (position) {

        onSuccess(position);

    }, function (error) {
        console.log("Could not get location");
    });

    //fin prueba
//    $cordovaGeolocation.getCurrentPosition(onSuccess, onError);

//BLOCK FOTO 
//    $scope.enviarFoto = function() {
//
//        var foto = $scope.imagen+"%%"+"numeroSolicitud";
//        pruebaFotoService.enviarFoto(foto).then(function (data) {
//            $scope.msgResult = data.msg;
//            $scope.msgResult = $scope.msgResult.replace('/%', '/');
//            $scope.showAlert($scope.msgResult);
//        });
//    };

    var pictureSource; // picture source
    var destinationType; // sets the format of returned value

    document.addEventListener("deviceready", onDeviceReady, false);
    //
    function onDeviceReady() {
        pictureSource = navigator.camera.PictureSourceType;
        destinationType = navigator.camera.DestinationType;
    }
    ;
    //
    function onPhotoDataSuccess(imageData) {
        var smallImage = document.getElementById('smallImage');
        smallImage.style.display = 'block';
        $scope.nuevoServicio.imagen = imageData;
        smallImage.src = "data:image/jpeg;base64," + imageData;
    }
    ;

    $scope.tomarFoto = function () {
        navigator.camera.getPicture(onPhotoDataSuccess, onFail, {quality: 50,
            destinationType: destinationType.DATA_URL});
    };

    $scope.tomarFotoEdit = function () {
        navigator.camera.getPicture(onPhotoDataSuccess, onFail, {quality: 20, allowEdit: true,
            destinationType: destinationType.DATA_URL});
    };

    $scope.obtenerFoto = function (tipo) {
        navigator.camera.getPicture(onPhotoDataSuccess, onFail, {quality: 50,
            targetWidth: 50,
            targetHeight: 50,
            destinationType: destinationType.DATA_URL,
            sourceType: tipo});
    };


    function onFail(message) {
        alert('Fallo por: ' + message);
    }
    ;




    function showAlert(msgTitle, msgResult) {
        var alertPopup = $ionicPopup.alert({
            title: msgTitle,
            template: msgResult
        });
    }
    ;
}
);


