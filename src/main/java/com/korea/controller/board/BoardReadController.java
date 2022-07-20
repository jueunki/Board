// 게시물을 읽었을때 몇명이 읽었는지 확인하는것! 카운트!!
// 여기서 쿠키를 삭제합니다!!

package com.korea.controller.board;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.dto.BoardDTO;
import com.korea.service.BoardService;


public class BoardReadController implements SubController{

	BoardService service = BoardService.getInstance();
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		
		// 파라미터
		String no = req.getParameter("no");
		String nowPage = req.getParameter("nowPage");
		
		// 서비스 실행
		int num = Integer.parseInt(no);
		
		// init쿠키 꺼내기
		Cookie[] cookies = req.getCookies();
		for(int i=0;i<cookies.length;i++)
		{
			if(cookies[i].getName().equals("init")) //init인쿠키를 찾았다면	
			{cookies[i].setMaxAge(0);		//쿠키제거
			resp.addCookie(cookies[i]);		//response에 쿠키제거 적용
			service.CountUp(num);			//조회수 증가
			break;//반복문 벗어나기
			}
		}
		
		
		// 게시물 받기
		BoardDTO dto = service.getBoardDTO(num);
		
		//세션에 읽고있는 게시물 저장 : 읽고있는 게시물을 수정하거나 삭제해야하니까 읽고있는 게시물을 세션에 넣어주는것이 좋다.
							  //수정,삭제로 이동시 현재 읽는 게시물 확인하기 쉽다.
		HttpSession session = req.getSession();
		session.setAttribute("dto", dto);
		
		
		// 뷰로 이동
		try {
		req.setAttribute("dto", dto);
		req.setAttribute("nowPage", nowPage);
		req.getRequestDispatcher("/WEB-INF/board/read.jsp").forward(req,resp);
		}catch(Exception e) {
			e.printStackTrace();}
		
		}
	}

	


