package hello.core.singleton;

public class SingletonService {
	// static 영역에 하나를 만들어서 올린다.
	private static final SingletonService instance = new SingletonService();
	
	// 조회만 허용하도록 한다.
	public static SingletonService getInstance() {
		return instance;
	}
	
	// new를 못쓰게 막는다.
	private SingletonService() {}
	
	public void logic() {
		System.out.println("싱글톤 객체 로직 호출");
	}
}
