package hello.exception.exhandler.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import hello.exception.api.ApiExceptionV3Controller;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RestControllerAdvice(basePackages = "hello.exception.api")
@RestControllerAdvice(assignableTypes = {ApiExceptionV3Controller.class})
//@ControllerAdvice + @ResponseBody 이다.
//@ControllerAdvice에 대상을 지정하지 않으면 모든 컨트롤러에 적용된다.
//지정방법 : annotataion 유무, 패키지, 클래스 지정
public class ExControllerAdvice {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 이렇게 상태코드를 변경가능하다.
	@ExceptionHandler(IllegalArgumentException.class)
	public ErrorResult illegalExHandler(IllegalArgumentException e) {
		log.error("[exceptionHandler] ex", e);
		return new ErrorResult("BAD", e.getMessage());
	}
	@ExceptionHandler // 매개변수와 잡고싶은 Exception타입이 같다면 생략가능
	public ResponseEntity<ErrorResult> userException(UserException e) {
		log.error("[exceptionHandler] ex", e);
		ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
		return new ResponseEntity<>(errorResult,HttpStatus.BAD_REQUEST);
	}
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler // 매개변수와 잡고싶은 Exception타입이 같다면 생략가능
	public ErrorResult exHandler(Exception e) {
		log.error("[exceptionHandler] ex", e);
		return new ErrorResult("EX", "내부오류");
	}
}
