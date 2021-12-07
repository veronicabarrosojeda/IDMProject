PanelWeb.factory('genericServices', ['$http', function ($http) {
        return{
            objectToForm: objectToForm,
            dataTableToColObject: dataTableToColObject,
            getColsByTable: getColsByTable,
            setErrorsInForm: setErrorsInForm
        };

        function getColsByTable(colColumns, colProperties)
        {
            var colCol = [];
            for (var i = 0; i < colColumns.length; i++)
            {
                var item = {};
                Object.getOwnPropertyNames(colColumns[i]).forEach(function (val, idx, array) {
                    if (propertyInCol(colProperties, val))
                    {
                        var itemCol = colColumns[i];
                        item[val] = itemCol[val];
                    }
                });
                colCol.push(item);
            }
            return colCol;
        }

        function setErrorsInForm(dataFrom)
        {
            $('[data-toggle="popover"]').popover()
            $('[data-toggle="tooltip"]').tooltip();
            var form = document.getElementById(dataFrom.idForm);
            for (var i = 0; i < dataFrom.colFormItem.length; i++)
            {
                for (var j = 0; j < form.elements.length; j++)
                {
                    if (dataFrom.colFormItem[i].msg != null && dataFrom.colFormItem[i].msg != "" && form.elements[j].name == dataFrom.colFormItem[i].idItem)
                    {
                        var elementForm = document.getElementsByName(form.elements[j].name);
                        elementForm.tooltip({'trigger': 'focus', 'title': dataFrom.colFormItem[i].msg});
                    }
                }
            }
        }

        function dataTableToColObject(dataTable, colColumns)
        {
            var colObject = [];
            for (var i = 0; i < dataTable.colRows.length; i++)
            {
                var itemObject = new Object();
                for (var j = 0; j < dataTable.colRows[i].colCells.length; j++)
                {
                    if (isCellInColColumns(dataTable.colColumns, dataTable.colRows[i].colCells[j].idCell))
                    {
                        itemObject[dataTable.colRows[i].colCells[j].idCell] = dataTable.colRows[i].colCells[j].valueCell;
                    }
                }
                colObject.push(itemObject);
            }
            return colObject;
        }

        function isCellInColColumns(colColumns, item)
        {
            for (var i = 0; i < colColumns.length; i++)
            {
                if (colColumns[i].field == item && colColumns[i].visible == 'S')
                {
                    return true;
                }
            }
            return false;
        }

        function propertyInCol(colPropertys, property)
        {
            for (var i = 0; i < colPropertys.length; i++)
            {
                if (colPropertys[i] == property)
                {
                    return true;
                }
            }
            return false;
        }



        function objectToForm(obj)
        {
            var form = {};
            form.idForm = "prueba";
            form.colFormItem = [];
            Object.getOwnPropertyNames(obj).forEach(function (val, idx, array) {
                var dtoFormItem = {};
                dtoFormItem.idItem = val;
                dtoFormItem.value = obj[val];
                form.colFormItem.push(dtoFormItem);
            });
            return form;
        }
    }]);


