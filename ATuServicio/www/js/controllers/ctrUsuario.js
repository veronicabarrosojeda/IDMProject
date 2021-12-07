AtuServicio.controller('ctrUsuario', function ($scope, $ionicPopup, sessionService, usuarioService,listItemService) {

    $scope.usuario = {idUsuario: '', nombre: '', apellido: '', nickname: '', password: '', token: '', correo: '', telefono: '', nroIdentidad: '', tipoIdentidad: '', isLogged: '', rename: ''};
    $scope.usuarioClave = {idUsuario: '', passwordActual: '', passwordNueva: '', passwordNuevaReiterada: ''};
    cargarDatos();
    getTiposIdentidad();


    $scope.ModificarPerfil = ModificarPerfil;
    $scope.ModificarClave = ModificarClave;
    $scope.showAlert = showAlert;
    $scope.resetClave = resetClave;
    $scope.resetForm = resetForm;

    function cargarDatos() {
        $scope.usuario = {nickname: sessionService.get('nickname')};
        if (sessionService.get('nombre') === "undefined")
            $scope.usuario.nombre = "";
        else
            $scope.usuario.nombre = sessionService.get('nombre');

        if (sessionService.get('apellido') === "undefined")
            $scope.usuario.apellido = "";
        else
            $scope.usuario.apellido = sessionService.get('apellido');

        if (sessionService.get('correo') === "undefined")
            $scope.usuario.correo = "";
        else
            $scope.usuario.correo = sessionService.get('correo');
        if (sessionService.get('telefono') === "undefined")
            $scope.usuario.telefono = "";
        else
            $scope.usuario.telefono = parseInt(sessionService.get('telefono'));
        if (sessionService.get('nroIdentidad') === "undefined")
            $scope.usuario.nroIdentidad = "";
        else
            $scope.usuario.nroIdentidad = parseInt(sessionService.get('nroIdentidad'));
        if (sessionService.get('tipoIdentidad') === "undefined")
            $scope.usuario.tipoIdentidad = "";
        else
            $scope.usuario.tipoIdentidad = parseInt(sessionService.get('tipoIdentidad'));
    }
    function resetClave() {
        $scope.usuarioClave = {idUsuario: '', passwordActual: '', passwordNueva: '', passwordNuevaReiterada: ''};
    }
    function resetForm() {
        $scope.usuario = {idUsuario: '', nombre: '', apellido: '', nickname: '', password: '', token: '', correo: '', telefono: '', nroIdentidad: '', tipoIdentidad: '', isLogged: '', rename: ''};
    }
    function ModificarClave()
    {
        if ($scope.usuarioClave.passwordActual === "") {
            $scope.showAlert("Clave actual incompleta");
        } else {
            if ($scope.usuarioClave.passwordNueva === "") {
                $scope.showAlert("Clave nueva incompleta");
            } else {
                if ($scope.usuarioClave.passwordNuevaReiterada === "") {
                    $scope.showAlert("Repetir clave incompleto");
                } else {
                    if ($scope.usuarioClave.passwordNueva !== $scope.usuarioClave.passwordNuevaReiterada) {
                        $scope.showAlert("Las claves no coinciden");
                    } else {
                        $scope.usuarioClave.idUsuario = sessionService.get('idUsuario');
                        var usuarioClave = angular.copy($scope.usuarioClave);
                        usuarioService.modificarClave(usuarioClave).then(function (data) {
                            if (data.tipo === 'success')
                            {
                                $scope.resetClave();
                            }
                            $scope.msgResult = data.msg;
                            $scope.msgResult = $scope.msgResult.replace('/%', '/');
                            $scope.showAlert($scope.msgResult);
                        });
                    }
                }
            }
        }
    }
    ;
    function ModificarPerfil()
    {
        if ($scope.usuario.nombre === "") {
            $scope.showAlert("Nombre incompleto");
        } else {
            if ($scope.usuario.apellido === "") {
                $scope.showAlert("Apellido incompleto");
            } else {
                if ($scope.usuario.correo === "") {
                    $scope.showAlert("Correo incompleto");
                } else {
                    if ($scope.usuario.telefono === "") {
                        $scope.showAlert("Telefono incompleto");
                    } else {
                        if ($scope.usuario.nroIdentidad === "") {
                            $scope.showAlert("N° Identidad incompleto");
                        } else {
                            if ($scope.usuario.tipoIdentidad === "") {
                                $scope.showAlert("Tipo de Identidad incompleto");
                            } else {
                                if ($scope.usuario.nickname === "") {
                                    $scope.showAlert("Usuario incompleto");
                                } else {
                                    $scope.usuario.idUsuario = sessionService.get('idUsuario');
                                    var usuario = angular.copy($scope.usuario);
                                    usuarioService.modificarPerfil(usuario).then(function (data) {
                                        $scope.msgResult = data.msg;
                                        $scope.msgResult = $scope.msgResult.replace('/%', '/');
                                        $scope.showAlert($scope.msgResult);
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    ;
    function showAlert(msgResult) {
        var alertPopup = $ionicPopup.alert({
            title: 'Perfil',
            template: msgResult
        });
    }
    ;
    function getTiposIdentidad() {
        var i = 1;
        listItemService.getTiposIdentidad(i).then(function (data) {
            $scope.tiposIdentidad = data;
        });
    }
}
);


