<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<%@include file="/resources/includes/link.jsp"%>
<link rel="stylesheet" href="resources/css/common.css">

</head>
<body>
	<%
	String MSG = (String) request.getAttribute("MSG");
	if (MSG != null) {
	%>
	<script>
			alert('<%=MSG%>
		');
	</script>
	<%
	session.setAttribute("MSG", null);
	}
	%>

	<div class="container-md" id=wrapper style="margin: 100px auto;">
		<!-- TopMenu -->
		<%@include file="/resources/includes/topmenu.jsp"%>

		<!-- NAV -->
		<%@include file="/resources/includes/nav.jsp"%>

		<!-- MainContents -->
		<div id=maincontents style="margin-top: 15px;">
			<!-- PagePath -->
			<div>
				<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
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
			BoardDTO dto = (BoardDTO) request.getAttribute("dto");
			String nowPage = (String) request.getAttribute("nowPage");

			String[] filelist = null;
			String[] filesize = null;
			if (dto.getFilename() != null) {
				filelist = dto.getFilename().split(";");
				filesize = dto.getFilesize().split(";");
			}

			//시작번호 계산
			int np = Integer.parseInt(nowPage);
			int numPerPage = 10;
			int start = (np * numPerPage) - numPerPage + 1;

			//끝번호 계산 
			int end = np * numPerPage;
			%>
			<form action="" method="post">
				<input id="title" class="form-control mb-3 w-50"
					value="<%=dto.getTitle()%>"> <input name="writer"
					class="form-control mb-3 w-50" value="<%=dto.getWriter()%>"
					disabled>
				<!-- Writer : 작성자 // disabled : 작성자 이름은 수정하지 못하도록 비활성화 시키는 부분!!!!-->


				<textarea id="content" class="form-control mb-3 w-50"
					style="height: 500px;"><%=dto.getContent()%></textarea>


				<a href="#" class="btn btn-primary" data-bs-toggle="modal"
					data-bs-target="#staticBackdrop2">글수정</a> <a
					href="/Board/list.do?nowPage=<%=nowPage%>&start=<%=start%>&end=<%=end%>"
					class="btn btn-primary">리스트</a> <a href="#" class="btn btn-primary"
					data-bs-toggle="modal" data-bs-target="#staticBackdrop3">글삭제</a>
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
							<!-- 첨부파일  -->
							<%@page import="java.net.URLEncoder"%>
							<%
							if (filelist != null) {
								for (int i = 0; i < filelist.length; i++) {

									String tmpfilename = filelist[i].substring(0, filelist[i].lastIndexOf("_"));
									tmpfilename += filelist[i].substring(filelist[i].lastIndexOf("."), filelist[i].length());

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

							<a id="downall" class="btn btn-primary" href="#">모두받기(NoZIP)</a>
							<a class="btn btn-primary" href="/Board/downloadAll.do">모두받기(ZIP)</a>

							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">닫기</button>
						</div>


						<!-- 다중파일 무압축 받기 -->
						<form name=multiform>
							<%
							for (int i = 0; i < filelist.length; i++) {
							%>
							<input type=hidden name=file value=<%=filelist[i]%>>
							<%
							}
							%>
						</form>

						<script>
							$(document)
									.ready(
											function() {

												form = document.multiform;
												var iFrameCnt = 0; //프레임 개수확인 ,프레임 이름 지정

												$('#downall')
														.click(
																function() { //다운로드 이미지 실행

																	for (i = 0; i < form.childElementCount; i++) {
																		fileName = form[i].value;
																		var url = "/Board/download.do?filename="
																				+ fileName;
																		fnCreateIframe(iFrameCnt); // 보이지 않는 iframe 생성, name는 숫자로  
																		$(
																				"iframe[name="
																						+ iFrameCnt
																						+ "]")
																				.attr(
																						"src",
																						url);
																		iFrameCnt++;
																		fnSleep(1000);
																	}
																});
												fnCreateIframe = function(name) {

													var frm = $('<iframe name="' + name + '" style="display: none;"></iframe>');
													frm.appendTo("body");
												}
												fnSleep = function(delay) {

													var start = new Date()
															.getTime();
													while (start + delay > new Date()
															.getTime())
														;

												};

											});
						</script>
					</div>
				</div>
			</div>
		</div>

		<!-- Footer -->
	</div>
	<!-- 글수정 모달 -->
	<!-- Modal -->
	<div class="modal fade" id="staticBackdrop2" data-bs-backdrop="static"
		data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="staticBackdropLabel">패스워드 확인</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<form action="/Board/update.do" method="post" name="updatefrm">
					<!-- method="post" : 를 method="get" 으로 바꿔주면 상단링크에 글수정을 했을때 수정된 내역이 나온다!!!! -->
					<div class="modal-body">
						글 수정을 하시겠습니까 ? <input type=password class="form-control" name=pwd
							placeholder="INSERT PASSWORD"> <input type=hidden
							name=title> <input type=hidden name=content> <input
							type=hidden name=nowPage value=<%=nowPage%>>
					</div>
					<div class="modal-footer">
						<!-- 글제목과 내용 수정을 하기위해서 버튼을 눌렀을때 바로 서밋이 되는게 아니라 같이 전달되게 해야해서 이벤트 처리를 한다. -->
						<button type="button" class="btn btn-primary" id=updatebtn>수정
							요청</button>
						<!-- 이부분은 나중에 submit으로 처리할것!!! -->
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">닫기</button>
					</div>
				</form>

				<script>
					$(document).ready(function() {

						$("#updatebtn").on("click", function() { //update버튼을 눌렀을때 실행될수있게한다.

							form = document.updatefrm;
							form[1].value = $("#title").val();
							form[2].value = $("#content").val();
							form.submit();

						})

					})
				</script>

			</div>
		</div>
	</div>
	<!-- 글삭제 Modal!!  -->
	<div class="modal fade" id="staticBackdrop3" data-bs-backdrop="static"
		data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="staticBackdropLabel">패스워드 확인</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<form action="/Board/delete.do" method="post" name="deletefrm">
					<!-- method="post" : 를 method="get" 으로 바꿔주면 상단링크에 글수정을 했을때 수정된 내역이 나온다!!!! -->
					<div class="modal-body">
						글을 삭제 하시겠습니까 ? 
						<input type=password class="form-control" name=pwd placeholder="INSERT PASSWORD">
						<input type=hidden name=nowPage value=<%=nowPage%>>
					</div>
					<div class="modal-footer">
						<!-- 글제목과 내용 수정을 하기위해서 버튼을 눌렀을때 바로 서밋이 되는게 아니라 같이 전달되게 해야해서 이벤트 처리를 한다. -->
						<button type="submit" class="btn btn-primary" id=deletebtn>삭제요청</button>
						<!-- 이부분은 나중에 submit으로 처리할것!!! -->
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
					</div>
				</form>
			</div>
		</div>
	</div>


</body>
</html>