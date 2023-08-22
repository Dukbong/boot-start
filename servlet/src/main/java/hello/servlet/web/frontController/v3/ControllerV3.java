package hello.servlet.web.frontController.v3;

import java.util.Map;

import hello.servlet.web.frontController.ModelView;

public interface ControllerV3 {

	ModelView process(Map<String, String> paramMap);
	
}
