package hello.core.autowired;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import hello.core.member.Member;

public class AutowiredTest {

	@Test
	void AutowiredOption() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
		
	}
	
	static class TestBean{
		
		// Member는 스프링 빈에 등록되지 않는거다.
		
		@Autowired(required = false)
		// 의존 관계 주입할 대상이 없다면 메소드 자체 실행을 안한다.
		public void setNoBean1(Member noBean1) {
			System.out.println("nobean1 = " + noBean1);
		}
		
		@Autowired
		public void setNoBean2(@Nullable Member noBean2) {
			System.out.println("nobean2 = " + noBean2);
		}
		
		@Autowired
		public void setNoBean3(Optional<Member> noBean3) {
			System.out.println("nobean3 = " + noBean3);
		}
	}
}
