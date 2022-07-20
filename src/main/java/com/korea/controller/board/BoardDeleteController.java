//패스워드를 건져내서 일치하다면 게시물과 첨부파일 삭제를 진행할것입니다.


package com.korea.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.dto.BoardDTO;
import com.korea.service.BoardService;

public class BoardDeleteController implements SubController{

	
	BoardService service = BoardService.getInstance();	//BoardService를 꺼냅니다.
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		
		// 파라미터 검증
		String pwd = req.getParameter("pwd");
		String nowPage = req.getParameter("nowPage");
		
		// 읽고있는 게시물 꺼내기
		HttpSession session = req.getSession();
		BoardDTO dto = (BoardDTO)session.getAttribute("dto");
		
		// 입력값 확인
		if(dto.getPwd().equals(pwd))	//패스워드 일치여부를 확인하는 부분입니다.
		{
			//패스워드가 일치하면 서비스함수를 실행해서 삭제작업을 해야하는데 삭제시 읽고있는 게시물을 전달해야합니다. 게시물번호를 알고있으면됩니다.
			//서비스 실행 함수 만들기(게시뭏dto)
			service.BoardRemove(dto);
		}
		
		// 서비스 실행
		
		// 뷰
		
	}

}








