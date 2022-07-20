<%@ page language="java" contentType="text/html; charset=UTF-8"
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
	<div class="container-md" id="wrapper"
		style="width: 80%; margin: 100px auto">
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
				<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
					<ol class="breadcrumb">
						<li class="breadcrumb-item"><a href="#">Home</a></li>
						<li class="breadcrumb-item active" aria-current="page">Board</li>
					</ol>
				</nav>
			</div>

			<h1>자유 게시판</h1>

			<%
			int totalcount = 0; //전체 게시물 수
			int numPerPage = 10; //페이지당 표시할 게시물 수

			int totalPage = 0; //전체 페이지 수
			int nowPage = 1; //현재 페이지 번호

			int start = 1; //게시물을 읽을때 사용되는 시작 값(전달할 인자)
			int end = 10; //게시물을 읽을때 사용되는 끝 값(전달할 인자)

			// 페이징 처리를 위한 변수!! = controller로 전달할것이다!!!!
			int pagePerBlock = 15;//블럭당 표시할 페이지 수 - 보여지는 블럭의 수를 몇개로 할지 설정하는것
			int totalBlock = 0; //전체 페이징 블럭 수 - 전체 페이지를 기준으로 블럭전체를 말함
			int nowBlock = 1; //현재 페이징 블럭 수 -
			%>

			<%
			//상단의 현재 페이지 번호 변경을 위한 처리!
			if(!request.getParameter("nowPage").equals("null"))
				{
				nowPage = Integer.parseInt(request.getParameter("nowPage"));
					
			/* 컨트롤러에 처음 접속했을때는 나우 페이지가 컨트롤러로 전달될게 없기째문에 값이 null로 나온다
			   */
				}
			%>

			<%
			totalcount = (int) request.getAttribute("tcnt"); //전체 게시물 수 받기
			totalPage = (int) Math.ceil(totalcount / numPerPage); //전체 페이지 수 계산

			//전체 블럭수 계산
			totalBlock = (int) Math.ceil((double) totalPage / pagePerBlock); //더블형으로 형변환을 했는데, 소수점 이하로 떨어지는것을 잡아줌.
			//현재 블럭숫자 계산
			nowBlock = (int) Math.ceil((double) nowPage / pagePerBlock); // Math.ceil : pagePerBlock을 기준으로 페이지를 나누어서 현재페이지를 찾는다.
			//토탈건수를 받는 이유 : 현재 페이지를 찾기위해서!!
			// Math.ceil : 500건이상 즉 501건이 되어버리면 소수점이 되어버릴수 있어서 그걸 잡아주는 인자!
			// (무조건 올림으로 계산하여 페이지를 하나 더 만들어준다!50page에서 한건이 더있으니까51page가 되도록 만들어줌!)
			%>
			<script>
				/* 페이징 처리함수 : 페이지 번호를 받아 해당 페이지를 표시,페이지 넘어가게하는 스크립트 방식!! */
				function paging(pageNum) {
					form = document.readFrm;
					form.nowPage.value = pageNum;
					var numPerPage = <%=numPerPage%>
					form.start.value = (pageNum * numPerPage) - numPerPage + 1; /* 시작 값과 */
					form.end.value = (pageNum * numPerPage); /* 끝 값을 */
					form.action = "/Board/list.do"; /* /Board/list.do : 여기로 start와 end값을 전달하게 될것이다 */
					form.submit();

				}
				/* 블럭처리 함수 : 이전/이후 버튼 누를때 이전블럭/다음블럭으로 이동 */
				function block(value) // block(value) : 블록 안의 value는 현재 블럭에서 -1(이전으로)하거나 +1(다음으로)한것이다.
				//  마지막에 value값으로 들어간다. 
				{
					form = document.readFrm;
					//이전 이후로 이동처리될때 
					//블럭값으로 이동될 첫번째 페이지 계산
					StartPage =<%=pagePerBlock%> * (value - 1) + 1;
					numPerPage =<%=numPerPage%>;

					form.nowPage.value = StartPage;
					/* 데이터베이스에서 데이터로 가져올수있는 DAO값 출력하는 부분! */
					form.start.value = (StartPage * numPerPage) - numPerPage
							+ 1;
					form.end.value = (StartPage * numPerPage);
					form.action = "/Board/list.do";
					form.submit();

				}
				/* 처음으로 이동 처리!! */
				function init()
				{
					form = document.initFrm;
					form.nowPage.value=1;
					form.action="/Board/list.do";
					form.submit();	
				}
				
				/* 게시물 읽기 */
				function read(no)	// no : 게시물 번호! 넘버! 
				{
					form = document.readFrm;
					form.no.value=no;
					form.nowPage.value=<%=nowPage%>;
					form.action="/Board/read.do";
					form.submit();
					
					
					
				}
				
				
			</script>
			
			<form name=initFrm method=get>
				<input type="hidden" name="nowpage">
			</form>
			
			<!-- 페이징 처리 폼 -->
			<form name="readFrm" method="get">
				<!-- 게시물 번호 -->
				<input type="hidden" name="no">
				<!-- DB로부터 읽을 시작 번호 -->
				<input type="hidden" name="start">
				<!-- DB로부터 읽을 끝 번호 -->
				<input type="hidden" name="end">
				<!-- 현재 페이지 번호 -->
				<input type="hidden" name="nowPage">
				<!--  -->
				<!-- <input type="hidden" name=""> -->

			</form>


			<!-- 현재페이지/전체페이지 -->
			<table class="table">
				<tr>
					<td style="border: 0px;"><span style="color: pink;"><%=nowPage%></span>/<%=totalPage %> Page</td>
					<td style="border: 0px; text-align: right;">
						<button class="btn btn-secondary" onclick="init()">처음으로</button>
						<a class="btn btn-secondary" href="/Board/post.do">글쓰기</a>
					</td>
				</tr>
			</table>

			<table class="table">
				<tr>
					<th>No</th>
					<th>TITLE</th>
					<th>WRITER</th>
					<th>DATE</th>
					<th>COUNT</th>
				</tr>

				<%@page import="java.util.*,com.korea.dto.*"%>
				<%
				ArrayList<BoardDTO> list = (ArrayList<BoardDTO>)request.getAttribute("list");
				for (int i = 0; i < list.size(); i++) 
				{
				%>
				<tr>
					<td><%=list.get(i).getNo()%></td>						
					<td><a href="javascript:read('<%=list.get(i).getNo() %>')"><%=list.get(i).getTitle() %></a></td>
					<!-- a태그 안에는 게시물의 번호가 들어갑니다 -->
					<td><%=list.get(i).getWriter()%></td>
					<td><%=list.get(i).getRegdate()%></td>
					<td><%=list.get(i).getCount()%></td>
				</tr>
				<%
				}
				%>
				<!-- list.get(i) : 리스트의 i번째 DTO를 받겠다? -->

				<tr>
					<!-- 페이지네이션 작업!!! -->
					<td colspan=5 style="border-bottom: 0px;">
						<nav aria-label="Page navigation example">
							<ul class="pagination">
								<!-- 이전으로!! -->
								<!-- 첫번째 페이지일때는 이전으로 기능이 필요없다! -->
								<%
								if (nowBlock > 1) // nowBlock : 이전으로 가는 기준점
								{
								%>

								<li class="page-item">
									<a class="page-link" href="javascript:block('<%=nowBlock - 1%>')" aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
									</a>
								</li>
								<%
								}
								%>

								<%
								int pageStart = (nowBlock - 1) * pagePerBlock + 1;
								int pageEnd = ((pageStart + (pagePerBlock - 1)) < totalPage) ? (pageStart + (pagePerBlock - 1)) : totalPage;
								%>
								<!-- <=totalPage)?(pageStart+(pagePerBlock-1)):totalPage; : 마지막블럭의 페이지의 개수가 블럭을 꽉 채우는 정도가 아니라면 마지막 블럭일때 다음으로 버튼을 더이상 넘어가지 않게 해준다! -->
								<!-- 페이지번호! 설정하게되면 구현된 페이지에 페이지번호 버튼들 표시가 나타날것이다-->
								<%
								for (; pageStart <= pageEnd; pageStart++) {
								%>

								<li class="page-item"><a class="page-link"
									href="javascript:paging('<%=pageStart%>')"><%=pageStart%></a></li>

								<%
								}
								%>


								<!-- 다음으로! 언제 표시가되고,안되어야하는지 알아야한다.-->
								<%
								if (totalBlock > nowBlock) {
								%>
								<li class="page-item"><a class="page-link"
									href="javascript:block('<%=nowBlock + 1%>')" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
								</a></li>
								<%
								}
								%>
							</ul>
						</nav>
					</td>
				</tr>
			</table>
			<a href="/Board/post.do">글쓰기</a>
		</div>
		<!-- Footer -->


	</div>



</body>
</html>