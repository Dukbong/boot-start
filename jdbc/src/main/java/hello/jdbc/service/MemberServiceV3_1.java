package hello.jdbc.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 트렌젝션 - 트랜젝션 매니저
 * @author jkmo2
 *
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV3_1 {

	private final MemberRepositoryV3 memberRepository;
	private final PlatformTransactionManager transactionManager;
	
	public void accountTransfer(String fromId, String toId, int money) throws SQLException {
		
		// 트랜젝션 시작
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		
		try {
			// 비즈니스 로직 시작
			bizLogic(fromId, toId, money);
			
			// 성공시 커밋
			transactionManager.commit(status);
		}catch(Exception e) {
			// 실패시 롤백
			transactionManager.rollback(status);
			throw new IllegalStateException(e);
		}
	}
	
	private void bizLogic(String fromId, String toId, int money) throws SQLException {
		Member fromMember = memberRepository.finbyId(fromId);
		Member toMember=  memberRepository.finbyId(toId);
		
		memberRepository.update(fromId, fromMember.getMoney() - money);
		
		if(toMember.getMameberId().equals("ex")) {
			throw new IllegalStateException("이체 중 발생한 오류");
		}
		
		memberRepository.update(toId, toMember.getMoney() + money);
	}
}
