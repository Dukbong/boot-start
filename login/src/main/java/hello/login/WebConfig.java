package hello.login;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginFilter;

@Configuration
public class WebConfig {

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
