package hello.exception.servlet;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//@Component
// 스프링 부트에서 편하게 작업하기 위해 주석
// 서블릿으로 할때는 있어야한다.
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error_page/404");
		// 404가 뜨면 컨트롤러를 실행하는데 /error-page/400 매핑 주소를 가진 메소드를 실행해라
		ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error_page/500");
		
		ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error_page/500");
		// 이렇게 예외 클래스를 넣으면 해당 예외의 자식 예외들도 모두 해당된다.
		// RuntimeException의 자식은 UnCheckedException이다.
		
		factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
	}

}
