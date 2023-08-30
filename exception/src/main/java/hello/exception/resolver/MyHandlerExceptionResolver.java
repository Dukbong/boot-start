package hello.exception.resolver;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		try {
			if (ex instanceof IllegalArgumentException) {
				log.info("IllegalArgumentException resolver to 400");
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				// sc_bad_request는 400 error이다.
				return new ModelAndView();
				// 이렇게 빈 ModelAndView를 반환하면 DispatcherServlet은 뷰를 렌더링하지 않고 정상흐름으로 서블릿이 리턴된다.
			}
		} catch (IOException e) {
			log.info("resolver ex", e);
		}
		return null;
		// null일경우 다른 ExceptionResolver를 찾서 실행한다.
		// 만약 처리할 수 있는 ExceptionResolver가 없으면 예외 처리가 안되고 기존에 발생한 에외를 서블릿 밖으로 던진다.
		// 그럼 그냥 서버 내부에서 오류가 발생한것이기 떄문에 500에러가 발생하는거다.
	}

}
