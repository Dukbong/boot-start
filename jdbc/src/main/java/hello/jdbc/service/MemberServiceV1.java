package hello.jdbc.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceV1 {

	private final MemberRepositoryV1 memberRepository;
	
	public void accountTransfer(String fromId, String toId, int money) throws SQLException {
		Member fromMember = memberRepository.finbyId(fromId);
		Member toMember=  memberRepository.finbyId(toId);
		
		memberRepository.update(fromId, fromMember.getMoney() - money);
		
		if(toMember.getMameberId().equals("ex")) {
			throw new IllegalStateException("이체 중 발생한 오류");
		}
		
		memberRepository.update(toId, toMember.getMoney() + money);
		
		
	}
	
}
