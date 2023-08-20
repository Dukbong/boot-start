package hello.core.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

@Component
public class OrderServiceImplSetter implements OrderService {

	private MemberRepository memberRepository;
	private DiscountPolicy discountPolicy;
	
	@Autowired
	public void setDiscountPolicy(DiscountPolicy discountPolicy) {
		this.discountPolicy = discountPolicy;
	}
	@Autowired
	public void setMemberRepository(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	public OrderServiceImplSetter() {
	}

	@Autowired
	public OrderServiceImplSetter(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
		super();
		this.memberRepository = memberRepository;
		this.discountPolicy = discountPolicy;
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
