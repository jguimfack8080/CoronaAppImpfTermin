package hbv;
import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

import redis.clients.jedis.Jedis;

public class AllServlet extends HttpServlet {
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
				PreparedStatement ps = connection.prepareStatement("insert into demo(name) values(?)");
				ps.setString(1,"moin-"+id);
				int rows=ps.executeUpdate();
				ps.close();

				ResultSet rs;
				ps = connection.prepareStatement("select * from demo where id=?");
				ps.setInt(1,1);
				rs = ps.executeQuery();


				// cursor pattern
				while(rs.next()){
					long rowid = rs.getLong("id");
					String name = rs.getString("name");
					rows++;
					out.println(name+" "+rowid);
				}
				rs.close();
				ps.close();
				MyConnectionPool.releaseConnection(connection);
				Jedis jedis = MyJedisPool.getJedis();
				jedis.set("x",""+id);
				jedis.publish("stream","created:"+id);
				MyJedisPool.releaseJedis(jedis);
				out.println(id+" "+rows);

			} catch(Exception e){
				out.println(e);
				e.printStackTrace(out);
				throw new RuntimeException(e);
			} 

	}
}

