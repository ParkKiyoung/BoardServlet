package action;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.BoardDAO;
import vo.BoardBean;

/**
 * Servlet implementation class ReplyAction
 */
@WebServlet("/board/replyAction")
public class ReplyAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplyAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		ServletContext context = request.getServletContext();
		String uploadPath = context.getRealPath("/file"); //���� ���ε� ������
		int size = 10*1024*1024; //���� ������
		
		MultipartRequest multi = new MultipartRequest(request, uploadPath,size,"UTF-8",new DefaultFileRenamePolicy());
		

		int re_lev = Integer.parseInt(multi.getParameter("BOARD_RE_LEV"));
		int ref = Integer.parseInt(multi.getParameter("BOARD_RE_REF"));
		int re_step =Integer.parseInt(multi.getParameter("BOARD_RE_STEP"));
		
		String filename = multi.getFilesystemName("BOARD_FILE");
		String content = multi.getParameter("BOARD_CONTENT");
		String name = multi.getParameter("BOARD_NAME");
		String pass = multi.getParameter("BOARD_PASS");
		String subject = multi.getParameter("BOARD_SUBJECT");
		String originFileName = multi.getOriginalFileName("BOARD_FILE");
		
		BoardBean bb = new BoardBean();
		bb.setBOARD_CONTENT(content);
		bb.setBOARD_SUBJECT(subject);
		bb.setBOARD_PASS(pass);
		bb.setBOARD_NAME(name);
		bb.setBOARD_FILE(filename);
		
		bb.setBOARD_RE_LEV(re_lev);
		bb.setBOARD_RE_REF(ref);
		bb.setBOARD_RE_STEP(re_step);

		
		BoardDAO dao = BoardDAO.getInstance();
		dao.ReplyInsert(bb);
		response.sendRedirect("boardList.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
