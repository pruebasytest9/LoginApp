package com.construccion.login;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Configura la conexión a la base de datos MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdcontruccion", "admin", "123456");

            // Consulta para verificar las credenciales
            String query = "SELECT * FROM usuarios WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Las credenciales son válidas, el usuario ha iniciado sesión con éxito
                response.sendRedirect("index.jsp");
            } else {
                // Las credenciales no son válidas, muestra un mensaje de error
                response.sendRedirect("login.jsp?error=1");
            }

            // Cierra la conexión a la base de datos
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("index.jsp");
    }
}
