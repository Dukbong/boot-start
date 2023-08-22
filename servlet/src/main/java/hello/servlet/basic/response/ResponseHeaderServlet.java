package hello.servlet.basic.response;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//[status - line]
		// 이런식으로 번호를 부여할 수 있다.
		response.setStatus(404);
		// 위 방법 보다 더 안전하고 좋은 방법이다. (의미를 바로 알 수 있다.)
		// 글자로 상태 코드를 저장 해두었다. 
		response.setStatus(HttpServletResponse.SC_OK);
		
		//[response-headers]
		response.setHeader("Content-Type", "text/plain;charset=utf-8");
		// 아래 두 줄은 캐시를 제거하는 것이다.
		response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
		response.setHeader("Pargma", "no-cache");
		
		response.setHeader("my-header", "hello~");
		
		// [Header 편의 메소드]
		// content(response);
		// cookie(response);
		redirect(response);
		
		PrintWriter writer = response.getWriter();
		writer.print("ok");
	}
	
	private void content(HttpServletResponse response) {
		// Content-Type : text/plain;charset=utf-8
		// Content-Length : 2
		// response.setHeader("Content-Type", "text/plain;charset=utf-8"
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		// 이렇게 두번으로 나눌 수 있다.
		//response.setContentLength(2); // (생략시 자동으로 알맞게 생성해준다.)
	}
	
	private void cookie(HttpServletResponse response) {
		// Set-Cookie : myCookie=good; Max-Age=600;
		// response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
		Cookie cookie = new Cookie("myCookie", "good");
		cookie.setMaxAge(600); // 600초
		response.addCookie(cookie);
	}
	
	private void redirect(HttpServletResponse response) throws IOException {
		// Status Code 302
		// Location: /basic/hello-form.html
		
		// response.setStatus(HttpServletResponse.SC_FOUND); // 302
		// response.setHeader("Location", "/basic/hello-form.html");
		response.sendRedirect("/basic/hello-form.html");
	}

	
}
