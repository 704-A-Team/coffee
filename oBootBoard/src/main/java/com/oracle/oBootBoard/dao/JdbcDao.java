package com.oracle.oBootBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.oracle.oBootBoard.dto.BDto;
@Repository
public class JdbcDao implements BDao {
	// JDBC 사용 하려면 어떻게 한다?!   --> DataSource
	private final DataSource dataSource;
	public JdbcDao(DataSource dataSource) {    // autowired 쓴거나 마찬가지
		this.dataSource = dataSource;
	}
	// DataSourceUtils -->> 메모리 최적화 해준다
	private Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
	} 
	
	@Override
	public ArrayList<BDto> boardList() {
		ArrayList<BDto> bList = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		// String sql = "Select * From ( Select rownum rn, a.* From ( Select * from mvc_board ) a )";
		String sql1 = "Select * From mvc_board order by bgroup desc, bstep asc";
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(sql1);
			rs = ptmt.executeQuery();
			while(rs.next()) {
				// setter 방식 이외 
				// BDto bDto = new BDto(); bDto.setbId = (rs.getInt("bId");
				// 생성자 방식 사용
				int bId = rs.getInt("bid");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent =  rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bdate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				bList.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		
		System.out.println("BDto boardList start");
		return bList;
	}
	@Override
	public BDto contentView(String strId) {
		BDto bDto = null;
		Connection connection = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		String sql = "Select * From Mvc_Board where bId = ?";
		
		// upBit(strId); HW03
		upBit(strId);
		
		try {
			connection = getConnection();
			ptmt = connection.prepareStatement(sql);
			ptmt.setString(1, strId);
			rs = ptmt.executeQuery();
			if(rs.next()) {
				bDto = new BDto();
				bDto.setbId(rs.getInt("bId"));
				bDto.setbName(rs.getString("bName"));
				bDto.setbTitle(rs.getString("bTitle"));
				bDto.setbContent(rs.getString("bContent"));
				bDto.setbDate(rs.getTimestamp("bDate"));
				bDto.setbHit(rs.getInt("bHit"));
				bDto.setbGroup(rs.getInt("bGroup"));
				bDto.setbStep(rs.getInt("bStep"));
				bDto.setbIndent(rs.getInt("bIndent"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(ptmt != null) ptmt.close();
				if(connection != null) connection.close();
					
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return bDto;
	}
	// 내부에서 호출하면 private로 한다
	private void upBit(String strId) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		String sql = "Update mvc_board Set bHit = bHit + 1 Where bId = ?";
		
		System.out.println("upBit upup " + strId);
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, Integer.parseInt(strId));
			int result = ptmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ptmt != null) ptmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	@Override
	public void modify(String bId, String bName, String bTitle, String bContent) {
		BDto bDto = new BDto();
		Connection conn = null;
		PreparedStatement ptmt = null;
		int result = 0;
		String sql = "Update mvc_board Set bName = ? , bTitle = ? , bContent = ? Where bId = ?";
		
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, bName);
			ptmt.setString(2, bTitle);
			ptmt.setString(3, bContent);
			ptmt.setString(4, bId);
			result = ptmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ptmt != null) ptmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void write(String bName, String bTitle, String bContent) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		String sql = "Insert Into Mvc_Board Values(mvc_board_seq.nextval , ? , ? , ? , sysdate , ? , mvc_board_seq.nextval , ? , ? )";
		
		// seqence할 때 한 ROW 일때 nextval 하여도 같은 값을 가진다
		// nextval 하고 값을 또 쓰려면 원칙적으로 currval을 쓴다
		// String sql ..  Values(mvc_board_seq.nextval , ? , ? , ? , sysdate , 0 , mvc_board_seq.currval , 0 , 0 )";
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, bName);
			ptmt.setString(2, bTitle);
			ptmt.setString(3, bContent);
			ptmt.setInt(4, 0);
			ptmt.setInt(5, 0);
			ptmt.setInt(6, 0);
			int result = ptmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ptmt != null) ptmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

	@Override
	public void bDelete(String bId) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		String sql = "Delete From mvc_Board Where bId = ?";
		
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, bId);
			int result = ptmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ptmt != null) ptmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
	@Override
	public BDto reply_view(String strbId){
		Connection conn = null;
		PreparedStatement ptmt = null;
		String sql = "Select * From mvc_board where bId = ?";
		ResultSet rs = null;
		BDto dto = null;
		
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, strbId);
			rs = ptmt.executeQuery();
			if(rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				if(rs != null ) rs.close();
				if(ptmt != null) ptmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;
	}
	@Override
	public void reply(int bId, String bName, String bTitle, String bContent, int bGroup, int bStep, int bIndent) {
		
		// 홍해 기적
		replyShape(bGroup, bStep);
		
		Connection conn = null;
		PreparedStatement ptmt = null;
		
		try {
			conn = getConnection();
			// String sql = "Insert into (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) mvc_Board Values( mvc_board_seq.nextval, ? , ? , ? , ? , ? , ?)";
			String sql = "insert into mvc_board (bId, bName, bTitle, bContent, "
			     	+ " bGroup, bStep, bIndent)"
				    + " values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";

			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, bName);
			ptmt.setString(2, bTitle);
			ptmt.setString(3, bContent);
			ptmt.setInt(4, bGroup);
			ptmt.setInt(5, bStep+1);
			ptmt.setInt(6, bIndent+1);
			int result = ptmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ptmt != null) ptmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
	private void replyShape(int bGroup, int bStep) {
		Connection conn = null;
		PreparedStatement ptmt = null;
		
		try {
			conn = getConnection();
			String sql = "update mvc_board set bStep = bStep + 1  Where bGroup = ? And bStep > ?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, bGroup);
			ptmt.setInt(2, bStep);
			int result = ptmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ptmt != null) ptmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
}
