package hello.jdbc.repository;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberRepositoryV0Test {

	MemberRepositoryV0 repository = new MemberRepositoryV0();
	
	@Test
	void crud() throws SQLException {
		
		// save
		Member member = new Member("memberV0", 10000);
		repository.save(member);
		
		
		// findById
		Member findMember = repository.finbyId(member.getMameberId());
		log.info("findMember=  {}", findMember);
		
		Assertions.assertThat(findMember).isEqualTo(member);
		
		// update 10000 -> 20000
		repository.update(member.getMameberId(), 20000);
		Member updateMember = repository.finbyId(member.getMameberId());
		
		Assertions.assertThat(updateMember.getMoney()).isEqualTo(20000);
		
		// delete
		repository.delete(member.getMameberId());
		Assertions.assertThatThrownBy(()->repository.finbyId(member.getMameberId()))
				  .isInstanceOf(NoSuchElementException.class);
	}
}
