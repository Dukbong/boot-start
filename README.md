# boot-start

### gradle 빌드 하고 실행하는 방법

해당 프로젝트로 이동 후 

window의 경우 gradlew.bat build

완료 후

build 폴더로 이동

libs 폴더로 이동

java -jar 파일명.jar 하면 끝

서버 배포시에는 이렇게 생성된 jar 파일만 배포하여 실행하면 된다.

⭐ build 폴더 제거하는 방법 : gradlew.bat clean

⭐ build 폴더를 제거하고 다시 생성하는 방법 : gradlew.bat clean build

### 포트 번호 변경
src/main/resources/application.properties에서 server.port=xxxx 이렇게 변경 가능하다.

### @ResponseBody 사용
HTTP의 Body에 문자 내용을 직접 반환

viewResolver 대신에 HttpMessageConverter가 동작한다.

기본 문자 처리 : StringHttpMessageConverter 

기본 객체 처리 : MappingJackson2HttpMessageConverter

byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어있다.

클라이언트의 HTTP Accept 헤더와 서버의 컨트롤러 반환 타입 정보 둘을 조합하여 선택된다.

### h2 database
가볍고 좋다.

실행 방법 : h2 설치된 폴더에서 bin 으로 들어간 후 h2.bat 실행

동시 접속이 가능하려면 jdbc:h2:tcp://localhost/~/test로 JDBC URL을 변경해야한다.

application.properties 파일에
spring.datasource.url=jdbc:h2:tcp://localhost/~/test
spring.datasource.driver-class-name=org.h2.Driver


### org.h2.jdbc.JdbcSQLInvalidAuthorizationSpecException: Wrong user name or password [28000-214]

오류 해결 하는 방법 : application.properties에 spring.datasource.username=sa 추가

### jpa 사용

application.properties 파일에
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=none
추가

### AOP
공통 관심사를 말한다.
주된 기능이 아닌 보조적인 기능이다.

흐름
controller가 service의 메소드를 호출한다는 가정하에

1. controller가 가짜 service(Proxy)를 호출한다.
2. Proxy에서 joinPoint.proceed() 메소드를통해 진짜 service를 부른다.

확인이 하고 싶다면
controller에서 service을 인젝션할때
참조변수명.getClass()로 확인할 수 있다
