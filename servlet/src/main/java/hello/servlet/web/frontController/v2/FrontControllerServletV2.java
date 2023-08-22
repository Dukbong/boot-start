package hello.servlet.web.frontController.v2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontController.MyView;
import hello.servlet.web.frontController.v2.controlelr.MemberFromControllerV2;
import hello.servlet.web.frontController.v2.controlelr.MemberListControllerV2;
import hello.servlet.web.frontController.v2.controlelr.MemberSaveControllerV2;

@WebServlet(name="frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

	private Map<String, ControllerV2> controllerMap = new HashMap<>();
	/* v2
	 * 0. 클라이언트 요청
	 * 1. URL 매핑 정보에서 컨트롤러 조회
	 * 2. 프런트 컨트롤로에서 알맞는 컨트롤러 호출
	 * 3. 컨트롤러에서 비즈니스 로직 수행 후 MyView(응답할 뷰의 정보를 가진 객체) 반환
	 * 4. 프런트 컨트롤러에서 MyView의 render() 호출
	 * 5. render() 내부에서 JSP forward 수행
	 * 6. 클라이언트 응답
	 * */
	public FrontControllerServletV2() {
		controllerMap.put("/front-controller/v2/members/new-form", new MemberFromControllerV2());
		controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
		controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("FrontControllerServletV1.service");
		
		String requestURI = request.getRequestURI();
		// URI = /front-controller/v1/members 이런식으로 나온다.
		
		ControllerV2 controller = controllerMap.get(requestURI);
		
		if(controller == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		MyView view = controller.process(request, response);
		view.render(request, response);
	}
	
	

}
