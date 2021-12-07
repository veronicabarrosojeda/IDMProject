//angular.module('starter.controllers', [])

AtuServicio.controller('ctrRegistro', function ($scope, $ionicPopup, $location, registroService, msgService,sessionService) {
//    if (sessionService.get('idUsuario') != null || sessionService.get('idUsuario') != "")
//    {
//        $location.path('/solicitud');
//    }
    $scope.usuario = {};
    $scope.altaUsuario = altaUsuario;
    $scope.showAlert = showAlert;

    function altaUsuario()
    {
        var us = {};
        $scope.usuario.idRol = 3;
        angular.copy($scope.usuario, us);
        registroService.altaUsuario(us).then(function (data) {

            $scope.msgResult = data.msg;

            if ($scope.msgResult == "¡Usuario registrado correctamente!") {
                $scope.usuario = {};
                $scope.showAlert($scope.msgResult);
                $location.path('/tab/login');
            } else {
                $scope.msgResult = msgService.darMensaje(data.msg);
                $scope.showAlert($scope.msgResult);
            }



        });
    }

    function showAlert(msgResult) {
        var alertPopup = $ionicPopup.alert({
            title: 'Registro',
            template: msgResult
        });

    }
    ;
});

