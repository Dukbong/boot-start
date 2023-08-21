package hello.core.scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

public class SingletonTest {
	@Test
	void singletonBeanFind() {
		ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
		// 클래스를 넣어주면 해당 클래스를 컴포넌트 스캔으로 읽는다.
		
		SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
		SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
		System.out.println("singletonBean1 = " + singletonBean1);
		System.out.println("singletonBean2 = " + singletonBean2);
		Assertions.assertThat(singletonBean1).isSameAs(singletonBean2);
		
		ac.close();
	}
	
	@Scope("singleton")
	static class SingletonBean{
		@PostConstruct
		public void init() {
			System.out.println("SingletonBean.init");
		}
		@PreDestroy
		public void destroy() {
			System.out.println("SingletonBean.destroy");
		}
		
	}
}
