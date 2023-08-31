package hello.exception.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApiExceptionV3Controller {
	
	// advice로 관리한다.
//	@ResponseStatus(HttpStatus.BAD_REQUEST) // 이렇게 상태코드를 변경가능하다.
//	@ExceptionHandler(IllegalArgumentException.class)
//	// 컨트롤러에서 IllegalArgumentException이 발생하면 DispatcherServlet까지 예외가 전달되고
//	// DispatcherServlet은 ExceptionHandler를 통해 처리할 수있는 게 있는지 확인한다.
//	// 있다면 @ExceptionHandler가 잡아서 처리한다.
//	// 해당 메소드의 리턴은 @RestController에 의해 json으로 반환된다.
//	// 아무것도 지정하지 않으면 정상흐름으로 판단하여 상태코드는 200이다.
//	public ErrorResult illegalExHandler(IllegalArgumentException e) {
//		log.error("[exceptionHandler] ex", e);
//		return new ErrorResult("BAD", e.getMessage());
//	}
//	@ExceptionHandler // 매개변수와 잡고싶은 Exception타입이 같다면 생략가능
//	public ResponseEntity<ErrorResult> userException(UserException e) {
//		log.error("[exceptionHandler] ex", e);
//		ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
//		return new ResponseEntity<>(errorResult,HttpStatus.BAD_REQUEST);
//	}
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
//	@ExceptionHandler // 매개변수와 잡고싶은 Exception타입이 같다면 생략가능
//	public ErrorResult exHandler(Exception e) {
//		log.error("[exceptionHandler] ex", e);
//		return new ErrorResult("EX", "내부오류");
//	}
	
	@GetMapping("/api3/members/{id}")
	public MemberDto getMember(@PathVariable("id") String id) {
		if(id.equals("ex")) {
			throw new RuntimeException("잘못된 사용자");
		}
		if(id.equals("bad")) {
			throw new IllegalArgumentException("잘못된 입력 값");
		}
		if(id.equals("user-ex")) {
			throw new UserException("사용자 오류");
		}
		
		return new MemberDto(id, "hello " + id);
	}
	
	@Data
	@AllArgsConstructor
	static class MemberDto{
		private String memberId;
		private String name;
	}
}
