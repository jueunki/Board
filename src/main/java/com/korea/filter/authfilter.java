package com.korea.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class authfilter implements Filter{
	
	/* 페이지별로 권한을 0,1,2로 나눠서 권한부여하는것! */
	//Key : URL, Value : Grade
	Map<String,Integer> pageGradeMap = new HashMap();
	//-----여기까징
	public static boolean filterflag=false;
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	 
		pageGradeMap.put("/Board/list.do", 0); //모두 이용가능한 권한!!
		pageGradeMap.put("/Board/post.do", 1); //일반 계정권한 이상만 이용가능!!
		
		pageGradeMap.put("/Notice/list.do", 0); //모두 이용가능한 권한!!
		pageGradeMap.put("/Notice/post.do", 2); //관리자 등급에서만 사용가능한 권한!!
	}

	
	//Request 전 처리!
	/* req,resp : filter처리를 할수있습니다. 내장객체를 실어주기전에 체인을 기준으로 req전처리 resp후처리 
	 * 이건내일 다시 정리........!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		//request 전 처리
	System.out.println("========== Filter 처리(Request 전)!!!!==========");
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
	
		
			//Session 으로부터 Grade 추출
			
			//servlet request를 Http request로 다운캐스팅하는작업이다.
			HttpServletRequest request = (HttpServletRequest)req;

			//session을 통해서 grade를 가져오는 작업이다.
			HttpSession session = request.getSession();
			int usergrade = 0; //기본권한은 0으로 잡는다.
			if(session.getAttribute("grade")!=null) //처음접속이 아닐경우.session에 grade가 담겨있는경우.
				usergrade=(Integer)session.getAttribute("grade");
			
			
			//System.out.println("UesrGrade : " + usergrade);
			
			//URL(page) grade 확인
			String URL = request.getRequestURI();
			//System.out.println("Filter's URL : " + URL);
			int pagegrade=0;	//기본권한 0으로 설정!
			if(pageGradeMap.get(URL)!=null)
				pagegrade=pageGradeMap.get(URL); //
			//System.out.println("PageGrade : " + pagegrade);
			
			//guest계정이 1이상의 page로 접근 불가
			
			//user와 pagegrade를 이용해서 권한설정하는것!
			if(usergrade==0 && pagegrade>=1) {
				throw new ServletException("로그인이 필요한 페이지입니다.");
				}
			//admin 관리자 계정 
			//아래에 적은거는 관리자가아닌데 페이지에 접근하려할때
			
			//admin이 아닌 계정의 권한 제한!
			if(usergrade<2 && pagegrade==2) {
				throw new ServletException("페이지에 접근할 권한이 없습니다.");
			}
		
		
		
		
		chain.doFilter(req, resp);
		//Response 전 처리
		System.out.println("========== Filter 처리(Response 전)!!!!==========");
	
		
	
	}

	}




	
