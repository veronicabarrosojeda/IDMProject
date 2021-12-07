AtuServicio.controller('ctrMisSolicitudes', function ($scope, sessionService, seguimientoService, configConexServer) {

    $scope.verTodas = true;
    $scope.verDatos = false;
    $scope.verDetallesSolicitud = verDetallesSolicitud;
    $scope.volverForm = volverForm;

    getSolicitudesUsuario();
    function getSolicitudesUsuario() {
        var id = sessionService.get('idUsuario');
        var token = sessionService.get('token');
        var dataUser = {idUsuario: id, token: token};
        seguimientoService.getServiciosUser(dataUser).then(function (data) {
            $scope.solicitudes = data;
        });
    }
    function verDetallesSolicitud(solicitud) {
        $scope.soli = solicitud;
        if ($scope.soli.rutaImagen == null || $scope.soli.rutaImagen == "")
        {
            $scope.soli.rutaImagen = configConexServer.getImgXDefecto();
        } else
        {
            $scope.soli.rutaImagen = configConexServer.getStorage() + $scope.soli.rutaImagen;
        }
        $scope.verDatos = true;
        $scope.verTodas = false;
    }

    function volverForm() {
        $scope.soli = {};
        $scope.verDatos = false;
        $scope.verTodas = true;
    }
});

