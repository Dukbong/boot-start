package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

	@Test
	public void lifeCycleTest() {
		//ApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
		ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
		// ConfigurableApplicationContext는 AnnotationConfiApplicationContext의 부모다.
		// close() 메소드를 호출하기 위해 만들었다.
		NetworkClient client = ac.getBean(NetworkClient.class);

		ac.close();
	}
	
	//설정 정보에서 초기화메서드, 종료 메서드 지정
//	@Configuration
//	static class LifeCycleConfig{
//		@Bean(initMethod = "init", destroyMethod = "close")
//		// 초기화 콜백은 init이라는 메소드
//		// 소멸전 콜백은 close라는 메소드
//		public NetworkClient networkClient() {
//			NetworkClient networkClient = new NetworkClient();
//			networkClient.setUrl("http://hello-spring.dev");
//			return networkClient;
//		}
//	}
	
	// 어노테이션으로 지정
	@Configuration
	static class LifeCycleConfig{
		@Bean(initMethod = "init", destroyMethod = "close")
		// 초기화 콜백은 init이라는 메소드
		// 소멸전 콜백은 close라는 메소드
		public NetworkClient networkClient() {
			NetworkClient networkClient = new NetworkClient();
			networkClient.setUrl("http://hello-spring.dev");
			return networkClient;
		}
	}
}
