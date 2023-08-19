package hello.core.beanfind;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDicountPolicy;
import hello.core.discount.RateDiscountPolicy;

public class ApplicationContextExtendsFindTest {
	// 스프링 빈 조회는 상속관계를 보여준다.
	// 부모 타입으로 조회하면 자식 타입도 함께 조회된다.
	// 그러므로 Object 타입으로 조회하면 모든 스프링 빈을 조회할 수 있다.
	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

	@Test
	@DisplayName("부모 타입으로 조회, 자식이 둘 이상 있으면 중복오류가 발생한다.")
	void findBeanByParentTypeDuplicate() {
		Assertions.assertThrows(NoUniqueBeanDefinitionException.class, 
				()->ac.getBean(DiscountPolicy.class));
	}
	@Test
	@DisplayName("부모 타입으로 조회, 자식이 둘 이상 있으면 빈 이름을 지정하여 오류를 막을 수 있다.")
	void findBeanByParentTypeBeanName() {
		DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
		org.assertj.core.api.Assertions.assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
	}
	@Test
	@DisplayName("특정 하위 타입으로 조회")
	// 유연하지 못하기 때문에 좋지 않은 방법이다.
	void findBeanBySubType() {
		DiscountPolicy rateDiscountPolicy = ac.getBean(RateDiscountPolicy.class);
		org.assertj.core.api.Assertions.assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
	}
	@Test
	@DisplayName("부모 타입으로 모두 조회하기")
	void findAllBeanParentType() {
		Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
		org.assertj.core.api.Assertions.assertThat(beansOfType.size()).isEqualTo(2);
		
		for(String key : beansOfType.keySet()) {
			System.out.println("key = " + key + ", value = " + beansOfType.get(key));
		}
	}
	@Test
	@DisplayName("부모 타입으로 모두 조회하기 - Object")
	void findAllBeanObjectType() {
		Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
		// 스프링이 내부적으로 사용하는 빈까지 다 나온다.
		// 이유는 내부적으로 사용하는 빈도 결국엔 Object를 상속받기 때문이다.
		for(String key : beansOfType.keySet()) {
			System.out.println("key = " + key + ", value = " + beansOfType.get(key));
		}
	}
	
	
	@Configuration
	static class TestConfig{
		@Bean
		public DiscountPolicy rateDiscountPolicy() {
			return new RateDiscountPolicy();
		}
		@Bean
		public DiscountPolicy fixDiscountPolicy() {
			return new FixDicountPolicy();
		}
	}
}
