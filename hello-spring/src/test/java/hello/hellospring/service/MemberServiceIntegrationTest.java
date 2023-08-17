package hello.hellospring.service;

import static org.junit.jupiter.api.Assertions.fail;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

@SpringBootTest // 스프링 컨테이너와 테스를 함께 실행한다.
@Transactional 
// 테스트 케이스에 있으면 테스트 시작 전에 트랜젝션을 시작하고
// 테스트 완료 후에 항상 롤백한다.
// 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.
class MemberServiceIntegrationTest {
	
	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	
	@Test
	void join() {
		// given
		Member member = new Member();
		member.setName("hello");
		// when
		Long saveId = memberService.join(member);
		// then
		Member findMember = memberService.findOne(saveId).get();
		Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
	}
	
	@Test
	void 중복_회원_예외() {
		// given
		Member member1 = new Member();
		member1.setName("Spring");
		
		Member member2 = new Member();
		member2.setName("Spring");
		// when
		memberService.join(member1);
		try {
			memberService.join(member2);
			fail("예외가 발생하였습니다.");
		}catch(IllegalStateException e) {
			// then
			Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 이름입니다.");
		}
	}
}
