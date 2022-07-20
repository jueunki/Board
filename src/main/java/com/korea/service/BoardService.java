package com.korea.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.korea.dao.BoardDAO;
import com.korea.dto.BoardDTO;

public class BoardService {

	BoardDAO dao = BoardDAO.getInstance(); // DAO를 쓸수있는 함수!
	private String UploadPath = "C://upload/";

	// public : 컨트롤러에서 사용할수 있게 해주는 함수!
	// 싱글톤 패턴처리
	private static BoardService instance = null;

	public static BoardService getInstance() {
		if (instance == null)
			instance = new BoardService();
		return instance;
	}

	private BoardService() {
	}

	public List<BoardDTO> getBoardList(int start, int end) // 시작값과 끝값을 받아서 DAO에서 컨트롤러로 전달!
	{
		return dao.Select(start, end);
	}

	public int getTotalCnt() {
		return dao.getTotalCount();
	}

	public boolean PostBoard(BoardDTO dto) {
		return dao.Insert(dto);

	}

	// 파일포함 글쓰기 서비스
	public boolean PostBoard(BoardDTO dto, ArrayList<Part> parts) {
		
		// 업로드 처리
		// 1) 하위폴더명(Email/2022-07-14/파일쌓이기! + Data클래스를 통한 날짜별 폴더를 생성한다.)
		String email = dto.getWriter();
		Date now = new Date();
		
		// 날짜 포멧 변경하기 :
		// https://junghn.tistory.com/entry/JAVA-%EC%9E%90%EB%B0%94-%EB%82%A0%EC%A7%9C-%ED%8F%AC%EB%A7%B7-%EB%B3%80%EA%B2%BD-%EB%B0%A9%EB%B2%95SimpleDateFormat-yyyyMMdd
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = simpleDateFormat.format(now);
		
		//게시물 번호
		String no = String.valueOf(dao.getLastNo()+1);

		// 2) 기본업로드Path+하위폴더명
		// File RealPath = new File("기본업로드경로+하위폴더경로+게시물(넘버)추가");
		String subPath = email + "/" + date + "/" + no;

		// 3) File클래스 경로잡고
		File RealPath = new File(UploadPath + subPath);

		// 4) 하위폴더 생성
		if (!RealPath.exists())
			RealPath.mkdirs();

		// 파일명을 저장하기위한 StringBuffer를 추가
		StringBuffer totalFilename = new StringBuffer();
		
		// 파일 사이즈를 저장하기 위한 StringBuffer 추가
		StringBuffer totalFilesize = new StringBuffer();

		// 5) Parts의 각 Part를 write()
		for (Part part : parts) {
			if (part.getName().equals("files")) {
				String FileName = getFileName(part); // 파일 이름 가져오기!!

				// 파일명, 확장자명 구분하기
				FileName.lastIndexOf("."); // lastIndex : 파일명의 맨 뒷쪽 이름부터 찾아내겠다는 처리
				int start = FileName.length() - 4; // 확장자를 구하기 위한 시작idx
				int end = FileName.length(); // 확장자를 구하기 위한 끝idx
				String ext = FileName.substring(start, end); // 파일명 잘라내기(확장자만)
				FileName = FileName.substring(0, start); // 파일명 잘라내기(확장자제외)

				// 파일명 + _UUID + 확장자
				FileName = FileName + "_" + UUID.randomUUID().toString() + ext;

				// DAO 파일명 전달 : DTO저장을 위한 파일명buffer에 추가
				totalFilename.append(FileName + ";");
				// DTO 저장을 위한 파일명 Buffer에 추가!!
				totalFilesize.append(String.valueOf(part.getSize()) + ";"); // UUID를 포함해서 저장된다!!

				try {
					part.write(RealPath + "/" + FileName); // 파일 업로드!!
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// DTO에 총 파일명과 총 파일의 사이즈를 저장합니다.
		dto.setFilename(totalFilename.toString());
		dto.setFilesize(totalFilesize.toString());

		// DAO 파일명 전달!
		return dao.Insert(dto);
	}

	// 파일명 추출(PostBoard(dto,parts)가 사용된다.)
	private String getFileName(Part part) // 이거 만드는 이유는 보드DAO에 넣으려고 만드는것! 파일의 내용도 함께 전달할 수 있기때문!
	{
		String contentDisp = part.getHeader("content-disposition");
		String[] arr = contentDisp.split(";"); // 배열화
		String Filename = arr[2].substring(11, arr[2].length() - 1);
		return Filename;
	}

	// 게시물 하나 가져오기!!
	public BoardDTO getBoardDTO(int no) {
		return dao.Select(no);
	}

	// 단일 파일 다운로드
	public boolean download(String filename, // 파일이름
			HttpServletRequest req, // req파일위치
			HttpServletResponse resp // resp파일위치
	) {
		// 가져와야할것들!!
		// 1. 파일명 가져오기 : //위에 String filename 으로 가져왔음
		// 2. 등록날짜 가져오기 : 읽고있는 게시물을 수정하거나 삭제해야하니까 읽고있는 게시물을 세션에 넣어주는것이 좋다.

		// 3. 이메일 계정 가져오기
		HttpSession session = req.getSession(); // 읽고있는 게시물을 세션에 담는다.
		BoardDTO dto = (BoardDTO) session.getAttribute("dto"); // 이메일 가져왔음

		String email = dto.getWriter(); // 이메일계정과
		String regdate = dto.getRegdate(); // 등록날짜를 알고있어야한다. 이메일과 등록날짜를 받는다.
		String no = String.valueOf(dto.getNo());
		regdate = regdate.substring(0, 10); // 잘라내기 하는부분인데 강사님께 한번더 물어볼것!

		// System.out.println("REGDate : " + regdate);
		// 1 경로 설정
		String downdir = "c://upload"; // 업로드기준으로
		String filepath = downdir + "/" + email + "/" + regdate + "/" + no + "/" + filename; // 이메일과 날짜를다운로드하면
																					// 날짜폴더안에 단일파일 경로을 업로드했다

		// 헤더 설정
		resp.setContentType("application/octet-stream"); // 다운로드용으로 만들기위한 작업!

		// 문자셋 설정
		try {
			filename = URLEncoder.encode(filename, "utf-8").replaceAll("\\+", "%20"); // 공백때문에 문제생기는것을 잡아주는 작업
			resp.setHeader("Content-Disposition", "attachment; fileName=" + filename); // 파일이름 전달작업.

			// 04스트림 형성 (다운로드 처리)
			FileInputStream fin = new FileInputStream(filepath);
			ServletOutputStream bout = resp.getOutputStream(); // 브라우저방향으로 전달할...........

			int read = 0;
			byte buff[] = new byte[4096];
			while (true) {
				read = fin.read(buff, 0, buff.length); // 버퍼공간에 0번째부터 버퍼공간으로 받아낼것을 지정.
				if (read == -1)
					break;
				bout.write(buff, 0, read);
			}
			bout.flush(); // 초기화
			bout.close(); // 닫기
			fin.close(); // 닫기

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean downloadAllZIP(BoardDTO dto,HttpServletResponse resp)
	{
		String id = UUID.randomUUID().toString();
		//압축파일 경로
		String zipFileName="C:\\Users\\inyeo\\Downloads/All.zip";
		
				//파일명,//등록날짜//이메일계정 가져오기
				String email = dto.getWriter(); // 이메일계정과
				String regdate = dto.getRegdate(); // 등록날짜를 알고있어야한다. 이메일과 등록날짜를 받는다.
				String no = String.valueOf(dto.getNo());
				regdate = regdate.substring(0, 10);  

				
				// 1 경로 설정
				String downdir = "c://upload"; // 업로드기준으로
				String subpath = downdir + "/" + email + "/" + regdate + "/" + no + "/";	// 이메일과 날짜를다운로드하면
																							// 날짜폴더안에 단일파일 경로을 업로드했다
																							//첨부파일의 위치를 왁인한다!!!
				//파일 이름 리스트
				String filelist[] = dto.getFilename().split(";"); //파일형태가 배열형태로 들어가게된다!
				
				// 헤더 설정
				resp.setContentType("application/octet-stream"); // 다운로드용으로 만들기위한 작업!
				resp.setHeader("Content-Disposition", "attachment; fileName=ALL_" + id +".Zip");
				
				try {
					//파일에서 프로그램 방향으로 ZIPStream 생성해야한다
					ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFileName));	//해당파일 방향으로 파일이 만들어지는데. 프로그램 방향으로 데이터를 읽을준비를 하는것!!
											//보조 스트림					//기본스트림
					// 파일개수만큼 전달되어야해서 
					for(int i=0;i<filelist.length;i++) //0번부터 파일이름만큼 반복함.
					{
						//파일에서 프로그램 방향으로 inStream 생성해야한다 : 아카이브안에 들어갈 파일명 하나하나를 읽어들일수 있게 해주는것!
						FileInputStream fin = new FileInputStream(subpath+filelist[i]); //파일의 절대경로
						
						// ZipEntry생성, zout에 전달! : 어떤파일이 넣어질것인지 엔트리방식으로 형성한다.
						ZipEntry ent = new ZipEntry(filelist[i]);	//엔트리를 만들어서 파일명을 설정해주는 부분.
						zout.putNextEntry(ent);
						
						int read=0;
						byte buff[] = new byte[4096];
						while(true)
						{
							read=fin.read(buff,0,buff.length-1);
							if(read==-1)
								break;
							zout.write(buff,0,read);
							
						}
						zout.closeEntry();	//엔트리 단위 종료
						fin.close();		//파일 input 스트림 종료
						
					}
					zout.close();			//zipoutput 스트림 종료
					
					
					
					
					return true;

				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
	}
	public void CountUp(int no) {
		dao.CountUp(no);	// mvc패턴 자체가 역할을 나누는 작업입니다. db연결을 담당하는것이dao
	}
	public boolean UpdateBoard(BoardDTO dto)
	{
	return dao.Update(dto);	
	}
	public boolean BoardRemove(BoardDTO dto)
	{
		//첨부파일 삭제를 진행합니다.
		String email = dto.getWriter();
		String regdate = dto.getRegdate();
		regdate = regdate.substring(0,10);
		String no = String.valueOf(dto.getNo());
		
		String dirpath = UploadPath+email+"/"+regdate+"/"+no;
		
		//첨부파일 폴더경로!
		File dir = new File(dirpath);
		//폴더 경로로 부터 파일리스트 가져오기!
		File[] filelist = dir.listFiles();
		//첨부파일 모두 삭제!
		for(File filename : filelist)
		{
			filename.delete();
		}
		//첨부파일 폴더 삭제!
		dir.delete();
	
	return dao.Delete(dto);	
	}
	
}
