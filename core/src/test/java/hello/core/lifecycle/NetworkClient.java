package hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//public class NetworkClient implements InitializingBean, DisposableBean{
public class NetworkClient{
	private String url;
	
	public NetworkClient() {
		System.out.println("생성자 호출, url = " + url);
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	// 서비스 시작시 호출
	public void connect() {
		System.out.println("connect = " + url);
	}
	
	public void call(String message) {
		System.out.println("call = " + url + ", message = " + message);
	}
	
	// 서비스 종료시 호출
	public void disconnect() {
		System.out.println("close = " + url);
	}
	
	@PostConstruct
	// 초기화 콜백
	public void init() {
		connect();
		call("초기화 연결 메시지");
	}
	@PreDestroy
	// 소멸전 콜백
	public void close() {
		disconnect();
	}

	// 인터페이스 사용시
//	// 의존 관계 주입이 끝나는 시점에서 자동으로 호출되는 메서드
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		connect();
//		call("초기화 연결 메시지");
//	}
//
//	// 소멸 직전에 자동으로 호출되는 메서드
//	@Override
//	public void destroy() throws Exception {
//		disconnect();
//	}
}
