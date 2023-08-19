package hello.core.beanfind;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;

public class ApplicationContextInfoTest {
	
	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
	
	@Test
	@DisplayName("모든 빈 출력하기")
	void findAllBean() {
		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		// 등록된 bean의 이름을 가져온다. 이는 곧 메소드의 이름을 말한다.
		
		for(String beanDefinitionName : beanDefinitionNames) {
			Object bean = ac.getBean(beanDefinitionName);
			// getBean에서 타입을 지정해주지 않았기 때문에 Object로 반환
			// 모든 bean을 가져올꺼기 때문에 타입이 여러개라서 이런식으로 한다.
			System.out.println("name = " + beanDefinitionName + ", object = " + bean);
			/*
			name = org.springframework.context.annotation.internalConfigurationAnnotationProcessor, object = org.springframework.context.annotation.ConfigurationClassPostProcessor@3382f8ae
			name = org.springframework.context.annotation.internalAutowiredAnnotationProcessor, object = org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor@60641ec8
			name = org.springframework.context.annotation.internalCommonAnnotationProcessor, object = org.springframework.context.annotation.CommonAnnotationBeanPostProcessor@75f65e45
			name = org.springframework.context.event.internalEventListenerProcessor, object = org.springframework.context.event.EventListenerMethodProcessor@6eeade6c
			name = org.springframework.context.event.internalEventListenerFactory, object = org.springframework.context.event.DefaultEventListenerFactory@4a891c97
			위에는 스프링에서 필수적으로 등록하는 것이고 아래는 내가 등록한 빈이다.
			name = appConfig, object = hello.core.AppConfig$$EnhancerBySpringCGLIB$$b6d0ee4f@a5bd950
			name = memberRepository, object = hello.core.member.MemoryMemberRepository@4d18aa28
			name = discountPolicy, object = hello.core.discount.RateDiscountPolicy@75390459
			name = memberService, object = hello.core.member.MemberServiceImpl@7756c3cd
			name = orderService, object = hello.core.order.OrderServiceImpl@2313052e
			*/
		}
	}
	
	@Test
	@DisplayName("애플리케이션 빈 출력하기")
	void findApplicationBean() {
		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		
		for(String beanDefinitionName : beanDefinitionNames) {
			BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
			
			// Role ROLE_APPLICATION : 직접 등록한 애플리케이션 빈 
			// Role ROLE_INFRASTRUCURE : 스프링이 내부에서 사용하는 빈
			if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
				Object bean = ac.getBean(beanDefinitionName);
				System.out.println("name = " + beanDefinitionName + ", object = " + bean);
			}
			/*
			name = appConfig, object = hello.core.AppConfig$$EnhancerBySpringCGLIB$$b6d0ee4f@a5bd950
			name = memberRepository, object = hello.core.member.MemoryMemberRepository@4d18aa28
			name = discountPolicy, object = hello.core.discount.RateDiscountPolicy@75390459
			name = memberService, object = hello.core.member.MemberServiceImpl@7756c3cd
			name = orderService, object = hello.core.order.OrderServiceImpl@2313052e
			*/
		}
	}
}
