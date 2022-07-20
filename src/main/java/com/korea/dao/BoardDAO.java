package com.korea.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.korea.dto.BoardDTO;

public class BoardDAO {

	// DB연결
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "book_ex";
	private String pw = "1234";

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	// 싱글톤 패턴
	private static BoardDAO instance;

	public static BoardDAO getInstance() {
		if (instance == null)
			;
		instance = new BoardDAO();
		return instance;
	}

	private BoardDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("DBConnected..");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 조회함수
	// 시작페이지,끝페이지번호 받아서 조회
	public List<BoardDTO> Select(int start, int end) // end값은 항상 10까지!
	{

		ArrayList<BoardDTO> list = new ArrayList(); // 검색된 내용들을 담아서
		BoardDTO dto = null; // 내용들을 임시로 연결시킬 참조변수
		try {
			String sql = "select rownum rn , no, title, content, writer, regdate,pwd,count,ip,filename,filesize"
					+ " from" + "(" + "    select /*+ INDEX_DESC (tbl_board PK_NO) */"
					+ "    rownum rn,no, title, content, writer, regdate,pwd,count,ip,filename,filesize"
					+ "    from tbl_board where rownum <=?" + ")" + " where rn>=?";
			// sql문을 넣고! row넘버가 어디서부터 어디까지 검색 결과물이 나올수있게 하는 작업!

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			rs = pstmt.executeQuery();
			//
			while (rs.next()) {
				dto = new BoardDTO();
				dto.setNo(rs.getInt("NO"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWriter(rs.getString("writer"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setPwd(rs.getString("pwd"));
				dto.setIp(rs.getString("ip"));
				dto.setFilename(rs.getString("filename"));
				dto.setFilesize(rs.getString("filesize"));
				dto.setCount(rs.getString("count"));
				list.add(dto); // 리스트에 추가한다!
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
		return list; // 작업들을 리턴한다!
	}

	// 모든 게시물개수 조회!!
	public int getTotalCount() {
		int result = 0;
		try {

			pstmt = conn.prepareStatement("select count(*) from tbl_board"); // 서로 연결된다는뜻 "="의 좌우로!
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1); // 처음 나오는 게시물의 개수 값을 result에 들어가??

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
		return result;

	}

	public boolean Insert(BoardDTO dto) {

		try {

			pstmt = conn
					.prepareStatement("insert into tbl_board values(tbl_board_seq.NEXTVAL,?,?,?,sysdate,?,0,?,?,?)");
			// 총 물음표 열개 업로드 처리를 안할때는 0으로 표시 업로드 처리하는것은 ?로 표시!!
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getWriter());
			pstmt.setString(4, dto.getPwd());
			pstmt.setString(5, dto.getIp());
			pstmt.setString(6, dto.getFilename());
			pstmt.setString(7, dto.getFilesize());
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

	public BoardDTO Select(int No) {
		BoardDTO dto = new BoardDTO();
		try {
			pstmt = conn.prepareStatement("select * from tbl_board where no=?");
			pstmt.setInt(1, No); // 게시물 번호를 받는부분!
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto.setWriter(rs.getString("writer"));
				dto.setContent(rs.getString("content"));
				dto.setTitle(rs.getString("title"));
				dto.setPwd(rs.getString("pwd"));
				dto.setNo(rs.getInt("no"));
				dto.setIp(rs.getString("ip"));
				dto.setFilename(rs.getString("filename"));
				dto.setFilesize(rs.getString("filesize"));
				dto.setCount(rs.getString("count"));
				dto.setRegdate(rs.getString("regdate"));

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
		return dto;
	}

	// 마지막 No 확인하는 부분!
	public int getLastNo() {
		try {
			pstmt = conn.prepareStatement(
					"select /*+INDEX_DESC(tbl_board PK_NO)*/ rownum rn,no from tbl_board where rownum=1");
			rs = pstmt.executeQuery();
			rs.next();
			int no = rs.getInt(2); // No값

			return no;
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
		return 0;
	}

	// 카운트 증가 처리
	public void CountUp(int no) {
		try {
			pstmt = conn.prepareStatement("update tbl_board set count=count+1 where no=?");
			pstmt.setInt(1, no);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

//타이틀만 바꿨지만 게시물 안의 여러부분을 바꿀수 있게 하려고 dto로 설정을 했다.
	public boolean Update(BoardDTO dto) {

		try {

			pstmt = conn.prepareStatement("update tbl_board set title=?,content=? where no=?");
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getNo());
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

	public boolean Delete(BoardDTO dto) {
		try {
			//DB삭제부문!
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return false;

	}

}

/* try {}catch (Exception e) { e.printStackTrace();}finally { } */
