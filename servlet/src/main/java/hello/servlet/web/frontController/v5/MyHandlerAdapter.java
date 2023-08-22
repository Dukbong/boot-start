package hello.servlet.web.frontController.v5;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontController.ModelView;

public interface MyHandlerAdapter {

	boolean supports(Object handler);
	
	ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;
	
}
