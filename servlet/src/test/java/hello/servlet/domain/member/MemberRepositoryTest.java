package hello.servlet.domain.member;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberRepositoryTest {

	MemberRepository memberRepository = MemberRepository.getInstance();
	
	@AfterEach // 단위 테스트 마다 완료 후 실행 될 코드
	void afterEach() {
		memberRepository.clearStore();
	}
	
	@Test
	@DisplayName("저장 테스트")
	void save() {
		// given
		Member member = new Member("hello", 20);
		// when
		Member saveMember = memberRepository.save(member);
		// then
		Member findMember = memberRepository.findById(saveMember.getId());
		Assertions.assertThat(findMember).isEqualTo(saveMember);
	}
	
	@Test
	@DisplayName("전체 조회 테스트")
	void findAll() {
		// given
		Member member1 = new Member("member1", 10);
		Member member2 = new Member("member2", 20);
		memberRepository.save(member1);
		memberRepository.save(member2);
		// when
		List<Member> list = memberRepository.findAll();
		// then
		Assertions.assertThat(list.size()).isEqualTo(2);
		Assertions.assertThat(list).contains(member1, member2); // List안에 member1, member2가 있냐.
	}
}
