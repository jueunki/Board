package com.korea.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.korea.auth.LoginController;
import com.korea.auth.LogoutController;
import com.korea.controller.board.BoardDeleteController;
import com.korea.controller.board.BoardDownloadAllController;
import com.korea.controller.board.BoardDownloadController;
import com.korea.controller.board.BoardListController;
import com.korea.controller.board.BoardPostController;
import com.korea.controller.board.BoardReadController;
import com.korea.controller.board.BoardUpdateController;
import com.korea.controller.member.MemberInfoController;
import com.korea.controller.member.MemberJoinController;
import com.korea.controller.member.MemberUpdateController;
import com.korea.controller.notice.NoticeListController;
import com.korea.controller.notice.NoticePostController;

@MultipartConfig //지시서!
(
		fileSizeThreshold = 1024 * 1024 * 10, 	// 10MB
		maxFileSize = 1024 * 1024 * 50, 		// 50MB 파일 하나당 50MB를 초과하지 못하게 설정해놓은것
		maxRequestSize = 1024 * 1024 * 100 		// 100MB
)
public class FrontController extends HttpServlet{
	/*
	 * Request 요청이 들어온다 FrontController Service함수의 URL확인을 한다 URL에 해당하는
	 * SubController를 꺼낸다 (객체주소확인) SubController의 execute를 실행한다
	 * 회원정보수정,삭제,로그인처리,로그아웃컨드롤러는 URL과 객체형태로 한번만 호출이된다.+함께 저장을 해준다 요청URL을 꺼내고나서
	 * 서브컨트롤러를 꺼내서 해당 컨트롤러가 꺼내져서 req,resp 객체의 주소가 함께 전달되어서 함께 처리되는것입니다!
	 */
 
	
	
	
	//url과 ~의 컬렉션으로 저장공간형태를 만들어줘야합니다.
	//URL : SubController객체주소
	HashMap <String,SubController> list = null;
	
	
	@Override
	public void init() throws ServletException {
		list = new HashMap();
		//회원관련
		list.put("/MemberJoin.do", new MemberJoinController());
		list.put("/MemberInfo.do", new MemberInfoController());
		list.put("/MemberUpdate.do", new MemberUpdateController());
		//인증관련
		list.put("/Login.do", new LoginController());
		list.put("/Logout.do", new LogoutController());
		
		//게시판 관련
		list.put("/Board/list.do", new BoardListController());
		list.put("/Board/post.do", new BoardPostController());
		list.put("/Board/read.do", new BoardReadController());
		list.put("/Board/download.do", new BoardDownloadController());
		list.put("/Board/downloadAll.do", new BoardDownloadAllController());
		list.put("/Board/update.do", new BoardUpdateController());
		list.put("/Board/delete.do", new BoardDeleteController());
		
		//공지사항
		list.put("/Notice/list.do", new NoticeListController());
		list.put("/Notice/post.do", new NoticePostController());
	}	

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		
		
		String url = req.getRequestURI();
		System.out.println("URL : " + url);
		SubController sub=list.get(url);
		if(sub!=null)
		sub.execute(req, resp);
		/* 멤버조인 컨트롤러(객체주소가 담겨져있다)가 꺼내어진다면 서브컨트롤러로 처리를 맡긴다는것입니다. */
	}


}
