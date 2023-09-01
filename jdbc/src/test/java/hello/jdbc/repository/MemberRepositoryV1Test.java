package hello.jdbc.repository;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariDataSource;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberRepositoryV1Test {

	MemberRepositoryV1 repository;
	
	@BeforeEach
	void beforeEach() {
//		기본 DriverManagerDataSource - 항상 새로운 커넥션을 획득
//		단점 : 할때마다  새로운 커넥션을 획득하여 진행하기 때문에 성능에 좋지 않다.
//		DriverManagerDataSource dataSource = new DriverManagerDataSource(ConnectionConst.URL,
//																 		 ConnectionConst.USERNAME,
//																		 ConnectionConst.PASSWORD);
		
//		커넥션 풀링
//		close를 하면 종료가 아닌 커넥션을 커넥션 풀(HikariDataSource)에 반납하는데
//		현재 테스트 코드를 보면 순차적으로 사용후 close를 하기 때문에
//		반납한 것을 다시 재 사용하는 걸 볼 수 있다.
//		만약 멀티 스레드 환경이라면 다른 대기중에 커넥션을 사용할 것이다.
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(ConnectionConst.URL);
		dataSource.setUsername(ConnectionConst.USERNAME);
		dataSource.setPassword(ConnectionConst.PASSWORD);
		dataSource.setMaximumPoolSize(10);
		dataSource.setPoolName("MyPool");
		
		repository = new MemberRepositoryV1(dataSource);
	}
	
	@Test
	void crud() throws SQLException {
		
		// save
		Member member = new Member("memberV0", 10000);
		repository.save(member);
		
		
		// findById
		Member findMember = repository.finbyId(member.getMameberId());
		log.info("findMember=  {}", findMember);
		
		Assertions.assertThat(findMember).isEqualTo(member);
		
		// update 10000 -> 20000
		repository.update(member.getMameberId(), 20000);
		Member updateMember = repository.finbyId(member.getMameberId());
		
		Assertions.assertThat(updateMember.getMoney()).isEqualTo(20000);
		
		// delete
		repository.delete(member.getMameberId());
		Assertions.assertThatThrownBy(()->repository.finbyId(member.getMameberId()))
				  .isInstanceOf(NoSuchElementException.class);
	}
}
