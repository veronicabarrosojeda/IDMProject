PanelWeb.factory('msgServices', ['$http', function ($http) {
        return{
            darMensaje: darMensaje
        };

        function darMensaje(dtoMsg) {

            var prefijo = "";
            if (dtoMsg.tipo == "success")
            {
                prefijo = "¡OK!";
            } else if (dtoMsg.tipo == "danger")
            {
                prefijo = "¡Error!";
            } else if (dtoMsg.tipo == "warning")
            {
                prefijo = "¡Advertencia!";
            }

            var msg = "";
            var arrMsg = [];
            arrMsg = dtoMsg.msg.split("%");

            if (arrMsg.length > 1)
            {
                dtoMsg.msg = "";
                for (var i = 0; i < arrMsg.length; i++)
                {
                    if (arrMsg[i] !== "")
                    {
                        dtoMsg.msg += "<br\>" + "-" + arrMsg[i];
                    }
                }
            }

            msg = "<div class='msgAlter alert alert-" + dtoMsg.tipo + "' id='Mensaje' role='alert'> <button type='button' class='close' data-dismiss='alert'>&times;</button><strong>" + prefijo + "</strong> " + dtoMsg.msg + "</div>"

            setTimeout(function () {
                $("#Mensaje").fadeOut(3000);
                $("#Mensaje").hide();
            }, 6000);

            $('#msgPanel').html(msg);
        }
        ;

    }]);
