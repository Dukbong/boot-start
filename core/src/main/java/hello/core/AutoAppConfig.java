package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
		basePackages = "hello.core.member",
		// 컴퍼넌트 스캔을 할 시작 위치를 지정한다. (해당 패키지 하위 패키지를 모두 탐색한다.)
		// 없다면 모든 패키지를 탐색한다.
		// 여러개를 지정하고 싶다면 basePackages = {"hello.core", hello.service} 이런식으로
		
		basePackageClasses = AutoAppConfig.class,
		// 지정한 클래스의 패키지를 탐색 시작 위로 지정한다.
		// AutoAppConfig의 패키지는 hello.core 이기때문에 여기 부터 탐색한다.
		
		excludeFilters = @ComponentScan.Filter(type=FilterType.ANNOTATION, classes=Configuration.class)
		// 컴포넌트 스캔을 할껀데 @Configuration 어노테이션 붙은건 뺄꺼다.
		// AppConfig와 테스트코드에서 만든 것들을 제외하기 위해서 작성한거다.
		// 기존 예제 코드들을 남기고 컴포넌트 스캔을 연습하기 위해서 한거다.
		
		// 만약 이런 조건을 아무것도 적지 않는다면
		// 기본적으로 해당 클래스가 속한 패키지의 하위를 모두 탐색한다.
		// 현재 예제에서는 hello.core 하위를 모두 탐색한다.
)
public class AutoAppConfig {
	
}
