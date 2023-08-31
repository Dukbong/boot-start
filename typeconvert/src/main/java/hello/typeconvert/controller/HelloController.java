package hello.typeconvert.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.typeconvert.type.IpPort;

@RestController
public class HelloController {

	@GetMapping("/hello-v1")
	public String helloV1(HttpServletRequest request) {
		String data = request.getParameter("data"); // 문자타입
		Integer intValue = Integer.parseInt(data); // 숫자 타입으로 변경
		System.out.println("intValue = " + intValue);
		return "ok";
	}
	
	@GetMapping("/hello-v2")
	public String helloV2(@RequestParam/*("data")*/ Integer data) {
		// 이건 스프링이 중간에서 타입을 변환해주었기 떄문이다.
		// 이러한 예는 @ModelAttribute, @PathVarable에서도 볼수 있다.
		System.out.println("intValue = " + data);
		return "ok";
	}
	
	@GetMapping("/ip-port")
	public String ipPort(@RequestParam IpPort ipPort) {
		System.out.println("ipPort IP" + ipPort.getIp());
		System.out.println("ipPort Port" + ipPort.getPort());
		return "ok";
	}
}
