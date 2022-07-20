package com.korea.service;

import org.mindrot.bcrypt.BCrypt;

import com.korea.dao.MemberDAO;
import com.korea.dto.MemberDTO;

public class MemberService {

	/* DAO를 사용해야하기 때문에 Member를 참조변수로 둡니다.*/
	
	MemberDAO dao = MemberDAO.getInstance(); //DAO를 쓸수있는 함수!
	public BCrypt passwordEncoder = new BCrypt(); //여기안에 솔트 테이블이라고 있는데 그안에있는 여러 값들중 하나를 보여준다. 로그인 할때마다 다 다름!
	//public : 컨트롤러에서 사용할수 있게 해주는 함수!
	//싱글톤 패턴처리
	private static MemberService instance=null;
	public static MemberService getInstance() {
		if(instance==null)
			instance = new MemberService();
		return instance;
	}
	private MemberService() {}
	
	public boolean MemberInsert(MemberDTO dto)
	{
		//패스워드 암호화
		String pwd = passwordEncoder.hashpw(dto.getPwd(), passwordEncoder.gensalt());
		//gensalt() : 패스워드를 암호화했을때 추가되는 특정한 랜덤값, 첨가물!
		System.out.println("PWD(EN) : "+pwd);
		dto.setPwd(pwd);
		return dao.insert(dto);
	}
	public MemberDTO MemberSearch(String email)
	{
		return dao.Select(email);
	}
	public boolean MemberUpdate(MemberDTO dto) {
		
		return dao.Update(dto);
	}
	
	
	
	
}
