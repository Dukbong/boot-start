package hello.core.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hello.core.discount.FixDicountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImplSetterTest {

	@Test
	void createOrder() {
		// 수정자 주입(setter 주입을 하면 한눈에 알아볼 수 없기 때문에 테스트하기 어렵다.
		OrderServiceImplSetter orderServiceSetter = new OrderServiceImplSetter();
		Assertions.assertThrows(NullPointerException.class, 
				()-> orderServiceSetter.createOrder(1L, "itemA", 10000));
		
		// 생성자 주입은 테스트할때 컴파일오류로 바로바로 보이기 때문에 테스트하기 쉽다.
		MemberRepository memberRepository = new MemoryMemberRepository();
		memberRepository.save(new Member(1L, "name", Grade.VIP));
		OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(), new FixDicountPolicy());
		Order order = orderService.createOrder(1L, "itemB", 20000);
		org.assertj.core.api.Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
	}
}
