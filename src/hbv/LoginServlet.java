package hbv;

import java.io.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
  
   
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       response.setContentType("text/html");

        // Recuperation des parametres de la requête
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        PrintWriter out = response.getWriter();

        if (username != null && username.isEmpty() && password != null && password.isEmpty()) {
                       
          out.println("<script>alert('fehlende Angaben');" +" window.location.href='login.html?error=fehlendeAngaben'</script>");
          
            return;
        }

        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Création de la session utilisateur
            HttpSession session = request.getSession();

           
                    String sql = "SELECT * FROM usersApp WHERE BINARY  username=? AND password=?";

                    try (PreparedStatement statement = conn.prepareStatement(sql)) {
                        statement.setString(1, username);
                        statement.setString(2, password);
                        ResultSet rs = statement.executeQuery();

                        if (rs.next()) {
                            session.setAttribute("username", username);
                            session.setAttribute("firstName", rs.getString("first_name"));
                            session.setAttribute("lastName", rs.getString("last_name"));
                            session.setAttribute("email", rs.getString("email"));
                            session.setAttribute("city", rs.getString("city"));
                            session.setAttribute("postalCode", rs.getString("postal_code"));

                            response.sendRedirect("central.html?username=" + username);
                        } else {
                           // response.sendRedirect("login.html?error=invalidLogin");
                               out.println("<script>alert('Username oder Password nicht gültig.');" +" window.location.href='login.html?error=invalidlogin'</script>");
                            

                        }
           
                        rs.close();
                        statement.close();
                    
                    } catch (SQLException e) {
                        System.out.println("Echec");
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("Echec de la redirection de la réponse");
                        e.printStackTrace();
                    } 

                        DatabaseConnection.releaseConnection(conn);
                   
        } catch (SQLException e) {
            System.out.println("Echec");
            e.printStackTrace();
        }
    }
}
