package hello.core.discount;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import hello.core.member.Grade;
import hello.core.member.Member;

@Component
// RateDiscount도 등록되어있다. (문제가 생긴다.)
// OrderService에서 DiscountPolicy 타입으로 생성자 주입을 받는데
// 지금 두개가 나오기 때문에 오류가 발생한다. (NoUniqueBeanDefinitionException)

//@Qualifier("fixDiscountpolicy")
public class FixDicountPolicy implements DiscountPolicy {

	private int discountFixAmount = 1000; 
	
	@Override
	public int discount(Member member, int price) {
		if(member.getGrade() == Grade.VIP) {
			// Enum의 경우 == 으로 비교하는게 맞다.
			return discountFixAmount;
		}else {
			return 0;
		}
	}

}
