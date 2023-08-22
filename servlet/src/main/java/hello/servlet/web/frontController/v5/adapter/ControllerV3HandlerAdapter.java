package hello.servlet.web.frontController.v5.adapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontController.ModelView;
import hello.servlet.web.frontController.v3.ControllerV3;
import hello.servlet.web.frontController.v5.MyHandlerAdapter;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {

	@Override
	public boolean supports(Object handler) {
		return handler instanceof ControllerV3;
		// ControllerV3가 handler 본인 또는 자식인지 판단 
	}

	@Override
	public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException, IOException {
		ControllerV3 controller = (ControllerV3)handler; // 프런트 컨트롤러에서 supports 메소드로 타입체크 했음
		Map<String, String> paramMap = createParamMap(request);
		ModelView mv = controller.process(paramMap); // 컨트롤러(핸들러) 호출
		return mv;
	}
	
	private Map<String, String> createParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator()
		.forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
		return paramMap;
	}

}
