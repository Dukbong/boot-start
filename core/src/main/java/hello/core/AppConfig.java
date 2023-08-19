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
	
	// Q
	// @Bean memberService -> new MemoryMemberRepository();
	// @Bean orderService -> new MemoryMemberRepository();
	// 이렇게 두개를 생성하는데 이는 싱글톤이 깨지는 것처럼 보인다.
	// 이를 스프링 컨테이넝는 어떻게 해결할까?
	
	// 예상
	// call AppCofig.memberRepository
	// call AppCofig.discountPolicy
	// call AppCofig.memberService
	// call AppCofig.memberRepository
	// call AppCofig.orderService
	// call AppCofig.memberRepository
	// call AppCofig.discountPolicy
	
	// 실제
	// call AppCofig.memberRepository
	// call AppCofig.discountPolicy
	// call AppCofig.memberService
	// call AppCofig.memberRepository
	
	// 이런 상황은 스프링은 클래스의 바이트 코드를 조작하는 라이브러리를 사용한다.
	// 그것은 바로 @Configuration 이다.
	// 실제로 만든 클래스가 아닌 프록시(가짜)를 만들어서 그것으로 싱글톤이 가능하게 만들어준다.
	
	// @Bean이 붙은 메소드마다 이미 스프링 빈에 등록되어있다면 해당 빈을 반환하고
	// 없다면 등록하고 반환하는 코드가 동적으로 프록시에 의해 만들어진다.
	
	// @Configuration을 뺴도 bean이 등록은 되지만 싱글톤이 깨지게 되며 예상처럼 나온다.
	// 이걸 해결하려면 @Autowired 를 사용하면 된다.
	@Bean
	public MemberRepository memberRepository() {
		System.out.println("call AppCofig.memberRepository");
		return new MemoryMemberRepository();
	}
	@Bean
	public DiscountPolicy discountPolicy() {
		System.out.println("call AppCofig.discountPolicy");
		return new RateDiscountPolicy();
	}
	@Bean
	public MemberService memberService() {
		System.out.println("call AppCofig.memberService");
		return new MemberServiceImpl(memberRepository());
	}
	@Bean
	public OrderService orderService() {
		System.out.println("call AppCofig.orderService");
		return new OrderServiceImpl(memberRepository(), discountPolicy());
	}
}
