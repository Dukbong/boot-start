package hello.exception.filter;

import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterCOnfig) {
		log.info("log filter init");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestURI = httpRequest.getRequestURI();
		
		String uuid = UUID.randomUUID().toString();
		
		try {
			log.info("REQUEST : [{}][{}][{}]",uuid, request.getDispatcherType(), requestURI);
		}catch (Exception e) {
			throw e;
		}finally {
			log.info("RESPONSE : {}", uuid, request.getDispatcherType(),"requestURI");
		}
	}
	
	
	
}
