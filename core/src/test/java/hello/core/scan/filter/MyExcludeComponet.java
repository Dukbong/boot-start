package hello.core.scan.filter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // type은 클래스에 붙는거다.
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponet {

}
