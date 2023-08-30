package hello.exception.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ErrorPageController {

	// RequestDispatcher 상수로 정의되어있다.
	public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
	public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
	public static final String ERROR_MESSAGE = "javax.servlet.error.message";
	public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
	public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
	public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

	
	@RequestMapping("/error_page/404")
	public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
		log.info("errorPage 404");
		printErrorInfo(request);
		return "/error_page/404";
	}
	
	@RequestMapping("/error_page/500")
	public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
		log.info("errorPage 500");
		printErrorInfo(request);
		return "/error_page/500";
	}
	
	@RequestMapping(value = "/error_page/500", produces = MediaType.APPLICATION_JSON_VALUE)
	// produces는 클라이언트가 보내는 Accept가 application/json이면 해당 컨트롤러를 실행한다는 뜻이다.
	public ResponseEntity<Map<String, Object>> errorPage500Api(HttpServletRequest request, HttpServletResponse response){
		log.info("API error Page 500");
		Map<String, Object> result = new HashMap<>();
		Exception ex = (Exception) request.getAttribute(ERROR_EXCEPTION);
		result.put("status", request.getAttribute(ERROR_STATUS_CODE));
		result.put("message", ex.getMessage());
		
		Integer statusCode = (Integer)request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));
	}
	private void printErrorInfo(HttpServletRequest request) {
		log.info("ERROR_EXCEPTION : {}", request.getAttribute(ERROR_EXCEPTION));
		log.info("ERROR_EXCEPTION_TYPE : {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
		log.info("ERROR_MESSAGE : {}", request.getAttribute(ERROR_MESSAGE));
		log.info("ERROR_REQUEST_URI : {}", request.getAttribute(ERROR_REQUEST_URI));
		log.info("ERROR_SERVLET_NAME : {}", request.getAttribute(ERROR_SERVLET_NAME));
		log.info("ERROR_STATUS_CODE : {}", request.getAttribute(ERROR_STATUS_CODE));
		log.info("DispatchType = {}", request.getDispatcherType());
	}
}
