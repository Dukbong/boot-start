package hello.jdbc.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 트렌젝션 - 파라미터 연동, 풀을 고려한 종료
 * @author jkmo2
 *
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV2 {

	private final MemberRepositoryV2 memberRepository;
	private final DataSource dataSource; // Connection 종료를 위해 필요
	
	public void accountTransfer(String fromId, String toId, int money) throws SQLException {
		
		Connection con = dataSource.getConnection();
		try {
			// 트렌젝션 시작
			con.setAutoCommit(false); 
			
			// 비즈니스 로직 시작
			bizLogic(con, fromId, toId, money);
			
			// 성공시 커밋
			con.commit(); 
		}catch(Exception e) {
			// 실패시 롤백
			con.rollback();
			throw new IllegalStateException(e);
		}finally {
			// 트랜젝션 종료 및 커넥션 종료
			release(con);
		}
	}
	
	private void release(Connection con) {
		if(con != null) {
			try {
				// 트렌젝션 종료
				con.setAutoCommit(true);
				con.close();
			}catch(Exception e) {
				log.info("error", e);
			}
		}
	}
	
	private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
		Member fromMember = memberRepository.finbyId(con, fromId);
		Member toMember=  memberRepository.finbyId(con, toId);
		
		memberRepository.update(fromId, fromMember.getMoney() - money);
		
		if(toMember.getMameberId().equals("ex")) {
			throw new IllegalStateException("이체 중 발생한 오류");
		}
		
		memberRepository.update(toId, toMember.getMoney() + money);
	}
}
