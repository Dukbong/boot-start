package hello.typeconvert;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hello.typeconvert.converter.IntegerToStringConverter;
import hello.typeconvert.converter.IpPortToStringConverter;
import hello.typeconvert.converter.StringToIntegerConverter;
import hello.typeconvert.converter.StringToIpPortConverter;
import hello.typeconvert.formatter.MyNumberFormatter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
//		registry.addConverter(new StringToIntegerConverter());
//		registry.addConverter(new IntegerToStringConverter());
		registry.addConverter(new StringToIpPortConverter());
		registry.addConverter(new IpPortToStringConverter());
		
		//추가
		registry.addFormatter(new MyNumberFormatter());
	}

}
