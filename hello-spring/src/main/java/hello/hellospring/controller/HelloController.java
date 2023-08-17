package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
	@GetMapping("hello")
	public String hello(Model model) {
		model.addAttribute("data", "hello!!");
		return "hello"; // templates에서 찾는다.
		// 뷰 리절브가 동작하여 hello.html을 찾는다.
	}
	
	@GetMapping("hello-mvc")
	public String helloMvc(@RequestParam("name") String name, Model model) {
		model.addAttribute("name", name);
		return "hello-template";
	}
	
	@GetMapping("hello-string")
	@ResponseBody
	public String helloString(@RequestParam("name") String name) {
		return "hello " + name;
	}
	
	@GetMapping("hello-api")
	@ResponseBody
	public Hello helloApi(String name) {
		Hello hello = new Hello();
		hello.setName(name);
		return hello; // {"name" : "spring"} 이렇게 json 형식으로 넘어간다.
		// ResponseBody가 있으면 HttpMessageConveter가 동작한다. (뷰 리절브 아님)
		// 객체를 반환하면 json으로 반환된다.
	}
	
	static class Hello{ // 내부 클래스
		private String name;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
