package hello.jdbc.exception.basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CheckedTest {

	@Test
	void checked_catch() {
		Service service = new Service();
		service.callCatch(); 
	}
	
	@Test
	void checked_throw() {
		Service service = new Service();
		Assertions.assertThatThrownBy(()->{
			service.callThrow();
		});
	}
	
	/**
	 * Exception을 상속받은 예외는 체크 예외가 된다.
	 */
	static class MyCheckedException extends Exception{
		
		public MyCheckedException(String message) {
			super(message);
		}
		
	}
	
	/**
	 * Checked 예외는
	 * 필수로 던지거나 처리해줘야한다.
	 * @author jkmo2
	 *
	 */
	
	static class Service{
		Repository repository = new Repository();
		
		/**
		 * 예외를 잡아서 처리하는 코드
		 */
		public void callCatch() {
			try {
				repository.call();
			} catch (MyCheckedException e) {
				// 예외 처리 로직
				log.info("예외처리, message {}", e.getMessage(), e);
				e.printStackTrace();
			}
		}
		
		/**
		 * 체크 예외를 밖으로 던지는 코드
		 */
		
		public void callThrow() throws MyCheckedException {
			repository.call();
		}
	}
	
	static class Repository{
		public void call() throws MyCheckedException {
			throw new MyCheckedException("ex");
		}
	}
}
