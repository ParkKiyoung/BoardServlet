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

/**
 * Servlet implementation class SearchAction
 */
@WebServlet("/board/search.bo")
public class SearchAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String pageNum = request.getParameter("pageNum");
		String field = request.getParameter("field");
		String word = request.getParameter("word");
		
		BoardDAO dao = BoardDAO.getInstance();
		int cnt = dao.BoardCount(field, word);
		
		int currentPage = Integer.parseInt(pageNum);
		int pageSize = 10;
		int startRow = (currentPage-1)*pageSize+1;
		int endRow = (currentPage*pageSize);
		
		int totPage = cnt/pageSize+(cnt%pageSize==0?0:1);
		int blockPage = 3;
		int startPage = ((currentPage-1)/blockPage)*blockPage+1;
		int endPage = startPage+blockPage-1;
		
		if(endPage>totPage)endPage=totPage;
		request.setAttribute("totPage", totPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("blockPage",blockPage);
		
		ArrayList<BoardBean> arr = dao.boardSearch(field,word,startRow,endRow);
		request.setAttribute("field", field);
		request.setAttribute("word", word);
		request.setAttribute("startRow", startRow);
		request.setAttribute("endRow", endRow);
		request.setAttribute("list", arr);
		
		RequestDispatcher rd = request.getRequestDispatcher("searchList.jsp");
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
