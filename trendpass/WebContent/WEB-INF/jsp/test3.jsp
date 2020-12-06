<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.io.File"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Display S3 image_file</title>
</head>
<body>
<%
File file = (File)request.getAttribute("file");
%>
<img src = file alt="写真"  title="test">

</body>
</html>