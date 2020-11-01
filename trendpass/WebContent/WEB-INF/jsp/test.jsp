<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="InsertSpot" method="post"
modelAttribute="fileUploadForm"  enctype=multipart/form-data>
  <p>画像</p>
  <input type="file" name="image" accept="image/*" required>
  <input type="submit" value="送信">
</form>
</body>
</html>