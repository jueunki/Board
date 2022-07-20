//일반 BoardDownloadController 에서는 파일이름을 받는것이고 Allcontroller에서는 파일이름을 받지않습니다!!!!

package com.korea.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.korea.controller.SubController;
import com.korea.service.BoardService;

public class BoardDownloadController implements SubController{

	BoardService service = BoardService.getInstance();
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		
		// 파라미터
		String filename=req.getParameter("filename"); //파일명을 받고
		String flag = req.getParameter("flag");
		// 입력값
		
		
		// 서비스실행 : 다운로드 함수를 만들어서 다운로드 요청을 한다.
		boolean result=false;
		result=service.download(filename, req, resp); //다운로드를 실행합니다.
		 			//전체파일 다운로드
			 
		
		// View
		
	}

}
