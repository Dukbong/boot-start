package hello.core;

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
		MemberService memberService = new MemberServiceImpl();
		Member memberA = new Member(1L, "memberA", Grade.VIP);
	
		memberService.join(memberA);
		
		Member memberTest = memberService.findMember(memberA.getId());
		
		System.out.println("new member = " + memberA.getName());
		System.out.println("findMember = " + memberTest.getName());
	}

}
