package com.korea.dto;

public class MemberDTO {
	private String email;
	private String pwd;
	private String addr1;
	private String addr2;
	private int grade; //일반 : 1, 관리자 : 2, 익명 : 0 /이냐에 따라서 페이지 권한을 줄것입니다.
	
	public MemberDTO() {
		grade=1;
	}
	
	//Getter and Setter
	
	//ToString
		
	//생성자(우클릭+소스+밑에서두번째꺼)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "MemberDTO [email=" + email + ", pwd=" + pwd + ", addr1=" + addr1 + ", addr2=" + addr2 + ", grade="
				+ grade + "]";
	}
	public MemberDTO(String email, String pwd, String addr1, String addr2, int grade) {
		super();
		this.email = email;
		this.pwd = pwd;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.grade = grade;
	}
	
	
	
	
	
	
	
}
