package hello.springmvc.basic.requestmapping;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MappingController {

	//private Logger log = LoggerFactory.getLogger(MappingController.class);
	
	@RequestMapping("/hello-basic")
	// @RequestMapping({"/hello-basic", "/hello-go"})
	// 이런식으로 여러개도 가능하다.
	public String helloBasic() {
		log.info("hello Basic");
		return "ok";
	}
	
	@RequestMapping(value="/mapping-get-v1", method=RequestMethod.GET)
	public String mappingGetV1() {
		log.info("hello Basic");
		return "ok";
	}
	
	/**
	 * 편리한 축약 어노테이션(코드보기)
	 * @GetMapping
	 * @PostMapping
	 * @PutMapping
	 * @DeleteMapping
	 * @PatchMapping
	 */
	@GetMapping("/hello-basic")
	public String mappingGetV2() {
		log.info("hello Basic");
		return "ok";
	}
	
	/**
	 * PathVariable 사용
	 * 변수명이 같으면 생략 가능
	 * @PathVariable("userId") String userId -> @PathVariable userId
	 * /mapping/userA
	 */
	@GetMapping("/mapping/{userId}")
	public String mappingPath(@PathVariable("userId") String data) {
		log.info("mappingPath = {}", data);
		return "ok";
	}
	
	/**
	 * PathVariable 다중 매핑
	 */
	@GetMapping("/mapping/users/{userId}/orders/{orderId}")
	public String mappingPath(
			@PathVariable String userId,
			@PathVariable Long orderId) {
		log.info("mappingPath userId = {}, orderId= {}", userId, orderId);
		return "ok";
	}
	
	/**
	 * 특정 param으로 추가 매핑
	 * params = "mode",
	 * params = "!mode",
	 * params = "mode=debug",
	 * params = mode!=debug
	 */
	@GetMapping(value="/mapping-param", headers="mode=debug")
	// /mapping-param?mode=debug 라고 해야 요청 된다.
	public String mappingParam() {
		log.info("mappingHeader");
		return "ok";
	}
	
	/**
	 * 특정 헤더로 추가 매핑
	 * headers = "mode",
	 * headers = "!mode",
	 * headers = "mode=debug",
	 * headers = mode!=debug
	 */
	@GetMapping(value="/mapping-header", headers="mode=debug")
	// 헤더 정보에 key = mode, value = debug가 있어야 요청된다.
	public String mappingHeader() {
		log.info("mappingHeader");
		return "ok";
	}
	
	// 미디어 타입 조건 매핑 - HTTP 요청 Content-Type, consume
	/**
	 * Content-Type 헤더 기반 추가 매핑 Media Type
	 * consume="application/json"
	 * consume="!application/json"
	 * consume="application/*"
	 * consume="*\/*"
	 * MediaType.APPLICATION_JSON_VALUE
	 */
	@PostMapping(value="/mapping-consume", consumes = "application/json")
	// 헤더 정보에 Content-Type이 application/json이여야 요청된다.
	public String mappingConsume() {
		log.info("mappingConsume");
		return "ok";
	}
	
	/**
	 * Accept 헤더 기반  Media Type
	 * produces="text/html"
	 * produces="!text/html"
	 * produces="text/*"
	 * produces="*\/*"
	 */
	//@PostMapping(value="/mapping-produce", produces = "text/html")
	@PostMapping(value="/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
	// 헤더 정보에 Accept가 text/html이여야 요청된다.
	public String mappingProduces() {
		log.info("mappingProduces");
		return "ok";
	}
	
	// consumes와 produces는 MediaType객체안에 여러가지가 정의되어 있기 때문에
	// 문자로 적는거 보다는 찾아서 사용하는게 바람직하다.
}
