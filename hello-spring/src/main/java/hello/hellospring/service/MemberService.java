package hello.hellospring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

@Transactional
public class MemberService {
	private final MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		super();
		this.memberRepository = memberRepository;
	}
	

	/**
	 * 회원가입
	 */
	public Long join(Member member) {
			validateDuplicateMember(member);
			memberRepository.save(member);
			return member.getId();
	}
	
	private void validateDuplicateMember(Member member) {
		// 같은 이름의 중복을 방지한다.
//		Optional<Member> result = memberRepository.findByName(member.getName());
//		result.ifPresent(m ->{
//			throw new IllegalStateException("이미 존재하는 이름입니다.");
//		});
		// 위 코드를 줄이면
		memberRepository.findByName(member.getName())
			.ifPresent(m->{
				throw new IllegalStateException("이미 존재하는 이름입니다.");
			});
	}
	
	/**
	 * 전체 회원 조회
	 */
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}
	
	public Optional<Member> findOne(Long memberId){
		return memberRepository.findById(memberId);
	}
}
