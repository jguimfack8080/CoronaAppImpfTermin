package hbv;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.lang.ref.*;
import redis.clients.jedis.*;

public class RedisPoolServlet extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();

    Jedis jedis = MyJedisPool.getJedis();
    long j = jedis.incr("bar");
    out.println("bar:"+j);
    MyJedisPool.releaseJedis(jedis);
  }
}
