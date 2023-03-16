package hbv;
import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;


public class SQLPoolServlet extends HttpServlet {
	static Random ran = new Random();

	protected void doGet(HttpServletRequest  request, 
			HttpServletResponse response)
			throws IOException, ServletException {

			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			try {
				// simple select statement is ok
				Connection connection = MyConnectionPool.borrowConnection();
				int id = ran.nextInt(1000000)+1;

				PreparedStatement ps = connection.prepareStatement("select * from demo where id=?");
				ps.setInt(1,1);
				int rows=0;
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					long rowid = rs.getLong("id");
					String name = rs.getString("name");
					rows++;
				}
				rs.close();
				ps.close();
				MyConnectionPool.releaseConnection(connection);
				out.println(id+" "+rows);


			} catch(Exception e){
				out.println(e);
				e.printStackTrace(out);
				throw new RuntimeException(e);
			} 

	}
}

