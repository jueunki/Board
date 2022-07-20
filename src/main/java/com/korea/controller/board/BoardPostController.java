package com.korea.controller.board;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.korea.controller.SubController;
import com.korea.dto.BoardDTO;
import com.korea.service.BoardService;

public class BoardPostController implements SubController {

	BoardService service = BoardService.getInstance(); // BoardService를 가져오는 작업!!

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		String flag = req.getParameter("flag");
		try {
			if (flag == null) // list.jsp에서 글쓰기 버튼을 누른 경우!!
			{
				req.getRequestDispatcher("/WEB-INF/board/post.jsp").forward(req, resp);
			} else // post.jsp에서 등록할 내용을 기입하고 그쓰기 버튼을 누륵 경우!!
			{
				// 1. 파라미터 꺼내기
				// -1) Title,content,pwd,writer,ip,(filename 와 filesize는 추후에 가져온다)
				String title = req.getParameter("title");
				String content = req.getParameter("content");
				String pwd = req.getParameter("pwd");
				String ip = req.getRemoteAddr();
				HttpSession session = req.getSession();
				String writer = (String) session.getAttribute("email");

				// 2. 입력값 검증(입력이 잘 되었는지 확인!)
				// 자바스크립트로 해도되고 서블릿으로 처리해도됩니다.
				if (title == null || content == null || pwd == null || ip == null) 
				{
//					하나라도 널이라면 되돌려 보내는데, 글쓰기했을때 잘못입력시 그전에 읽었던 위치로 가야한다.
//					글요청을 했는데 제대로 요청이 안되면은 Post로 이동하면된다.
//					입력값이 잘 되었으면 Boardlist로 가면된다.
					req.getRequestDispatcher("/WEB-INF/board/post.jsp").forward(req, resp);
					return;
				}

				// 3. 서비스 실행(dao와 연결되어있기때문에 doa로가서 설정해야한다.)
				BoardDTO dto = new BoardDTO();
				dto.setTitle(title);
				dto.setContent(content);
				dto.setPwd(pwd);
				dto.setIp(ip);
				dto.setWriter(writer);
				//추가(정리8일차-Upload) 파일 part 전달
				ArrayList<Part> parts = (ArrayList<Part>) req.getParts();
				boolean result = false;
				long size = parts.get(3).getSize();
				if(size==0) 	//파일 전달이 안된경우(파일이없다면)
					result = service.PostBoard(dto);
				else				//파일이 포함되어있는경우(파일이있다면)
					result = service.PostBoard(dto,parts);
				
				// 4. View 글쓰기를 했던 페이지위치로 가면된다.
				// 정상적인 경우라면 View!!
				if (result) {
//					int tcnt = service.getTotalCnt();
//					req.setAttribute("tcnt", tcnt);
//					ArrayList<BoardDTO> list = (ArrayList<BoardDTO>) service.getBoardList(1, 10);
//					req.setAttribute("list", list);
//					req.getRequestDispatcher("/WEB-INF/board/list.jsp?nowPage=1").forward(req, resp);
//					
					resp.sendRedirect("/Board/list.do");
					return; // ?nowPage=1 : 1번 게시물로 돌아가는 작업!!
				} 
				else {
					req.getRequestDispatcher("/WEB-INF/board/post.jsp").forward(req, resp);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // 설명 : 폼을 보고싶으면 바로전달,

}
