/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import configuration.ConnectionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.UsuarioModel;

/**
 *
 * @author ALEJANDRO DIAZ
 */
@WebServlet("/usuario")
public class Usuario extends HttpServlet {

    private static final long serialVersionUID = 1L;

    Connection conn;
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Usuario</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Usuario at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Se ejecuta el doGet");
        ConnectionBD conexion = new ConnectionBD();
        List<UsuarioModel> listaUsuarios = new ArrayList<>();
        String sql = "SELECT apellidos, celular, correo, fecha_nac, matricula, nombre, hora FROM usuario";

        try {
            conn = conexion.getConnectionBD();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Itera sobre los resultados y crea objetos UsuarioModel
            while (rs.next()) {
                UsuarioModel usuario = new UsuarioModel();
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setCelular(rs.getString("celular"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setFecha_nac(rs.getDate("fecha_nac"));
                usuario.setMatricula(rs.getString("matricula"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setHora(rs.getTime("hora"));
                listaUsuarios.add(usuario);
            }

            // Pasa la lista de usuarios al JSP
            request.setAttribute("usuarios", listaUsuarios);
            request.getRequestDispatcher("/views/mostrar_usuarios.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los usuarios" + e);
        } finally {
            // Close resources
            // Close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ConnectionBD conexion = new ConnectionBD();
        // Obtener los parámetros del formulario 
        String nombre = request.getParameter("txt_nombre");
        String correo = request.getParameter("txt_correo");
        String matricula = request.getParameter("txt_matricula");
        String apellidos = request.getParameter("txt_apellidos");
        String celular = request.getParameter("txt_celular");
        String fecha = request.getParameter("txt_fecha_nac");
        String hora = request.getParameter("txt_hora");

        Date fechaFinal = Date.valueOf(fecha);
        Time horaFinal = Time.valueOf(hora + ":00");

        try {
            // Crear la consulta SQL para insertar el usuario 
            String sql = "INSERT INTO usuario (apellidos, celular, correo, "
                    + "fecha_nac, matricula, nombre, hora) VALUES (?, ?, ?, ?, ?, ?, ?)";
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, apellidos);
            ps.setString(2, celular);
            ps.setString(3, correo);
            ps.setDate(4, fechaFinal);
            ps.setString(5, matricula);
            ps.setString(6, nombre);
            ps.setTime(7, horaFinal);

            // Ejecutar la consulta 
            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                // Si se insertó correctamente, redirigir al usuario a una página de éxito 
                request.setAttribute("mensaje", "Usuario registrado con éxito!");
                request.getRequestDispatcher("/views/resultado.jsp").forward(request, response);
            } else {
                // Si falló, redirigir a una página de error 
                request.setAttribute("mensaje", "Error al registrar usuario.");
                request.getRequestDispatcher("/views/resultado.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Ocurrió un error: " + e.getMessage());
            request.getRequestDispatcher("/views/resultado.jsp").forward(request, response);
        } finally {
            // Cerrar los recursos 
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ConnectionBD conexion = new ConnectionBD();
        String matricula = request.getParameter("matricula");
        // Validate input
        if (matricula == null || matricula.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Invalid request
            return;
        }

        String sql = "DELETE FROM usuario WHERE matricula like ?";

        try {
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, matricula);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                response.setStatus(HttpServletResponse.SC_OK); // Eliminar exitoso 
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // No se encontró el usuario 
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Error del servidor 
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConnectionBD conexion = new ConnectionBD();
        String matricula = request.getParameter("matricula");
        if (matricula == null || matricula.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Matricula not found in URL");
            return;
        }
        System.out.println("Query String: " + request.getQueryString());
        System.out.println("MMMMMM: " + matricula);
        // Obtener los parámetros del formulario 
        String nombre = request.getParameter("txt_nombre");
        String correo = request.getParameter("txt_correo");
        String apellidos = request.getParameter("txt_apellidos");
        String celular = request.getParameter("txt_celular");
        String fecha = request.getParameter("txt_fecha_nac");
        String hora = request.getParameter("txt_hora");
        
        Date fechaFinal = Date.valueOf(fecha);
        Time horaFinal = Time.valueOf(hora);
        String sql2 = "UPDATE usuario SET apellidos = '"+apellidos+
                "', celular = '"+celular+
                "', fecha_nac = '"+fechaFinal+
                "', nombre = '"+nombre+
                "', hora = '"+hora+
                "', correo = '"+correo+
                "' WHERE matricula like '"+matricula+"'";
        System.out.println("SQL "+ sql2);
        try {
                        conn = conexion.getConnectionBD();

            // Crear la consulta SQL para insertar el usuario 
            String sql = "UPDATE usuario SET apellidos = ? , celular = ?, fecha_nac = ?,"
                    + "nombre = ?, hora = ?, correo = ? WHERE matricula LIKE ?";
            ps.setString(1, apellidos);
            ps.setString(2, celular);
            ps.setDate(3, fechaFinal);
            ps.setString(4, nombre);
            ps.setTime(5, horaFinal);
            ps.setString(6, correo);
            
            // Ejecutar la consulta 
            int filasActualizadas = ps.executeUpdate();

            // Enviar respuesta de éxito 
            response.setContentType("text/plain");
            if (filasActualizadas > 0) {
                response.getWriter().write("Usuario actualizado exitosamente.");
            } else {
                response.getWriter().write("No se encontró el usuario para actualizar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error al actualizar el usuario.");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
