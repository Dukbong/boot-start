package hello.jdbc.exception.basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnCheckedException {
	
	@Test
	void unchecked_catch() {
		Service service = new Service();
		service.callCatch();
	}
	
	@Test
	void unchecked_throw() {
		Service service = new Service();
		Assertions.assertThatThrownBy(()->{
			service.callThrow();
		}).isInstanceOf(MyUnCheckedException.class);
		
	}
	
	/**
	 * RunTimeException을 상속하면 언체크 예외가 된다.
	 */
	static class MyUnCheckedException extends RuntimeException{
		
		public MyUnCheckedException(String message){
			super(message);
		}
	}
	
	/*
	 * Unchecked 예외는
	 * 잡거나 던지지 않아도 된다.
	 */
	static class Service{
		Repository repo = new Repository();
		public void callCatch() {
			try {
				repo.call();
			}catch(MyUnCheckedException e) {
				log.info("예외 처리, message = {}", e.getMessage(), e);
			}
		}
		
		// 언체크 예외는 체크와 다르게 throws로 던지지 않아도 호출한 곳으로 던져진다.
		public void callThrow() {
			repo.call();
		}
	}
	
	static class Repository{
		public void call() {
			throw new MyUnCheckedException("Ex");
		}
	}

}
