/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.CustomResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.UsuarioModel;

/**
 *
 * @author Alejandro Diaz Ruiz
 */
@WebServlet(
        urlPatterns = "/api/data")
public class ApiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private Connection conn2;

    // Método para obtener la conexión a la base de datos (debes configurarlo según tu base de datos)
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        // Configura la conexión a tu base de datos
        String jdbcURL = "jdbc:mysql://localhost:3306/prueba";
        String jdbcUsername = "root";
        String jdbcPassword = "";
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //Configuración del JSON que responderá la petición 
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<String> list = new ArrayList<>();

        List<UsuarioModel> listaUsuarios = new ArrayList<>();
        String sql = "SELECT apellidos, celular, correo, fecha_nac, matricula, nombre, hora FROM usuario";

        try {
            conn2 = getConnection();
            PreparedStatement ps = conn2.prepareStatement(sql);
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

        } catch (Exception e) {
        }

        CustomResponse cResponse = new CustomResponse(200, "Ok", listaUsuarios);

        String jsonResponse = new Gson().toJson(cResponse);
        response.getWriter().write(jsonResponse);
    }
}
