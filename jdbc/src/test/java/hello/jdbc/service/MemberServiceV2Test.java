package hello.jdbc.service;

import java.sql.SQLException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.extern.slf4j.Slf4j;

/**
 * 트렌젝션 - 커넥션 파라미터 전달 방식
 */
@Slf4j
public class MemberServiceV2Test {

	public static final String MEMBER_A = "memberA";
	public static final String MEMBER_B = "memberB";
	public static final String MEMBER_EX = "ex";
	
	private MemberRepositoryV2 memberRepository;
	private MemberServiceV2 memberService;
	
	@BeforeEach
	void befor() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource(ConnectionConst.URL,
																		 ConnectionConst.USERNAME,
																		 ConnectionConst.PASSWORD);
		
		memberRepository = new MemberRepositoryV2(dataSource);
		memberService = new MemberServiceV2(memberRepository, dataSource);
	}
	
	@AfterEach
	void afterEach() throws SQLException{
		memberRepository.delete(MEMBER_A);
		memberRepository.delete(MEMBER_B);
		memberRepository.delete(MEMBER_EX);
	}
	
	@Test
	@DisplayName("정상 이체")
	void accountTransfer() throws SQLException {
		// given
		Member memberA = new Member(MEMBER_A, 10000);
		Member memberB = new Member(MEMBER_B, 10000);
		
		memberRepository.save(memberA);
		memberRepository.save(memberB);
		
		// when
		log.info("START TX");
		memberService.accountTransfer(memberA.getMameberId(), memberB.getMameberId(), 2000);
		log.info("END TX");
		
		
		// then
		Member findMemberA = memberRepository.finbyId(memberA.getMameberId());
		Member findMemberB = memberRepository.finbyId(memberB.getMameberId());
		
		Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);
		Assertions.assertThat(findMemberB.getMoney()).isEqualTo(12000);
	}
	
	@Test
	@DisplayName("이체 중 예외 발생")
	void accountTransferEx() throws SQLException {
		// given
		Member memberA = new Member(MEMBER_A, 10000);
		Member memberEx = new Member(MEMBER_EX, 10000);
		
		memberRepository.save(memberA);
		memberRepository.save(memberEx);
		
		// when
		Assertions.assertThatThrownBy(()->{
			memberService.accountTransfer(memberA.getMameberId(), memberEx.getMameberId(), 2000);
		}).isInstanceOf(IllegalStateException.class);
		// 오류 발생시 rollback 하기 떄문이다.
		
		// then
		Member findMemberA = memberRepository.finbyId(memberA.getMameberId());
		Member findMemberEx = memberRepository.finbyId(memberEx.getMameberId());
		
		Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);
		Assertions.assertThat(findMemberEx.getMoney()).isEqualTo(10000);
	}
}
