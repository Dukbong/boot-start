package hello.core;

import hello.core.discount.FixDicountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {
	// 객체 생성과 연결을 담당한다.
	
	// 기획자 역할이고 각각의 클래스는 배역이라고 할 수 있다.
	// 기획자가 배역에 맞는 배우를 골라서 넣어주면 된다.
	
	public MemberService memberService() {
		return new MemberServiceImpl(new MemoryMemberRepository());
	}
	
	public OrderService orderService() {
		return new OrderServiceImpl(new MemoryMemberRepository(), new FixDicountPolicy());
	}
}
