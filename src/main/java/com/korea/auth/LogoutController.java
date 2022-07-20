package com.korea.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.filter.authfilter;

public class LogoutController implements SubController{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
	 
		HttpSession session = req.getSession();
		session.invalidate();
		
	try {
		//필터 로그아웃시 flag를 false로 설정!
		//재 접속시 한번은 session으로부터 grade를 꺼내지 않는다.
		
		req.setAttribute("MSG", "로그아웃 완료");
		req.getRequestDispatcher("/").forward(req, resp);
		//resp.sendRedirect("/");
	
	}catch (Exception e) {
		e.printStackTrace();
	}
	}
	
	/*
	 * do filter이전으로 true를 잡아서 처리를해야한다
	 * filterflag가 false로 유지되게해야지 Logout에서 
	 * filter -> Login 
	 * filter <- Logout
	 */
}
