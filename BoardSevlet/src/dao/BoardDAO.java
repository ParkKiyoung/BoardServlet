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
import vo.CommentBean;

public class BoardDAO {
	private static BoardDAO instance = new BoardDAO();

	public static BoardDAO getInstance() {
		return instance;
	}

		//DB연결
	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/board");
		return ds.getConnection();
	}

	public void BoardInsert(BoardBean bb) {//글작성
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
	public void ReplyInsert(BoardBean bb) {//답글 작성
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "";
		try {
			con = getConnection();
			int ref = bb.getBOARD_RE_REF();
			int re_step = bb.getBOARD_RE_STEP();
			int re_lev = bb.getBOARD_RE_LEV();
			sql = "update board set re_step=re_step+1 where re_ref=? and re_step>?";
			ps =con.prepareStatement(sql);
			ps.setInt(1, ref);
			ps.setInt(2, re_step);
			ps.executeUpdate();
			
			re_step = re_step+1;
			re_lev = re_lev+1;
			
			sql = "insert into board values(board_seq.nextval,?,?,?,?,?,?,?,?,?,sysdate)";
			ps = con.prepareStatement(sql);
			ps.setString(1, bb.getBOARD_NAME());
			ps.setString(2, bb.getBOARD_PASS());
			ps.setString(3, bb.getBOARD_SUBJECT());
			ps.setString(4, bb.getBOARD_CONTENT());
			ps.setString(5, bb.getBOARD_FILE());
			ps.setInt(6, ref);
			ps.setInt(7, re_lev);
			ps.setInt(8, re_step);
			ps.setInt(9, 0);
			ps.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeCon(con, ps, rs);
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

	public ArrayList<BoardBean> boardList(int startRow, int endRow) {//리스트 보기
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<BoardBean> arr = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "select * from(select rownum rn, aa.* from (select * from board order by re_ref desc, re_step)aa) "
					+ "where rn >= ? and rn <=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, startRow);
			ps.setInt(2, endRow);
			rs=ps.executeQuery();
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
				bb.setBOARD_RE_STEP(rs.getInt("re_step"));
				bb.setBOARD_PASS(rs.getString("board_pass"));
				arr.add(bb);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeCon(con,ps,rs);
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
		
	}	private void closeCon(Connection con, PreparedStatement ps, ResultSet rs) {
		try {
			if(con!=null)con.close();
			if(ps!=null)ps.close();
			if(rs!=null)rs.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public BoardBean BoardView(int num, int i) {//글 상세 보기+조회수 증가
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		BoardBean bb = null;
		String sql = "";
		try {
			con = getConnection();
			st = con.createStatement();
			if(i==1) {
			sql = "update board set board_readcount = board_readcount+1 where board_num = "+num;
			st.executeUpdate(sql);
			}
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
				bb.setBOARD_RE_STEP(rs.getInt("re_step"));
				bb.setBOARD_PASS(rs.getString("board_pass"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeCon(con, st,rs);
		}
		return bb;
	}

	public int passCheck(int num, String pass) {//수정, 삭제용 비번 확인
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select * from board where board_num = "+num;
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				if(pass.equals(rs.getString("board_pass"))){
					return 2;
					
				}else {
					return 1;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	public void BoardUpdate(BoardBean bb) {
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = getConnection();
			String sql = "update board set board_name = ?, board_PASS=?,board_subject=?,board_content=?,Board_FILE=? where BOARD_num="+bb.getBOARD_NUM();
			ps = con.prepareStatement(sql);
			ps.setString(1, bb.getBOARD_NAME());
			ps.setString(2, bb.getBOARD_PASS());
			ps.setString(3, bb.getBOARD_SUBJECT());
			ps.setString(4, bb.getBOARD_CONTENT());
			ps.setString(5, bb.getBOARD_FILE());
			ps.executeQuery();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeCon(con,ps);
		}
	}

	public int boardDelete(int num, String pass) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			con = getConnection();
			sql = "select * from board where BOARD_NUM="+num;
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				if(pass.equals(rs.getString("BOARD_PASS"))) {
					sql = "delete board where BOARD_num="+num;
					st.execute(sql);
					return 2;
				}
				return 1;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeCon(con, st, rs);
		}
		return 1;
	}

	public int BoardCount(String filed, String word) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		
		try {
			con = getConnection();
			if(filed.equals("")||filed==null) {
				 sql = "select count(*) from board ";
			}else {
				 sql = "select count(*) from board where "+filed+" like '%"+word+"%'";	
			}
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				cnt = rs.getInt("count(*)");
				return cnt;
			}
					
		}catch(Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}

	public ArrayList<BoardBean> boardSearch(String field, String word, int startRow, int endRow) { //검색 배열
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<BoardBean> arr = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "select * from(select rownum rn, aa.* from "
					+ "(select * from board where "+field+" like '%"+word+"%'"
					+ " order by board_num desc)aa) "
					+ "where rn >= ? and rn <=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, startRow);
			ps.setInt(2, endRow);
			rs=ps.executeQuery();
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
				bb.setBOARD_RE_STEP(rs.getInt("re_step"));
				bb.setBOARD_PASS(rs.getString("board_pass"));
				arr.add(bb);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeCon(con,ps,rs);
		}
		return arr;
	}

	public void commentInsert(CommentBean cb) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con=getConnection();
			String sql = "insert into board_comment values(comment_seq.nextval,?,?,?,sysdate,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, cb.getC_name());
			ps.setString(2, cb.getC_pass());
			ps.setString(3, cb.getC_msg());
			ps.setInt(4, cb.getB_num());
			ps.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeCon(con, ps);
			
		}
		
	}

	public ArrayList<CommentBean> commentView(int num) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList<CommentBean> arr = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "select * from board_comment where b_num = "+num;
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				CommentBean cb = new CommentBean();
				cb.setC_num(rs.getInt("c_num"));
				cb.setC_date(rs.getString("c_date"));
				cb.setC_msg(rs.getString("c_msg"));
				cb.setC_name(rs.getString("c_name"));
				cb.setC_pass(rs.getString("c_pass"));
				arr.add(cb);
			}
					
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeCon(con, st, rs);
		}
		return arr;
	}


}
