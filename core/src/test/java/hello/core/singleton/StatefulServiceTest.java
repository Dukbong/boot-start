package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class StatefulServiceTest {
	@Test
	void statefulServiceSingleton() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
		StatefulService statefulService1 = ac.getBean(StatefulService.class);
		StatefulService statefulService2 = ac.getBean(StatefulService.class);
		
		// ThreadA : A사용자 10000원 주문
		statefulService1.order("userA", 10000);
		// ThreadB : B사용자 20000원 주문
		statefulService2.order("userB", 20000);
		
		// ThreadA : A사용자 주문 금액 조회
		int price1 = statefulService1.getPrice(); // 오류!! 20000원이 조회된다.
		System.out.println("price = " + price1);
		
		Assertions.assertThat(price1).isEqualTo(20000);
	}
	
	@Test
	// 공유 필드 제거 후
	void statefulServiceSingletonO() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
		StatefulService statefulService1 = ac.getBean(StatefulService.class);
		StatefulService statefulService2 = ac.getBean(StatefulService.class);
		
		// ThreadA : A사용자 10000원 주문
		int priceUserA = statefulService1.order2("userA", 10000);
		// ThreadB : B사용자 20000원 주문
		int priceUserB = statefulService2.order2("userB", 20000);
		
		System.out.println("price = " + priceUserA);
		
		Assertions.assertThat(priceUserA).isEqualTo(10000);
	}
	
	@Configuration
	static class TestConfig {
		
		@Bean
		public StatefulService statefulServie() {
			return new StatefulService();
		}
	}
}
