package hello.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionTest {

	@Test
	void driverManager() throws SQLException {
		// 단점 : 
		// DriverManager는 커넥션을 획득할 때 마다 url, username, password를 전달해야한다.
		
		Connection con1 = DriverManager.getConnection(ConnectionConst.URL,
													  ConnectionConst.USERNAME,
													  ConnectionConst.PASSWORD);
		Connection con2 = DriverManager.getConnection(ConnectionConst.URL,
													  ConnectionConst.USERNAME,
													  ConnectionConst.PASSWORD);
		
		log.info("connection1 = {}, class = {}",con1, con1.getClass());
		log.info("connection2 = {}, class = {}",con2, con2.getClass());
	}
	
	@Test
	void dataSourceDriverManager() throws SQLException, InterruptedException {
		// DriverManager 단점을 보완
		// DataSource를 생성할때만 url, username, password를 입력한 후
		// getConnection()으로 호출만 하면 커넥션을 획득할 수 있다.
		// 중요 : 설정과 사용이 분리되어있다.
		
		// DriverManagerDataSource - 항상 새로운 커넥션을 획득
		// DriverManagerDataSource는 DataSource를 구현한 객체이다.
		// Spring에서 제공한다.
		
		// 설정
		DriverManagerDataSource dataSource = new DriverManagerDataSource(ConnectionConst.URL,
																		 ConnectionConst.USERNAME,
																		 ConnectionConst.PASSWORD);
		useDataSource(dataSource);
		Thread.sleep(1000); // pool에 커넥션들이 추가되는걸 보고 싶어서 하는 거다.
		// pool에 커넥션을 추가하는 것은 별도의 스레드에서 진행하게 되는데
		// 너무 빨라서 추가되는 로그가 보이지 않고 종료되버린다.
		// 이를 보기위해서는 Thread를 잠시 멈춰야 한다.
	}
	
	@Test
	void dataSourceConnectionPool() throws SQLException {
		// 커넥션 풀링
		// HikariDataSource도 DataSource를 구현한 객체이다.
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(ConnectionConst.URL);
		dataSource.setUsername(ConnectionConst.USERNAME);
		dataSource.setPassword(ConnectionConst.PASSWORD);
		dataSource.setMaximumPoolSize(10); // 기본값이 10이다.
		dataSource.setPoolName("MyPool");
		
		useDataSource(dataSource);
	}
	
	private void useDataSource(DataSource dataSource) throws SQLException {
		// 하나의 DriverMangerDataSource로 만들었지만 서로 다른 커넥션 2개가 생성되는걸 볼 수 있다.
		
		// 사용
		Connection con1 = dataSource.getConnection();
		Connection con2 = dataSource.getConnection();
		log.info("connection1 = {}, class = {}",con1, con1.getClass());
		log.info("connection2 = {}, class = {}",con2, con2.getClass());
	}
}
