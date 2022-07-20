//일반 BoardDownloadController 에서는 파일이름을 받는것이고 Allcontroller에서는 파일이름을 받지않습니다!!!!

package com.korea.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.dto.BoardDTO;
import com.korea.service.BoardService;

public class BoardDownloadAllController implements SubController{

	BoardService service = BoardService.getInstance();
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		
		//읽고있는 게시물 꺼내기
		HttpSession session = req.getSession();
		BoardDTO dto = (BoardDTO)session.getAttribute("dto");
		
		//서비스 실행(ZIP 압축 다운메서드)
		boolean result = service.downloadAllZIP(dto,resp);
	}

}
