package hello.core.common;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


// A: Provider로 빈 생성을 지연시켜서 request scope 빈이 정상적으로 생성되도록 하였다.
// B: ProxyMode
@Component
//@Scope("request") // A
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {
	private String uuid;
	private String requestURL;
	
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}
	
	public void log(String message) {
		System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
	}
	
	@PostConstruct
	public void init() {
		uuid = UUID.randomUUID().toString();
		System.out.println("[" + uuid + "] requst scope bean create : " + this);
	}
	
	@PreDestroy
	public void close() {
		System.out.println("[" + uuid + "] requst scope bean close : " + this);
	}
}