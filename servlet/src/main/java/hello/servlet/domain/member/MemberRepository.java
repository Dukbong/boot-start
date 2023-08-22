package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 동시성 문제가 고려되어 있지 않는다.
 * 실무에서는 ConcurrentHashMap, AtomicLong 사용을 고려해보자.
 */
public class MemberRepository {

	private static Map<Long, Member> store = new HashMap<>();
	private static long sequence = 0L;
	
	private static final MemberRepository instance = new MemberRepository();
	
	// 싱글 톤으로 만들기 위함.
	private MemberRepository() {}
	
	public static MemberRepository getInstance() {
		return instance;
	}
	
	public Member save(Member member) {
		member.setId(++sequence);
		store.put(member.getId(), member);
		return member;
	}
	
	// 원래 조회해올때는 null일수 있기때문에 Optional을 사용하는게 좋다.
	public Member findById(Long id) {
		//Optional<Member> member = Optional.ofNullable(store.get(id));
		return store.get(id);
	}
	
	public List<Member> finAll(){
		return new ArrayList<>(store.values());	// Map을 ArrayList로 만들기
	}
	
	public void clearStore() {
		store.clear();
	}
}
