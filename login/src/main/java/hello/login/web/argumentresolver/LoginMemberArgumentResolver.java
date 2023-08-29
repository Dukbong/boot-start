package hello.login.web.argumentresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {@Override
	public boolean supportsParameter(MethodParameter parameter) {
		log.info("supportsParameter 실행");
		
		// Login 어노테이션을 갖었는지?
		boolean hasLoginAnnotaion = parameter.hasParameterAnnotation(Login.class);
		// 파라미터 타입이 Member를 상속 / 구현 했는가?
		boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

		return hasLoginAnnotaion && hasMemberType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		log.info("resolverArgument 실행 ");
		
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		
		HttpSession session = request.getSession(false);
		
		if(session == null) {
			return null;
		}
		return session.getAttribute(SessionConst.LOGIN_MEMBER);
	}

}
