package hello.hellospring.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;

class MemberServiceTest {
	
	MemberService memberService;
	MemoryMemberRepository memberRepository;
	
	@BeforeEach
	// 각각 Test 하기 전에 실행
	public void beforeEach(){
		memberRepository = new MemoryMemberRepository();
		memberService = new MemberService(memberRepository);
		// MemberService 입장에서 DI하고 있는 것이다.
	}
	
	@AfterEach
	public void afterEach() {
		memberRepository.clearStore();
	}
	
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
	
	@Test
	void findMembers() {
		
	}
	@Test
	void findOne() {
		
	}
}
