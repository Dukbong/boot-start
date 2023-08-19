package hello.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

@Configuration
public class AppConfig {
	// 객체 생성과 연결을 담당한다.
	
	// 기획자 역할이고 각각의 클래스는 배역이라고 할 수 있다.
	// 기획자가 배역에 맞는 배우를 골라서 넣어주면 된다.
	
	// 순수 자바
	// 이런식으로 AppConfig에서 역할과 구현 클래스를 한눈에 볼 수 있어야한다.
//	private MemberRepository memberRepository() {
//		return new MemoryMemberRepository();
//	}
//	
//	private DiscountPolicy discountPolicy() {
////		return new FixDicountPolicy();
//		return new RateDiscountPolicy();
//	}
//	
//	public MemberService memberService() {
//		return new MemberServiceImpl(memberRepository());
//	}
//	
//	public OrderService orderService() {
//		return new OrderServiceImpl(memberRepository(), discountPolicy());
//	}
	
	// 스프링 전환
	@Bean
	public MemberRepository memberRepository() {
		return new MemoryMemberRepository();
	}
	@Bean
	public DiscountPolicy discountPolicy() {
		return new RateDiscountPolicy();
	}
	@Bean
	public MemberService memberService() {
		return new MemberServiceImpl(memberRepository());
	}
	@Bean
	public OrderService orderService() {
		return new OrderServiceImpl(memberRepository(), discountPolicy());
	}
}
