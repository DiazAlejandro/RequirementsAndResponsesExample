<%-- 
    Document   : registro_usuarios
    Created on : 21/09/2024, 07:25:52 PM
    Author     : ALEJANDRO DIAZ
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Registro Usuarios</h1>
        <form action="${pageContext.request.contextPath}/usuario" method="POST">
                APELLIDO: <br>
                <input type="text" name="txt_apellidos"><br>
                NOMBRE: <br>
                <input type="text" name="txt_nombre"><br>
                CELULAR: <br>
                <input type="tel" name="txt_celular"><br>
                CORREO: <br>
                <input type="text" name="txt_correo"><br>
                FECHA_NACIMIENTO: <br>
                <input type="date" name="txt_fecha_nac"><br>
                MATRICULA: <br>
                <input type="text" name="txt_matricula"><br> 
                HORA: <br>
                <input type="time" name="txt_hora"><br>
                <input type="submit" name="accion" value="Agregar">
            </form>
                <a href="mostrar_usuarios.jsp">Cargar Usuarios</a>
                
    </body>
</html>
