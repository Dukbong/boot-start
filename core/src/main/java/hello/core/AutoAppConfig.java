package hello.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

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
	
	// 수동 빈 등록과 자동 빈 등록시 이름이 중복되면 어떻게 되는지 알기 위한 테스트
	// 이경우 수동 빈 등록이 우선권을 갖는다.
	// 어떻게?? 수동 빈이 자동빈을 오버라이딩 한다.
	// 하지만 실제로 웹을 돌리면 스프링 부트에서 디폴트값으로 에러를 발생시킨다.
	// 이유는 수동빈으로 의도적으로 오버라이딩 한것인지 실수인지 알수 없는 경우가 많기 때문이다.
	// 만약 의도적이라면 application.properties 파일에
	// bean-definition-overriding-true를 입력해줘야한다.
	// 디폴트값은 false이다.
//	@Bean(name="memoryMemberRepository")
//	MemberRepository memberRepotiRepository() {
//		return new MemoryMemberRepository();
//	}
}
