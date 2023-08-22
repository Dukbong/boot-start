package hello.servlet.web.frontController.v1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontController.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontController.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontController.v1.controller.MemberSaveControllerV1;

@WebServlet(name="frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

	private Map<String, ControllerV1> controllerMap = new HashMap<>();
	/* v1
	 * 0. 클라이언트 요청
	 * 1. 클라인트 모든 요청을 프런트 컨트롤러에서 받는다.
	 * 2. URL 매핑 정보를 조회하여 컨트롤로 호출
	 * 3. 컨트롤러 내부에서 비즈니스 로직 & JSP forward 수행
	 * 4. 응답
	 * */
	public FrontControllerServletV1() {
		controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
		controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
		controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("FrontControllerServletV1.service");
		
		String requestURI = request.getRequestURI();
		// URI = /front-controller/v1/members 이런식으로 나온다.
		
		ControllerV1 controller = controllerMap.get(requestURI);
		
		if(controller == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		controller.process(request, response);
	}
	
	

}
