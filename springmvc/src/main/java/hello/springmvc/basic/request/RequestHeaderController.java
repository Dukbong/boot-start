package hello.springmvc.basic.request;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RequestHeaderController {

	@RequestMapping("/headers")
	public String headers(HttpServletRequest request,
						  HttpServletResponse response,
						  HttpMethod httpMethod,
						  Locale locale,
						  @RequestHeader MultiValueMap<String, String> headerMap,
						  @RequestHeader("host") String host,
						  @CookieValue(value = "myCookie", required = false) String cookie)
	{
		/**
		 * MultiValueMap
		 * Map과 유사한데 하나의 키에 여러 값을 받을 수 있는 구조이다.
		 * 
		 * MultiValueMap<String, String> map = new LinkedMultiValueMap();
		 * map.add("keyA", "valueA");
		 * map.add("keyA", "valueB");
		 * 
		 * List<String> values = map.get("keyA");
		 * 값을 꺼내면 List로 반환한다.
		 */
		log.info("request={}",request);
		log.info("response={}",response);
		log.info("httpMethod={}",httpMethod);
		log.info("Locale={}",locale);
		log.info("headerMap={}",headerMap);
		log.info("header={}",host);
		log.info("myCookie={}",cookie);
		
		return "ok";
	}
}
