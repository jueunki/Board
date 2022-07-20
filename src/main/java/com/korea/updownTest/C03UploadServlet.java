package com.korea.updownTest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
//자바UUID : 특정 고유폴더를 만들어서 업로드 시키는 방식
//<<하위폴더명    생성 (UUID)
//String subdir = UUID.randomUUID().toString();
//업로드 경로 생성
//File upload = new File("C://upload" + subdir);
//하위폴더 생성
//if(!upload.exists())
//upload.mkdir(); >>


@WebServlet("/upload3")
@MultipartConfig //지시서!
(
		fileSizeThreshold = 1024 * 1024 * 10, 	// 10MB
		maxFileSize = 1024 * 1024 * 50, 		// 50MB 파일 하나당 50MB를 초과하지 못하게 설정해놓은것
		maxRequestSize = 1024 * 1024 * 100 		// 100MB
)

//location : 업로드한 파일이 임시로 저장될 위치를 지정,절대경로만 가능
//location : fileSizeThresHold와 연관이 되어있는데 10메가가 넘어서는 데이터가 있다면 나머지 부분은 임시로 저장하는 공간이다.
// 기본값은 해당 자바가 실행되는 temp폴더
// maxFileSize : 업로드 가능한 파일의 최대 크기를 바이트 단위로 지정, -1은 제한없음(기본값)
// fileSizeThresHold : 업로드한 파일의 크기가 태그값보다 크면 locarion에 지정한 디렉토리에
// 임시로 파일을 저장한다.
public class C03UploadServlet extends HttpServlet {
//<<<<Multiple설정을 하여 여러개의 파일을 업로드 했을때 콘솔창에 원하는 양식의 파일들이 추출되는것을 확인할수 있다!!>>>>
	@Override  //오버라이드 해서 doPost객체를 받아와줍니다.
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 하위 폴더명 생성(UUID)
		String subdir = UUID.randomUUID().toString();
				
		// 업로드 경로 생성 // 하위 폴더 생성
		File upload = new File("C://upload/"+subdir);
		
		if(!upload.exists())
			upload.mkdirs();
		
		
		//MultiPart로 전달되는 모든 Part를 받아서 반복처리로 확인
		for(Part part : req.getParts())
		{
			String FileName = getFileName(part); //파일 이름 가져오기!!
			part.write("C://upload/"+subdir+"/"+FileName); //파일 업로드!!
		}
		
	}		
//	파트를 전달받아서 파일이름을 지정하는것을 먼저 해야한다.
		private String getFileName(Part part) //이거 만드는 이유는 보드DAO에 넣으려고 만드는것! 파일의 내용도 함께 전달할 수 있기때문!
		{
			String contentDisp=part.getHeader("content-disposition");
			String[] arr = contentDisp.split(";"); //배열화
			
			String Filename = arr[2].substring(11,arr[2].length()-1);
			
			return Filename;
		}
}
