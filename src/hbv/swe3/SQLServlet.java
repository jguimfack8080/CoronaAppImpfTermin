package hbv;
import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;


public class SQLServlet extends HttpServlet {

  protected void doGet(HttpServletRequest  request, 
      HttpServletResponse response)
      throws IOException, ServletException {

      response.setContentType("text/plain");
      PrintWriter out = response.getWriter();
      try {
        // Naming Context
        Context initCtx = new InitialContext();
        DataSource ds = (DataSource)initCtx.lookup("java:/comp/env/jdbc/mariadb");

        // get connection ...
        Connection connection = ds.getConnection();
        MyContextListener.incrementLeasedConnections();

        // preparedstatement to prevent sql-injection
        // simple select statement is ok
        int rows = 0;
        PreparedStatement ps;

        ps = connection.prepareStatement("select * from demo where id=1");

        ResultSet rs = ps.executeQuery();
        rows=0;

        // cursor pattern
        while(rs.next()){
          long id = rs.getLong("id");
          String name = rs.getString("name");
          rows++;
        }
        out.format("%3d\n",rows);
        rs.close();
        ps.close();

        MyContextListener.decrementLeasedConnections();
        connection.close();

      } catch(Exception e){
        out.println(e);
        e.printStackTrace(out);
        throw new RuntimeException(e);
      } 
      long ende = System.currentTimeMillis();

  }
}

