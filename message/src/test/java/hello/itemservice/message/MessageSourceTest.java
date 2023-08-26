package hello.itemservice.message;

import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
//@RequiredArgsConstructor
public class MessageSourceTest {

	private final MessageSource messageSource;


	@Autowired
	MessageSourceTest(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Test
	@DisplayName("메시지 확인")
	void helloMessage() {
		String result = messageSource.getMessage("hello", null, null); // code, args, locale
		log.info("result = {}", result);
		Assertions.assertThat(result).isEqualTo("안녕");
	}
	
	@Test
	@DisplayName("메시지가 없을때")
	void notFoundMessageCode() {
		// core 버전
		Assertions.assertThatThrownBy(()->{
			messageSource.getMessage("no_code", null, null);
		}).isInstanceOf(NoSuchMessageException.class);
		
		// jupiter 버전
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchMessageException.class, 
				()->messageSource.getMessage("no_code", null, null));
	}
	
	@Test
	@DisplayName("메시지 없는 경우 디폴트 메시지 보여주기")
	void notFoundMessageCodeDefaultMessage() {
		String result = messageSource.getMessage("no_code", null, "기본 메시지", null);
		Assertions.assertThat(result).isEqualTo("기본 메시지");
	}
	
	@Test
	@DisplayName("메시지에 치환을 이용하는 방법")
	void argumentMessage() {
		String result = messageSource.getMessage("hello.name", new Object[] {"Spring"}, null);
		Assertions.assertThat(result).isEqualTo("안녕 Spring");
	}
	
	@Test
	void defaultLang() {
		Assertions.assertThat(messageSource.getMessage("hello", null, null)).isEqualTo("안녕");
		Assertions.assertThat(messageSource.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
	}
	
	@Test
	void enLang() {
		Assertions.assertThat(messageSource.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
	}
	
}
