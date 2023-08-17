package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

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
