package hello.jdbc.exception.basic;

import java.net.ConnectException;
import java.sql.SQLException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CheckedAppTest {
	
	@Test
	void checked() {
		Controller con = new Controller();
		Assertions.assertThatThrownBy(()->{
			con.request();
		}).isInstanceOf(Exception.class);
	}

	static class Controller{
		Service service = new Service();
		
		public void request() throws ConnectException, SQLException {
			service.logic();
		}
	}
	
	static class Service{
		Repository repo = new Repository();
		NetworkClient network = new NetworkClient();
		
		public void logic() throws SQLException, ConnectException {
			repo.call();
			network.call();
		}
	}
	static class NetworkClient{
		public void call() throws ConnectException {
			throw new ConnectException("연결 실패");
		}
		
	}
	static class Repository{
		public void call() throws SQLException {
			throw new SQLException("ex");
		}
	}
}
