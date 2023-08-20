package hello.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;

// Qualifier의 단점이 타입 체크를 할 수 있도록 어노테이션을 직접 만들기
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
// 이렇게 적으면 @MainDiscountPolicy를 쓰면 위에 있는 모든 어노테이션이 동작한다.
// 이는 스프링의 기능이지 자바의 기능이 아니다.
public @interface MainDiscountPolicy {

}
