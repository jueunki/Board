<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 반드시 메서드 타입은 post로 해줘야고, enctype은 꼭 써줘야한다 -->

<!--
	Multipart
	클라이언트에서 서버로 페이지 요청시 업로드 파일을 포함하는 경우에
	Post방식으로 body에 파일들을 나누어서 처리해야 한다.
	이떄 여러 repuest에 나눠진 body들이 서버로 전달되기 위해서
	multipart/form-data 옵션을 적용한다.

-->
<form method="post" action="/upload1" enctype="multipart/form-data">
	Upload's file : <input type="file" name="test">
	<input type="submit" value="upload">
</form>
<!-- 실행하게되면! 
Part name : test
File Size : 2701528 byte
Header : [content-disposition, content-type]
content-disposition : form-data; name="test"; filename="01 [교재] 설치.pptx"
이렇게 콘솔창에 띄워진다!!!
구현된 화면에서 원하는 파일을 선택하고 업로드 버튼을 누르면 나오는 내역이다.
-->
</body>
</html>