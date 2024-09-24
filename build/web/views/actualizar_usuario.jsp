<%-- 
Document   : actualizar_usuario.jsp
Created on : 22/09/2024, 04:56:05 PM
Author     : ALEJANDRO DIAZ
--%>

<%@page import="java.sql.Time"%>
<%@page import="java.util.Date"%>
<%@page import="configuration.ConnectionBD"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet" %> 

<!DOCTYPE html> 
<html> 
    <head> 
        <meta charset="UTF-8"> 
        <title>Actualizar Usuario</title> 
    </head> 
    <body> 
        <h2>Actualizar Usuario</h2> 
        <%
            String matricula = request.getParameter("matricula");
            String nombre = "";
            String correo = "";
            String apellidos = "";
            String celular = "";
            Date fecha_nac = null;
            Time hora = null;
            ConnectionBD conexion = new ConnectionBD();
            Connection connection = conexion.getConnectionBD();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {

                // Consulta para obtener los datos del usuario por ID 
                String sql = "SELECT nombre, correo, apellidos, celular, fecha_nac, hora"
                        + " FROM usuario WHERE matricula LIKE ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, matricula);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    nombre = resultSet.getString("nombre");
                    correo = resultSet.getString("correo");
                    apellidos = resultSet.getString("apellidos");
                    celular = resultSet.getString("celular");
                    fecha_nac = resultSet.getDate("fecha_nac");
                    hora = resultSet.getTime("hora");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (statement != null) {
                        statement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        %> 

        <!-- Formulario con los datos del usuario para actualizar --> 
        <form id="formActualizarUsuario"> 
            APELLIDO: <br>
            <input type="text" id="txt_apellidos" value="<%= apellidos%>" ><br>
            NOMBRE: <br>
            <input type="text" id="txt_nombre" value="<%= nombre%>" ><br>
            CELULAR: <br>
            <input type="tel" id="txt_celular" value="<%= celular%>" ><br>
            CORREO: <br>
            <input type="text" id="txt_correo" value="<%= correo%>" ><br>
            FECHA_NACIMIENTO: <br>
            <input type="date" id="txt_fecha_nac" value="<%= fecha_nac%>" ><br>
            MATRICULA: <br>
            <input type="text" id="txt_matricula" value="<%= matricula%>" readonly><br> 
            HORA: <br>
            <input type="time" id="txt_hora" value="<%= hora%>" ><br>
            <input type="button" value="Actualizar" onclick="actualizarUsuario()"> 
        </form> 
        <div id="resultado"></div> 
        <script>
            function actualizarUsuario() {
                const apellidos = document.getElementById("txt_apellidos").value;
                const nombre = document.getElementById("txt_nombre").value;
                const celular = document.getElementById("txt_celular").value;
                const correo = document.getElementById("txt_correo").value;
                const fecha_nac = document.getElementById("txt_fecha_nac").value;
                const matricula = document.getElementById("txt_matricula").value;
                const hora = document.getElementById("txt_hora").value;
                
                const datos = new URLSearchParams();
                datos.append("apellidos", apellidos);
                datos.append("nombre", nombre);
                datos.append("celular", celular);
                datos.append("fecha_nac", fecha_nac);
                datos.append("matricula", matricula);
                datos.append("hora", hora);
                console.log(datos );
                var urlParams = new URLSearchParams(window.location.search);
                var matricula22 = urlParams.get("matricula");
                console.log("MATRICULA  " + matricula22);
                fetch("${pageContext.request.contextPath}/usuario?matricula="+matricula, {
                    method: "PUT",
                    body: datos,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                })
                        .then(response => response.text())
                        .then(data => {
                            document.getElementById("resultado").innerText = data;
                        })
                        .catch(error => {
                            document.getElementById("resultado").innerText = "Error al actualizar usuario.";
                            console.error('Error:', error);
                        });
            }

        </script> 
    </body> 
</html> 