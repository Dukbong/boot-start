package hello.core.member;

public class MemberServiceImpl implements MemberService {

	// 순수하게 자바 코드로만 작성할 경우 아래와 같이 DIP 원칙이 지켜지지 않는다.
	// 현재 MemberServiceImpl은 memberRepository라는 인터페이스와
	// MemoryMemberRepository라는 구현체 두가지 모두를 의존하고 있다.
//	private final MemberRepository memberRepository = new MemoryMemberRepository();
	
	
	// DIP를 지키기 위해
	private final MemberRepository memberRepository;

	// AppConfig라는 외부 파일에서 memberRepository의 구현체를 넣어주고 있기 때문에
	// 해당 코드는 DIP를 위반하지 않게 된다.
	// 해당 클래스 입장에서 보면 의존관계가 외부에서 주입해주는 것이기 때문에 DI또는 의존성 주입이라고 한다.
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	@Override
	public void join(Member member) {
		memberRepository.save(member);
	}

	@Override
	public Member findMember(Long memberId) {
		return memberRepository.findById(memberId);
	}

}
