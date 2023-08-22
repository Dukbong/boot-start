package hello.servlet.basic;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

	@Override
	// helloServlet이 호출되면 해당 메소드가 호출된다.
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("helloServlet.service");
		System.out.println("request = " + request);
		System.out.println("response = " + response);
		
		String username = request.getParameter("username");
		System.out.println("username = " + username);
		
		// HTTP header부분에 들어가는 정보 (Content-Type)
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		
		// HTTP body부분에 들어가는 내용
		response.getWriter().write("hello " + username);
	}
	
}
