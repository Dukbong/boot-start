package hello.jdbc.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.ex.MyDbException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * 예외 누수 문제 해결
 * 체크 예외를 런타입 예외로 변경
 * MemberRepository 인터페이스 사용
 * throws SQLException 제거
 */
@Slf4j
@RequiredArgsConstructor
public class MemberRepositoryV4_1 implements MemberRepository{
	
	private final DataSource dataSource;

	@Override
	public Member save(Member member) {
		String sql = "insert into member(member_id, money) values (?, ?)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getMameberId());
			pstmt.setInt(2, member.getMoney());
			pstmt.executeUpdate(); // 이때 Connection을 통해 실제 데이터 베이스에 전달한다.
			return member;
		} catch (SQLException e) {
			throw new MyDbException(e);
		} finally {
			close(con, pstmt, null);
		}
	}
	@Override
	public Member findById(String memberId){
		String sql = "select * from member where member_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery(); // select 문일 때 사용
			if(rs.next()) {
				Member member = new Member();
				member.setMameberId(rs.getString("member_id"));
				member.setMoney(rs.getInt("money"));
				return member;
			}else {
				throw new NoSuchElementException("member not found memberId = "+memberId);
			}
		}catch(SQLException e) {
			log.error("db error", e);
			throw new MyDbException(e);
		}finally {
			close(con, pstmt, rs);
		}
	}
	@Override
	public void update(String memberId, int money){
		String sql = "update member set money = ? where member_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, money);
			pstmt.setString(2, memberId);
			int resultSize = pstmt.executeUpdate();
			log.info("resultSize = {}", resultSize);
		} catch (SQLException e) {
			log.error("db error ", e);
			throw new MyDbException(e);
		} finally {
			close(con, pstmt, null);
		}
	}
	@Override
	public void delete(String memberId) {
		String sql = "delete from member where member_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			log.error("db error ", e);
			throw new MyDbException(e);
		} finally {
			close(con, pstmt, null);
		}
	}
	
	// close할떄 예외가 발생할 수 있는데 이것을 방지
	private void close(Connection con, Statement stmt, ResultSet resultSet) {
		
		JdbcUtils.closeResultSet(resultSet);
		JdbcUtils.closeStatement(stmt);
		
		// 주의! 트랜젝션 동기화를 사용하려면 DataSourceUtils를 사용해야한다.
		DataSourceUtils.releaseConnection(con, dataSource);
		
	}
	
	private Connection getConnection() throws SQLException {
		// 주의! 트랜젝션 동기화를 사용하려면 DataSourceUtils를 사용해야한다.
		Connection con = DataSourceUtils.doGetConnection(dataSource);
		log.info("get Connection = {}, class = {}",con, con.getClass());
		return con;
	}
}
