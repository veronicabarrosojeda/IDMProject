AtuServicio.controller('ctrSeguimiento', function ($scope, $ionicPopup, $location, seguimientoService, sessionService,configConexServer) {

//    if (sessionService.get('idUsuario') != null || sessionService.get('idUsuario') != "")
//    {
//        $location.path('/solicitud');
//    }
    $scope.solicitud = {nroSolicitud: ''};
    $scope.showAlert = showAlert;
    $scope.buscar = buscar;
    $scope.volverForm = volverForm;
    $scope.verForm = true;
    $scope.historySolicitud = {idServicio: '', descripcionServicio: '', descripcionTipoServicio: '',
        fechaIngreso: '', fechaModificacion: '', idArea: '', nombreArea: '', estado: '', nombre: '',
        idTipoServicio: '', idUbicacionServicio: '', idUsuarioFuncionario: '',
        nombreTipoServicio: '', tipoOrigen: ''};

    function buscar() {
        seguimientoService.getServicio($scope.solicitud.nroSolicitud).then(function (data) {
            if (data) {
                $scope.historySolicitud = data;
                if($scope.historySolicitud.rutaImagen == null || $scope.historySolicitud.rutaImagen == "")
                {
                    $scope.historySolicitud.rutaImagen = configConexServer.getImgXDefecto();
                }else
                {
                    $scope.historySolicitud.rutaImagen = configConexServer.getStorage()+$scope.historySolicitud.rutaImagen;
                }
                $scope.solicitud = {};
                $scope.verForm = false;
                $scope.verDatos = true;
//                var imagen = data.rutaImagen;
//              var smallImage = document.getElementById('smallImage');
//                smallImage.style.display = 'block';
//                $scope.imagen = imagen;
//                smallImage.src = "data:image/jpeg;base64," + imagen;

            } else {
                $scope.msgResult = "Verifique el numero de solicitud";
                $scope.showAlert($scope.msgResult);
            }
        });
    }
    function volverForm() {
        $scope.historySolicitud = {};
        $scope.verForm = true;
        $scope.verDatos = false;
    }

    function showAlert(msgResult) {
        var alertPopup = $ionicPopup.alert({
            title: 'Seguimiento',
            template: msgResult
        });

    }
    ;
});

