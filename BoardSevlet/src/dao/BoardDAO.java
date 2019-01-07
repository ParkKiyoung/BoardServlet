package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import vo.BoardBean;

public class BoardDAO {
	private static BoardDAO instance = new BoardDAO();

	public static BoardDAO getInstance() {
		return instance;
	}

		//DB¿¬°á
	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/board");
		return ds.getConnection();
	}

	public void BoardInsert(BoardBean bb) {
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = getConnection();
			String sql = "insert into board values(board_seq.nextval,?,?,?,?,?,?,?,?,?,sysdate)";
			ps = con.prepareStatement(sql);
			ps.setString(1, bb.getBOARD_NAME());
			ps.setString(2, bb.getBOARD_PASS());
			ps.setString(3, bb.getBOARD_SUBJECT());
			ps.setString(4, bb.getBOARD_CONTENT());
			ps.setString(5, bb.getBOARD_FILE());
			ps.setInt(6, 0);
			ps.setInt(7, 0);
			ps.setInt(8, 0);
			ps.setInt(9, 0);
			ps.executeQuery();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeCon(con,ps);
		}
		
	}

	private void closeCon(Connection con, PreparedStatement ps) {
		try {
		if(con!=null)con.close();
		if(ps!=null)ps.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
