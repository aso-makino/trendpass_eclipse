<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>スポット投稿テスト用</h1>
<form action="InsertSpot" method="post"
modelAttribute="fileUploadForm"  enctype=multipart/form-data>
  <p>画像</p>
  <input type="file" name="image" accept="image/*" required>
  <input type="submit" value="送信">
</form>

<h1>デプロイ確認12/5-17:30</h1>

<h1>口コミ投稿テスト用</h1>
<form action="InsertReviewServlet" method="POST">
	タイトル:<input type="text" name = "title" ><br>
	評価:
	<label><input type="radio" name="evaluation" value="1">☆</label>
	<label><input type="radio" name="evaluation" value="2">☆☆</label>
	<label><input type="radio" name="evaluation" value="3">☆☆☆</label>
	<label><input type="radio" name="evaluation" value="4">☆☆☆☆</label>
	<label><input type="radio" name="evaluation" value="5">☆☆☆☆☆</label>
	<br>
	口コミ:<br>
	<textarea name="review2" rows="5" cols="40"></textarea>
	<br>
	<input type="submit" value="送信"><input type="reset" value="リセット">
	</form>
</body>

</html>