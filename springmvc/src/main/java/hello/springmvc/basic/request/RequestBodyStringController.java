package hello.springmvc.basic.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RequestBodyStringController {

	@PostMapping("/request-body-string-v1")
	public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		
		log.info("message Body = {}", messageBody);
		
		response.getWriter().write("ok");
	}
	
	@PostMapping("/request-body-string-v2")
	public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
		// spring MVC는 InputStream(Reader) : HTTP 요청 메시지의 body내용을 직접 조회 가능
		// spring MVC는 OutputStream(Writer) : HTTP 응답 메시지의 바디에 직접 결과 출력 가능
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		
		log.info("message Body = {}", messageBody);
		
		responseWriter.write("ok");
	}
	
	@PostMapping("/request-body-string-v3")
	public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
		String body = httpEntity.getBody();
		log.info("message Body = {}", body);
		return new HttpEntity<>("ok");
	}
	
	// 가장  많이 사용하는 편리한 방법
	@ResponseBody
	@PostMapping("/request-body-string-v4")
	public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
		log.info("message Body = {}", messageBody);
		return "ok";
	}
}
