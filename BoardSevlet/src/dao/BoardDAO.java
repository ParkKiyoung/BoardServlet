package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
			String sql = "insert into board values(board_seq.nextval,?,?,?,?,?,board_seq.nextval,?,?,?,sysdate)";
			ps = con.prepareStatement(sql);
			ps.setString(1, bb.getBOARD_NAME());
			ps.setString(2, bb.getBOARD_PASS());
			ps.setString(3, bb.getBOARD_SUBJECT());
			ps.setString(4, bb.getBOARD_CONTENT());
			ps.setString(5, bb.getBOARD_FILE());
			ps.setInt(6, 0);
			ps.setInt(7, 0);
			ps.setInt(8, 0);
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

	public ArrayList<BoardBean> boardList() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList<BoardBean> arr = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "select * from board";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				BoardBean bb = new BoardBean();
				bb.setBOARD_NUM(rs.getInt("board_num"));
				bb.setBOARD_CONTENT(rs.getString("board_content"));
				bb.setBOARD_DATE(rs.getDate("board_date"));
				bb.setBOARD_FILE(rs.getString("board_file"));
				bb.setBOARD_NAME(rs.getString("board_name"));
				bb.setBOARD_PASS(rs.getString("board_pass"));
				bb.setBOARD_RE_LEV(rs.getInt("re_lev"));
				bb.setBOARD_RE_REF(rs.getInt("re_ref"));
				bb.setBOARD_READCOUNT(rs.getInt("board_readcount"));
				bb.setBOARD_SUBJECT(rs.getString("board_subject"));
				arr.add(bb);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeCon(con, st,rs);
		}
		return arr;
	}

	private void closeCon(Connection con, Statement st, ResultSet rs) {
		try {
			if(con!=null)con.close();
			if(st!=null)st.close();
			if(rs!=null)rs.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public BoardBean BoardView(int num) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		BoardBean bb = null;
		String sql = "";
		try {
			con = getConnection();
			st = con.createStatement();
			sql = "update board set board_readcount = board_readcount+1 where board_num = "+num;
			st.executeUpdate(sql);
			sql = "select * from board where BOARD_NUM = "+num;
			rs = st.executeQuery(sql);
			while(rs.next()) {
				bb = new BoardBean();
				bb.setBOARD_NUM(rs.getInt("board_num"));
				bb.setBOARD_CONTENT(rs.getString("board_content"));
				bb.setBOARD_DATE(rs.getDate("board_date"));
				bb.setBOARD_FILE(rs.getString("board_file"));
				bb.setBOARD_NAME(rs.getString("board_name"));
				bb.setBOARD_PASS(rs.getString("board_pass"));
				bb.setBOARD_RE_LEV(rs.getInt("re_lev"));
				bb.setBOARD_RE_REF(rs.getInt("re_ref"));
				bb.setBOARD_READCOUNT(rs.getInt("board_readcount"));
				bb.setBOARD_SUBJECT(rs.getString("board_subject"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeCon(con, st,rs);
		}
		return bb;
	}
}
