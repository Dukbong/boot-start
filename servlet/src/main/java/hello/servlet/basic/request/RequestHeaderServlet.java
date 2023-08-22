package hello.servlet.basic.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		printStartLine(request);
		printHeaders(request);
		printHeaderUtils(request);
		printEtc(request);
	}
	private void printStartLine(HttpServletRequest request) {
		System.out.println("--- REQEUST-LINE - start ---");
		
		String method = request.getMethod();
		System.out.println("request.getMethod() = " + method); // GET
		
		String protocal = request.getProtocol();
		System.out.println("request.getProtocal() = " + protocal); // HTTP/1.1
		
		String scheme = request.getScheme();
		System.out.println("request.getScheme() = " + scheme); // http
		
		StringBuffer URL = request.getRequestURL();
		System.out.println("request.getRequestURL() = " + URL.toString());
		// http://localhost:8888/request-header
		
		String URI = request.getRequestURI();
		System.out.println("request.getRequestURI() = " + URI); // /request-header
		
		String queryString = request.getQueryString();
		System.out.println("request.getQueryString() = " + queryString); // username=hi
		
		boolean https = request.isSecure();
		System.out.println("requst.isSecure() = " + https); // https 사용 유무
		
		System.out.println("--- REQUEST-LINE - end ---");
		System.out.println();
	}
	
	// Header 모든 정보
	private void printHeaders(HttpServletRequest request) {
		System.out.println("--- Headers - start ---");
		// 옛날 방식
//		Enumeration<String> headerNames = request.getHeaderNames();
//		while(headerNames.hasMoreElements()) {
//			String headerName = headerNames.nextElement();
//			System.out.println(headerName +" : " + headerName);
//		}
		
		// 요즘 방식
		request.getHeaderNames().asIterator()
			.forEachRemaining(headerName -> System.out.println("headerName = " + headerName));
	}
	
	private void printHeaderUtils(HttpServletRequest request) {
		System.out.println("--- Header 편의 조회 start ---");
		System.out.println("[Host 편의 조회]");
		System.out.println("request.getServerName() = " + request.getServerName()); // Host 헤더
		System.out.println("request.getServerPort() = " + request.getServerPort()); // Host 헤더
		System.out.println();
		
		System.out.println("[Accept-Language 편의 조회]");
		request.getLocales().asIterator()
			.forEachRemaining(locale -> System.out.println("locale = " + locale));
		System.out.println("request.getLocale() = " + request.getLocale());
		System.out.println();
		
		System.out.println("[Cookie 편의 조회]");
		if(request.getCookies() != null) {
			for(javax.servlet.http.Cookie cookie : request.getCookies()) {
				System.out.println(cookie.getName() + " : " + cookie.getValue());
			}
		}
		System.out.println();
		
		// Get 방식의 경우 content가 없다.
		System.out.println("[Content 편의 조회]");
		System.out.println("request.getContentType() = " + request.getContentType());
		System.out.println("reqeust.getContentLength() = " + request.getContentLength());
		System.out.println("reqeust.getCharacterEncoding() = " + request.getCharacterEncoding());
		
		System.out.println("--- Header 편의 조회 end ---");
		System.out.println();
	}
	
	private void printEtc(HttpServletRequest request) {
		System.out.println("--- 기타 조회 start ---");
		
		System.out.println("[Remote 정보 ]");
		System.out.println("request.getRemoteHost() = " + request.getRemoteHost());
		System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr());
		System.out.println("reqeust.getRemotePort() = " + request.getRemotePort());
		System.out.println();
		
		System.out.println("[Local 정보]"); // 서버정보
		System.out.println("request.getLocalName() = " + request.getLocalName());
		System.out.println("request.getLocalAddr() = " + request.getLocalAddr());
		System.out.println("request.getLocalPort() = " + request.getLocalPort());
		
		System.out.println("--- 기타 조회 end ---");
		System.out.println();
	}
	
}
