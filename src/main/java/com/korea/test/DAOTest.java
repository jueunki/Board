package com.korea.test;

import java.util.List;

import org.junit.Test;

import com.korea.dao.BoardDAO;
import com.korea.dto.BoardDTO;
/* 참고 : DAO는 memberDB 관련된 함수만 들어갑니다. */
public class DAOTest {
	
	/*
	 * @Test public void test() {
	 */
		/*
		 * MemberDTO dto = new MemberDTO(); dto.setEmail("example@example.com");
		 * dto.setPwd("1234"); dto.setAddr1("대구광역식 달서구 000");
		 * dto.setAddr2("00아파트 000동 000호");
		 */
		/*
		 * 계정 정보를 이렇게 만든후 sqldeveloper에 
		 * create table tbl_member 
		 * ( 
		 * email varchar(50)
		 * primary key, 
		 * pwd varchar2(255) not null, 
		 * addr1 varchar2(100), 
		 * addr2 varchar2(200), 
		 * grade int 
		 * ); 
		 * desc tbl_member;
		 * 
		 * select * from tbl_member;
		 * 
		 * 이렇게 써주고 실행해봅니다.
		 * 마지막 셀렉트프롬으로 실행했을때 디벨로퍼에서 결과값이 뜬다면 성공!
		 */		
		
		/*
		 * MemberDAO dao = MemberDAO.getInstance(); boolean result = dao.insert(dto);
		 * if(result) System.out.println("INSERT 성공"); else
		 * System.out.println("INSERT 실패"); }
		 */
		/* @Test */
	/*public void Test2() {
		//MemberDAO's Select(email)
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO dto = dao.Select("inyeong1233@naver.com");//존재하지않는 계정정보를 넣었을때 junit창에 잘못되었다고 뜹니다.
		System.out.println("결과 : " + dto.toString());
	}*/
	/*
	 * @Test public void Test3() { MemberDTO dto = new MemberDTO();
	 * dto.setEmail("inyeong1233@naver.com"); dto.setPwd("8770");
	 * dto.setAddr1("서울특별시"); dto.setAddr2("마포구");
	 * 
	 * MemberDAO dao = MemberDAO.getInstance(); dao.Update(dto); }
	 */
	/*
	 * @Test public void Test4() { MemberDTO dto = new MemberDTO();
	 * dto.setEmail("admin@admin.com"); dto.setPwd("1234"); dto.setAddr1("");
	 * dto.setAddr2(""); dto.setGrade(2);
	 * 
	 * MemberService service = MemberService.getInstance();
	 * service.MemberInsert(dto); //관리자 계정 등록
	 * 
	 * 
	 * dto.setEmail("guest@guest.com"); dto.setPwd("1234"); dto.setAddr1("");
	 * dto.setAddr2(""); dto.setGrade(0);
	 * 
	 * service.MemberInsert(dto); }
	 */
	/*
	 * @Test public void Test4() { BoardDAO dao = BoardDAO.getInstance();
	 * List<BoardDTO> list = dao.Select(11, 20); //list.forEach(dto ->
	 * System.out.println(dto)); for(int i=0;i<list.size();i++) {
	 * System.out.println(list.get(i)); } }
	 */
//	
//	@Test
//	public void Test5()
//	{
//		BoardDAO dao = BoardDAO.getInstance();
//		int result = dao.getTotalCount();
//		System.out.println("게시물 건수 : " + result);
//	}
//
//	@Test //dao테스트!!
//	public void Test6()
//	{
//		BoardDAO dao = BoardDAO.getInstance();
//		BoardDTO dto = new BoardDTO();
//		dto.setTitle("NEWTITLE");
//		dto.setContent("NEWCONTENT");
//		dto.setWriter("NEWWRITER");
//		dto.setPwd("112233");
//		dto.setIp("192.168.10.1");
//		
//		dao.Insert(dto);
//		
//	}
//

	@Test
	public void Test7()
	{
		BoardDAO dao = BoardDAO.getInstance();
		BoardDTO dto = dao.Select(500);
		System.out.println(dto);
	}










}
