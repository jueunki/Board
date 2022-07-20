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
	<div class="container-md" id="wrapper" style="width:80%; margin:100px auto">
	<!-- TopMenu -->
	<%@include file="/resources/includes/topmenu.jsp" %>	
	<!-- NAV -->
<!-- 	부트스트랩에서 코드를 가져와서 인클루드 폴더에 네비파일 만들어서 거기에 복붙합니다.
	하단코드는 복붙해놓은 파일과 연결시키는 부분! -->
	<%@include file="/resources/includes/nav.jsp" %> 
	<!-- MainContents -->
	<div id="maincontents" style="border 1px solid gray; margin-top:15px;">
	
	<form action="/MemberUpdate.do" method="post">
		<table class="table w-75 table-striped" style="margin:100px auto">
			<tr>
				<td><input type="password" name="pwd" class="form-control"></td>
			</tr>
			<tr>
				<td><input type="submit" value='확인' class="btn btn-primary"></td>
			</tr>
		</table>
		<input type="hidden" name="flag" value="true">
		<input type="hidden" name="addr1" value=<%=request.getParameter("addr1") %>>
		<input type="hidden" name="addr2" value=<%=request.getParameter("addr2") %>>
		<input type="hidden" name="newpwd" value=<%=request.getParameter("pwd") %>>
	</form>
		
	</div>
	<!-- Footer -->
	
	
	</div>



</body>
</html>