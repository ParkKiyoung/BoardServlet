package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import vo.CommentBean;

/**
 * Servlet implementation class CommentAction
 */
@WebServlet("/board/commentAction")
public class CommentAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		int c_num = Integer.parseInt(request.getParameter("c_num"));
		int b_num = Integer.parseInt(request.getParameter("b_num"));
		String pass = request.getParameter("pass");
		BoardDAO dao = BoardDAO.getInstance();
		int a = dao.commentDelete(c_num,pass);
		if(a==1) {//비밀번호 불일치
			request.setAttribute("a", 1);
			RequestDispatcher rd = request.getRequestDispatcher("c_passCheck.jsp?c_num="+c_num+"&BOARD_NUM="+b_num);
			rd.forward(request, response);
		}else if(a==2){//비밀번호 일치
			RequestDispatcher rd = request.getRequestDispatcher("boardView?BOARD_NUM="+b_num);
			rd.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String c_name = request.getParameter("name");
		String c_pass = request.getParameter("pass");
		String c_msg = request.getParameter("msg");
		int b_num = Integer.parseInt(request.getParameter("b_num"));
		
		CommentBean cb = new CommentBean();
		
		cb.setB_num(b_num);
		cb.setC_msg(c_msg);
		cb.setC_name(c_name);
		cb.setC_pass(c_pass);
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.commentInsert(cb);
		response.sendRedirect("boardView?BOARD_NUM="+b_num);
	}

}
