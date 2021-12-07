AtuServicio.factory('configConexServer', [function (){
    return{
        getIP:getIP,
        getServidor:getServidor,
        getStorage:getStorage,
        getImgXDefecto:getImgXDefecto
    };
    function getIP(){
        return "192.168.2.102";
    }
    function getServidor(){
        return "http://"+getIP()+":8080/ServerApp/ws/";
    }
    function getStorage(){
        return "http://"+getIP()+":8080";
    }
    function getImgXDefecto(){
        return "http://"+getIP()+":8080/ServerApp/STORAGE/nodis.jpg";
    }
}]);

