package hello.servlet.basic.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[전체 파라미터 조회] - start");
		
		// 최신 방법 
		request.getParameterNames().asIterator()
		.forEachRemaining(paramName -> System.out.println(paramName + " : " + request.getParameter(paramName)));
		
		System.out.println("[전체 파라미터 조회] - end");
		System.out.println();
		
		System.out.println("[단일 파라미터 조회] - start");
		String username = request.getParameter("username");
		String age = request.getParameter("age");
		
		System.out.println("username = " + username);
		System.out.println("age = " + age);
		System.out.println("[단일 파라미터 조회] - end");
		System.out.println();
		
		System.out.println("[이름이 같은 복수 파라미터 조회] - start");
		// ex) url?username="kim"&username="jang" 이런식일때
		// 만약 이 경우 request.getParameter("username") 하면 가장 먼저 찾는 하나만 반환한다.
		// 전체를 반환하고 싶다면 getParameterValues를 사용해야한다.
		String[] usernames = request.getParameterValues("username");
		for(String name : usernames) {
			System.out.println("username = " + name);
		}
		
		System.out.println("[이름이 같은 복수 파라미터 조회] - end");
		System.out.println();
		
		response.getWriter().write("OK");
		
	}
	
}
