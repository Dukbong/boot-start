package hello.servlet.web.frontController.v2.controlelr;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontController.MyView;
import hello.servlet.web.frontController.v2.ControllerV2;

public class MemberFromControllerV2 implements ControllerV2{

	@Override
	// 문제점 : 현재 request, response는 필요없다.
	public MyView process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return new MyView("/WEB-INF/views/new-form.jsp");
	}
	
}
