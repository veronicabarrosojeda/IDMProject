AtuServicio.factory('listItemService', ['$http', '$q', 'configConexServer', function ($http, $q, configConexServer) {
        return{
            getListItemRoles: getListItemRoles,
            getListItemSubAreas: getListItemSubAreas,
            getListItemMunicipios: getListItemMunicipios,
            getListItemBarrios: getListItemBarrios,
            getTiposIdentidad: getTiposIdentidad,
            getListItemCategorias: getListItemCategorias,
            getTipoServicio: getTipoServicio,
            getEmpresas: getEmpresas
        };
        function getListItemRoles() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsListItem/getRoles')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }

        function getListItemMunicipios() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsListItem/getMunicipios')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }

        function getListItemSubAreas() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsListItem/getSubAreas')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }


        function getListItemBarrios(id) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsListItem/getBarriosPorMunicipio', id)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }


        function getTiposIdentidad(id) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsListItem/getDetTipoById', id)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }

        function getListItemCategorias() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsListItem/getCategorias')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {


                    });

            return promise;
        }

        function getEmpresas() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsListItem/getEmpresas')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });

            return promise;
        }

        function getTipoServicio() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post(configConexServer.getServidor() + 'wsListItem/getTipoServicio')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {

                    });
            return promise;
        }
    }]);
