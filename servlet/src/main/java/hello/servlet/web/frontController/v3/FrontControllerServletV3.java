package hello.servlet.web.frontController.v3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontController.ModelView;
import hello.servlet.web.frontController.MyView;
import hello.servlet.web.frontController.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontController.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontController.v3.controller.MemberSaveControllerV3;

@WebServlet(name="frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

	private Map<String, ControllerV3> controllerMap = new HashMap<>();
	/* v3
	 * 0. 클라이언트 요청
	 * 1. URL 매핑 정보에서 컨트롤러 조회
	 * 2. 프런트 컨트롤로에서 알맞는 컨트롤러 호출
	 * 3. 컨트롤러에서 비즈니스 로직 수행 후 ModelView를 반환
	 * 4. 프런트 컨트롤러에서 ModelView에서 뷰의 이름을 꺼내서 viewResolver로 이름 완성
	 * 5. MyView에 render()메서드의 매개변수로 model(Map)까지 넘겨준다.
	 * 6. MyView 내부에서 model을 가지고 다 setAttribute해서 저장한 후
	 * 7. JSP foward
	 * 6. 클라이언트 응답
	 * */
	public FrontControllerServletV3() {
		controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
		controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
		controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("FrontControllerServletV1.service");
		
		String requestURI = request.getRequestURI();
		// URI = /front-controller/v1/members 이런식으로 나온다.
		
		ControllerV3 controller = controllerMap.get(requestURI);
		
		if(controller == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		Map<String, String> paramMap = createParamMap(request);
		
		ModelView mv = controller.process(paramMap);
		
		String viewName = mv.getViewName(); // 논리이름 ex) new-form
		
		MyView view = viewResolver(viewName);
		
		view.render(mv.getModel(), request, response);
	}
	
	private Map<String, String> createParamMap(HttpServletRequest request){
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator()
		.forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
		return paramMap;
	}
	
	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}
}
