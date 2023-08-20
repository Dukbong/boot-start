package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class ComponentFilterAppConfigTest {
	
	@Test
	void filterScan() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
		
		BeanA beanA = ac.getBean("beanA", BeanA.class);
		// 스프링빈에 현재 componetScan으로 인해 클래스이름으로 빈이 등록되어있다.
		Assertions.assertThat(beanA).isNotNull();
		
		// excludeFileter로 제외했기 때문에 찾을 수 없어야한다.
		org.junit.jupiter.api.Assertions.assertThrows(
				NoSuchBeanDefinitionException.class, 
				()->ac.getBean("beanB", BeanB.class)
		);
	}
	
	@Configuration
	@ComponentScan(
			includeFilters = @ComponentScan.Filter(type=FilterType.ANNOTATION, classes=MyIncludeComponet.class),
			excludeFilters = @ComponentScan.Filter(type=FilterType.ANNOTATION, classes=MyExcludeComponet.class)
			
			)
	static class ComponentFilterAppConfig{
		
	}
}
