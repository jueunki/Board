package com.korea.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.dto.MemberDTO;
import com.korea.service.MemberService;

public class LoginController implements SubController{
	MemberService service = MemberService.getInstance();
	
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {

		System.out.println("LoginController로 진입!");
		
		//파라미터 받기(= ID,PW...)
		String email = req.getParameter("email");
		String pwd = req.getParameter("pwd");
	try {
		//입력값 검증
		if(email==null || pwd==null)
		{
			resp.sendRedirect("/");
			
		}
		
		//서비스 실행
		//멤버 서비스를 이용해서 DAO로 이동해서 아이디가 있는지 DB로 확인해야한다. 확인이된다면true/false로 확인이 가능하다.
		//해당  id pw를 꺼내오기 
		MemberDTO dto = service.MemberSearch(email);
		if(dto!=null)
		{
		//<<"널이 아니라면 pw 일치 여부를 확인">>해야하고 일치한다면 메인페이지로 이동!!	
			//if(pwd.equals(dto.getPwd()))
			if(service.passwordEncoder.checkpw(pwd, dto.getPwd()))
					//설명! = pwd, dto.getPwd() : pwd-로그인에서 꺼내오는 패스워드! 
					//      , dto.getPwd()-DB로부터 꺼내온 암호화된 패스워드를 나타내는데 로그인시에 true,false로 나뉜다!  
			{
				//패스워드 일치(일치하면 이동!)
				
				//세션에 특정옵션을 부여하기!(ex.계정의 grade를 저장 등등..)
				HttpSession session = req.getSession();
				session.setAttribute("grade", dto.getGrade());
				session.setAttribute("email", dto.getEmail()); //세션에서 이메일을 꺼낼것임!
				session.setMaxInactiveInterval(60*5);
				
				
				
				//View(= main page)로 이동	
				resp.sendRedirect("/main.jsp");
			
			}
			else
			{
				//패스워드 불일치
				req.setAttribute("MSG", "패스워드가 일치하지 않습니다...");
				req.getRequestDispatcher("/").forward(req, resp);
				return;
			
			}
		}
		else
		{
			//아이디 조회 실패.. 해당 아이디가 없습니다.
			req.setAttribute("MSG", "일치하는 아이디가 없습니다..");
			req.getRequestDispatcher("/").forward(req, resp);
			return;

		}

		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
/*
 * 계정정보를 꺼내와서 디티오를 담아서 널이 아니고 계정정보가 있으면 내려오는데 아이디는 있는데 일치한 if = 아이디가 확인된것이고
 * 불일치한다면 else = 아이디가 확인되지않는것이다.
 */