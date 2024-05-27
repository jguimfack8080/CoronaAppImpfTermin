package hbv;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class ConfirmationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
     response.setContentType("text/html");

        // Vérification de l'existence de la session
        HttpSession session = request.getSession(false);
        if (session == null) {
            // Si la session n'existe pas, redirection vers la page de connexion
            response.sendRedirect("login.html");
            return;
        }

        // Récupération des informations de session
        //String username = (String) request.getSession().getAttribute("username");
        String firstName = (String) request.getSession().getAttribute("firstName");
        String lastName = (String) request.getSession().getAttribute("lastName");
        String email = (String) request.getSession().getAttribute("email");
        String city = (String) request.getSession().getAttribute("city");
        String postalCode = (String) request.getSession().getAttribute("postalCode");
        LocalDate date1 = LocalDate.parse((String) request.getSession().getAttribute("date1"));
        LocalTime heure1 = LocalTime.parse((String) request.getSession().getAttribute("heure1"));
        LocalDate date2 = LocalDate.parse((String) request.getSession().getAttribute("date2"));
        LocalTime heure2 = LocalTime.parse((String) request.getSession().getAttribute("heure2"));
        String vaccine = (String) request.getSession().getAttribute("vaccine");

        // Envoi de l'email contenant le document PDF en pièce jointe
        SendMail send = new SendMail();
        send.sendMailConfirmationWithPdf(email, firstName, lastName, city, postalCode, vaccine, date1, heure1, date2, heure2);

        // Écriture d'un message de succès dans le flux de sortie de la réponse
        response.getWriter().write("<h1>Die Terminbestätigung wurde erfolgreich an Ihre E-Mail-Adresse gesendet!</h1>");
    }
}


