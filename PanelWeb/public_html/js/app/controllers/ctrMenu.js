PanelWeb.controller("ctrMenu", ['$scope', '$http', 'serviceConfig', '$window', '$location', 'md5', 'loginService', 'sessionService', function ($scope, $http, serviceConfig, $window, $location, md5, loginService, sessionService) {

        $scope.colOpciones;
        $scope.title = "Panel de Administración";
        $scope.userName = sessionService.get('nickname');
        serviceConfig.getRutaServer().then(function (data) {
            $window.ip = data;
        });

        $scope.isLogged = function () {
            loginService.isLogged().then(function (data) {
                if (data == false)
                {
                    $window.location.href = "/PanelWeb/login.html";
                } else
                {
                    $scope.Go('inicio');
                }
            });
        };
        getOpcionMenu();
        $scope.logOut = function () {
            loginService.logOut();
        };

        $scope.getClassSubMenu = function (padre)
        {
            for (var i = 0; i < $scope.colOpciones.length; i++)
            {
                if ($scope.colOpciones[i].arbol == padre && $scope.colOpciones[i].arbol.length >= 5)
                {
                    return 'dropdown-submenu';
                }
            }
            return 'dropdown-menu';
        };

        $scope.Go = function (pantalla)
        {
            $location.path(pantalla);

        };

        $scope.Go('inicio');

        function getOpcionMenu()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                $("#menuOperaciones li").each(function () {

                    var res = false;
                    for (var i = 0; i < arr.length; i++)
                    {
                        if ($(this).attr('id') == arr[i].idPermiso)
                        {
                            res = true;
                        }
                    }

                    if (res == false)
                    {
                        document.getElementById($(this).attr('id')).children[0].style.display = "none";
                    }
                });
            }
        }
        ;

    }]);
