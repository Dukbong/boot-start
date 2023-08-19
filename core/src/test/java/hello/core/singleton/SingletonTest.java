package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;
import hello.core.member.MemberService;

public class SingletonTest {

	@Test
	@DisplayName("스프링 없는 순수한 DI 컨테이너")
	void pureContainer() {
		AppConfig appConfig = new AppConfig();
		
		// 1. 조회 : 호출 할 때 마다 객체를 생성
		MemberService memberService1 = appConfig.memberService();
		
		// 2. 조회 : 호출 할 때 마다 객체를 생성
		MemberService memberService2 = appConfig.memberService();
		
		// 참조 값이 다른 것을 확인
		System.out.println("memberService1 = " + memberService1);
		System.out.println("memberService2 = " + memberService2);
		
		// memberService1 != memberService2
		Assertions.assertThat(memberService1).isNotSameAs(memberService2);
		
		// 문제점 : 트래픽이 초당 100이 나오면 초당 100개의 객체가 생성되고 소멸된다.
		// 이는 메모리를 크게 낭비하는 꼴이 된다.
		// 이러한 문제를 해결하기 위해 딱 1개의 객체만 생성한 후 공유하도록 설계하는 것을 싱글톤 패턴이라고 한다.
	}
	
	@Test
	@DisplayName("싱글톤 패턴을 적용한 객체 사용")
	void singletonServiceTest() {
		SingletonService singletonService1 = SingletonService.getInstance();
		SingletonService singletonService2 = SingletonService.getInstance();
		
		System.out.println("singletonService1 = " + singletonService1);
		System.out.println("singletonService2 = " + singletonService2);
		
		Assertions.assertThat(singletonService1).isSameAs(singletonService2);
		// same은 자바의  ==
		// equals은 자바의 equals
	}
	
	@Test
	@DisplayName("스프링 컨테이너와 싱글톤")
	void springContainer() {
		
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		// 1. 조회 : 호출 하면 컨테이너에서 가져온다.
		MemberService memberService1 = ac.getBean("memberService", MemberService.class);
		// 2. 조회 : 호출 하면 컨테이너에서 가져온다.
		MemberService memberService2 = ac.getBean("memberService", MemberService.class);
		
		// 참조 값이 다른 것을 확인
		System.out.println("memberService1 = " + memberService1);
		System.out.println("memberService2 = " + memberService2);
		
		// memberService1 == memberService2
		Assertions.assertThat(memberService1).isSameAs(memberService2);
	}
}
