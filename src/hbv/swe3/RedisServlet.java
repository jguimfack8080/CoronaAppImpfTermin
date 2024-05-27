package hbv;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.lang.ref.*;
import redis.clients.jedis.*;

public class RedisServlet extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();

    long start = System.currentTimeMillis();
    Jedis jedis = new Jedis("localhost", 6379);
    jedis.auth("OesRQnYew8jAneTSKnUC");

    Long result = jedis.incr("bar");
    jedis.close();
    long ende = System.currentTimeMillis();
    out.println("time " + (1000000 + (ende - start)));
  }
}
