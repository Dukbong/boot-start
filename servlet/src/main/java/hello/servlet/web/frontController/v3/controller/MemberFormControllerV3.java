package hello.servlet.web.frontController.v3.controller;

import java.util.Map;

import hello.servlet.web.frontController.ModelView;
import hello.servlet.web.frontController.v3.ControllerV3;

public class MemberFormControllerV3 implements ControllerV3 {

	@Override
	public ModelView process(Map<String, String> paramMap) {
		return new ModelView("new-form");
	}

}
