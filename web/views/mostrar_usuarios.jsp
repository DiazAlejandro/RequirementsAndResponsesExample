<%@page import="java.util.ArrayList"%>
<%@page import="model.UsuarioModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Lista de Usuarios</title>
        <style>
            table {
                width: 80%;
                border-collapse: collapse;
            }
            table, th, td {
                border: 1px solid black;
            }
            th, td {
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
        <script>
            function eliminarUsuario(matricula) {
                console.log(`eliminarUsuario?matricula=` + matricula);
                if (confirm("¿Estás seguro de que quieres eliminar este usuario?")) {
                    fetch(`usuario?matricula=` + matricula, {
                        method: 'DELETE'
                    }).then(response => {
                        if (response.ok) {
                            alert('Usuario eliminado exitosamente');
                            location.reload();
                        } else {
                            alert('Error al eliminar usuario');
                        }
                    }).catch(error => console.error('Error:', error));
                }
            }
        </script>

    </head>
    <body>
        <h2>Lista de Usuarios</h2>

        <table>
            <thead>
                <tr>
                    <th>Apellidos</th>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Matricula</th>
                    <th>Celular</th>
                    <th>Fecha de Nacimiento</th>
                    <th>Hora</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<UsuarioModel> listaUsuarios = (ArrayList<UsuarioModel>) request.getAttribute("usuarios");

                    if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                        for (UsuarioModel usuario : listaUsuarios) {
                %>
                <tr>
                    <td><%= usuario.getApellidos()%></td>
                    <td><%= usuario.getNombre()%></td>
                    <td><%= usuario.getCorreo()%></td>
                    <td><%= usuario.getMatricula()%></td>
                    <td><%= usuario.getCelular()%></td>
                    <td><%= new java.text.SimpleDateFormat("dd-MM-yyyy").format(usuario.getFecha_nac())%></td>
                    <td><%= new java.text.SimpleDateFormat("HH:mm:ss").format(usuario.getHora())%></td>
                    <td> <button onclick="eliminarUsuario(<%= usuario.getMatricula()%>)">Eliminar</button> </td>
                    <td>
                        <!-- Botón para actualizar, que redirige a actualizarUsuario.jsp con el ID del usuario --> 
                        <form action="${pageContext.request.contextPath}/views/actualizar_usuario.jsp" method="GET"> 
                            <input type="hidden" name="matricula" value="<%= usuario.getMatricula()%>"> 
                            <input type="submit" value="Actualizar"> 
                        </form> 
                    </td>

                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="7">No hay usuarios registrados.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
