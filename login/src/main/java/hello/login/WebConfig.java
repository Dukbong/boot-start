package hello.login;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginFilter;
import hello.login.web.intercepter.LogInterceptor;
import hello.login.web.intercepter.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	// WerbMVCConfigurer는 인터셉터를 등록하기 위해서 구현하는거다.
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogInterceptor())
			    .order(1)
			    .addPathPatterns("/**")
			    .excludePathPatterns("/css/**", "/*.ico", "/error");
		
		registry.addInterceptor(new LoginInterceptor())
				.order(2)
				.addPathPatterns("/**")
				.excludePathPatterns("/", "/members/add","/login","/logout","/css/**","/*.ico","/error");
		
	}
	

	@Bean
	public FilterRegistrationBean<Filter> logFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean =  new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new LogFilter());
		filterRegistrationBean.setOrder(1); // 순서
		filterRegistrationBean.addUrlPatterns("/*"); // 필터를 사용할 url
		
		return filterRegistrationBean;
	}
	

	@Bean
	public FilterRegistrationBean<Filter> loginCheckFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new LoginFilter());
		filterRegistrationBean.setOrder(2);
		filterRegistrationBean.addUrlPatterns("/*");
		
		return filterRegistrationBean;
	}
}
