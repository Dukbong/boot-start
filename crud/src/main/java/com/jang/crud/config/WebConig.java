package com.jang.crud.config;

import java.util.List;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jang.crud.resolver.ExceptionResolver;

public class WebConig implements WebMvcConfigurer {

	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		
		resolvers.add(new ExceptionResolver());
	}
	
}
