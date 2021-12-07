/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Logic.Controllers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class CGeneric {

    public static Object hasMapToObject(Object tranfer, Object hasMapData) throws IllegalArgumentException, IllegalAccessException, IOException {
        HashMap p = (HashMap) hasMapData;
        for (Field property : tranfer.getClass().getFields()) {
            String name = property.getName();
            Object value = p.get(name);
            Type t = property.getType();
            if (value != null) {
                if (property.getType() == String.class) {
                    property.set(tranfer, value.toString());
                } else if (property.getType() == Double.class || property.getType() == double.class) {
                    property.setDouble(tranfer, Double.parseDouble(value.toString()));
                } 
                else if (property.getType() == Short.class || property.getType() == short.class) {
                    property.setShort(tranfer, Short.parseShort(value.toString()));
                } 
                else if (property.getType() == Long.class || property.getType() == long.class) {
                    property.setLong(tranfer, Long.parseLong(value.toString()));
                } else if (property.getType() == Float.class || property.getType() == float.class) {
                    property.setFloat(tranfer, Float.parseFloat(value.toString()));
                } else if (property.getType() == Integer.class || property.getType() == int.class) {
                    property.setInt(tranfer, Integer.parseInt(value.toString()));
                }    
                else if (property.getType() == Date.class) {
                   
                        property.set(tranfer, value);
                    
                } else if (property.getType() == Time.class) {
                    try {
                        Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(value.toString());
                        property.set(tranfer, fecha);
                    } catch (ParseException ex) {
           
                    }
                }
            }
        }

        return tranfer;
    }
}
