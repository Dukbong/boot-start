package hello.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hello.core.AppConfig;

public class MemberServiceTest {
	
	MemberService memberService;
	
	@BeforeEach
	// Test 전에 실행되는 코드
	public void beforeEach() {
		AppConfig appconfig = new AppConfig();
		memberService = appconfig.memberService(); 
	}
	
	@Test
	void join() {
		// give
		Member member = new Member(1L, "memberA", Grade.VIP);
		
		// when
		memberService.join(member);
		Member findMember = memberService.findMember(1L);
		
		// then
		Assertions.assertThat(member).isEqualTo(findMember);
	}
}
