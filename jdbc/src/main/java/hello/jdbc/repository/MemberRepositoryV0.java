package hello.jdbc.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

/***
 * JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV0 {

	public Member save(Member member) throws SQLException {
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
			log.error("db error", e);
			throw e;
		} finally {
			close(con, pstmt, null);
		}
	}
	
	public Member finbyId(String memberId) throws SQLException {
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
			throw e;
		}finally {
			close(con, pstmt, rs);
		}
	}
	
	public void update(String memberId, int money) throws SQLException {
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
			throw e;
		} finally {
			close(con, pstmt, null);
		}
	}
	
	public void delete(String memberId) throws SQLException {
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
			throw e;
		} finally {
			close(con, pstmt, null);
		}
	}
	
	// close할떄 예외가 발생할 수 있는데 이것을 방지
	private void close(Connection con, Statement stmt, ResultSet resultSet) {
		
		if(resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				log.info("error", e);
			}
		}
		
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				log.info("error", e);
			}
		}
		
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				log.info("error", e);
			}
		}
		
	}
	
	private Connection getConnection() {
		return DBConnectionUtil.getConnection();
	}
}
