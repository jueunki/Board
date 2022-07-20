package com.korea.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.korea.dto.MemberDTO;

public class MemberDAO {

	// DB연결
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "book_ex";
	private String pw = "1234";

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	// 싱글톤 패턴
	private static MemberDAO instance;

	public static MemberDAO getInstance() {
		if (instance == null)
			;
		instance = new MemberDAO();
		return instance;
	}

	private MemberDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("DBConnected..");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// INSERT 함수
	public boolean insert(MemberDTO dto) {
		try {
			pstmt = conn.prepareStatement("insert into tbl_member values(?,?,?,?,?)");
			pstmt.setString(1, dto.getEmail());
			pstmt.setString(2, dto.getPwd());
			pstmt.setString(3, dto.getAddr1());
			pstmt.setString(4, dto.getAddr2());
			pstmt.setInt(5, dto.getGrade());
			int result = pstmt.executeUpdate();
			if (result > 0)
				return true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public MemberDTO Select(String email) {
		MemberDTO dto = new MemberDTO();
		try {
			pstmt = conn.prepareStatement("select * from tbl_member where email=?");
			pstmt.setString(1, email);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setEmail(rs.getString("email"));
				dto.setPwd(rs.getString("pwd"));
				dto.setAddr1(rs.getString("addr1"));
				dto.setAddr2(rs.getString("addr2"));
				dto.setGrade(rs.getInt("grade"));
				return dto;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	public boolean Update(MemberDTO dto) {
		try {
				
			
			
			
				pstmt=conn.prepareStatement
						("update tbl_member set addr1=?,addr2=?,grade=?,pwd=? where email=?");
				pstmt.setString(1, dto.getAddr1());
				pstmt.setString(2, dto.getAddr2());
				pstmt.setInt(3, dto.getGrade());
				pstmt.setString(4, dto.getPwd());
				pstmt.setString(5, dto.getEmail());
				
				int result = pstmt.executeUpdate();
				
				if(result>0) {
					System.out.println("성공");
					return true;
				} else {
					System.out.println("실패");
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
