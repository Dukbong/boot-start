package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")
// reason = 에러 메시지이다.
// messages.properties에서 가져올 수 있다.
public class BadRequestException extends RuntimeException {

}
