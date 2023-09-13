package hello.jdbc.exception.basic;

import java.sql.SQLException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnCheckedAppTest {
	
	// 예외 포함 스택 트레이스 (중요)
	@Test
	void printEx() {
		Controller controller = new Controller();
		try {
			controller.request();
		}catch(Exception e) {
			log.info("ex ", e);
		}
	}
	
	
	@Test
	void checked() {
		Controller con = new Controller();
		Assertions.assertThatThrownBy(()->{
			con.request();
		}).isInstanceOf(RuntimeException.class);
	}

	static class Controller{
		Service service = new Service();
		
		public void request(){
			service.logic();
		}
	}
	
	// service는 정말 어디에도 의존 하지 않고 작성되는게 좋다.
	// 체크 예외를 던지는 경우 service도 레파지토리에서 사용하는 기술의 예외를 의존하게 된다.
	// 그리고 레파지토리 기술이 변경되면 체크 예외가 달라지기 때문에
	// 모든 것들을 다 변경해줘야하지만 체크 예외로 하면 이런 경우를 방지할 수 있다.
	static class Service{
		Repository repo = new Repository();
		NetworkClient network = new NetworkClient();
		
		public void logic() {
			repo.call();
			network.call();
		}
	}
	static class NetworkClient{
		public void call()  {
			throw new RuntimeConnectException("연결 실패");
		}
		
	}
	static class Repository{
		public void call(){
			try {
				runSQL();
			} catch (SQLException e) {
				// 예외를 변경하거나 할때 기존 예외를 매개변수로 넣어줘야한다.
				throw new RuntimeSQLException(e);
			}
		}
		
		public void runSQL() throws SQLException {
			throw new SQLException("ex");
		}
	}
	
	static class RuntimeConnectException extends RuntimeException{
		public RuntimeConnectException(String message) {
			super(message);
		}
	}
	static class RuntimeSQLException extends RuntimeException{
		public RuntimeSQLException(Throwable cause) {
			super(cause);
		}
	}
}
