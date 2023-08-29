package hello.exception.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ServletExController {

	@GetMapping("/error_ex")
	public void errorEx() {
		throw new RuntimeException("예외 발생");
	}
	
	@GetMapping("/error_404")
	public void error404(HttpServletResponse response) throws IOException {
		response.sendError(404, "404 오류!");
	}
	
	@GetMapping("/error_500")
	public void error500(HttpServletResponse response) throws IOException {
		response.sendError(500);
	}
}
