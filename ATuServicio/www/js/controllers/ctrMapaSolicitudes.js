
AtuServicio.controller('ctrMapaSolicitudes', function ($scope, listItemService, servicioService) {
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
    $scope.diasPendiente;
    $scope.getRutaImagenByClave = getRutaImagenByClave;
    init();


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
        $scope.$apply();
    };
    function onError(error) {
        console.log('code: ' + error.code + '\n' + 'message: ' + error.message + '\n');
    }
    navigator.geolocation.getCurrentPosition(onSuccess, onError);


    function init()
    {
        $scope.qtSolicitantes = 0;
        $scope.diasPendiente = 0;
        var dataWhere = {};
        dataWhere.colEstados = ['1', '3', '5'];
        dataWhere.tipoDeServicios = [];
        dataWhere.colEmpresas = [];
        getServicios(dataWhere);
        getEstadosServicio();
        getTipoServicio();
        getEmpresas();
    }

    function getServicios(dataWhere)
    {

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
        } else
        {
            return "images/imgApp/iconMap/pinRojo.png";
        }
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
            marker.coords = coords;
            marker.options = {icon: colDataServicio[i].imgEstado};
            $scope.colSolicitudes.push(marker);
        }
    }
    ;
//
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
        dataWhere.colEstados = [];
        dataWhere.qtSolicitantes = $scope.qtSolicitantes;
        dataWhere.diasPendiente = $scope.diasPendiente;
        dataWhere.tipoDeServicios = ListItemSelectToListValue();
        dataWhere.colEmpresas = ListItemEmpresaSelectToListValue();
        $('#filtros input:checked').each(function () {
            dataWhere.colEstados.push($(this).attr('id'));
        });

        getServicios(dataWhere);
    }
//
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


});