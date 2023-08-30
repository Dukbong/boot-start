package hello.exception.resolver;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

	private final ObjectMapper objMapper = new ObjectMapper();
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		try {
			
			if(ex instanceof UserException) {
				log.info("UserException resolver to 400");
				String acceptHeader = request.getHeader("accept");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				
				if(acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
					Map<String, Object> errorResult = new HashMap<>();
					errorResult.put("ex", ex.getClass());
					errorResult.put("message", ex.getMessage());
					
					String result = objMapper.writeValueAsString(errorResult);
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					response.getWriter().write(result);
					return new ModelAndView();
					
					/*
					 * sendError가 아니기 때문에 여기서 정상적으로 응답한다.*/
					
				}else {
					// text/html
					return new ModelAndView("/error/500");
				}
			}
			
		}catch (Exception e) {
			log.info("resolver ex ", e);
		}
		
		return null;
	}

	
}
