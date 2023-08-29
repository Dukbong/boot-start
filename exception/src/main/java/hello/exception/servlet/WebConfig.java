package hello.exception.servlet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hello.exception.filter.LogFilter;
import hello.exception.interceptor.LogInterceptor;

public class WebConfig implements WebMvcConfigurer {

	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogInterceptor())
				.order(1)
				.addPathPatterns("/**")
				.excludePathPatterns("/css/**", "/*.ico", "/error", "/error-page/**");
				// 오류 페이지 경로를 여기서 뺄수 있다.
	}

	@Bean
	public FilterRegistrationBean logFilter(){
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		
		filterRegistrationBean.setFilter(new LogFilter());
		filterRegistrationBean.setOrder(1);
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
		// 해당 필터는 모든 URL에 적용되며
		// 일반 요청과 sendError()로 재요청 두가지 경우 모두 필터가 적용됩니다.
		// setDispatcherTypes를 지정하지 않으면 기본값은 REQUEST이다.
		
		return filterRegistrationBean;
	}
}
