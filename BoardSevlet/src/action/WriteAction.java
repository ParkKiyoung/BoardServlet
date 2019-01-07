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
 * Servlet implementation class WriteAction
 */
@WebServlet("/board/writeAction")
public class WriteAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		ServletContext context = request.getServletContext();
		String uploadPath = context.getRealPath("/file"); //파일 업로드 절대경로
		int size = 10*1024*1024; //파일 사이즈
		
		MultipartRequest multi = new MultipartRequest(request, uploadPath,size,"UTF-8",new DefaultFileRenamePolicy());
		
		//multi가 request를 받아서 다형으로 하기 때문에 파라미터값을 받을때 multi를 사용한다.
		
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

		
		BoardDAO dao = BoardDAO.getInstance();
		dao.BoardInsert(bb);
		response.sendRedirect("list.bo");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
