package hello.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class MemberApp {

	public static void main(String[] args) {
		
		/*
		 * 현재는 순수하게 자바 코드로 console로 테스트를 확인했지만
		 * 해당 내용을 테스트 코드로 만들어서 쉽게 확인이 가능하다.
		 * src/test/java/member/MemberServcieTest
		 * */
		
		// DIP 위반 된 코드 였던것.
//		MemberService memberService = new MemberServiceImpl();
//		Member memberA = new Member(1L, "memberA", Grade.VIP);
//	
//		memberService.join(memberA);
//		
//		Member memberTest = memberService.findMember(memberA.getId());
//		
//		System.out.println("new member = " + memberA.getName());
//		System.out.println("findMember = " + memberTest.getName());
		
		// DIP 문제를 해결한 코드;
//		AppConfig appConfig = new AppConfig();
//		MemberService memberService = appConfig.memberService();
//		Member member = new Member(1L, "memberA", Grade.VIP);
//		memberService.join(member);
//		
//		Member findMember = memberService.findMember(1L);
//		System.out.println("new Member=  " + member.getName());
//		System.out.println("find Member=  " +findMember.getName());
		
		
		// 스프링 전환
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		
		MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
		// getBean("불러올 메소드명", 반환타입.class);
		// 기본적인 방법은 두가지 모두 적거나 타입이나 이름만 적거나 해서 찾을 수 있다.
		Member member = new Member(1L, "memberA", Grade.VIP);
		memberService.join(member);
		
		Member findMember = memberService.findMember(1L);
		System.out.println("new Member=  " + member.getName());
		System.out.println("find Member=  " +findMember.getName());
	}

}
