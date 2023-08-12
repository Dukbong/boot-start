package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeTraceAop {
	
	// AOP가 프록시를 통해 가능한 이유는 DI를 통해 느슨한 결합을 했기 때문이다.
	
	@Around("execution(* hello.hellospring..*(..))")
	// 서비스 하위 목록만 하고 싶다면?
	// @Around("execution(* hello.hellospring.service.*(..))")
	public Object execut(ProceedingJoinPoint joinPoint) throws Throwable{
		long start = System.currentTimeMillis();
		System.out.println("START : " + joinPoint.toString());
		try {
			Object result = joinPoint.proceed();
			return result;
		}finally {
			long finish = System.currentTimeMillis();
			long timeMs = finish - start;
			System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
		}
	}
}
