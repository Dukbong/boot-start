package hello.core.member;

public class MemberServiceImpl implements MemberService {

	// 순수하게 자바 코드로만 작성할 경우 아래와 같이 DIP 원칙이 지켜지지 않는다.
	// 현재 MemberServiceImpl은 memberRepository라는 인터페이스와
	// MemoryMemberRepository라는 구현체 두가지 모두를 의존하고 있다.
	private final MemberRepository memberRepository = new MemoryMemberRepository();
	
	@Override
	public void join(Member member) {
		memberRepository.save(member);
	}

	@Override
	public Member findMember(Long memberId) {
		return memberRepository.findById(memberId);
	}

}
