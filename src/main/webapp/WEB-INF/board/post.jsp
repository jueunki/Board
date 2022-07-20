<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 코드를 가져옵니다. -->
<%@include file="/resources/includes/link.jsp" %>
<link rel="stylesheet" href="resources/css/common.css">

</head>
<body>
<!-- 메인 만드는 작업 시작! -->
	<div class="container-md" id="wrapper" style="margin:100px auto">
	<!-- TopMenu -->
	<%@include file="/resources/includes/topmenu.jsp" %> 
	
	
	
	<!-- NAV -->
<!-- 	부트스트랩에서 코드를 가져와서 인클루드 폴더에 네비파일 만들어서 거기에 복붙합니다.
	하단코드는 복붙해놓은 파일과 연결시키는 부분! -->
	<%@include file="/resources/includes/nav.jsp" %> 
	<!-- MainContents -->
	<div id="maincontents" style="margin-top:15px;">
		<!-- PagePath -->
			<div>
				<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
					<ol class="breadcrumb">
						<li class="breadcrumb-item"><a href="#">Home</a></li>
						<li class="breadcrumb-item"><a href="#">Board</a></li>
						<li class="breadcrumb-item active" aria-current="page">Post</li>
					</ol>
				</nav>
			</div>
			<h1>글쓰기</h1>
			<form action="/Board/post.do" method="post" enctype="multipart/form-data">
				<input name="title" class="form-control mb-3 w-50" placeholder="Title">
				<textarea name="content" class="form-control mb-3 w-50" style="height:500px;"></textarea>
				<input type="password" name="pwd" class="form-control mb-3 w-50" placeholder="Enter password">
				<input type="file" name="files" class="form-control mb-3 w-50" multiple>
				<input type="submit" value="글쓰기" class="btn btn-primary">
				<input type="hidden" name="flag" value="true">
				<a href="#" class="btn btn-primary">처음으로</a>
			</form>
	</div>
	<!-- Footer -->
	
	
	</div>



</body>
</html>