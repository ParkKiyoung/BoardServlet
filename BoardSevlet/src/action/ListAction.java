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
 * Servlet implementation class ListAction
 */
@WebServlet("/board/list.bo")
public class ListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		BoardDAO dao = BoardDAO.getInstance();
		
		String filed = "";//일반 리스트 이기 때문에 검색값은 없음
		String word = "";
		
		String pageNum = request.getParameter("pageNum")==null?"1":request.getParameter("pageNum");
		
		int currentPage = Integer.parseInt(pageNum);
		int pageSize = 5;
		int startRow = (currentPage-1)*pageSize+1;
		int endRow = (currentPage*pageSize);
		
		int cnt = dao.BoardCount(filed,word); //게시글 수
		
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
		
		ArrayList<BoardBean> arr = dao.boardList(startRow,endRow);
		
		
		request.setAttribute("list", arr);
		request.setAttribute("cnt", cnt);
		RequestDispatcher rd = request.getRequestDispatcher("nomalList.jsp");
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
