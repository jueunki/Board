//업데이트 컨트롤러를 사용하는 위치는 read.jsp입니다.

package com.korea.controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.dto.BoardDTO;
import com.korea.service.BoardService;

public class BoardUpdateController implements SubController{

	BoardService service = BoardService.getInstance();
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		//패스워드가 일치하다면 아래에 1번과 2번 순서로 진행된다.
		
		//파라미터 확인하기 : 변경요청할때 사용하는것
		String title = req.getParameter("title");		//타이틀과,
		String content = req.getParameter("content");	//컨텐츠만 바꿀것이다.
		String pwd = req.getParameter("pwd");
		String nowPage = req.getParameter("nowPage");
		
		System.out.println("TITLE : " + title);		//변경된 타이틀을 dto에 넣는다.
		System.out.println("content : " + content);	//변경된 컨텐츠를 dto에 넣는다.
		System.out.println("pwd : " + pwd);	//서비스 함수를 호출해서,
		
		//패스워드 일치여부 확인하는것, 읽고있는 게시물을 꺼내옵니다(session에서!)
		HttpSession session = req.getSession();
		BoardDTO dto = (BoardDTO)session.getAttribute("dto");
	
		if(dto.getPwd().equals(pwd))	//읽고있는 게시물의 Pwd와 변경 요청시 전달한 Pwd가 일치할때,
		{
			//서비스 함수를 호출한다 -> DAO -> DB를 변경한다.
			dto.setTitle(title);				//변경된 타이틀이다.
			dto.setContent(content);			//변경된 컨텐츠다.
			service.UpdateBoard(dto);			//업데이트 요청서비스다.	//BoardDAO로 가서 public boolean형으로 UpdateBoard를 만들어줍니다.
			session.setAttribute("dto", dto);	//세션 객체에 읽고있는 게시물 변경후 저장한다.
			
			//Read.jsp로 이동한다.(no,nowPage로 전달해야한다.)
			try {
				String MSG="업데이트 성공!!";
				req.setAttribute("MSG", MSG);
				req.getRequestDispatcher("/Board/read.do?no="+dto.getNo()+"&nowPage="+nowPage).forward(req, resp);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else	//패스워드가 틀리다면의 경우를 작성하는 부분이다.
		{
			
			try {
				String MSG="패스워드 불일치!";
				req.setAttribute("MSG", MSG);
				req.getRequestDispatcher("/Board/read.do?no="+dto.getNo()+"&nowPage="+nowPage).forward(req, resp);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
	}

}
