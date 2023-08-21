package hello.core.scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeTest1 {
	
	@Test
	void prototypeFind() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
		PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
		prototypeBean1.addCount();
		Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);
		
		PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
		prototypeBean2.addCount();
		Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);
	}
	
	@Test
	void singletonClientUsePrototype() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
		
		ClientBean clientBean1 = ac.getBean(ClientBean.class);
		int count1 = clientBean1.logic();
		Assertions.assertThat(count1).isEqualTo(1);
		
		ClientBean clientBean2 = ac.getBean(ClientBean.class);
		int count2 = clientBean2.logic();
		Assertions.assertThat(count2).isEqualTo(1);
	}
	
	@Scope("singleton") // 생략 가능
	static class ClientBean{
//		private final PrototypeBean prototypeBean; // 생성시점에 주입된다.
//		private final ObjectProvider<PrototypeBean> prototypeBeanProvider;
		private final Provider<PrototypeBean> prototypeBeanProvider;
		
		// ObjectProvider는 지정된 타입을 찾아주는 역할이다.
		// getObject() 메소드를 통해 내부에서 스프링컨테이너를 통해 해당 빈을 찾아서 반환한다.
		// 하지만 스프링에 의존적이다. 자바표준도 있으니 바꿔서 사용가능하다.
//		@Autowired
//		public ClientBean(ObjectProvider<PrototypeBean> prototypeBeanProvider) {
//			this.prototypeBeanProvider = prototypeBeanProvider;
//		}
		@Autowired
		public ClientBean(Provider<PrototypeBean> prototypeBeanProvider) {
			this.prototypeBeanProvider = prototypeBeanProvider;
		}
		
//		@Autowired
//		public ClientBean(PrototypeBean prototypeBean) {
//			this.prototypeBean = prototypeBean;
//		}
		
		public int logic() {
//			PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
			PrototypeBean prototypeBean = prototypeBeanProvider.get();
			prototypeBean.addCount();
			int count = prototypeBean.getCount();
			return count;
		}
	}
	
	@Scope("prototype")
	static class PrototypeBean{
		private int count = 0;
		
		public void addCount() {
			count++;
		}
		public int getCount() {
			return count;
		}
		
		@PostConstruct
		public void init() {
			System.out.println("PrototypeBean.init " + this);
		}
		
		// prototype은 호출 안된다.
		@PreDestroy
		public void destroy() {
			System.out.println("PrototypeBean.destroy");
		}
	}
}
