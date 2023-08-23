package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

	/**
	 * 요청 매핑 공부 겸 API 예시
	 * 
	 * 회원 목록 조회 : GET    '/users'
	 * 회원 등록 :    POST   '/users'
	 * 회원 조회 :    GET    '/users/{userId}'
	 * 회원 수정 :    PATCH  '/users/{userId}'
	 * 회원 삭제 :    DELETE '/users/{userId}'
	 */
	
	//@GetMapping("/mapping/users")
	@GetMapping
	public String user() {
		return "get users";
	}
	
	//@PostMapping("/mapping/users")
	@PostMapping
	public String addUser() {
		return "post user";
	}
	
	//@GetMapping("/mapping/users/{userId}")
	@GetMapping("/{userId}")
	public String findUser(@PathVariable String userId) {
		return "get userId" + userId;
	}
	
	//@PatchMapping("/mapping/users/{userId}")
	@PatchMapping("/{userId}")
	public String updateUser(@PathVariable String userId) {
		return "patch userId" + userId;
	}
	
	//@DeleteMapping("/mapping/users/{userId}")
	@DeleteMapping("/{userId}")
	public String deleteUser(@PathVariable String userId) {
		return "delete userId" + userId;
	}
}
