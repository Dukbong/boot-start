package hello.springmvc.basic.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;

/**
 * {"username":"hello", "age"=20}
 * Content-Type : application/json
 */
@Slf4j
@Controller
public class RequestJsonController {

	private ObjectMapper objMapper = new ObjectMapper();
	
	@PostMapping("/request-body-json-v1")
	public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServletInputStream inputstream = request.getInputStream();
		String messageBody = StreamUtils.copyToString(inputstream, StandardCharsets.UTF_8);
		
		log.info("messageBody = {}", messageBody);
		
		HelloData helloData = objMapper.readValue(messageBody, HelloData.class);
		
		log.info("username = {}, age = {}", helloData.getUsername(), helloData.getUsername());
		
		response.getWriter().write("ok");
	}
	
	@ResponseBody
	@PostMapping("/request-body-json-v2")
	public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
		log.info("messageBody = {}", messageBody);
		
		HelloData helloData = objMapper.readValue(messageBody, HelloData.class);
		
		log.info("username = {}, age = {}", helloData.getUsername(), helloData.getUsername());
		
		return "ok";
	}
	
	@ResponseBody
	@PostMapping("/request-body-json-v4")
	public String requestBodyJsonV4(HttpEntity<HelloData> helloDataEntity) throws IOException {
		HelloData helloData = helloDataEntity.getBody();
		log.info("username = {}, age = {}", helloData.getUsername(), helloData.getUsername());
		return "ok";
	}
	
	
	@ResponseBody
	@PostMapping("/request-body-json-v3")
	public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
		log.info("username = {}, age = {}", helloData.getUsername(), helloData.getUsername());
		return "ok";
		// 반환을 객체로 하면 Http 메시지 컨버터에 의해 Json으로 변환되어 Http 메시지 바디에 입력된다.
	}
	
	@ResponseBody
	@PostMapping("/request-body-json-v5")
	public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) throws IOException {
		log.info("username = {}, age = {}", helloData.getUsername(), helloData.getUsername());
		return helloData;
		// 객체가 Http메시지 컨버터에 의해 Json으로 변환되어 Http 메시지 바디에 입력된다.
		// {"username"="kim", "age"=20}
	}
}