package hello.core.scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;


public class PrototypeTest {

	@Test
	void prototypeBeanFind() {
		ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
		// 클래스를 넣어주면 해당 클래스를 컴포넌트 스캔으로 읽는다.
		
		System.out.println("find prototypeBean1");
		PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
		System.out.println("find prototypeBean2");
		PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
		System.out.println("prototypeBean1 = " + prototypeBean1);
		System.out.println("prototypeBean2 = " + prototypeBean2);
		Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);
		
		ac.close(); // 프로토 타입은 @PreDestory가 실행되지 않는다.
		
		// 종료하고 싶다면 직접해야한다.
		prototypeBean1.destroy();
		prototypeBean2.destroy();
	}
	
	@Scope("prototype")
	static class PrototypeBean{
		@PostConstruct
		public void init() {
			System.out.println("prototypeBean.init");
		}
		@PreDestroy
		public void destroy() {
			System.out.println("prototypeBean.destroy");
		}
	}
}
