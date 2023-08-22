package hello.servlet.web.frontController.v4;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontController.MyView;
import hello.servlet.web.frontController.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontController.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontController.v4.controller.MemberSaveControllerV4;

@WebServlet(name="frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet{
	/* v4
	 * 0. 클라이언트 요청
	 * 1. URL 매핑 정보에서 컨트롤러 조회
	 * 2. 프런트 컨트롤로에서 알맞는 컨트롤러 호출 (매개변수로 Map 두개)
	 * 3. 컨트롤러에서 비즈니스 로직 수행 후 String으로 이름만 반환
	 * 4. 프런트 컨트롤러에서 반환 된 이름으로 viewResolver로 이름 완성
	 * 5. MyView에 render()메서드의 매개변수로 model(Map)까지 넘겨준다.
	 * 6. MyView 내부에서 model을 가지고 다 setAttribute해서 저장한 후
	 * 7. JSP foward
	 * 6. 클라이언트 응답
	 * */
	private Map<String, ControllerV4> controllerMap = new HashMap<>();
	
	public FrontControllerServletV4() {
		controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
		controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
		controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURL = request.getRequestURI();
		ControllerV4 controller = controllerMap.get(requestURL);
		if(controller == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		Map<String, String> paramMap = createParamMap(request);
		Map<String, Object> model = new HashMap<>();
		String viewName = controller.process(paramMap, model);
		MyView myView = viewResolver(viewName);
		myView.render(model, request, response);
	}

	private Map<String, String> createParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator()
		.forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
		return paramMap;
	}
	
	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}
	
	
}
