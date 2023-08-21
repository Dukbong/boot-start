package hello.core.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

	private final LogDemoService logDemoService;
//	private final MyLogger myLogger;
	// 에러 발생 : request scope는 요청이와야 생성하는데 서버 실행단계에서는 아무 요청이없다.
	// 그러므로 의존관계 주입시 빈을 조회하는데 없어서 에러가 발생한다.
	// 이 문제는 Provider를 사용해서 지연시키는 방법이 있다.
//	private final ObjectProvider<MyLogger> myLoggerProvider;
	
	// proxyMode로 해보기.
	private final MyLogger myLogger;
	
	@RequestMapping("log-demo")
	@ResponseBody
	public String logDemo(HttpServletRequest request) {
		String requestURL = request.getRequestURI().toString();
		// 요청한 URL을 반환 받는다.
		System.out.println("MyLogger = " + myLogger.getClass());
//		MyLogger myLogger = myLoggerProvider.getObject();
		myLogger.setRequestURL(requestURL);
		
		myLogger.log("controller test");
		logDemoService.logic("testId");
		return "OK";
	}
}
