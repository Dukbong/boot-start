package hello.servlet.web.frontController.v5.adapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontController.ModelView;
import hello.servlet.web.frontController.v4.ControllerV4;
import hello.servlet.web.frontController.v5.MyHandlerAdapter;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {

	@Override
	public boolean supports(Object handler) {
		return handler instanceof ControllerV4;
		// ControllerV4가 handler 본인 또는 자식인지 판단 
	}

	@Override
	public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException, IOException {
		ControllerV4 controller = (ControllerV4)handler; // 프런트 컨트롤러에서 supports 메소드로 타입체크 했음
		Map<String, String> paramMap = createParamMap(request);
		Map<String, Object> model = new HashMap<>();
		
		String viewName = controller.process(paramMap, model); // 컨트롤러(핸들러) 호출
		
		ModelView mv = new ModelView(viewName);
		mv.setModel(model);
		
		return mv;
	}
	
	private Map<String, String> createParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator()
		.forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
		return paramMap;
	}

}
