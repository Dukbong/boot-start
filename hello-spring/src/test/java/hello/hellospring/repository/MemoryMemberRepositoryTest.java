package hello.hellospring.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hello.hellospring.domain.Member;

class MemoryMemberRepositoryTest {
	
	MemoryMemberRepository repository = new MemoryMemberRepository();
	
	@AfterEach
	// AfterEach는 테스트 하나 끝날때 마다 실행된다.
	public void afterEach() {
		repository.clearStore();
	}
	
	/*
	 * 주의 사항
	 * - 테스트 코드는 순서가 보장되지 않는다.
	 * - 그러기 때문에 항상 하나의 테스트가 끝나면 초기화 해줘야한다.
	 * */
	
	@Test
	public void save() {
		Member member = new Member();
		member.setName("spring");
		
		repository.save(member);
		
		Member result = repository.findById(member.getId()).get();
		
		// System.out.println("result = " + (result == member));
		
		// Assertions.assertEquals(member, result);
		Assertions.assertThat(member).isEqualTo(result);
	}
	
	@Test
	public void findByName() {
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);
		
		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);
		
		Member result = repository.findByName("spring1").get();
		
		Assertions.assertThat(result).isEqualTo(member1);
	}
	
	@Test
	public void findAll() {
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);
		
		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);
		
		List<Member> result = repository.findAll();
		
		Assertions.assertThat(result.size()).isEqualTo(2);
	}
}
