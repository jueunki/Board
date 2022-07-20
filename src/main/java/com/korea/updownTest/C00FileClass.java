package com.korea.updownTest;

import java.io.*;

public class C00FileClass {

	public static void main(String[] args) {
//		파일클래스 자바 관련된 것들 확인하는용도

		File tmp = new File("c://upload"); // 파일확인은 : "c://upload//test.뭐였지 "
											// "upload"만 하게되면 PATH : upload
											// PATH : C:\정보처리기사과정B\12 서버 프로그램 구현\workspace\Board\ upload 이렇게 뜬당.
		// 1 파일&디렉토리 여부 확인
		if (tmp.isFile())
			System.out.println("파일 입니다.");
		if (tmp.isDirectory())
			System.out.println("디렉토리 입니다.");

		// 2 경로확인
		System.out.println("PATH : " + tmp.getPath()); // 상대경로
		System.out.println("PATH : " + tmp.getAbsolutePath()); // 절대경로
//			실행해보면 절대,상대경로가 같은값이 나오는데 맨 상단에 File tmp = new File("c://upload"); 라고 절대경로를 고정해놔서 그렇다

		// 3 디렉토리 생성
		if (!tmp.exists()) // 디렉토리가 없다면이라는뜻
			tmp.mkdirs(); // 디렉토리를 생성하지않는다는뜻
//	해당경로가 있다면 true가 되기때문에 느낌표를 사용하여 없다면 "if(!tmp.exists())"으로 설정해준다.
		// PATH : c:\ upload\ tmp
		// PATH : c:\ upload\ tmp 실행하면 이렇게 뜬다

		// 4 파일 목록 확인
		File[] list = tmp.listFiles(); // 파일목록이 들어가있게 만들어주는부분!
		System.out.println("--------");
		for (int i = 0; i < list.length; i++) {
			if (list[i].isFile()) // *************파일만 뽑아올수있게 하는부분!**************
			{
				System.out.println("File(전체경로) : " + list[i]);
				System.out.println("File(이름만) : " + list[i].getName());
			}
		}
		System.out.println("--------");
		// 5 필터처리(원하는 파일만 가져오기)
		File[] list2 = tmp.listFiles(new FilenameFilter() { // new FilenameFilter(){} : 익명객체(이름부여 없이 만드는 객체)
//지정한 파일을 가져오고
//템프나 디렉토리중에 네임에 대한 필터를 할 수 있다?
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.contains(".pptx");
				// name.endWidth("t"); //끝 문자가 t인 파일 필터!
				// name.startWidth("s"); //첫 문자가 s인 파일 필터
				// ****여기설명 ???**** 적용이 되어서 필터링된다.

			}
		});

		for (int i = 0; i < list2.length; i++) {
			System.out.println("list2[i]");
		}
	}
}
