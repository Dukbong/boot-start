package hello.servlet.web.servlet.springmvc.old;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

@Component("/springmvc/old-controller") // 스프링 빈에 등록될 이름
public class OldController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("OldController.handlerReqeust");
		return new ModelAndView("new-form");
	}
	
	/*
	 * SpringMVC에서 HandlerMapping
	 * 1. 핸들러 매핑에서 이 컨트롤러를 찾을 수 있어야한다.
	 * ex) 스프링 빈의 이름으로 핸들러를 찾을 수 있는 핸들러 매핑이 필요하다.
	 * 우선 순위 
	 * 1순위 RequestMappingHandlerMapping : @RequestMapping
	 * 2순위 BeanNameUrlHandlerMapping : 스프링 빈의 이름으로 핸들러 찾는다.
	 * 
	 * HandlerAdapter
	 * 1. 핸들러 매핑을 통해서 찾은 핸들러를 실행 할 수 있는 핸들러 어탭터가 필요하다.
	 * ex) Controller 인터페이스를 실행할 수 있는 핸들러 어탭터를 찾고 실행해야한다.
	 * 우선순위
	 * 1순위 RequestMappingHandlerAdapter : @RequsetMapping
	 * 2순위 HttpRequestHandlerAdapter : HttpRequestHandler 처리
	 * 3순위 SimpleControllerHandlerAdapter : Controller 인터페이스 처리 (과거에 사용하던 방식)*/

} 
