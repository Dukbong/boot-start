package hello.springmvc.basic.response;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
// @ResponseBody >> 전체적으로 적용된다.
// @Controller + @ResponseBody = @RestController이다.
public class ResponseBodyCOntroller {

	@GetMapping("/response-body-v1")
	public void responseBodyV1(HttpServletResponse response) throws IOException {
		response.getWriter().write("ok");
	}
	
	@GetMapping("/response-body-v2")
	public ResponseEntity<String> responseBodyV2() {
		return new ResponseEntity<>("ok", HttpStatus.OK);	
	}
	
	@ResponseBody
	@GetMapping("/response-body-v3")
	public String responseBodyV3() {
		return "ok";
	}
	
	@GetMapping("/response-body-json-v1")
	public ResponseEntity<HelloData> responseBodyJsonV1(){
		HelloData helloData = new HelloData();
		helloData.setUsername("userA");
		helloData.setAge(20);
		return new ResponseEntity<>(helloData, HttpStatus.OK);
	}
	
	@ResponseStatus(HttpStatus.OK) // 상태코드를 지정할 수 있다.
	@ResponseBody
	@GetMapping("/response-body-json-v2")
	public HelloData responseBodyJsonV2(){
		/*
		 * 해당 코드의 단점은 동적으로 상태코드를 변경할 수 없다는 것이다.
		 * 만약 상태코드를 동적으로 변경하고 싶다면 Entity를 사용하는것이 좋다.
		 * */
		HelloData helloData = new HelloData();
		helloData.setUsername("userA");
		helloData.setAge(20);
		return helloData;
	}
}
