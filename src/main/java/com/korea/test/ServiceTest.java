package com.korea.test;

import org.junit.Test;

import com.korea.dto.MemberDTO;
import com.korea.service.MemberService;

public class ServiceTest{

	@Test
	public void test() {
		MemberDTO dto = new MemberDTO();
		dto.setEmail("example@example.com");
		dto.setPwd("1234");
		dto.setAddr1("대구광역식 달서구 000");
		dto.setAddr2("00아파트 000동 000호");
		
		MemberService service = MemberService.getInstance();
		service.MemberInsert(dto);
	}

}
