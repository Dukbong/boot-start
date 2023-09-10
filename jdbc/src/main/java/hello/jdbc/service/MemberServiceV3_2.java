package hello.jdbc.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 트렌젝션 - 트랜젝션 템플릿
 */

//@Service
public class MemberServiceV3_2 {

	private final MemberRepositoryV3 memberRepository;
//	private final PlatformTransactionManager transactionManager;
	private final TransactionTemplate txTemplate;
	
	
	public MemberServiceV3_2(MemberRepositoryV3 memberRepository, PlatformTransactionManager transactionManager) {
		this.memberRepository = memberRepository;
		this.txTemplate = new TransactionTemplate(transactionManager);
		// 트랜젝션 테믈릿은 트렌잭션 매니저가 있어야 사용할 수 있다.
	}

	public void accountTransfer(String fromId, String toId, int money) throws SQLException {
		
		txTemplate.executeWithoutResult((status)->{
			
			// 비즈니스 로직
			try {
				bizLogic(fromId, toId, money);
			}catch (SQLException e) {
				throw new IllegalStateException(e);
			}
			
		});

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
