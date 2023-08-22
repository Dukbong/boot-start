package hello.servlet.basic.response;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.servlet.basic.HelloData;


@WebServlet(name="responseJsonServlet", urlPatterns = "response-json")
public class ResponseJsonServlet extends HttpServlet{

	private ObjectMapper objMapper = new ObjectMapper();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Content-Type : application/json
		response.setContentType("applictaion/json");
		response.setCharacterEncoding("utf-8");
		
		HelloData helloData = new HelloData();
		helloData.setUsername("kim");
		helloData.setAge(20);
		
		// 객체를 json으로 만들기
		String result = objMapper.writeValueAsString(helloData);
		response.getWriter().write(result);
		// {"username":"kim","age":20}
	}
	
	
}
