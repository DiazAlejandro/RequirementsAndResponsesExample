<%-- 
    Document   : resultado
    Created on : 21/09/2024, 08:30:03 PM
    Author     : RUFINA RUIZ
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<!DOCTYPE html> 
<html> 
    <head> 
        <meta charset="UTF-8"> 
        <title>Resultado del Registro</title> 
    </head> 
    <body> 
        <h2>Resultado</h2> 
        <p><%= request.getAttribute("mensaje")%></p> 
        <a href="views/registro_usuarios.jsp">Regresar al formulario</a> 
    </body> 
</html>