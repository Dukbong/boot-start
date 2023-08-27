package hello.login.domain.login;

import java.util.Optional;

import org.springframework.stereotype.Service;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final MemberRepository memberRepository;
	
	/***
	 * @return null 로그인 실패
	 */
	public Member login(String loginId, String password) {
//		Optional<Member> findMemberOptionalV1 = memberRepository.findByLoginId(loginId);
//		
//		Member member = findMemberOptionalV1.get();
//		if(member.getPassword().equals(password)) {
//			return member;
//		}else {
//			return null;
//		}
		
		Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
		return findMemberOptional.filter(m -> m.getPassword().equals(password))
					             .orElse(null);
	}
}
