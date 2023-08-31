package hello.typeconvert.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MyNumberFormatterTest {

	MyNumberFormatter formatter = new MyNumberFormatter();
	
	@Test
	void parse() throws ParseException {
		Number result = formatter.parse("1,000", Locale.KOREA);
		Assertions.assertThat(result).isEqualTo(1000L); // Long타입으로 반환된다.
	}
	
	@Test
	void print() {
		String result = formatter.print(1000, Locale.KOREA);
		Assertions.assertThat(result).isEqualTo("1,000");
		
	}
}
