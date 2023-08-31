package hello.typeconvert.formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyNumberFormatter implements Formatter<Number> {@Override
	public String print(Number object, Locale locale) {
		// 1000 -> "1,000"
		log.info("text = {}, locale = {}", object, locale);
		NumberFormat instance = NumberFormat.getInstance(locale);
//		String str =instance.format(object);
		return instance.format(object);
	}

	@Override
	public Number parse(String text, Locale locale) throws ParseException {
		// "1,000" -> 1000
		log.info("text = {}, locale = {}", text, locale);
		NumberFormat format=  NumberFormat.getInstance(locale);
//		Number num = format.parse(text);
		return format.parse(text);
	}
	
	

}
