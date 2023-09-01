package hello.jdbc.connection;

import java.sql.Connection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBConnectionUtilTest {

	
	@Test
	void connection() {
		Connection connection = DBConnectionUtil.getConnection();
		// DB와 연결되있는지 확인하는 것이다.
		
		Assertions.assertThat(connection).isNotNull();
	}
}
