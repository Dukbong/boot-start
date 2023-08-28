package hello.login.web.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("log filter doFilter");
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestURI = httpRequest.getRequestURI();
		
		String uuid = UUID.randomUUID().toString();
		
		try {
			log.info("REQUEST [{}][{}]", uuid, requestURI);
			chain.doFilter(request, response);
			// chain.doFilter
			// 다음 필터가 있으면 필터를 호출하고 없다면 디스패처 서블릿을 호출한다.
			// 만약 이 로직을 호출하지 않으면 다음 단계로 진행되지 않는다.
		}catch(Exception e) {
			throw e;
		}finally {
			log.info("RESPONSE [{}][{}]", uuid, requestURI);
		}
	}

}
