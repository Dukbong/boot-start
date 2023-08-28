package hello.login.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import hello.login.domain.member.Member;

public class SessionManagerTest {

	SessionManager sessionManager = new SessionManager();
	
	@Test
	void sessionTest() {
		
		MockHttpServletResponse response = new MockHttpServletResponse();
		// HttpServletResponse는 인터페이스이기때문에
		// 그냥은 테스트를 할 수 없는데 이를 할 수 있게 해주는게 Mock이다.
		// 오로지 테스트용도이다.
		
		// session 생성 (서버에서 클라이언트로 전달<최초>)
		Member member = new Member();
		sessionManager.createSession(member, response);
		
		// 요청에 대한 응답 쿠키 저장 (클라이언트에서 서버로 전달)
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(response.getCookies());
		
		// 세션 조회
		Object result = sessionManager.getSession(request);
		
		Assertions.assertThat(result).isEqualTo(member);
		
		// 세션 만료
		sessionManager.expire(request);
		
		Object result2 = sessionManager.getSession(request);
		
		Assertions.assertThat(result2).isNull();
	}
}
