package hbv;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.*;

public class MyFilter implements Filter  {
  ServletContext ctx;

  public void  init(FilterConfig config) throws ServletException{
    ctx = config.getServletContext();
  }

  public void  doFilter(ServletRequest request, 
      ServletResponse response,
      FilterChain chain) 
      throws java.io.IOException, ServletException {

      if(request instanceof HttpServletRequest){
        HttpServletRequest hsr = (HttpServletRequest) request;
        String forwardedFor = hsr.getHeader("X-Forwarded-For");
        String requestURL = ""+hsr.getRequestURL();
      }

      chain.doFilter(request,response);
  }

  public void destroy( ){ }
}
