<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%@page import = "java.io.*" %>
<%
   File dir = new File("C://upload//");
   File [] files = dir.listFiles();
   for(int i=0;i<files.length;i++){
      out.println("<a href=/download.do?filename="+files[i].getName().replaceAll(" ","+")+">"+files[i].getName()+"</a><br>");
   }			//a태그는 여기부터 ----------------------------------------------------------여기까지
%>

</body>
</html>