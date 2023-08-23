package hello.servlet.web.servlet.servletmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller // @component와 @ReqeustMapping 두가지를 합친 기능을 한다.
// Controller 대신 아래처럼 적어도 되긴한다.
// @Component
// @RequestMapping
public class SpringMemberFormControllerV1 {

	/*
	 * @Controller : 스프링이 자동으로 스프링 빈에 등록해준다. (내부에 Component 어노테이션을 가지고 있다.)
	 * 스프링 MVC에서 해당 클래스를 어노테이션 기반 컨트롤러로 인식한다.
	 * 
	 * 참고 :
	 * RequestMappingHandlerMapping은 스프링 빈 중에서
	 * @RequestMapping또는 @Controller가 클래스 레벨에
	 * 붙어 있는 경우에 매핑 정보로 인식한다.
	 * */
	
	@RequestMapping("/springmvc/v1/members/new-form")
	public ModelAndView process() {
		return new ModelAndView("new-form");
	}
}
