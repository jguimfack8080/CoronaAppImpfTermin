package hbv;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.time.*;
import java.util.*;

public class UploadServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();

			String uuid = System.nanoTime() + "_" + UUID.randomUUID();

			String thename = request.getParameter("name");

			// one file upload
			Part part = request.getPart("thefile");

			// if png or jpeg
			if (part.getContentType().equals("image/png") || part.getContentType().equals("image/jpeg")) {
				long startWriteTime = System.nanoTime();
				part.write("/data/upload/"+uuid+"_file.noe");
				long endWriteTime = System.nanoTime();
				try (FileWriter fw = new FileWriter("/data/upload/" + uuid + "_meta.txt");
						PrintWriter pw = new PrintWriter(fw)) {
					pw.println("filename:" + part.getSubmittedFileName());
					pw.println("contenttype:" + part.getContentType());
					pw.println("filesize:" + part.getSize());
					pw.println("partname:" + part.getName());
					pw.println("localdatetime:" + LocalDateTime.now());
					pw.println("writetimemicros:" + ((endWriteTime - startWriteTime)/1000));
						}
				out.println("uploaded:(name:" + thename + ")  " + 
						part.getContentType()+" in: "+((endWriteTime-startWriteTime)/1000)+"mics");
			} else {
				out.println("wrong file type");
			}
	}
}

