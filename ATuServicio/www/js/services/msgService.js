AtuServicio.factory('msgService', ['$http',function ($http) {

        return{
            darMensaje: function (dtoMsg) {

//            var prefijo = "";
//            if (dtoMsg.tipo == "success")
//            {
//                prefijo = "¡OK!";
//            } else if (dtoMsg.tipo == "danger")
//            {
//                prefijo = "¡Error!";
//            } else if (dtoMsg.tipo =="warning")
//            {
//                prefijo = "¡Advertencia!";
//            }
                
                var arrMsg = [];
                arrMsg = dtoMsg.split('%');
                var mensaje="";
                if (arrMsg.length > 1)
                {
                    for (var i = 0; i < arrMsg.length; i++)
                    {
                        if (arrMsg[i] !== "")
                        {
                            mensaje+=arrMsg[i] + "<br\>";
                        }
                    }
                }
                return mensaje;
            }

        }
        ;
    }]);