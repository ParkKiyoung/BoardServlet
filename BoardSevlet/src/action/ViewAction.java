package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import vo.BoardBean;
import vo.CommentBean;

/**
 * Servlet implementation class ViewAction
 */
@WebServlet("/board/boardView")
public class ViewAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		int num = Integer.parseInt(request.getParameter("BOARD_NUM"));
		int i = 1;
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardBean bb = dao.BoardView(num,i);
		ArrayList<CommentBean> arr = dao.commentView(num);
		int cnt = dao.commentCount(num);
		
		request.setAttribute("bb", bb);
		request.setAttribute("cnt", cnt);
		request.setAttribute("arr", arr);
		RequestDispatcher rd  = request.getRequestDispatcher("boardView.jsp");
		rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
