<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- include경로를 넣고 file 부분을 최상위로 잡아줍니다."/"이거 앞에 써주면됩니다. -->
<%@include file="/resources/includes/link.jsp" %>
<link rel="stylesheet" href="resources/css/common.css">

</head>
<body>

	<div class="container-md" style="width:50%; margin:200px auto;text-align:center;">
	<h1 class="mb-5">회원가입</h1>
		<form action="/MemberJoin.do" method="post">
			<input type="email" name="email" placeholder="example@example.com" class="form-control m-2" >
			<input type="password" name="pwd" placeholder="Enter password" class="form-control m-2">
			<input name="addr1" placeholder="Emter Address1" class="form-control m-2">
			<input name="addr2" placeholder="Enter Address2" class="form-control m-2">
			<input type="submit" value="가입하기" class="btn btn-primary w-100 m-2">
			<input type="reset" value="RESET" class="btn btn-primary w-100 m-2">
			<a href="/" class="btn btn-primary w-100 m-2">이전으로</a>
			<input type="hidden" name="flag" value="true">
		</form>
	
	</div>


</body>
</html>