package hello.springmvc.basic.request;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RequestParamController {

	
	@RequestMapping("/request-param-v1")
	public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		int age = Integer.parseInt(request.getParameter("age"));
		
		log.info("username = {}, age = {}", username, age);
		
		response.getWriter().write("ok");
	}
	
	@ResponseBody // @RestController와 같은 역할이다.
	// return한 값을 HTTP Message Body에 입력한다.
	@RequestMapping("/request-param-v2")
	public String requestParamV2(@RequestParam("username") String memberName,
							   @RequestParam("age") int memberAge)
	{
		log.info("username = {}, age = {}", memberName, memberAge);
		return "ok";
	}
	
	
	@ResponseBody
	@RequestMapping("/request-param-v3")
	public String requestParamV3(@RequestParam String username,
								 @RequestParam int age)
	{
		log.info("username = {}, age = {}", username, age);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/request-param-v4")
	public String requestParamV4(String username, int age)
	// 이렇게 @RequestParam을 생략이 가능한 것들은 String, int, Integer 등의 단순 타입일때이다.
	{
		log.info("username = {}, age = {}", username, age);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/request-param-required")
	public String requestParamRequired(
				@RequestParam(required = true) String username,
				@RequestParam(required = false) Integer age)
	// required옵션은 기본이 true이다.
	// 옵션을 false로 두면 아래와 같은 요청에도 가능하다.
	// /request-param-required?username="kim"
	// 이렇게 age가 없어도 된다.
	// 하지만 이럴경우 int 대신 Integer를 써야한다.
	// 이유는 없는경우 null을 반환하는데 int는 null을 받을 수 없지만 int를 객체화한 Integer은 null도 받을 수 있다.
	
	// 참고사항 : 지금 코드에서 username= 까지만 적어도 빈 문자열이 들어간다.
	// 빈문자열은 null이  아니기때문에 가능하다.
	{
		log.info("username = {}, age = {}", username, age);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/request-param-default")
	public String requestParamDefault(
			@RequestParam(required = true, defaultValue="guest") String username,
			@RequestParam(required = false, defaultValue="-1") int age)
	// 값이 없다면 defaultValue를 넣어준다.
	// 만약 defaultValue가 있다면 required 옵션이 필요가 없어진다.
	
	// 참고사항 : 지금 코드에서 username= 까지만 작성하면 required 옵션만 했을때랑 다르게
	// defaultValue 옵션은 빈문자열과 null 두가지 모두 defaultValue로 대체한다.
	{
		log.info("username = {}, age = {}", username, age);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/request-param-map")
	public String requestParamMap(@RequestParam Map<String, Object> paramMap)
	// 만약 username=kim&username=jang 이런식으로 넘어온다면
	// MultiValueMap을 사용하면 된다.
	// 파라미터 값이 1개가 확실할때는 Map을 아닐경우 MultiValueMap을 사용하는게 좋다.
	{
		log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));
		return "ok";
	}
	
	
}
