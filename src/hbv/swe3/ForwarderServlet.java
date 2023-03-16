package hbv;
import java.io.*;
import java.time.*;
import java.time.format.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ForwarderServlet extends HttpServlet {

	protected void doGet(HttpServletRequest  request, 
			HttpServletResponse response)
			throws IOException, ServletException {
			RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/hello");
			dispatcher.forward(request, response);
	}
}
