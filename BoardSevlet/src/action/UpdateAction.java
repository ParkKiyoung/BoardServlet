package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class UpdateAction
 */
@WebServlet("/board/update.bo")
public class UpdateAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��� üũ
		request.setCharacterEncoding("utf-8");
		int num = Integer.parseInt(request.getParameter("num"));
		String pass = request.getParameter("pass");
		
		BoardDAO dao = BoardDAO.getInstance();
		int a = dao.passCheck(num, pass);
		
		if(a==1) {//��й�ȣ ����ġ
			request.setAttribute("a", 1);
			RequestDispatcher rd = request.getRequestDispatcher("passCheck.jsp?BOARD_NUM="+num+"&checkNum=1");
			rd.forward(request, response);
		}else if(a==2){//��й�ȣ ��ġ
			BoardBean bb = dao.BoardView(num, 0);
			request.setAttribute("bb", bb);
			RequestDispatcher rd = request.getRequestDispatcher("updateForm.jsp");
			rd.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//����
		request.setCharacterEncoding("utf-8");
		
		ServletContext context = request.getServletContext();
		String uploadPath = context.getRealPath("/file"); //���� ���ε� ������
		int size = 10*1024*1024; //���� ������
		
		MultipartRequest multi = new MultipartRequest(request, uploadPath,size,"UTF-8",new DefaultFileRenamePolicy());
		
		//multi�� request�� �޾Ƽ� �������� �ϱ� ������ �Ķ���Ͱ��� ������ multi�� ����Ѵ�.
		
		String filename = multi.getFilesystemName("BOARD_FILE");
		String content = multi.getParameter("BOARD_CONTENT");
		String name = multi.getParameter("BOARD_NAME");
		String pass = multi.getParameter("BOARD_PASS");
		String subject = multi.getParameter("BOARD_SUBJECT");
		String originFileName = multi.getOriginalFileName("BOARD_FILE");
		int num = Integer.parseInt(multi.getParameter("BOARD_NUM"));
		
		BoardBean bb = new BoardBean();
		bb.setBOARD_NUM(num);
		bb.setBOARD_CONTENT(content);
		bb.setBOARD_SUBJECT(subject);
		bb.setBOARD_PASS(pass);
		bb.setBOARD_NAME(name);
		bb.setBOARD_FILE(filename);

		
		BoardDAO dao = BoardDAO.getInstance();
		dao.BoardUpdate(bb);
		response.sendRedirect("boardList.jsp");
	}

}
