package hello.hellospring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import hello.hellospring.domain.Member;

public class JdbcMembrRepository implements MemberRepository {

	private final DataSource dataSource;
	
	
	public JdbcMembrRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Member save(Member member) {
		return null;
	}

	@Override
	public Optional<Member> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Member> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Member> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
