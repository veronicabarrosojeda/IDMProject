//angular.module('starter.controllers', [])

AtuServicio.controller('ctrInicio', function ($scope, $location, $ionicPopup, sessionService) {

    if (sessionService.get('idUsuario') != null || sessionService.get('idUsuario') != "")
    {
        $location.path('/solicitud');
    }
    $scope.titulo = "Bienvenido";
    $scope.altaServicio = altaServicio;
    $scope.showAlert = showAlert;

//    $scope.showAlert("ADVERTENCIA: Para el correcto funcionamiento active su Ubicacion");

    
    function showAlert(msgTitle, msgResult) {
        var alertPopup = $ionicPopup.alert({
            title: msgTitle,
            template: msgResult
        });
    }
    ;
    function altaServicio()
    {
        $location.path('/altaSolicitud');
    }


});

