package hello.hellospring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.hellospring.domain.Member;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
	
	// JpaRepository를 상속하면
	// 스프링이 구현한 후 빈으로 등록을 해준다.
	/*
	 * JpaRepository 인터페이스는
	 * findAll() : List<T>
	 * findAll(Sort) : List<T>
	 * findAll(Iterable<ID>) : List<T>
	 * Save(Iterable<S>) : List<S>
	 * flush()
	 * SaveAndFlush(T) : T
	 * deleteInBatch(Iterable<T>)
	 * deleteAllInBatch()
	 * getOne(ID) : T
	 * 의 추상메소드를 가지고 있고 스프링이 이를 모두 구현하여 빈을 등록한다.*/
	
	
	// select m from Member m whrere m.name = ?
	// 이는 현재 메소드의 이름을 통해 자동으로 만들어진다.
	@Override
	Optional<Member> findByName(String name);
}
