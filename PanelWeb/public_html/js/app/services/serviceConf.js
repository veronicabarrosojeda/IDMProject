'use strict';

PanelWeb.factory('serviceConfig', ['$http', '$q', function ($http, $q) {
        return{
            getRutaServer: getRutaServer
        };

        function getRutaServer()
        {
            var defered = $q.defer();
            var promise = defered.promise;
            var allText = null;
            var file = "config.txt";
            var rawFile = new XMLHttpRequest();
            if (rawFile != null)
            {
                rawFile.open("GET", file, false);
                rawFile.onreadystatechange = function ()
                {
                    if (rawFile.readyState === 4)
                    {
                        if (rawFile.status === 200 || rawFile.status == 0)
                        {
                            allText = rawFile.responseText;
                            var arr = allText.split("=");
                            var ip = arr[1];
                            defered.resolve(ip);
                        }
                    }
                }
                rawFile.send(null);
            }
            else
            {
                defered.resolve("");
            }
            return promise;
        }
    }]);