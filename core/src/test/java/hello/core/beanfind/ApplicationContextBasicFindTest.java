package hello.core.beanfind;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class ApplicationContextBasicFindTest {
	
	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
	
	@Test
	@DisplayName("빈 이름 조회")
	void findBeanByName() {
		MemberService memberService = ac.getBean("memberService", MemberService.class);
		System.out.println("memberService = " + memberService);
		System.out.println("memberService = " + memberService.getClass());
		
		Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
		// memberService가 MemberServiceImpl이면 성공
	}
	
	@Test
	@DisplayName("이름 없이 타입으로만 조회")
	// 만약 같은 타입이 2개 이상일 경우는 ????? 아래에서 확인해보자.
	// ApplicationContextSameBeanFindTest 에서 확인해볼 수있다.
	void findBeanByType() {
		MemberService memberService = ac.getBean(MemberService.class);
		// 인터페이스로 하여도 빈이 찾는건 빈에 등록된 반환된 객체이다.
		Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
		// memberService가 MemberServiceImpl이면 성공
	}
	
	@Test
	@DisplayName("구체 타입으로 조회")
	void findBeanByName2() {
		MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
		
		Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
		// memberService가 MemberServiceImpl이면 성공
	}
	
	@Test
	@DisplayName("빈 이름으로 조회 X")
	void errorByName() {
//		ac.getBean("xxxx", MemberService.class);
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchBeanDefinitionException.class,
				()->ac.getBean("xxxx", MemberService.class));
	}
}
