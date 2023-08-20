package hello.core.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

@Component
//@RequiredArgsConstructor
// final 붙은 필드의 생성자를 만들어주는 어노테이션
public class OrderServiceImpl implements OrderService {

	// SRP(단일책임 원칙)이  잘 지켜지고 있는 클래스들이다. (회원과 할인을 잘 나눠져 있다.)
	// 하지만 DIP는 위반하고 있다.(추상과 구현체 두가지 모두를 의존하고 있다.)
	// Fix에서 Rate으로 변경하는 순간 OCP를 위반하게 된다.(현재 코드를 확장 변경하면 클라이언트 코드에 영향을 주게 된다.)
//	private final MemberRepository memberRepository = new MemoryMemberRepository();
//	private final DiscountPolicy discountPolicy = new FixDicountPolicy();
	
	
	// 순수 자바로 DIP를 지키면서 코드를 작성하는 방법은 아래와 같다.
	// 인터페이스만 의존하고 있기 때문에 DIP를 지키고 있다.
	// 실제 구현객체는 AppConfig에서 만들어서 생성자를 통해 넣어준다.
	private final MemberRepository memberRepository;
	private final DiscountPolicy discountPolicy;
	
	
//	@Autowired
//	public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//		this.memberRepository = memberRepository;
//		this.discountPolicy = discountPolicy;
//	}
	
	// Autowired 필드명 매칭 방법
//	@Autowired
//	// 같은 타입이 2개 이상이면 매개변수 명을 빈에 등록된 이름으로 변경해줘야한다.
//	// 같은 타입이 2개 이상일 경lombok을 이용해서 RequiredArgsConstructor를 사용하면 안된다.
//	// 만약 RateDiscountPolicy를 사용하려면 여기에 rateDiscountPolicy를 쓰면 된다.
//	// 만약 FixDiscountPolicy를 사용하려면 여기에 fixDiscountPolicy를 쓰면된다.
//	public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy rateDiscountPolicy) {
//		this.memberRepository = memberRepository;
//		this.discountPolicy = rateDiscountPolicy;
//	}
	
	// Qualifier 구분자로 매칭 방법
//	@Autowired
//	public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy")DiscountPolicy DiscountPolicy) {
//		this.memberRepository = memberRepository;
//		this.discountPolicy = DiscountPolicy;
//	}
	
	// Primary 사용
	@Autowired
	public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy DiscountPolicy) {
		this.memberRepository = memberRepository;
		this.discountPolicy = DiscountPolicy;
	}

	@Override
	public Order createOrder(Long memberId, String itemName, int itemPrice) {
		Member member = memberRepository.findById(memberId);
		int discountPrice = discountPolicy.discount(member, itemPrice);
		
		return new Order(memberId, itemName, itemPrice, discountPrice);
	}
	
	// 테스트용도 : 싱글톤이 꺠지나 확인용
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}

}
