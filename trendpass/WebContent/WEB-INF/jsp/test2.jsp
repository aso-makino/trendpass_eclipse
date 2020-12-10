<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>テスト</title>
</head>
<body>
<% boolean isSelect = (Boolean)request.getAttribute("isSelect");%>
<% String error = (String)request.getAttribute("error");%>

<%=error %>

</body>
</html>