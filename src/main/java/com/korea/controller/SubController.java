package com.korea.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* 상속관계로 사용하기위해서 쓰는것입니다. */
/*SubController interface : 회원가입 컨트롤러 (서브), 인증 컨트롤러 (서브), 게시판 컨트롤러 (서브)를 만들것입니다.*/

public interface SubController {
	
	void execute(HttpServletRequest req, HttpServletResponse resp);
	
}
