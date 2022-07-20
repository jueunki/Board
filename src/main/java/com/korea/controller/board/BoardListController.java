//여기서 쿠키를 만듭니다!!


package com.korea.controller.board;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.korea.controller.SubController;
import com.korea.dto.BoardDTO;
import com.korea.service.BoardService;

public class BoardListController implements SubController{

	BoardService service = BoardService.getInstance(); //서비스 연결작업!
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		try{
			// 파라미터
			String tmpstart = req.getParameter("start");
			String tmpend = req.getParameter("end");
			String nowPage = req.getParameter("nowPage");
			int start=0;
			int end=0;
			//파라미터로 들어오는값을 먼저 확인해줘야한다.
			/* tmpstart==null : ==null은 한번더 주어지지 않았다는 뜻이다*/
			if(tmpstart==null || tmpend==null) //내용이 전달되지않았다면 start를 1 end를 10으로 잡는다는 뜻.
			{
				start=1;
				end=10;
			}
			else //내용이 전달되어있다면 start와 end를 실행시킨다.
			{
				start = Integer.parseInt(tmpstart);
				end = Integer.parseInt(tmpend);
			}

			// 입력값
			
			// 서비스 실행
			List<BoardDTO> list = service.getBoardList(start, end);
			int tcnt = service.getTotalCnt();
				//tcnt : 총건수
			
			
			req.setAttribute("list", list); // req내장객체가 잘 전달되도록 유지!
			req.setAttribute("tcnt", tcnt);
			//정리 6일차(페이징처리) 추가
			//req.setAttribute("nowPage", nowPage);
			
			
			// 쿠키 생성(게시글 읽기 새로고침시 중복 Count방지! : 게시글읽을때 새로고침하면 새로고침한 만큼 읽히는것을 방지하는부분!!)
			Cookie init = new Cookie("init","true");
			resp.addCookie(init);
			
			
			
			
			req.getRequestDispatcher("/WEB-INF/board/list.jsp?nowPage="+nowPage).forward(req, resp);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
