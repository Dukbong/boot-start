package hello.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 이 어노테이션은 lombok의 어노테이션이다.
@RestController
public class LogTestController {

	//@Slf4j 어노테이션을 사용하면 해당 객체를 알아서 만들어준다.
	//private final Logger log = LoggerFactory.getLogger(LogTestController.class);
	//private final Logger log = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/log-test")
	public String logTest() {
		String name = "Spring";
		
		System.out.println("name = " + name);
		
		// 잘못된 log 사용법
		log.trace("trace log = " + name);
		
		// 올바른 log 사용법
		log.trace("trace log = {}",name);
		log.debug("debug log = {}",name);
		log.info("info log = {}",name);
		log.warn("warn log = {}",name);
		log.error("error log = {}", name);
		
		return "OK";
	}
}
