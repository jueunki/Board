<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 코드를 가져옵니다. -->
<%@include file="/resources/includes/link.jsp"%>
<link rel="stylesheet" href="resources/css/common.css">

</head>
<body>
	<!-- 메인 만드는 작업 시작! -->
	<div class="container-md" id=wrapper style="margin: 100px auto;">

		<!-- TopMenu -->
		<%@include file="/resources/includes/topmenu.jsp"%>
		<!-- NAV -->
		<!-- 	부트스트랩에서 코드를 가져와서 인클루드 폴더에 네비파일 만들어서 거기에 복붙합니다.
	하단코드는 복붙해놓은 파일과 연결시키는 부분! -->
		<%@include file="/resources/includes/nav.jsp"%>

		<!-- MainContents -->
		<div id="maincontents" style="margin-top: 15px;">

			<!-- PagePath -->
			<div>
				<nav style="-bs-breadcrumb-divider: '&amp;gt;';"
					aria-label="breadcrumb">
					<ol class="breadcrumb">
						<li class="breadcrumb-item"><a href="#">Home</a></li>
						<li class="breadcrumb-item"><a href="#">Board</a></li>
						<li class="breadcrumb-item active" aria-current="page">Read</li>
					</ol>
				</nav>
			</div>
			<h1>글내용</h1>
			<%@page import="com.korea.dto.*"%>
			<%
			BoardDTO dto = (BoardDTO) request.getAttribute("dto"); 	//dto를 꺼내서 읽고있는 게시물을 확인할수 있다.
			String nowPage = (String) request.getAttribute("nowPage");

			String[] filelist = null;
			String[] filesize = null;
			if (dto.getFilename() != null) {
				filelist = dto.getFilename().split(";");
				filesize = dto.getFilesize().split(";");
			}
			System.out.println("FILE :" + filelist);
			//시작번호 계산
			int np = Integer.parseInt(nowPage);
			int numPerPage=10;
			int start=(np*numPerPage)-numPerPage+1;
			//끝번호 계산
			int end=np*numPerPage;
			%>

			<form action="" method="post">
				<input name="title" class="form-control mb-3 w-50"
					value="<%=dto.getTitle()%>"> <input name="writer"
					class="form-control mb-3 w-50" value="<%=dto.getWriter()%>">


				<textarea name="content" class="form-control mb-3 w-50"
					style="height: 500px;">value=<%=dto.getContent()%></textarea>



				<input type="submit" value="글수정" class="btn btn-primary"> <a
					href="/Board/list.do?nowPage=<%=nowPage%>" class="btn btn-primary">리스트</a> <a href="#"
					class="btn btn-primary">글삭제</a>


				<!-- Button trigger modal -->
				<button type="button" class="btn btn-primary" data-bs-toggle="modal"
					data-bs-target="#staticBackdrop">첨부파일 보기</button>
			</form>
			<!-- Modal -->
			<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static"
				data-bs-keyboard="false" tabindex="-1"
				aria-labelledby="staticBackdropLabel" aria-hidden="true">
				<div
					class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="staticBackdropLabel">첨부파일 리스트</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"
								aria-label="Close"></button>
						</div>
						<div class="modal-body">
							<!-- 첨부파일 -->

							<!-- 공백처리작업!!!!!!!!!!!! -->
							<%@page import="java.net.URLEncoder"%>
							<%
							if (filelist != null) {
								for (int i = 0; i < filelist.length; i++) {

									String tmpfilename = filelist[i].substring(0, filelist[i].lastIndexOf("_"));
									tmpfilename += filelist[i].substring(filelist[i].lastIndexOf("."), filelist[i].length());
									//각각의 배열되는 요소마다 인코딩을해서 넣어놓았다. 인코딩된 파일 이름들이 들어가있다.
									filelist[i] = URLEncoder.encode(filelist[i], "utf-8").replaceAll("\\+", "%20");
									out.println("<a href=/Board/download.do?filename=" + filelist[i] + ">" + tmpfilename + "(" + filesize[i]
									+ " byte)</a><br>");
								}
							} else {
								out.println("파일 없음");
							}
							%>
						</div>
						<div class="modal-footer">

							<a id="downall" class="btn btn-secondary" href="#">모두받기(NoZIP)</a>
							<a class="btn btn-secondary" href="/Board/downloadAll.do">모두받기(NoZIP)</a>
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="madal">닫기</button>
						</div>
						<!-- 다중파일 무압축 다운받기!! -->
						<form name="multiform">
						<%
							for(int i=0;i<filelist.length;i++)
							{
							%>
							<input type="hidden" name="file" value=<%=filelist[i] %>>	<!-- filelist[i] : 파일명 -->
							<%
							}
						%>					
						</form>
						
						<script>
						$(document).ready(function(){
						
							form = document.multiform;
							var iFrameCnt = 0;	// 프레임의 개수확인, 프레임 이름을 지정한다.
							
								$('#downall').click(function(){	//다운로드 이미지 실행	//다운로드올을 클릭한다면 실행시킨다는 문장.
									
									for(i=0;i<form.childElementCount;i++)	//자식태그의 개수를 말한다.
									{
										fileName = form[i].value;
										var url = "/Board/download.do?filename="+fileName;
										fnCreateIframe(iFrameCnt); //보이지않는 iframe생성, name은 숫자로 //숨겨진 프레임을 만들어서 처리할것이다.
										$("iframe[name=" + iFrameCnt + "]").attr("src", url);	//요청을 보내는 부분! //만들어진 프레임을 찾아서 붙인다는 문장.
										iFrameCnt++;
										fnSleep(1000); //시간차를 주는 작업. 1000이면 1초!
									}
								});
								
								fnCreateIframe = function (name){
								
								var frm = $('<iframe name="' + name + '" style="display: none;"></iframe>')
								frm.appendTo("body");	//프레임을 바디에 붙이는부분!
								}
								
								fnSleep = function (delay){
								
									var start = new Date().getTime();	
									while (start + delay > new Date().getTime());	//현재시간보다 크다면 반복하겠다는 뜻 //스레드를 둬도 되지만 일이커져서 이대로 감.
									
									};
									
								});
						
						
						</script>
						

					</div>
				</div>
				<!-- Footer -->


			</div>
</body>
</html>