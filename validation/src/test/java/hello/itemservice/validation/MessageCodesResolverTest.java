package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageCodesResolverTest {

	MessageCodesResolver codeResolver = new DefaultMessageCodesResolver();
	
	@Test
	void messageCodesResolverObject() {
		// rejectValue에서 ErrorCode에 앞 글자만 적는지 알기 위한 테스트
		
		String[] messageCodes = codeResolver.resolveMessageCodes("required", "item");
		
		for(String message : messageCodes) {
			log.info("message = {}", message);
		}
		
		Assertions.assertThat(messageCodes).containsExactly("required.item","required");
		/*
		 * 객체 오류 생성 규칙
		 * 1. code . objectName
		 * 2. code
		 * */
	}
	
	@Test
	void messageCodesResolverField() {
		String[] messageCodes = codeResolver.resolveMessageCodes("required", "item", "itemName", String.class);
		
		for(String messageCode : messageCodes) {
			log.info("messageCode = {}", messageCode);
		}
		
		// bindingResult.rejectValue("itemName", "required");
		// new FieldError("item", "itemName", null,false, messageCodes, null, null);
		
		Assertions.assertThat(messageCodes).containsExactly(
				"required.item.itemName",
				"required.itemName",
				"required.java.lang.String",
				"required"
				);
		/*
		 * 필드 오류 생성 규칙
		 * 1. code . objectName . fieldName
		 * 2. code . fieldName
		 * 3. code . fieldType
		 * 4. code
		 * */
	}
}
